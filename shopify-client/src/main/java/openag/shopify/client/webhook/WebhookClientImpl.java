package openag.shopify.client.webhook;

import openag.shopify.client.http.Http;
import openag.shopify.domain.Webhook;

import java.util.List;

public class WebhookClientImpl implements WebhookClient {

  private final Http http;

  public WebhookClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public List<Webhook> getWebhooks(WebhookListRequest request) {
    return http.list(e -> e.path("/webhooks.json").queryParams(request.params()), Webhook.class);
  }

  @Override
  public Webhook createWebhook(Webhook webhook) {
    return http.exchange(e -> e.path("/webhooks.json").body("webhook", webhook), Webhook.class);
  }

  @Override
  public void deleteWebhook(long id) {
    http.delete(e -> e.path("/webhooks/" + id + ".json"));
  }
}
