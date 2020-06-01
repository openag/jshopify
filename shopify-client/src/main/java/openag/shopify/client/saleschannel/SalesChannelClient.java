package openag.shopify.client.saleschannel;

import openag.shopify.client.http.Page;
import openag.shopify.domain.ProductListing;

public interface SalesChannelClient {

  /**
   * Retrieve product listings that are published to your app
   */
  Page<ProductListing> getProductListings(ProductListingsRequest request);

}
