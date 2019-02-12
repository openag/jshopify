package openag.shopify.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * todo;
 */
public class Exchange<T> {

  private final String path;

  private Map<String, String> params;

  private BiFunction<Gson, JsonObject, T> extractor;

  Exchange(String path) {
    this.path = path;
  }

  public static <T> Exchange<T> path(String path) {
    return new Exchange<>(path);
  }

  public Exchange<T> params(Map<String, String> params) {
    ensureParams();
    this.params.putAll(params);
    return this;
  }

  public Exchange param(String name, String value) {
    ensureParams();
    this.params.put(name, value);
    return this;
  }

  public Exchange<T> response(BiFunction<Gson, JsonObject, T> extractor) {
    this.extractor = extractor;
    return this;
  }

  BiFunction<Gson, JsonObject, T> getExtractor() {
    return extractor;
  }

  private void ensureParams() {
    if (this.params == null) {
      this.params = new HashMap<>();
    }
  }

  URI absoluteUrl(String baseUrl) {

    return null;
  }
}
