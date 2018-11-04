package openag.shopify.client;

import openag.shopify.domain.Product;

import java.util.Optional;

/**
 * Shopify REST API Client
 */
public interface ShopifyClient {

//  /**
//   * Returns all collects (collection-product mappings) for the provided collection
//   *
//   * @param id collection ID
//   */
//  List<Collect> findCollectsForCollection(long id);

  Optional<Product> getProduct(long id);

}
