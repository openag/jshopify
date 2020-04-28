package openag.shopify.client.http;

import java.util.HashMap;
import java.util.Map;

public class PageRequest {

  private int page = 1;
  private int limit = 50;

  /**
   * The page number to fetch (starts with 1)
   */
  public PageRequest page(int page) {
    if (page > 0) {
      this.page = page;
    }
    return this;
  }

  /**
   * The maximum number of results to show.
   */
  public PageRequest limit(int limit) {
    if (limit > 0) {
      this.limit = limit;
    }
    return this;
  }

  public Map<String, String> params() {
    final Map<String, String> map = new HashMap<>();
    map.put("page", String.valueOf(page));
    map.put("limit", String.valueOf(limit));
    return map;
  }
}
