package openag.shopify.client.http;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulation of request-response operation pair
 */
public class Exchange {

  private final NativeHttp.Method method;

  private String path;
  private List<String> pathVariables;
  private Map<String, String> queryParams;

  private JsonObject body;

  Exchange(NativeHttp.Method method) {
    this.method = method;
  }

  public Exchange path(String path) {
    this.path = path;
    return this;
  }

  public Exchange pathVariable(long value) {
    if (pathVariables == null) {
      pathVariables = new ArrayList<>(2);
    }
    pathVariables.add(String.valueOf(value));
    return this;
  }

  public Exchange queryParams(Map<String, String> params) {
    ensureParams();
    this.queryParams.putAll(params);
    return this;
  }

  public Exchange body(String wrapperElementName, Object body) {
    final JsonObject root = new JsonObject();
    root.add(wrapperElementName, Http.gson.toJsonTree(body));
    this.body = root;
    return this;
  }


  private void ensureParams() {
    if (this.queryParams == null) {
      this.queryParams = new HashMap<>();
    }
  }

  NativeHttp.Method getMethod() {
    return method;
  }

  String getPath() {
    return path;
  }

  List<String> getPathVariables() {
    return pathVariables;
  }

  Map<String, String> getQueryParams() {
    return queryParams;
  }

  JsonObject getBody() {
    return body;
  }
}
