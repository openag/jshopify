package openag.shopify.samples.reactive;

import openag.shopify.HmacHash;
import openag.shopify.SigningKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static openag.shopify.Constants.HTTP_HEADER_SHOPIFY_SHOPIFY_HMAC_SHA_256;
import static openag.shopify.Constants.HTTP_HEADER_SHOPIFY_SHOP_DOMAIN;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @RestController
  public static class WebhookCallback {

    private final SigningKeyResolver signingKeyResolver;

    public WebhookCallback(SigningKeyResolver signingKeyResolver) {
      this.signingKeyResolver = signingKeyResolver;
    }

    @PostMapping
    Mono<Void> handle(@RequestBody byte[] body, ServerHttpRequest request) {
      final HttpHeaders headers = request.getHeaders();
      final HmacHash hmac = HmacHash.fromBase64(headers.getFirst(HTTP_HEADER_SHOPIFY_SHOPIFY_HMAC_SHA_256));
      final byte[] secret = signingKeyResolver.getKey(headers.getFirst(HTTP_HEADER_SHOPIFY_SHOP_DOMAIN))
          .getBytes(StandardCharsets.UTF_8);

      if (HmacHash.hmac(body, secret).equals(hmac)) {
        System.out.println(new String(body));
      }
      return Mono.empty();
    }
  }

}
