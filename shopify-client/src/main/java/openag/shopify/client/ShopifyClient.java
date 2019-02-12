package openag.shopify.client;

import openag.shopify.client.product.ProductClient;

/**
 * Shopify REST API Client
 */
public interface ShopifyClient extends ProductClient {

  /**
   * Attempts to fetch the provided file throwing exception if http error code returned
   */
  FileRef downloadFile(String url);
}
