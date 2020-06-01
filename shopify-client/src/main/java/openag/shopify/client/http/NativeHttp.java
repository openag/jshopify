package openag.shopify.client.http;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import openag.shopify.client.ShopifyClientException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.net.http.HttpResponse.BodyHandlers.ofInputStream;

/**
 * Simple wrapper around java native HTTP client
 */
class NativeHttp implements Http {
  private final HttpClient http;
  private final UrlBuilder urlBuilder;
  private final Consumer<HttpRequest.Builder> authenticator;

  private final RateLimiter rateLimiter = new RateLimiter();

  NativeHttp(HttpClient http,
             UrlBuilder urlBuilder,
             Consumer<HttpRequest.Builder> authenticator) {
    this.http = http;
    this.urlBuilder = urlBuilder;
    this.authenticator = authenticator;
  }

  @Override
  public <E> E exchange(Request rq, ResponseType.Obj<E> rt) {
    final HttpResponse<InputStream> response = executeRequest(rq);
    if (response.statusCode() == 404) {
      return null;
    }
    return unwrap(response, rt);
  }

  @Override
  public <E> List<E> exchange(Request rq, ResponseType.Arr<E> rt) {
    final HttpResponse<InputStream> response = executeRequest(rq);
    if (response.statusCode() == 404) {
      return null;
    }
    return unwrap(response, rt);
  }

  @Override
  public void exchange(Request rq) {
    executeRequest(rq); //todo: handle error response?
  }

  private static <E> List<E> unwrap(HttpResponse<InputStream> response, ResponseType.Arr<E> rt) {
    return
        withJsonExtractor(response.body(), rt, json -> {
          final boolean convert = !JsonObject.class.isAssignableFrom(rt.getElementType());
          return
              StreamSupport.stream(((JsonArray) json).spliterator(), false)
                  .map(jsonElement -> convert ? gson.fromJson(jsonElement, rt.getElementType()) : (E) jsonElement)
                  .collect(Collectors.toList());
        });
  }

  private <E> E unwrap(HttpResponse<InputStream> response, ResponseType.Obj<E> rt) {
    return withJsonExtractor(response.body(), rt, json -> {
      if (JsonObject.class.isAssignableFrom(rt.getElementType())) {
        return rt.getElementType().cast(json);
      }
      return gson.fromJson(json, rt.getElementType());
    });
  }

