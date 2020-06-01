package openag.shopify.client.webhook;

import openag.shopify.domain.Webhook;

import java.util.HashMap;
import java.util.Map;

public class WebhookListRequest {

  private String topic;

  public WebhookListRequest topic(Webhook.Topic topic) {
    if (topic != null) {
      this.topic = topic.getTopic();
    }
    return this;
  }

  public Map<String, String> params() {
    final Map<String, String> params = new HashMap<>();
    if (this.topic != null) {
      params.put("topic", this.topic);
    }
    return params;
  }
}
