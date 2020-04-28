package openag.shopify.client.saleschannel;

import openag.shopify.client.http.Http;
import openag.shopify.domain.ProductListing;

import java.util.List;

public class SalesChannelClientImpl implements SalesChannelClient {

  private final Http http;

  public SalesChannelClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public List<ProductListing> getProductListings(ProductListingsRequest request) {
    return http.list(e -> e.path("/product_listings.json").queryParams(request.params()), ProductListing.class);
  }
}
