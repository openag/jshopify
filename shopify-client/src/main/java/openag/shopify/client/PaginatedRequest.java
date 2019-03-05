package openag.shopify.client;

import java.util.HashMap;
import java.util.Map;

public class PaginatedRequest<T extends PaginatedRequest> {

  private int page = 1;

  private int limit = 50;

  /**
   * The page number to fetch (starts with 1)
   */
  public T page(int page) {
    if (page > 0) {
      this.page = page;
    }
    return cast(this);
  }

  /**
   * The maximum number of results to show.
   */
  public T limit(int limit) {
    if (limit > 0) {
      this.limit = limit;
    }
    return cast(this);
  }

  private T cast(PaginatedRequest o) {
    return (T) o;
  }

  public Map<String, String> params() {
    final Map<String, String> map = new HashMap<>();
    map.put("page", String.valueOf(page));
    map.put("limit", String.valueOf(limit));
    return map;
  }
}
