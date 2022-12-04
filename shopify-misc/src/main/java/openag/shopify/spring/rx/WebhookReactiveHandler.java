package openag.shopify.spring.rx;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import openag.shopify.HmacHash;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static openag.shopify.Constants.HTTP_HEADER_SHOPIFY_SHOPIFY_HMAC_SHA_256;
import static openag.shopify.Constants.HTTP_HEADER_SHOPIFY_SHOP_DOMAIN;
import static openag.shopify.ShopifyUtils.hmac;

/**
 * Spring reactive web handler for json webhook http call
 */
public class WebhookReactiveHandler {

  private final SigningKeyResolver signingKeyResolver;
  private final JsonWebhookHandler webhookHandler;
  private final ObjectMapper mapper;

  public WebhookReactiveHandler(
      SigningKeyResolver signingKeyResolver,
      JsonWebhookHandler webhookHandler,
      ObjectMapper mapper) {
    this.signingKeyResolver = signingKeyResolver;
    this.webhookHandler = webhookHandler;
    this.mapper = mapper;
  }

  public Mono<ServerResponse> handle(ServerRequest request) {
    final ServerRequest.Headers headers = request.headers();

    final HmacHash hmac = HmacHash.fromBase64(headers.firstHeader(HTTP_HEADER_SHOPIFY_SHOPIFY_HMAC_SHA_256));
    final String domain = headers.firstHeader(HTTP_HEADER_SHOPIFY_SHOP_DOMAIN);

    return
        request
            .bodyToMono(String.class)
            .zipWith(signingKeyResolver.getKey(domain))
            .flatMap(tuple -> {
              final String body = tuple.getT1();
              final String secret = tuple.getT2();
              if (hmac(body, secret).equals(hmac)) {
                return Mono.just(body);
              }
              return Mono.error(
                  new RuntimeException("Unable to verify webhook signature for " + domain + ", secret=" + secret));
            })
            .flatMap(body ->
                toJson(body).
                    flatMap(json ->
                        webhookHandler.handle(json, headers)
                            .then(ServerResponse.ok().build())));
  }

  private Mono<JsonNode> toJson(String body) {
    try {
      return Mono.just(mapper.readTree(body));
    } catch (JsonProcessingException e) {
      return Mono.error(e);
    }
  }
}
