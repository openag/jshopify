package openag.shopify.webhooks;

import com.google.gson.JsonObject;
import openag.shopify.Constants;
import openag.shopify.domain.Webhook;
import openag.shopify.events.ShopifyEvent;
import org.springframework.http.HttpHeaders;

/**
 * Specialization of {@link ShopifyJsonWebhookHandler} that converts raw json even into corresponding event object and
 * fires it via {@link #publishEvent(ShopifyEvent)} call. Subclasses must provide own implementation for {@link
 * #publishEvent(ShopifyEvent)}
 */
public abstract class EventProducerWebhookHandler implements ShopifyJsonWebhookHandler {

  @Override
  public void handle(JsonObject json, HttpHeaders headers) {
    final String domain = headers.getFirst(Constants.HTTP_HEADER_SHOPIFY_SHOP_DOMAIN);
    final Webhook.Topic topic = Webhook.Topic.parse(headers.getFirst(Constants.HTTP_HEADER_SHOPIFY_TOPIC));

    final ShopifyEvent<Object> event = EventsFactory.fromJsonObject(domain, topic, json);

    publishEvent(event);
  }

  protected abstract void publishEvent(ShopifyEvent<?> event);
}
