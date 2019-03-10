package openag.shopify.events;

import openag.shopify.domain.CollectionListing;

public abstract class CollectionListingEvent implements ShopifyEvent {
  private final CollectionListing collection;

  public CollectionListingEvent(CollectionListing collection) {
    this.collection = collection;
  }

  public CollectionListing getCollectionListing() {
    return collection;
  }

  public static class Created extends CollectionListingEvent {
    public Created(CollectionListing collection) {
      super(collection);
    }
  }

  public static class Updated extends CollectionListingEvent {
    public Updated(CollectionListing collection) {
      super(collection);
    }
  }

  public static class Deleted extends CollectionListingEvent {
    public Deleted(CollectionListing collection) {
      super(collection);
    }
  }

}
