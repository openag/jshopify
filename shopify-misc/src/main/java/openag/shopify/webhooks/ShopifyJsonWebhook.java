package openag.shopify.webhooks;

import com.google.gson.JsonObject;
import openag.shopify.spring.ShopifyPayload;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoint for accepting incoming Webhook calls
 */
@RestController
@RequestMapping("/webhook/json")
public class ShopifyJsonWebhook {

  private final ShopifyJsonWebhookHandler handler;

  public ShopifyJsonWebhook(ShopifyJsonWebhookHandler handler) {
    this.handler = handler;
  }

  @PostMapping
  public void onHook(@ShopifyPayload JsonObject json, @RequestHeader HttpHeaders headers) {
    handler.handle(json, headers);
  }
}
