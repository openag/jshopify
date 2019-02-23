package openag.shopify.client.product;

import com.google.gson.reflect.TypeToken;
import openag.shopify.client.Http;
import openag.shopify.domain.Product;

import java.util.List;
import java.util.Optional;

public class ProductClientImpl implements ProductClient {

  private final Http http;

  public ProductClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public List<Product> findProducts(ProductListRequest request) {
    return http.get("/admin/products.json")
        .params(request.params())
        .list((gson, json) -> gson.fromJson(json.getAsJsonArray("products"),
            new TypeToken<List<Product>>() {
            }.getType()));
  }

  @Override
  public Optional<Product> getProduct(long id) {
    return http.get("/admin/products/" + id + ".json")
        .getOne((gson, jsonObject) -> gson.fromJson(jsonObject.get("product").getAsJsonObject(), Product.class));
  }
}
