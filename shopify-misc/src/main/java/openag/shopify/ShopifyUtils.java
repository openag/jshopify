package openag.shopify;

import java.nio.charset.StandardCharsets;

/**
 * Set of common Shopify utility methods
 */
public class ShopifyUtils {

  /**
   * Runs HMAC cryptographic hash function with provided message text. By default, uses 'standard' Shopify HMAC_SHA256
   * algorithm
   *
   * @param message message to be hashed
   * @param key     key to use for hashing
   * @return HEX-encoded HMAC hash
   */
  public static HmacHash hmac(String message, String key) {
    return HmacHash.hmac(
        message.getBytes(StandardCharsets.UTF_8),
        key.getBytes(StandardCharsets.UTF_8));
  }
}
