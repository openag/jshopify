package openag.shopify.apps.cl.events;

import openag.shopify.domain.Product;
import org.springframework.context.ApplicationEvent;

public abstract class ProductEvent extends ApplicationEvent {

  public ProductEvent(Product product) {
    super(product);
  }

  public Product getProduct() {
    return (Product) getSource();
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
