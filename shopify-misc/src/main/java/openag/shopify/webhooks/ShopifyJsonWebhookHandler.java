package openag.shopify.webhooks;

import com.google.gson.JsonObject;
import org.springframework.http.HttpHeaders;

/**
 * Processor for incoming JSON webhook calls
 */
public interface ShopifyJsonWebhookHandler {

  /**
   * Handles the raw json webhook call
   *
   * @param json    webhook payload, raw json
   * @param headers hook call HTTP headers
   */
  void handle(JsonObject json, HttpHeaders headers);
}
