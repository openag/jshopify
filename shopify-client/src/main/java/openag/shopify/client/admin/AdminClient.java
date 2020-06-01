package openag.shopify.client.admin;

import java.util.Optional;

/**
 * Rest client for shop /admin endpoint
 */
public interface AdminClient {

  /**
   * Retrieves authorization code, usually part of app authorization flow
   */
  Optional<AccessTokenResponse> fetchAccessToken(String authorizationCode);

  //todo: implement Step 5: Request new access tokens https://help.shopify.com/en/api/getting-started/authentication/oauth/api-credential-rotation

}
