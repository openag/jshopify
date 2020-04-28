package openag.shopify.client.webhook;

import openag.shopify.client.http.PageRequest;
import openag.shopify.domain.Webhook;

import java.util.Map;

public class WebhookListRequest extends PageRequest {

  private String topic;

  public WebhookListRequest topic(Webhook.Topic topic) {
    if (topic != null) {
      this.topic = topic.getTopic();
    }
    return this;
  }

  @Override
  public Map<String, String> params() {
    final Map<String, String> params = super.params();
    if (this.topic != null) {
      params.put("topic", this.topic);
    }
    return params;
  }
}
