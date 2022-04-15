package openag.shopify;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;

/**
 * Set of common Shopify utility methods
 */
public class ShopifyUtils {

  /**
   * Pre-configured {@link Gson} instance suitable for Shopify-formatted json
   */
  public static final Gson gson = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
      .create();

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
