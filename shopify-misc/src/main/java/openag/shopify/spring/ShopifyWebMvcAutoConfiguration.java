package openag.shopify.spring;

import openag.shopify.SigningKeyResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class ShopifyWebMvcAutoConfiguration implements WebMvcConfigurer {

  private final SigningKeyResolver signingKeyResolver;

  public ShopifyWebMvcAutoConfiguration(SigningKeyResolver signingKeyResolver) {
    this.signingKeyResolver = signingKeyResolver;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(shopifyPayloadResolver());
  }

  @Bean
  public ShopifyPayloadResolver shopifyPayloadResolver() {
    return new ShopifyPayloadResolver(signingKeyResolver);
  }
}
