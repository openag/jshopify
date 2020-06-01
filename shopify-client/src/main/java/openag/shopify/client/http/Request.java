package openag.shopify.client.http;

import com.google.gson.JsonObject;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.net.http.HttpRequest.BodyPublishers.noBody;

/**
 * Encapsulation of request operation
 */
public class Request {

  private final Http.Method method;
  private final String path;

  private Http.ContentType contentType = Http.ContentType.JSON;

  private List<String> pathVariables;
  private Map<String, String> queryParams;

  private Object body;

  private Request(NativeHttp.Method method, String path) {
    this.method = method;
    this.path = path;
  }

  public static Request get(String path) {
    return new Request(Http.Method.GET, path);
  }

  public static Request post(String path) {
    return new Request(Http.Method.POST, path);
  }

  public static Request delete(String path) {
    return new Request(Http.Method.DELETE, path);
  }

  public Request contentType(Http.ContentType contentType) {
    if (contentType != null) {
      this.contentType = contentType;
    }
    return this;
  }

  public Request pathVariable(long value) {
    if (pathVariables == null) {
      pathVariables = new ArrayList<>(2);
    }
    pathVariables.add(String.valueOf(value));
    return this;
  }

  public Request queryParams(Map<String, String> params) {
    ensureParams();
    this.queryParams.putAll(params);
    return this;
  }

  public Request body(Object body) {
    return body(null, body);
  }

  public Request body(String wrapperElementName, Object body) {
    if (wrapperElementName != null) {
      final JsonObject root = new JsonObject();
      root.add(wrapperElementName, Http.gson.toJsonTree(body));
      this.body = root;
    } else {
      this.body = body;
    }
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

  Http.ContentType getContentType() {
    return contentType;
  }

  /**
   * todo:
   *
   * @return
   */
  HttpRequest.BodyPublisher bodyPublisher() {
    if (body == null) {
      return noBody();
    }

    if (contentType == Http.ContentType.FORM_URL_ENCODED && body instanceof Map) {
      return ofFormData((Map<?, ?>) body);
    }

    if (contentType == Http.ContentType.JSON) {
      return HttpRequest.BodyPublishers.ofString(Http.gson.toJson(body));
    }
    throw new RuntimeException("Unsupported combination of " + contentType + " and " + body.getClass());
  }

  private static HttpRequest.BodyPublisher ofFormData(Map<?, ?> data) {
    var builder = new StringBuilder();
    for (Map.Entry<?, ?> entry : data.entrySet()) {
      if (builder.length() > 0) {
        builder.append("&");
      }
      builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
      builder.append("=");
      builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
    }
    return HttpRequest.BodyPublishers.ofString(builder.toString());
  }

}
