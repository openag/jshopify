package openag.shopify.client.http;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.function.Consumer;

public class HttpFactory {

  public static Http newHttp(UrlBuilder urlBuilder, Consumer<HttpRequest.Builder> authenticator) {
    final HttpClient httpClient = HttpClient.newBuilder().build();
    return new NativeHttp(httpClient, urlBuilder, authenticator);
  }
}
