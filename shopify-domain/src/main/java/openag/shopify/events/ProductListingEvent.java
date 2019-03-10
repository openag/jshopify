package openag.shopify.events;

import openag.shopify.domain.ProductListing;

public abstract class ProductListingEvent implements ShopifyEvent {
  private final ProductListing product;

  public ProductListingEvent(ProductListing product) {
    this.product = product;
  }

  public ProductListing getProductListing() {
    return product;
  }

  public static class Created extends ProductListingEvent {
    public Created(ProductListing product) {
      super(product);
    }
  }

  public static class Updated extends ProductListingEvent {
    public Updated(ProductListing product) {
      super(product);
    }
  }

  public static class Deleted extends ProductListingEvent {
    public Deleted(ProductListing product) {
      super(product);
    }
  }

}
