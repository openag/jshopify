package openag.shopify.events;

import openag.shopify.domain.CollectionListing;

public abstract class CollectionListingEvent extends AbstractShopifyEvent<CollectionListing> {

  CollectionListingEvent(CollectionListing collection) {
    super(collection);
  }

  public CollectionListing getCollectionListing() {
    return getTarget();
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
