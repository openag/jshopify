package openag.shopify.client.saleschannel;

import openag.shopify.client.Http;
import openag.shopify.domain.ProductListing;

import java.util.List;

public class SalesChannelClientImpl implements SalesChannelClient {

  private final Http http;

  public SalesChannelClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public List<ProductListing> getProductListings(ProductListingsRequest request) {
    return http.get("/admin/product_listings.json")
        .params(request.params())
        .list("product_listings", ProductListing.class);
  }
}
