package openag.shopify.app;

import openag.shopify.client.AccessScope;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Default {@link ScopesProvider} implementation; returns same list for any shop
 */
public class StaticScopesProvider implements ScopesProvider {

  private final List<AccessScope> scopes;

  public StaticScopesProvider(AccessScope... scopes) {
    this.scopes = Arrays.asList(scopes);
  }

  @Override
  public List<AccessScope> scopesForShop(String shop) {
    return Collections.unmodifiableList(this.scopes);
  }
}
