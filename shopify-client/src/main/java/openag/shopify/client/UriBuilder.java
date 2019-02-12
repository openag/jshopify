package openag.shopify.client;

import java.util.HashMap;
import java.util.Map;

public class UriBuilder {

  private final String path;

  private Map<String, String> params;

  private UriBuilder(String path) {
    this.path = path;
  }

  public static UriBuilder path(String path) {
    return new UriBuilder(path);
  }

  public UriBuilder param(String name, String value) {
    if (params == null) {
      this.params = new HashMap<>();
    }
    this.params.put(name, value);
    return this;
  }


}
