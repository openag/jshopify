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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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
   * todo:
   */
  public <T> Optional<T> getOne(Exchange<T> exchange) {
    final URI uri = exchange.absoluteUrl(this.baseUrl);
    final HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).GET().build();
    final HttpResponse<String> response = execute(httpRequest);
    if (response.statusCode() == 404) {
      return Optional.empty();
    }
    //todo check other response codes
    return Optional.of(exchange.getExtractor().apply(gson, gson.fromJson(response.body(), JsonObject.class)));
  }


  public <T> List<T> getList(Exchange<List<T>> exchange) {
//    final HttpRequest httpRequest = HttpRequest.newBuilder().uri(absolute(path)).GET().build();
//    final HttpResponse<String> response = execute(httpRequest);
//    todo: check response codes
//    return exchange.getExtractor().apply(gson, gson.fromJson(response.body(), JsonObject.class));
    return null;
  }


  /**
   * todo:
   */
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
}
