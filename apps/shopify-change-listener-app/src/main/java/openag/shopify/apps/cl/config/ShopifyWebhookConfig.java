package openag.shopify.apps.cl.config;

import openag.shopify.spring.ShopDomainBlockingInterceptor;
import openag.shopify.spring.ValidatingJsonBodyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ShopifyWebhookConfig implements WebMvcConfigurer {

  private final Environment environment;

  public ShopifyWebhookConfig(Environment environment) {
    this.environment = environment;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(validatingJsonBodyResolver());
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(shopDomainBlockingInterceptor()).addPathPatterns("/webhook/**");
  }

  @Bean
  public ValidatingJsonBodyResolver validatingJsonBodyResolver() {
    return new ValidatingJsonBodyResolver(environment.getProperty("shopify.webhook.secret"));
  }

  @Bean
  public ShopDomainBlockingInterceptor shopDomainBlockingInterceptor() {
    return new ShopDomainBlockingInterceptor(environment.getProperty("shopify.domain"));
  }
}
