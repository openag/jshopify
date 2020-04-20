package openag.shopify.samples;

import openag.shopify.Constants;
import openag.shopify.domain.Webhook;
import openag.shopify.webhooks.ShopifyJsonWebhook;
import openag.shopify.webhooks.ShopifyJsonWebhookHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ShopifyJsonWebhook webhook() {
    return new ShopifyJsonWebhook(shopifyJsonWebhookHandler());
  }

  @Bean
  public ShopifyJsonWebhookHandler shopifyJsonWebhookHandler() {
    return (json, headers) -> {
      final String domain = headers.getFirst(Constants.HTTP_HEADER_SHOPIFY_SHOP_DOMAIN);
      final Webhook.Topic topic = Webhook.Topic.parse(headers.getFirst(Constants.HTTP_HEADER_SHOPIFY_TOPIC));

      System.out.println("Received webhook call from " + domain + " for '" + topic + "':");
      System.out.println(json);
    };
  }
}
