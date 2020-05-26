package openag.shopify.webhooks;

import openag.shopify.events.ShopifyEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Extension of {@link EventProducerWebhookHandler} that publishes events using spring {@link
 * ApplicationEventPublisher}
 */
public class ApplicationEventPublisherWebhookHandler extends EventProducerWebhookHandler {

  private final ApplicationEventPublisher publisher;

  public ApplicationEventPublisherWebhookHandler(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  protected void publishEvent(ShopifyEvent<?> event) {
    this.publisher.publishEvent(event);
  }
}
