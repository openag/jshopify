package openag.shopify.domain;

public class ProductListing extends AbstractProduct {

  /**
   * The unique identifer of the product this listing is for. The primary key for this resource.
   */
  private long productId;

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  @Override
  public String toString() {
    return "ProductListing{" +
        "productId=" + productId +
        "} " + super.toString();
  }
}
