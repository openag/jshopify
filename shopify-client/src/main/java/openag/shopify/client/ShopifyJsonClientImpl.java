package openag.shopify.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import openag.shopify.client.http.Http;
import openag.shopify.client.product.ProductListRequest;

import java.util.Iterator;
import java.util.Optional;


class ShopifyJsonClientImpl implements ShopifyJsonClient {

  private final Http http;

  ShopifyJsonClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public Optional<JsonObject> getProduct(long id) {
    return http.getOptional(e -> e.path("/products/#{product_id}.json").pathVariable(id), JsonObject.class);
  }

  @Override
  public Iterator<JsonObject> iterateProducts(ProductListRequest request) {
    return http.iterate(e -> e.path("/products.json").queryParams(request.params()), JsonObject.class);
  }

  @Override
  public int countProducts(ProductListRequest request) {
    final JsonPrimitive json = http.getOne(e -> e.path("/products/count.json").queryParams(request.params()), JsonPrimitive.class);
    return json.getAsInt();
  }
}
