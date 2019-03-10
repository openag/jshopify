package openag.shopify.domain;

import java.util.Arrays;
import java.util.Date;

class AbstractProduct {

  /**
   * The description of the product, complete with HTML formatting.
   */
  private String bodyHtml;

  /**
   * The date and time when the product was created.
   */
  private Date createdAt;

  /**
   * A human-friendly unique string for the Product automatically generated from its title.
   */
  private String handle;

  /**
   * A list of image objects, each one representing an image associated with the product.
   */
  private ProductImage[] images;

  //todo: options

  /**
   * A categorization that a product can be tagged with, commonly used for filtering.
   */
  private String productType;

  /**
   * The date and time when the product was published.
   */
  private Date publishedAt;

  /**
   * A categorization that a product can be tagged with, commonly used for filtering.
   */
  private String tags;

  /**
   * The name of the product.
   */
  private String title;

  /**
   * The date and time when the product was last modified.
   */
  private Date updatedAt;

  /**
   * A list of variant objects, each one representing a slightly different version of the product.
   */
  private ProductVariant[] variants;

  /**
   * The name of the vendor of the product.
   */
  private String vendor;

  public String getBodyHtml() {
    return bodyHtml;
  }

  public void setBodyHtml(String bodyHtml) {
    this.bodyHtml = bodyHtml;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  public ProductImage[] getImages() {
    return images;
  }

  public void setImages(ProductImage[] images) {
    this.images = images;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public Date getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Date publishedAt) {
    this.publishedAt = publishedAt;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public ProductVariant[] getVariants() {
    return variants;
  }

  public void setVariants(ProductVariant[] variants) {
    this.variants = variants;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  @Override
  public String toString() {
    return "AbstractProduct{" +
        "bodyHtml='" + bodyHtml + '\'' +
        ", createdAt=" + createdAt +
        ", handle='" + handle + '\'' +
        ", images=" + Arrays.toString(images) +
        ", productType='" + productType + '\'' +
        ", publishedAt=" + publishedAt +
        ", tags='" + tags + '\'' +
        ", title='" + title + '\'' +
        ", updatedAt=" + updatedAt +
        ", variants=" + Arrays.toString(variants) +
        ", vendor='" + vendor + '\'' +
        '}';
  }
}
