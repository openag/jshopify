package openag.shopify.client.webhook;

import com.google.gson.reflect.TypeToken;
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
        .list((gson, json) -> gson.fromJson(json.getAsJsonArray("webhooks"),
            new TypeToken<List<Webhook>>() {
            }.getType()));
  }

  @Override
  public Webhook createWebhook(Webhook webhook) {
    //todo: implement POST
    return null;
  }
}
