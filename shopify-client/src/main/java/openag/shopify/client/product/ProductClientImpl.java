package openag.shopify.client.product;

import openag.shopify.client.http.Http;
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
    return http.list(e -> e.path("/products.json").queryParams(request.params()), Product.class);
  }

  @Override
  public List<Collect> findCollects(CollectListRequest request) {
    return http.list(e -> e.path("/collects.json").queryParams(request.params()), Collect.class);
  }

  @Override
  public Optional<Collect> getCollect(long id) {
    return http.getOptional(e -> e.path("/collects/#{collect_id}.json").pathVariable(id), Collect.class);
  }

  @Override
  public void deleteCollect(long id) {
    http.delete(e -> e.path("/collects/#{collect_id}.json").pathVariable(id));
  }

  @Override
  public Collect createCollect(Collect collect) {
    return http.exchange(e -> e.path("/collects.json").body("collect", collect), Collect.class);
  }

  @Override
  public Optional<Product> getProduct(long id) {
    return http.getOptional(e -> e.path("/products/#{product_id}.json").pathVariable(id), Product.class);
  }
}
