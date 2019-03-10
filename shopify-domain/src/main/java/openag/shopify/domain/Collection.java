package openag.shopify.domain;

/**
 * Common attributes for {@link SmartCollection} and {@link CustomCollection}
 */
public class Collection extends AbstractCollection {
  private long id;

  private String publishedScope;
  //todo: template_suffix

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getPublishedScope() {
    return publishedScope;
  }

  public void setPublishedScope(String publishedScope) {
    this.publishedScope = publishedScope;
  }
}
