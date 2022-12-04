package openag.shopify.spring.rx;

import reactor.core.publisher.Mono;

/**
 * Reactive shop domain to signature key resolver
 */
public interface SigningKeyResolver {

  /**
   * Resolves shop domain to key
   *
   * @param domain shopify shop domain
   * @return key wrapped in mono
   */
  Mono<String> getKey(String domain);

}
