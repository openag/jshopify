package openag.shopify.client.product;

import java.util.HashMap;
import java.util.Map;

public class ProductListRequest {

  private String productType;

  private String vendor;

  private String handle;

  public ProductListRequest productType(String productType) {
    this.productType = productType;
    return this;
  }

  public ProductListRequest vendor(String vendor) {
    this.vendor = vendor;
    return this;
  }

  public ProductListRequest handle(String handle) {
    this.handle = handle;
    return this;
  }

  public Map<String, String> params() {
    final Map<String, String> params = new HashMap<>();
    if (productType != null) {
      params.put("product_type", productType);
    }
    if (vendor != null) {
      params.put("vendor", vendor);
    }
    if (handle != null) {
      params.put("handle", handle);
    }
    return params;
  }
}
