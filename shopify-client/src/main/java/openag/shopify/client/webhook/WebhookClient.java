package openag.shopify.client.webhook;

import openag.shopify.domain.Webhook;

import java.util.List;

public interface WebhookClient {

  /**
   * Get a list of webhooks
   */
  List<Webhook> getWebhooks(WebhookListRequest request);

  /**
   * Create a new webhook subscription by specifying both an address and a topic
   */
  Webhook createWebhook(Webhook webhook);

}
