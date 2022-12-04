package openag.shopify.samples;

import com.fasterxml.jackson.databind.ObjectMapper;
import openag.shopify.spring.rx.Headers;
import openag.shopify.spring.rx.JsonWebhookHandler;
import openag.shopify.spring.rx.SigningKeyResolver;
import openag.shopify.spring.rx.WebhookReactiveHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /**
   * Example of reactive json webhook handler configuration
   */
  @Configuration
  public static class ReactiveConfiguration {

    @Bean
    public SigningKeyResolver signingKeyResolver(Environment environment) {
      return domain -> Mono.justOrEmpty(environment.getProperty("shopify.webhook-sign-key"));
    }

    @Bean
    public JsonWebhookHandler jsonWebhookHandler() {
      return (json, headers) -> {
        final Headers h = new Headers(headers);
        System.out.println(h.shopDomain());
        System.out.println(h.collection());
        System.out.println(h.action());
        System.out.println(headers);
        System.out.println(json);
        return Mono.empty();
      };
    }

    @Bean
    public RouterFunction<ServerResponse> routes(SigningKeyResolver signingKeyResolver,
                                                 JsonWebhookHandler jsonWebhookHandler) {
      final ObjectMapper mapper = new ObjectMapper();

      final WebhookReactiveHandler handler = new WebhookReactiveHandler(signingKeyResolver, jsonWebhookHandler, mapper);

      return RouterFunctions.route()
          .POST("/webhook/json", handler::handle)
          .build();
    }
  }
}
