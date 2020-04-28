package openag.shopify.client;

import com.google.gson.JsonObject;
import openag.shopify.client.product.ProductListRequest;

import java.util.Iterator;
import java.util.Optional;

/**
 * Low-level Shopify REST API client that works with raw JSON payloads
 */
public interface ShopifyJsonClient {

  /**
   * Fetches product entity by ID
   */
  Optional<JsonObject> getProduct(long id);

  /**
   * Iterate across all found products
   */
  Iterator<JsonObject> iterateProducts(ProductListRequest request);

  /**
   * todo:
   *
   * @param request
   * @return
   */
  int countProducts(ProductListRequest request);
}
