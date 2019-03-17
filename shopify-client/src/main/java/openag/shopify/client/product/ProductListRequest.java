package openag.shopify.client.product;

import openag.shopify.client.PaginatedRequest;

import java.util.Map;

public class ProductListRequest extends PaginatedRequest<ProductListRequest> {

  private String productType;

  private String vendor;

  public ProductListRequest productType(String productType) {
    this.productType = productType;
    return this;
  }

  public ProductListRequest vendor(String vendor) {
    this.vendor = vendor;
    return this;
  }

  @Override
  public Map<String, String> params() {
    final Map<String, String> params = super.params();
    if (productType != null) {
      params.put("product_type", productType);
    }
    if (vendor != null) {
      params.put("vendor", vendor);
    }
    return params;
  }
}
