package openag.shopify.events;

import openag.shopify.domain.Product;

public abstract class ProductEvent implements ShopifyEvent {
  private final Product product;

  public ProductEvent(Product product) {
    this.product = product;
  }

  public Product getProduct() {
    return product;
  }

  public static class Created extends ProductEvent {
    public Created(Product product) {
      super(product);
    }
  }

  public static class Updated extends ProductEvent {
    public Updated(Product product) {
      super(product);
    }
  }

  public static class Deleted extends ProductEvent {
    public Deleted(Product product) {
      super(product);
    }
  }

}
