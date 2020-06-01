package openag.shopify.app;

import openag.shopify.client.AccessScope;

import java.util.List;

/**
 * Resolves required access scopes for the specified shop. Provides greater flexibility when application should request
 * different scopes for different shops
 */
public interface ScopesProvider {

  /**
   * Returns list of requested access scopes for the provided shop
   *
   * @param shop shop name (ex your-development-shop.myshopify.com)
   * @return list of {@link AccessScope} instances; can return empty list; never NULL
   */
  List<AccessScope> scopesForShop(String shop);
}
