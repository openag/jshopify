package openag.shopify.domain;

import java.util.Arrays;
import java.util.Date;

public class Product {

  private String bodyHtml;
  private Date createdAt;
  private String handle;
  private long id;
  private ProductImage[] images;
  //todo: options
  private String productType;
  private Date publishedAt;
  //todo: published_scope
  private String tags;
  //todo; template_suffix
  private String title;
  //todo: metafields_global_title_tag
  //todo: metafields_global_description_tag
  private Date updatedAt;
  private ProductVariant[] variants;
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getVendor() {
    return vendor;
  }

  public ProductImage[] getImages() {
    return images;
  }

  public ProductVariant[] getVariants() {
    return variants;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  @Override
  public String toString() {
    return "Product{" +
        "bodyHtml='" + bodyHtml + '\'' +
        ", createdAt=" + createdAt +
        ", handle='" + handle + '\'' +
        ", id=" + id +
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
