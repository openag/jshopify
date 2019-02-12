package openag.shopify.client.product;

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
}
