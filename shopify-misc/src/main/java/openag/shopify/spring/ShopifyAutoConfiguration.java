package openag.shopify.spring;

import openag.shopify.SigningKeyResolver;
import openag.shopify.StaticSigningKeyResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableConfigurationProperties(ShopifyProperties.class)
@Configuration
public class ShopifyAutoConfiguration implements WebMvcConfigurer {

  private final ShopifyProperties properties;

  ShopifyAutoConfiguration(ShopifyProperties properties) {
    this.properties = properties;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(shopifyPayloadResolver());
  }

  @Bean
  public ShopifyPayloadResolver shopifyPayloadResolver() {
    return new ShopifyPayloadResolver(signingKeyResolver());
  }

  @Bean
  @ConditionalOnMissingBean
  public SigningKeyResolver signingKeyResolver() {
    return new StaticSigningKeyResolver(properties.getWebhookSignKey());
  }

}
