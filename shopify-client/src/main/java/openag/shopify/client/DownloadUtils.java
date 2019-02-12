package openag.shopify.client;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


class DownloadUtils {

  private final HttpClient http;

  public DownloadUtils() {
    http = HttpClient.newBuilder().build();
  }

  FileRef download(String url) {
    try {
      final HttpRequest request = HttpRequest.newBuilder()
          .GET()
          .uri(new URI(url)).build();
      final HttpResponse<InputStream> response = http.send(request, HttpResponse.BodyHandlers.ofInputStream());

      if (response.statusCode() >= 400) {
        throw new ShopifyClientException(response.statusCode());
      }

      return new FileRef(
          response.headers().firstValue("content-type").orElse(""),
          Long.parseLong(response.headers().firstValue("content-length").orElse("-1")),
          response.body());
    } catch (Exception e) {
      throw new ShopifyClientException(e);
    }
  }
}
