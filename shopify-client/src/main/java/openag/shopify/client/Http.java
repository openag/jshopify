package openag.shopify.client;

import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

/**
 * Simple wrapper around java native HTTP client
 */
public class Http {
  private final HttpClient http;
  private final ShopifyClientFactory.UrlBuilder urlBuilder;
  private final Consumer<HttpRequest.Builder> authenticator;

  private static final Gson gson = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
      .create();

  private final AtomicInteger rateNow = new AtomicInteger();
  private final AtomicInteger rateMax = new AtomicInteger(40);

  Http(HttpClient http,
       ShopifyClientFactory.UrlBuilder urlBuilder,
       Consumer<HttpRequest.Builder> authenticator) {
    if (authenticator == null) {
      throw new IllegalArgumentException("Authentication mechanism is not specified!");
    }
    this.http = http;
    this.urlBuilder = urlBuilder;
    this.authenticator = authenticator;
  }

  /**
   * Initiates HTTP GET exchange on the specified path
   */
  public Exchange get(String path) {
    return new Exchange(path, Method.GET);
  }

  public Exchange post(String path) {
    return new Exchange(path, Method.POST);
  }

  public Exchange delete(String path) {
    return new Exchange(path, Method.DELETE);
  }

  /**
   * Encapsulation of request-response operation pair
   */
  public class Exchange {

    private final String path;
    private final Method method;

    private List<String> pathVariables;
    private Map<String, String> queryParams;

    private JsonObject body;

    private Exchange(String path, Method method) {
      this.path = path;
      this.method = method;
    }

    public Exchange pathVariable(long value) {
      if (pathVariables == null) {
        pathVariables = new ArrayList<>(2);
      }
      pathVariables.add(String.valueOf(value));
      return this;
    }

    public Exchange queryParams(Map<String, String> params) {
      ensureParams();
      this.queryParams.putAll(params);
      return this;
    }

    public Exchange body(String wrapperElementName, Object body) {
      final JsonObject root = new JsonObject();
      root.add(wrapperElementName, gson.toJsonTree(body));
      this.body = root;
      return this;
    }

    /**
     * todo:
     */
    public void execute() {
      executeRequest();
    }

    /**
     * todo:
     *
     * @param wrapperElementName
     * @param elementType
     * @param <T>
     * @return
     */
    public <T> T getOne(String wrapperElementName, Class<T> elementType) {
      final HttpResponse<String> response = executeRequest();
      if (response.statusCode() == 404) {
        return null; //todo: should throw instead
      }
      return unwrapSingle(response.body(), wrapperElementName, elementType);
    }

    /**
     * todo:
     */
    public <T> Optional<T> getOptional(String wrapperElementName, Class<T> elementType) {
      final HttpResponse<String> response = executeRequest();
      if (response.statusCode() == 404) {
        return Optional.empty();
      }
      return Optional.ofNullable(unwrapSingle(response.body(), wrapperElementName, elementType));
    }

    private <T> T unwrapSingle(String body, String wrapperElementName, Class<T> elementType) {
      final JsonObject root = gson.fromJson(body, JsonObject.class);
      final JsonObject jsonObject = root.getAsJsonObject(wrapperElementName);
      return gson.fromJson(jsonObject, elementType);
    }

    /**
     * todo:
     *
     * @param wrapperElementName
     * @param elementType
     * @param <T>
     * @return
     */
    public <T> List<T> list(String wrapperElementName, Class<T> elementType) {
      final HttpResponse<String> response = executeRequest();
//    todo: check response codes

      final JsonObject body = gson.fromJson(response.body(), JsonObject.class);
      final JsonArray jsonArray = body.getAsJsonArray(wrapperElementName);

      final List<T> result = new ArrayList<>(jsonArray.size());
      jsonArray.forEach(jsonElement -> result.add(gson.fromJson(jsonElement, elementType)));
      return result;
    }

    /**
     * Creates new {@link HttpRequest} instance using all provided values
     */
    private HttpRequest buildRequest() throws URISyntaxException {
      final String url = urlBuilder.url(path,
          pathVariables != null ? pathVariables : Collections.emptyList(),
          queryParams != null ? queryParams : Collections.emptyMap());

      final HttpRequest.BodyPublisher bodyPublisher = (body == null) ? HttpRequest.BodyPublishers.noBody()
          : HttpRequest.BodyPublishers.ofString(gson.toJson(body));

      final HttpRequest.Builder builder = HttpRequest.newBuilder()
          .uri(new URI(url))
          .method(this.method.name(), bodyPublisher);

      if (method == Method.POST) {
        builder.header("Content-Type", "application/json");
      }

      authenticator.accept(builder);

      return builder.build();
    }

    /**
     * Main HTTP Request execution method, all requests pass through it
     */
    private HttpResponse<String> executeRequest() {
      try {
        final HttpRequest request = buildRequest();
        return doExecuteRequest(request);
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
    }

    private HttpResponse<String> doExecuteRequest(HttpRequest request) {
      try {
        if (rateMax.get() - rateNow.get() < 2) {
          Thread.sleep(1_000);
        }

        final HttpResponse<String> response = http.send(request, ofString());

        final HttpHeaders headers = response.headers();

        /* Update call rate parameters if provided */
        headers.firstValue("x-shopify-shop-api-call-limit").ifPresent(header -> {
          final String[] split = header.split("/");  // 1/40
          rateNow.set(Integer.parseInt(split[0]));
          rateMax.set(Integer.parseInt(split[1]));
        });

        final int statusCode = response.statusCode();

        /* Too Many Requests */
        if (statusCode == 429) {
          headers.firstValue("Retry-After").ifPresent(seconds -> {
            try {
              Thread.sleep(((long) Double.parseDouble(seconds)) * 1_000);
              doExecuteRequest(request);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          });
        }

        //    todo: check other response codes

        return response;
      } catch (InterruptedException | IOException e) {
        throw new ShopifyClientException(e);
      }
    }

    private void ensureParams() {
      if (this.queryParams == null) {
        this.queryParams = new HashMap<>();
      }
    }
  }

  public enum Method {
    GET, POST, DELETE
  }
}
