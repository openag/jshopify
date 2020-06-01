package openag.shopify.app;

import openag.shopify.client.ShopifyClientFactory;
import openag.shopify.client.admin.AccessTokenResponse;
import openag.shopify.client.admin.AdminClient;

/**
 * {@link CallbackRoute} extension that fetches the access token in case of successful authorization.
 */
public class AccessTokenCallbackRoute extends CallbackRoute {

  private final String apiKey;
  private final AccessTokenCallback tokenCallback;

  public AccessTokenCallbackRoute(String apiKey, String apiSecret,
                                  AccessTokenCallback tokenCallback) {
    super(apiSecret);
    this.apiKey = apiKey;
    this.tokenCallback = tokenCallback;
  }

  @Override
  protected void authorizationSuccessful(String shop, String hmac, String authorizationCode, String state) {
    final AdminClient client = ShopifyClientFactory.newAdminClient(shop, apiKey, apiSecret);
    final AccessTokenResponse tr = client.fetchAccessToken(authorizationCode)
        .orElseThrow(() -> new RuntimeException("Failed to retrieve access token for " + shop));
    tokenCallback.accessTokenRetrieved(shop, tr.getAccessToken(), tr.getScopes());
  }
}
