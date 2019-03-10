package openag.shopify.domain;

import java.util.Date;

/**
 * Mapping between single product and single collection
 */
public class Collect {

  /**
   * The ID of the custom collection containing the product.
   */
  private long collectionId;

  /**
   * The date and time when the collect was created.
   */
  private Date createdAt;

  /**
   * Whether the collect is featured.
   */
  private boolean featured;

  /**
   * A unique numeric identifier for the collect.
   */
  private long id;

  /**
   * The position of this product in a manually sorted custom collection. The first position is 1. This value is applied
   * only when the custom collection is sorted manually.
   */
  private int position;

  /**
   * The unique numeric identifier for the product in the custom collection.
   */
  private long productId;

  /**
   * This is the same value as position but padded with leading zeroes to make it alphanumeric-sortable.
   */
  private String sortValue;

  /**
   * The date and time when the collect was last updated.
   */
  private Date updatedAt;

  public long getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(long collectionId) {
    this.collectionId = collectionId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public boolean isFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public String getSortValue() {
    return sortValue;
  }

  public void setSortValue(String sortValue) {
    this.sortValue = sortValue;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "Collect{" +
        "collectionId=" + collectionId +
        ", createdAt=" + createdAt +
        ", featured=" + featured +
        ", id=" + id +
        ", position=" + position +
        ", productId=" + productId +
        ", sortValue='" + sortValue + '\'' +
        ", updatedAt=" + updatedAt +
        '}';
  }
}
