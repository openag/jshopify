package openag.shopify.app;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DefaultNonceGeneratorTest {
  
  @Test
  public void test_default_nonce() {
    final DefaultNonceGenerator generator = new DefaultNonceGenerator();
    final String nonce = generator.generate("test");
    assertTrue(nonce.length() >= 24);
  }

}
