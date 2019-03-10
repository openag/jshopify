package openag.shopify.client.product;

import openag.shopify.client.Http;
import openag.shopify.domain.Collect;
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
        .list("products", Product.class);
  }

  @Override
  public List<Collect> findCollects(CollectListRequest request) {
    return http.get("/admin/collects.json")
        .params(request.params())
        .list("collects", Collect.class);

  }

  @Override
  public Optional<Collect> getCollect(long id) {
    return http.get("/admin/collects/" + id + ".json")
        .getOptional("collect", Collect.class);
  }

  @Override
  public void deleteCollect(long id) {
    http.delete("/admin/collects/" + id + ".json").execute();
  }

  @Override
  public Collect createCollect(Collect collect) {
    return http.post("/admin/collects.json")
        .body("collect", collect)
        .getOne("collect", Collect.class);
  }

  @Override
  public Optional<Product> getProduct(long id) {
    return http.get("/admin/products/" + id + ".json")
        .getOptional("product", Product.class);
  }
}
