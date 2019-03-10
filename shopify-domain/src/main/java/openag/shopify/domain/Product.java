package openag.shopify.domain;

public class Product extends AbstractProduct {

  private long id;

  //todo: published_scope
  //todo; template_suffix
  //todo: metafields_global_title_tag
  //todo: metafields_global_description_tag

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        "} " + super.toString();
  }
}
