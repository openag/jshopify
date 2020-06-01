package openag.shopify.client.webhook;

import openag.shopify.client.http.Http;
import openag.shopify.client.http.ResponseType;
import openag.shopify.domain.Webhook;

import java.util.List;

import static openag.shopify.client.http.Request.*;

public class WebhookClientImpl implements WebhookClient {

  private final Http http;

  public WebhookClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public List<Webhook> getWebhooks(WebhookListRequest request) {
    return http.list(get("/webhooks.json").queryParams(request.params()),
        ResponseType.wrappedList("webhooks", Webhook.class));
  }

  @Override
  public Webhook createWebhook(Webhook webhook) {
    return http.exchange(post("/webhooks.json").body("webhook", webhook),
        ResponseType.wrappedObject("webhook", Webhook.class));
  }

  @Override
  public void deleteWebhook(long id) {
    http.exchange(delete("/webhooks/" + id + ".json"));
  }
}
