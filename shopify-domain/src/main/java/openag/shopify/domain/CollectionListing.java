package openag.shopify.domain;

public class CollectionListing extends AbstractCollection {

  /**
   * Identifies which collection this listing is for.
   */
  private long collectionId;

  //todo: default_product_image


  public long getCollectionId() {
    return collectionId;
  }

  @Override
  public String toString() {
    return "CollectionListing{" +
        "collectionId=" + collectionId +
        "} " + super.toString();
  }
}
