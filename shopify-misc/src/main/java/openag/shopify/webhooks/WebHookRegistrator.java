package openag.shopify.webhooks;

import openag.shopify.client.webhook.WebhookClient;
import openag.shopify.client.webhook.WebhookListRequest;
import openag.shopify.domain.Webhook;

import java.util.HashMap;
import java.util.Map;

import static openag.shopify.domain.Webhook.Topic.*;

/**
 * todo:
 */
public class WebHookRegistrator {

  private final WebhookClient client;

  /**
   * Topic to URL path mappings
   */
  private static final Map<Webhook.Topic, String> path = new HashMap<>();

  static {
    path.put(product_listings_add, "/webhook/productlisting/create");
    path.put(product_listings_update, "/webhook/productlisting/update");
    path.put(product_listings_remove, "/webhook/productlisting/delete");

    path.put(collection_listings_add, "/webhook/collectionlisting/create");
    path.put(collection_listings_update, "/webhook/collectionlisting/update");
    path.put(collection_listings_remove, "/webhook/collectionlisting/delete");

    path.put(products_create, "/webhook/products/create");
    path.put(products_update, "/webhook/products/update");
    path.put(products_delete, "/webhook/products/delete");
  }

  public WebHookRegistrator(WebhookClient client) {
    this.client = client;
  }

  public void cleanRegister(String baseUrl, Webhook.Topic... topics) {
    for (Webhook.Topic topic : topics) {

      /* delete all existing webhooks */
      client.getWebhooks(new WebhookListRequest().topic(topic)).forEach(webhook ->
          client.deleteWebhook(webhook.getId()));

      final Webhook webhook = new Webhook();
      webhook.setAddress(baseUrl + path.get(topic));
      webhook.setTopic(topic.getTopic());
      client.createWebhook(webhook);
    }
  }
}
