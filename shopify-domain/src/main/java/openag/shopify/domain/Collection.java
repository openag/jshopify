package openag.shopify.domain;

import java.util.Date;

/**
 * Common attributes for {@link SmartCollection} and {@link CustomCollection}
 */
public class Collection {
  private String bodyHtml;
  private String handle;
  private long id;
  //todo: image
  private Date publishedAt;
  private String publishedScope;
  //todo; sort_order
  //todo: template_suffix
  private String title;
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Date publishedAt) {
    this.publishedAt = publishedAt;
  }

  public String getPublishedScope() {
    return publishedScope;
  }

  public void setPublishedScope(String publishedScope) {
    this.publishedScope = publishedScope;
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
}
