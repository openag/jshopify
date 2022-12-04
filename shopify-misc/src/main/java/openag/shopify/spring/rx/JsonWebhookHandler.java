package openag.shopify.spring.rx;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

/**
 * Reactive version of webhook json payload handler
 */
public interface JsonWebhookHandler {

  /**
   * Json Webhook handler callback
   *
   * @param json    webhook json payload
   * @param headers all webhook http headers
   * @return empty mono to indicate completion of handling
   */
  Mono<Void> handle(JsonNode json, ServerRequest.Headers headers);

}
