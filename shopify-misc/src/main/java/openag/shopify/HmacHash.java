package openag.shopify;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * Container for HMAC hash value
 */
public class HmacHash {

  private static final String DEFAULT_HMAC_ALGORITHM = "HMACSHA256";

  private final byte[] hash;

  private HmacHash(byte[] hash) {
    if (hash == null || hash.length == 0) {
      throw new IllegalArgumentException("Provided hash is null or empty: " + Arrays.toString(hash));
    }
    this.hash = hash;
  }

  /**
   * Creates new {@link HmacHash} instance from provided hmac as base64 string
   */
  public static HmacHash fromBase64(String base64string) {
    return new HmacHash(Base64.getDecoder().decode(base64string));
  }

  /**
   * Creates new {@link HmacHash} instance from provided hex-encoded string
   */
  public static HmacHash fromHex(String hex) {
    return new HmacHash(decodeHexString(hex));
  }

  /**
   * Creates new {@link HmacHash} using provided hash bytes
   */
  public static HmacHash fromBytes(byte[] hash) {
    return new HmacHash(hash); //no defensive copy for now (for performance reasons)
  }

  /**
   * Calculates HMAC for the message with provided key using default algorithm
   */
  public static HmacHash hmac(byte[] message, byte[] key) {
    try {
      final Mac mac = Mac.getInstance(DEFAULT_HMAC_ALGORITHM);
      mac.init(new SecretKeySpec(key, DEFAULT_HMAC_ALGORITHM));
      return HmacHash.fromBytes(mac.doFinal(message));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @return hex-encoded HMAC hash
   */
  public String toHexString() {
    return toHexString(hash);
  }

  private static String toHexString(byte[] ba) {
    StringBuilder sb = new StringBuilder(ba.length * 2);
    for (byte b : ba) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }

  private static byte[] decodeHexString(String hexString) {
    if (hexString.length() % 2 == 1) {
      throw new IllegalArgumentException("Invalid hexadecimal String supplied.");
    }

    byte[] bytes = new byte[hexString.length() / 2];
    for (int i = 0; i < hexString.length(); i += 2) {
      bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
    }
    return bytes;
  }

  private static byte hexToByte(String hexString) {
    int firstDigit = toDigit(hexString.charAt(0));
    int secondDigit = toDigit(hexString.charAt(1));
    return (byte) ((firstDigit << 4) + secondDigit);
  }

  private static int toDigit(char hexChar) {
    int digit = Character.digit(hexChar, 16);
    if (digit == -1) {
      throw new IllegalArgumentException("Invalid Hexadecimal Character: " + hexChar);
    }
    return digit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HmacHash hmacHash = (HmacHash) o;
    return Arrays.equals(hash, hmacHash.hash);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(hash);
  }

  @Override
  public String toString() {
    return toHexString();
  }
}
