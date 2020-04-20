package openag.shopify.client;

import com.google.gson.JsonObject;

import java.util.Optional;


class ShopifyJsonClientImpl implements ShopifyJsonClient {

  private final Http http;

  ShopifyJsonClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public Optional<JsonObject> getProduct(long id) {
    return http.get("/products/#{product_id}.json").pathVariable(id)
        .getOptional("product", JsonObject.class);
  }
}
