package openag.shopify.client;

import openag.shopify.client.inventory.InventoryClientImpl;
import openag.shopify.client.product.ProductClientImpl;
import openag.shopify.client.webhook.WebhookClientImpl;

public class ShopifyClientFactory {

  /**
   * Creates new {@link ShopifyClient} with provided specification
   */
  public static ShopifyClient newClient(String domain, String apiKey, String password) {
    final Http http = new Http(domain, apiKey, password);
    return new ShopifyClientImpl(
        new ProductClientImpl(http),
        new InventoryClientImpl(http),
        new WebhookClientImpl(http));
  }
}
