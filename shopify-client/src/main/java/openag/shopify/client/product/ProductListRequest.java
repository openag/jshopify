package openag.shopify.client.product;

import java.util.HashMap;
import java.util.Map;

public class ProductListRequest {

  private int page = 1;

  /**
   * The page number to fetch (starts with 1)
   */
  public ProductListRequest page(int page) {
    if (page > 0) {
      this.page = page;
    }
    return this;
  }

  Map<String, String> params() {
    final Map<String, String> map = new HashMap<>();
    map.put("page", String.valueOf(page));
    return map;
  }

}
