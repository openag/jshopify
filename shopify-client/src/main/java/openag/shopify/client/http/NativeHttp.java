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
import java.util.stream.Collectors;

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
    if (authenticator == null) {
      throw new IllegalArgumentException("Authentication mechanism is not specified!");
    }
    this.http = http;
    this.urlBuilder = urlBuilder;
    this.authenticator = authenticator;
  }

  /**
   * @return single response entity as optional or empty optional if entity is not found
   */
  @Override
  public <T> Optional<T> getOptional(Consumer<Exchange> ec, Class<T> elementType) {
    return Optional.ofNullable(getOne(ec, elementType));
  }

  @Override
  public <T> T getOne(Consumer<Exchange> ec, Class<T> elementType) {
    final HttpResponse<InputStream> response = executeRequest(httpGet(ec));
    if (response.statusCode() == 404) {
      return null;
    }
    return unwrap(response.body(), elementType);
  }

  @Override
  public <T> T exchange(Consumer<Exchange> ec, Class<T> elementType) {
    final HttpResponse<InputStream> response = executeRequest(httpPost(ec));
    return unwrap(response.body(), elementType);
  }

  @Override
  public void delete(Consumer<Exchange> ec) {
    executeRequest(httpDelete(ec));
  }

  /**
   * Retrieves all elements from the response list converting each item to the requested type if necessary
   */
  @Override
  public <T> List<T> list(Consumer<Exchange> ec, Class<T> elementType) {
    Page<T> current = page(ec, elementType);

    final List<T> result = new LinkedList<>(current.items());

    while (current.hasNext()) {
      current = next(current);
      result.addAll(current.items());
    }

    return result;
  }

  @Override
  public <T> Iterator<T> iterate(Consumer<Exchange> ec, Class<T> elementType) {
    final Page<T> first = page(ec, elementType);
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
   *
   * @param elementType expected element type within the result list
   */
  public <T> Page<T> page(Consumer<Exchange> ec, Class<T> elementType) {
    final HttpResponse<InputStream> response = executeRequest(httpGet(ec));
    return toPage(elementType, response);
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
    return toPage(current.getElementType(), response);
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
    return toPage(current.getElementType(), response);
  }

  private static <T> Page<T> toPage(Class<T> elementType, HttpResponse<InputStream> response) {
    final JsonArray arr = unwrap(response.body(), JsonArray.class);

    final boolean isJsonType = isJsonType(elementType);

    final List<T> items = new ArrayList<>(arr.size());
    for (JsonElement json : arr) {
      items.add(isJsonType ? elementType.cast(json) : gson.fromJson(json, elementType));
    }

    // <https://keenwell.myshopify.com/admin/api/2020-04/products.json?limit=50&page_info=eyJkaXJlY3Rpb24iOiJwcmV2IiwibGFzdF9pZCI6NDQ3MTA4MTk1OTQ4NiwibGFzdF92YWx1ZSI6IlwiRVZPTFVUSU9OIFNQSEVSRVwiIEhZRFJPLUFHRSBXRUxMIE1VTFRJRlVOQ1RJT05BTCBDQVJFIn0>; rel="previous", <https://keenwell.myshopify.com/admin/api/2020-04/products.json?limit=50&page_info=eyJkaXJlY3Rpb24iOiJuZXh0IiwibGFzdF9pZCI6MTI1NTU1NzYxNDcsImxhc3RfdmFsdWUiOiJcIlByZW1pZXIgQmFzaWNcIiBDYXJlc3MgRmxvd2VyIFRvbmluZyBVcCBXYXRlciAyMDAgbWwifQ>; rel="next"

    final Map<String, String> links = response.headers().allValues("Link").stream()
        .flatMap(s -> Arrays.stream(s.split(",")))
        .flatMap(s -> LinkHeader.parseAll(s).stream())
        .collect(Collectors.toMap(LinkHeader::getRel, LinkHeader::getUrl));

    return new Page<>(links.get("next"), links.get("previous"), items, elementType);
  }

  private HttpRequest.Builder newRequestBuilder(Method method) {
    return newRequestBuilder(method, noBody());
  }

  private HttpRequest.Builder newRequestBuilder(Method method, HttpRequest.BodyPublisher bodyPublisher) {
    final HttpRequest.Builder builder = HttpRequest.newBuilder().method(method.name(), bodyPublisher);
    authenticator.accept(builder);
    return builder;
  }


  /**
   * Main HTTP Request execution method, all requests pass through it
   */
  private HttpResponse<InputStream> executeRequest(Exchange ex) {
    final HttpRequest request = buildDefaultRequest(ex);
    return doExecuteRequest(request);
  }

  /**
   * Creates new {@link HttpRequest} instance using all provided values
   */
  private HttpRequest buildDefaultRequest(Exchange ex) {
    final String url = urlBuilder.url(ex.getPath(),
        ex.getPathVariables() != null ? ex.getPathVariables() : Collections.emptyList(),
        ex.getQueryParams() != null ? ex.getQueryParams() : Collections.emptyMap());

    final HttpRequest.BodyPublisher bodyPublisher = (ex.getBody() == null) ? noBody()
        : HttpRequest.BodyPublishers.ofString(gson.toJson(ex.getBody()));

    final HttpRequest.Builder builder = newRequestBuilder(ex.getMethod(), bodyPublisher).uri(toUri(url));

    if (ex.getMethod() == Method.POST) {
      builder.header("Content-Type", "application/json");
    }

    return builder.build();
  }

  /**
   * Initiates HTTP GET exchange on the specified path
   */
  private static Exchange httpGet(Consumer<Exchange> ex) {
    return buildExchange((Consumer<Exchange>) ex, Method.GET);
  }

  private static Exchange httpPost(Consumer<Exchange> ex) {
    return buildExchange(ex, Method.POST);
  }

  private static Exchange httpDelete(Consumer<Exchange> ex) {
    return buildExchange(ex, Method.DELETE);
  }

  private static Exchange buildExchange(Consumer<Exchange> ex, Method get) {
    final Exchange exchange = new Exchange(get);
    ex.accept(exchange);
    return exchange;
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
      //    todo: check other response codes

      return response;
    } catch (InterruptedException | IOException e) {
      throw new ShopifyClientException(e);
    }
  }

  /**
   * @return true if provided class can be casted to GSON {@link JsonElement}; false otherwise
   */
  private static <T> boolean isJsonType(Class<T> elementType) {
    return JsonElement.class.isAssignableFrom(elementType);
  }

  /**
   * Converts response body into desired type, fully consuming and closing the provided body stream
   *
   * @param body        response body as stream
   * @param elementType the desired object type to be returned; GSON types could be used too
   */
  private static <T> T unwrap(InputStream body, Class<T> elementType) {
    try (final InputStreamReader in = new InputStreamReader(body, StandardCharsets.UTF_8)) {
      final JsonObject root = gson.fromJson(in, JsonObject.class);

      if (root.size() != 1) {
        throw new RuntimeException("Can't unwrap " + root.size() + " number of elements!");
      }

      final JsonElement unwrapped = root.entrySet().iterator().next().getValue();

      if (JsonElement.class.isAssignableFrom(elementType)) {
        return elementType.cast(unwrapped); //if json object is requested then no need to perform additional json conversion
      }
      return gson.fromJson(unwrapped, elementType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static URI toUri(String url) {
    try {
      return new URI(url);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public enum Method {
    GET, POST, DELETE
  }
}