  private static <R> R withJsonExtractor(InputStream body, ResponseType<?> rt, Function<JsonElement, R> f) {
    try (final InputStreamReader in = new InputStreamReader(body, StandardCharsets.UTF_8)) {
      final JsonObject root = gson.fromJson(in, JsonObject.class);

      final JsonElement unwrapped = rt.isWrapped() ? root.get(rt.getWrappingElementName()) : root;
      return f.apply(unwrapped);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Retrieves all elements from the response list converting each item to the requested type if necessary
   */
  @Override
  public <T> List<T> list(Request rq, ResponseType.Arr<T> rt) {
    Page<T> current = page(rq, rt);

    final List<T> result = new LinkedList<>(current.items());

    while (current.hasNext()) {
      current = next(current);
      result.addAll(current.items());
    }

    return result;
  }

  @Override
  public <T> Iterator<T> iterate(Request rq, ResponseType.Arr<T> rt) {
    final Page<T> first = page(rq, rt);
    if (first.isEmpty()) {
      return Collections.emptyIterator();
    }
    return new PageIterator<>(first);
  }

  private class PageIterator<T> implements Iterator<T> {
    private Page<T> page;
    private Iterator<T> it;

    private PageIterator(Page<T> page) {
      this.page = page;
      this.it = page.iterator();
    }

    @Override
    public boolean hasNext() {
      return it.hasNext() || page.hasNext();
    }

    @Override
    public T next() {
      if (it.hasNext()) {
        return it.next();
      }
      loadNextPage();
      if (!it.hasNext()) {
        throw new NoSuchElementException();
      }
      return it.next();
    }

    private void loadNextPage() {
      this.page = NativeHttp.this.next(this.page);
      this.it = this.page.iterator();
    }
  }

  /**
   * Loads the first page of items for the current request. Use {@link #next(Page)} and {@link #previous(Page)} to
   * navigate the pages further
   */
  public <E> Page<E> page(Request rq, ResponseType.Arr<E> rt) {
    final HttpResponse<InputStream> response = executeRequest(rq);
    return toPage(response, rt);
  }

  /**
   * Load next page for the provided page
   */
  public <T> Page<T> next(Page<T> current) {
    if (!current.hasNext()) {
      return Page.empty();
    }
    final HttpRequest.Builder builder = newRequestBuilder(Method.GET).uri(toUri(current.getNextUrl()));
    final HttpResponse<InputStream> response = doExecuteRequest(builder.build());
    return toPage(response, current.getRt());
  }

  /**
   * Load previous page for the provided page
   */
  public <T> Page<T> previous(Page<T> current) {
    if (!current.hasPrevious()) {
      return Page.empty();
    }
    final HttpRequest.Builder builder = newRequestBuilder(Method.GET).uri(toUri(current.getPrevUrl()));
    final HttpResponse<InputStream> response = doExecuteRequest(builder.build());
    return toPage(response, current.getRt());
  }

  private static <E> Page<E> toPage(HttpResponse<InputStream> response, ResponseType.Arr<E> rt) {
    final List<E> list = unwrap(response, rt);

    final Map<String, String> links = response.headers().allValues("Link").stream()
        .flatMap(s -> Arrays.stream(s.split(",")))
        .flatMap(s -> LinkHeader.parseAll(s).stream())
        .collect(Collectors.toMap(LinkHeader::getRel, LinkHeader::getUrl));

    return new Page<>(links.get("next"), links.get("previous"), list, rt);
  }

  private HttpRequest.Builder newRequestBuilder(Method method) {
    return newRequestBuilder(method, noBody());
  }

  private HttpRequest.Builder newRequestBuilder(Method method, HttpRequest.BodyPublisher bodyPublisher) {
    final HttpRequest.Builder builder = HttpRequest.newBuilder().method(method.name(), bodyPublisher);
    if (authenticator != null) {
      authenticator.accept(builder);
    }
    return builder;
  }


  /**
   * Main HTTP Request execution method, all requests pass through it
   */
  private HttpResponse<InputStream> executeRequest(Request ex) {
    final HttpRequest request = buildDefaultRequest(ex);
    return doExecuteRequest(request);
  }

  /**
   * Creates new {@link HttpRequest} instance using all provided values
   */
  private HttpRequest buildDefaultRequest(Request ex) {
    final String url = urlBuilder.url(ex.getPath(),
        ex.getPathVariables() != null ? ex.getPathVariables() : Collections.emptyList(),
        ex.getQueryParams() != null ? ex.getQueryParams() : Collections.emptyMap());

    final HttpRequest.BodyPublisher bodyPublisher = ex.bodyPublisher();

    final HttpRequest.Builder builder = newRequestBuilder(ex.getMethod(), bodyPublisher)
        .uri(toUri(url));

    if (ex.getMethod() == Method.POST) {
      builder.header("Content-Type", ex.getContentType().getValue());
    }

    return builder.build();
  }

  private HttpResponse<InputStream> doExecuteRequest(HttpRequest request) {
    rateLimiter.acquireExecutionSlot(); //this call will block if no execution slots available atm

    try {
      final HttpResponse<InputStream> response = http.send(request, ofInputStream());

      final HttpHeaders headers = response.headers();

      /* Update call rate parameters if provided */
      headers.firstValue("x-shopify-shop-api-call-limit").ifPresent(header -> {
        final String[] split = header.split("/");  // 1/40
        rateLimiter.updateRates(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
      });

      final int statusCode = response.statusCode();

      /* Too Many Requests */
      if (statusCode == 429) {
        final double delaySeconds = headers.firstValue("Retry-After")
            .map(Double::parseDouble)
            .orElse(1.0); //todo: random delay here

        rateLimiter.waitFor(delaySeconds);
        return doExecuteRequest(request);
      }

      return response;
    } catch (InterruptedException | IOException e) {
      throw new ShopifyClientException(e);
    }
  }

  private static URI toUri(String url) {
    try {
      return new URI(url);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

}
