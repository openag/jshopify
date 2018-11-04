package openag.shopify;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Set of common Shopify utility methods
 */
public class ShopifyUtils {

  private static final String DEFAULT_HMAC_ALGORITHM = "HMACSHA256";

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
  public static String hmac(String message, String key) {
    try {
      final Mac mac = Mac.getInstance(DEFAULT_HMAC_ALGORITHM);
      mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), DEFAULT_HMAC_ALGORITHM));
      return toHexString(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String toHexString(byte[] ba) {
    StringBuilder sb = new StringBuilder(ba.length * 2);
    for (byte b : ba) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }
}
