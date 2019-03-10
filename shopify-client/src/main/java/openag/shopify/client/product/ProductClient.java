package openag.shopify.client.product;

import openag.shopify.domain.Collect;
import openag.shopify.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductClient {

  /**
   * Retrieves {@link Product} by product unique ID; returns empty optional if product with provided ID does not exist
   */
  Optional<Product> getProduct(long id);

  /**
   * Finds list of products based on provided request details
   */
  List<Product> findProducts(ProductListRequest request);

  /**
   * Search for Collects. Collect provides an active mapping between collection and product
   */
  List<Collect> findCollects(CollectListRequest request);

  /**
   * Retrieve a collect with a certain ID
   */
  Optional<Collect> getCollect(long id);

  /**
   * Delete the link between a product an a collection (Removes a product from a collection.)
   */
  void deleteCollect(long id);

  /**
   * Adds a product to a custom collection. Create a new link between an existing product and an existing collection
   */
  Collect createCollect(Collect collect);
}
