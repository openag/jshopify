package openag.shopify.spring;

import openag.shopify.SigningKeyResolver;
import openag.shopify.StaticSigningKeyResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(ShopifyProperties.class)
@Configuration
public class ShopifyAutoConfiguration {

  private final ShopifyProperties properties;

  ShopifyAutoConfiguration(ShopifyProperties properties) {
    this.properties = properties;
  }


  @Bean
  @ConditionalOnMissingBean
  public SigningKeyResolver signingKeyResolver() {
    return new StaticSigningKeyResolver(properties.getWebhookSignKey());
  }

}
