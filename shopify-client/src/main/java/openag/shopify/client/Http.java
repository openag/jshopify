package openag.shopify.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import openag.shopify.ShopifyUtils;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Http {
  private final HttpClient http;
  private final String baseUrl;

  private final Gson gson = ShopifyUtils.gson;

  private final AtomicInteger availableSlots = new AtomicInteger(40);
  private volatile long slotLastUpdateTimestamp;

  /**
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
   * Initiates HTTP GET exchange on the specified path
   */
  public Exchange get(String path) {
    return new Exchange(path, Method.GET);
  }

  private HttpResponse<String> execute(HttpRequest request) {
    try {
      acquireSlot();
      final HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
      response.headers().firstValue("x-shopify-shop-api-call-limit").ifPresent(this::updateSlot);
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
    this.availableSlots.set(Integer.parseInt(split[1]) - Integer.parseInt(split[0]));
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

    /**
     * todo:
     */
    public <T> List<T> list(BiFunction<Gson, JsonObject, List<T>> extractor) {
      final HttpRequest httpRequest = request();
      final HttpResponse<String> response = execute(httpRequest);
//    todo: check response codes
      return extractor.apply(gson, gson.fromJson(response.body(), JsonObject.class));
    }


    public <T> Optional<T> getOne(BiFunction<Gson, JsonObject, T> extractor) {
      final HttpRequest httpRequest = request();
      final HttpResponse<String> response = execute(httpRequest);
      if (response.statusCode() == 404) {
        return Optional.empty();
      }
      //todo check other response codes
      return Optional.of(extractor.apply(gson, gson.fromJson(response.body(), JsonObject.class)));
    }


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
      return HttpRequest.newBuilder()
          .uri(absolute(sb.toString()))
          .method(this.method.name(), HttpRequest.BodyPublishers.noBody())
          .build();
    }

    private void ensureParams() {
      if (this.params == null) {
        this.params = new HashMap<>();
      }
    }
  }

  public enum Method {
    GET
  }
}
