package openag.shopify.client;

import openag.shopify.domain.Product;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.http.HttpClient;
import java.util.Optional;

public class ShopifyClientImpl implements ShopifyClient {


  private final HttpClient http;


  /**
   * @param domain   Shopify shop full domain (xxxx.myshopify.com)
   * @param apiKey   shop private app API key
   * @param password shop private app password
   */
  public ShopifyClientImpl(String domain, String apiKey, String password) {
    this.http = null;

    HttpClient.newBuilder()
        .authenticator(new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
//            return new PasswordAuthentication("");
            return null;
          }
        });

//    this.restTemplate = new RestTemplateBuilder()
//        .messageConverters(new GsonHttpMessageConverter(ShopifyUtils.gson))
//        .basicAuthorization(apiKey, password)
//        .rootUri("https://" + domain + "")
//        .build();
  }

  @Override
  public Optional<Product> getProduct(long id) {
//    final HttpRequest request = HttpRequest.newBuilder()
//        .uri(new URI("https://postman-echo.com/get"))
//        .GET()
//        .build();
//
//    final HttpResponse<Object> response = HttpClient.newBuilder()
//        .build()
//        .send(request, null);

    return Optional.empty();
  }

//  @Override
//  public List<Collect> findCollectsForCollection(long id) {
//    final JsonObject json = restTemplate.getForObject("/admin/collects.json?collection_id={collection_id}",
//        JsonObject.class, id);
//    if (json == null) {
//      return Collections.emptyList();
//    }
//    return Arrays.asList(ShopifyUtils.gson.fromJson(json.getAsJsonArray("collects"), Collect[].class));
//  }

}
