package openag.shopify.domain;

import java.util.Date;

class AbstractCollection {

  /**
   * The description of the collection, complete with HTML formatting.
   */
  private String bodyHtml;

  //todo: image

  /**
   * A human-friendly unique string for the Collection automatically generated from its title.
   */
  private String handle;

  /**
   * The date and time when the collection was published.
   */
  private Date publishedAt;

  /**
   * The name of the collection.
   */
  private String title;

  //todo: sort-order

  private Date updatedAt;

  public String getBodyHtml() {
    return bodyHtml;
  }

  public void setBodyHtml(String bodyHtml) {
    this.bodyHtml = bodyHtml;
  }

  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  public Date getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Date publishedAt) {
    this.publishedAt = publishedAt;
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

  @Override
  public String toString() {
    return "AbstractCollection{" +
        "bodyHtml='" + bodyHtml + '\'' +
        ", handle='" + handle + '\'' +
        ", publishedAt=" + publishedAt +
        ", title='" + title + '\'' +
        ", updatedAt=" + updatedAt +
        '}';
  }
}
