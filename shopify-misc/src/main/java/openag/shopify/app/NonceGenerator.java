package openag.shopify.app;

/**
 * Random nonce generator for application authorization flow
 */
public interface NonceGenerator {

  /**
   * Generates random nonce as string
   */
  String generate(String shop);
}
