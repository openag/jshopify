package openag.shopify.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultNonceGeneratorTest {

  @Test
  public void test_default_nonce() {
    final DefaultNonceGenerator generator = new DefaultNonceGenerator();
    final String nonce = generator.generate("test");
    assertTrue(nonce.length() >= 24);
  }

}
