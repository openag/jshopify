package openag.shopify.client.http;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.function.Consumer;

public class HttpFactory {

  public static Http newHttp(String domain, String apiVersion, Consumer<HttpRequest.Builder> authenticator) {
    final HttpClient httpClient = HttpClient.newBuilder().build();
    final UrlBuilder urlBuilder = new UrlBuilder(domain, apiVersion);
    return new NativeHttp(httpClient, urlBuilder, authenticator);
  }
  
}
