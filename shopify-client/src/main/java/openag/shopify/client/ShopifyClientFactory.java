package openag.shopify.client;

import openag.shopify.client.product.ProductClientImpl;

public class ShopifyClientFactory {

  /**
   * Creates new {@link ShopifyClient} with provided specification
   */
  public static ShopifyClient newClient(String domain, String apiKey, String password) {
    final Http http = new Http(domain, apiKey, password);
    return new ShopifyClientImpl(new ProductClientImpl(http));
  }
}
