package openag.shopify.spring.rx;

import openag.shopify.Constants;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * Wrapper over {@link ServerRequest.Headers} providing some helper methods for shopify-specific headers
 */
public class Headers {

  private final ServerRequest.Headers headers;

  public Headers(ServerRequest.Headers headers) {
    this.headers = headers;
  }

  public String shopDomain() {
    return headers.firstHeader(Constants.HTTP_HEADER_SHOPIFY_SHOP_DOMAIN);
  }

  public String topic() {
    return headers.firstHeader(Constants.HTTP_HEADER_SHOPIFY_TOPIC);
  }

  public String collection() {
    final String topic = topic();
    return topic.substring(0, topic.indexOf('/'));
  }

  public String action() {
    final String topic = topic();
    return topic.substring(topic.indexOf('/') + 1);
  }
}
