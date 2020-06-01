package openag.shopify.app;

import openag.shopify.client.AccessScope;

import java.util.List;

/**
 * Called when app access token is successfully acquired
 */
public interface AccessTokenCallback {

  void accessTokenRetrieved(String shop, String accessToken, List<AccessScope> scopes);

}
