package openag.shopify.client;

import com.google.gson.JsonObject;

/**
 * Bulk-load all Shopify entities as JSON
 */
public interface BulkShopifyJsonClient {

  Iterable<JsonObject> products();

  Iterable<JsonObject> customers();

  Iterable<JsonObject> orders();

}
