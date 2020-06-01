package openag.shopify.app;

import java.security.SecureRandom;
import java.util.Base64;

public class DefaultNonceGenerator implements NonceGenerator {

  private final SecureRandom random = new SecureRandom();

  @Override
  public String generate(String shop) {
    final byte[] bytes = new byte[8];
    random.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes) + System.currentTimeMillis();
  }
}
