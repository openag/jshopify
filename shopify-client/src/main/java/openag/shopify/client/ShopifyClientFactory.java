package openag.shopify.client;

import openag.shopify.client.inventory.InventoryClientImpl;
import openag.shopify.client.product.ProductClientImpl;
import openag.shopify.client.saleschannel.SalesChannelClientImpl;
import openag.shopify.client.webhook.WebhookClientImpl;

public class ShopifyClientFactory {

  /**
   * Creates new {@link ShopifyClient} with provided specification
   */
  public static ShopifyClient newClient(String domain, String apiKey, String password) {
    return newClient(new Http(domain, apiKey, password));
  }

  /**
   * todo:
   *
   * @param domain
   * @param accessToken
   * @return
   */
  public static ShopifyClient newClient(String domain, String accessToken) {
    return newClient(new Http(domain, accessToken));
  }

  private static ShopifyClient newClient(Http http) {
    return new ShopifyClientImpl(
        new ProductClientImpl(http),
        new InventoryClientImpl(http),
        new WebhookClientImpl(http),
        new SalesChannelClientImpl(http));
  }

}
