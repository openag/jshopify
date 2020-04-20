package openag.shopify.client;

import com.google.gson.JsonObject;

import java.util.Optional;

/**
 * Low-level Shopify REST API client that works with raw JSON payloads
 */
public interface ShopifyJsonClient {

  /**
   * Fetches product entity by ID
   */
  Optional<JsonObject> getProduct(long id);
}
