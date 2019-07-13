package openag.shopify.client.webhook;

import openag.shopify.client.Http;
import openag.shopify.domain.Webhook;

import java.util.List;

public class WebhookClientImpl implements WebhookClient {

  private final Http http;

  public WebhookClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public List<Webhook> getWebhooks(WebhookListRequest request) {
    return http.get("/webhooks.json")
        .queryParams(request.params())
        .list("webhooks", Webhook.class);
  }

  @Override
  public Webhook createWebhook(Webhook webhook) {
    return http.post("/webhooks.json")
        .body("webhook", webhook)
        .getOne("webhook", Webhook.class);
  }

  @Override
  public void deleteWebhook(long id) {
    http.delete("/webhooks/" + id + ".json").execute();
  }
}
