package openag.shopify.client;

import com.google.gson.JsonObject;
import openag.shopify.client.http.Http;
import openag.shopify.client.http.ResponseType;
import openag.shopify.client.product.ProductListRequest;

import java.util.Iterator;
import java.util.Optional;

import static openag.shopify.client.http.Request.get;


class ShopifyJsonClientImpl implements ShopifyJsonClient {

  private final Http http;

  ShopifyJsonClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public Optional<JsonObject> getProduct(long id) {
    return Optional.ofNullable(http.exchange(get("/products/#{product_id}.json").pathVariable(id),
        ResponseType.wrappedObject("product", JsonObject.class)));
  }

  @Override
  public Iterator<JsonObject> iterateProducts(ProductListRequest request) {
    return http.iterate(get("/products.json").queryParams(request.params()),
        ResponseType.wrappedList("products", JsonObject.class));
  }

  @Override
  public int countProducts(ProductListRequest request) {
    return http.exchange(get("/products/count.json").queryParams(request.params()),
        ResponseType.jsonObject(JsonObject.class))
        .get("count").getAsInt();
  }
}
