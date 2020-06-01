package openag.shopify.client.saleschannel;

import openag.shopify.client.http.Http;
import openag.shopify.client.http.Page;
import openag.shopify.client.http.ResponseType;
import openag.shopify.domain.ProductListing;

import static openag.shopify.client.http.Request.get;

public class SalesChannelClientImpl implements SalesChannelClient {

  private final Http http;

  public SalesChannelClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public Page<ProductListing> getProductListings(ProductListingsRequest request) {
    return http.page(get("/product_listings.json").queryParams(request.params()),
        ResponseType.wrappedList("product_listings",ProductListing.class));
  }
}
