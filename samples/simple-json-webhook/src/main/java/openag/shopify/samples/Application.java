package openag.shopify.samples;

import openag.shopify.events.ProductEvent;
import openag.shopify.webhooks.ApplicationEventPublisherWebhookHandler;
import openag.shopify.webhooks.ShopifyJsonWebhookController;
import openag.shopify.webhooks.ShopifyJsonWebhookHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application implements WebMvcConfigurer {
  private static final Logger log = LoggerFactory.getLogger(Application.class);

  private final ApplicationEventPublisher publisher;

  public Application(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ShopifyJsonWebhookController webhook() {
    return new ShopifyJsonWebhookController(shopifyJsonWebhookHandler());
  }

  @Bean
  public ShopifyJsonWebhookHandler shopifyJsonWebhookHandler() {
    return new ApplicationEventPublisherWebhookHandler(publisher);
  }

  @EventListener
  public void handleProductCreate(ProductEvent.Created event) {
    log.info("Product created in shop '{}': {}", event.getDomain(), event.getProduct());
  }
}
