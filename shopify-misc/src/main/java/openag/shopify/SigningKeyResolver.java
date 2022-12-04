package openag.shopify;

/**
 * Resolves Shopify payload sign key for the specified shop. Typically, all HTTP requests originating from Shopify will
 * be signed (HMAC) with some key. In case of standalone webhooks the key is provided on the hook registration UI, in
 * case of app requests will be signed with the App Private Key (App Password).
 */
public interface SigningKeyResolver {

  /**
   * Resolves payload sign key for the specified shop (domain)
   */
  String getKey(String domain);
}
