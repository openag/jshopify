package openag.shopify.client.product;

import openag.shopify.client.http.Http;
import openag.shopify.domain.Collect;
import openag.shopify.domain.Product;

import java.util.List;
import java.util.Optional;

import static openag.shopify.client.http.Request.*;
import static openag.shopify.client.http.ResponseType.wrappedList;
import static openag.shopify.client.http.ResponseType.wrappedObject;

public class ProductClientImpl implements ProductClient {

  private final Http http;

  public ProductClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public List<Product> findProducts(ProductListRequest request) { //todo: return Page
    return http.exchange(
        get("/products.json").queryParams(request.params()),
        wrappedList("products", Product.class));
  }

  @Override
  public List<Collect> findCollects(CollectListRequest request) {
    return http.exchange(get("/collects.json").queryParams(request.params()),
        wrappedList("collects", Collect.class));
  }

  @Override
  public Optional<Collect> getCollect(long id) {
    return Optional.ofNullable(http.exchange(get("/collects/#{collect_id}.json").pathVariable(id),
        wrappedObject("collect", Collect.class)));
  }

  @Override
  public void deleteCollect(long id) {
    http.exchange(delete("/collects/#{collect_id}.json").pathVariable(id));
  }

  @Override
  public Collect createCollect(Collect collect) {
    return http.exchange(post("/collects.json").body("collect", collect),
        wrappedObject("collect", Collect.class));
  }

  @Override
  public Optional<Product> getProduct(long id) {
    return Optional.ofNullable(http.exchange(get("/products/#{product_id}.json").pathVariable(id),
        wrappedObject("product", Product.class)));
  }
}
