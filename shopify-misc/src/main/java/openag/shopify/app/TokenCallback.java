package openag.shopify.app;

/**
 * Called when permanent token is obtained, which is the very last step of authentication process. Typically used within
 * Shopify application authorization flow, where access token is provided to the app in the end of the flow. The access
 * token then later can be used to access target shop data according to requested scopes
 */
public interface TokenCallback {

  void tokenObtained(String shop, String token);
}
