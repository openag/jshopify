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
    return http.get("/admin/webhooks.json")
        .params(request.params())
        .list("webhooks", Webhook.class);
  }

  @Override
  public Webhook createWebhook(Webhook webhook) {
    return http.post("/admin/webhooks.json")
        .body("webhook", webhook)
        .getOne("webhook", Webhook.class);
  }

  @Override
  public void deleteWebhook(long id) {
    http.delete("/admin/webhooks/" + id + ".json").execute();
  }
}
