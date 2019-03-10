package openag.shopify.client.saleschannel;

import openag.shopify.domain.ProductListing;

import java.util.List;

public interface SalesChannelClient {

  /**
   * Retrieve product listings that are published to your app
   */
  List<ProductListing> getProductListings(ProductListingsRequest request);


}
