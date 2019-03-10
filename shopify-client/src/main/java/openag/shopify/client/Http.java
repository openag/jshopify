package openag.shopify.client;

import com.google.gson.*;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Http {
  private final HttpClient http;
  private final String baseUrl;

  private static final Gson gson = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
      .create();

  private final AtomicInteger availableSlots = new AtomicInteger(40);
  private volatile long slotLastUpdateTimestamp;

  private String accessToken;

  /**
   * Creates new {@link Http} instance with application key/secret authentication scheme (suitable for private
   * applications for example)
   *
   * @param domain   Shopify shop full domain (xxxx.myshopify.com)
   * @param apiKey   shop private app API key
   * @param password shop private app password
   */
  Http(String domain, String apiKey, String password) {
    this.http =
        HttpClient.newBuilder()
            .authenticator(new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(apiKey, password.toCharArray());
              }
            })
            .build();
    this.baseUrl = "https://" + domain;
  }

  /**
   * todo:
   *
   * @param domain
   * @param accessToken
   */
  Http(String domain, String accessToken) {
    this.http = HttpClient.newBuilder().build();
    this.accessToken = accessToken;
    this.baseUrl = "https://" + domain;
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

  private URI absolute(String path) {
    try {
      return new URI(baseUrl + path);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Encapsulation of request-response operation pair
   */
  public class Exchange {

    private final String path;
    private final Method method;

    private Map<String, String> params;
    private JsonObject body;

    private Exchange(String path, Method method) {
      this.path = path;
      this.method = method;
    }

    public Exchange params(Map<String, String> params) {
      ensureParams();
      this.params.putAll(params);
      return this;
    }

    public Exchange param(String name, String value) {
      ensureParams();
      this.params.put(name, value);
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
     * todo:
     */
    private HttpRequest request() {
      final StringBuilder sb = new StringBuilder(path);

      if (method == Method.GET) {
        if (params != null) {
          sb.append("?").append(
              params.entrySet().stream()
                  .map(entry -> entry.getKey() + "=" + entry.getValue())
                  .collect(Collectors.joining("&")));
        }
      }

      HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.noBody();
      if (method == Method.POST) {
        bodyPublisher = HttpRequest.BodyPublishers.ofString(gson.toJson(body));
      }

      final HttpRequest.Builder builder = HttpRequest.newBuilder()
          .uri(absolute(sb.toString()))
          .method(this.method.name(), bodyPublisher);

      if (method == Method.POST) {
        builder.header("Content-Type", "application/json");
      }

      if (accessToken != null) {
        builder.header("X-Shopify-Access-Token", accessToken);
      }

      return builder.build();
    }

    /**
     * todo:
     */
    private HttpResponse<String> executeRequest() {
      final HttpRequest request = request();

      try {
        acquireSlot();
        final HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        response.headers().firstValue("x-shopify-shop-api-call-limit").ifPresent(this::updateSlot);
        //    todo: check response codes
        return response;
      } catch (InterruptedException | IOException e) {
        throw new ShopifyClientException(e);
      }
    }

    //todo: not safe implementation for now, improve!
    private synchronized void acquireSlot() throws InterruptedException {
      if (availableSlots.get() <= 0) {
        Thread.sleep(1_000); //expect that at least one slot will become available during this second
      } else {
        if (availableSlots.get() > 0) {
          availableSlots.decrementAndGet();
        }
      }
    }

    private void updateSlot(String s) {
      final String[] split = s.split("/");  // 1/40
      availableSlots.set(Integer.parseInt(split[1]) - Integer.parseInt(split[0]));
    }


    private void ensureParams() {
      if (this.params == null) {
        this.params = new HashMap<>();
      }
    }
  }

  public enum Method {
    GET, POST, DELETE
  }
}
