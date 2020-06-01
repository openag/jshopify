package openag.shopify.client.admin;

import openag.shopify.client.AccessScope;

import java.util.Collections;
import java.util.List;

public class AccessTokenResponse {

  private final String accessToken;
  private final List<AccessScope> scopes;

  AccessTokenResponse(String accessToken, List<AccessScope> scopes) {
    this.accessToken = accessToken;
    this.scopes = scopes;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public List<AccessScope> getScopes() {
    return Collections.unmodifiableList(scopes);
  }
}
