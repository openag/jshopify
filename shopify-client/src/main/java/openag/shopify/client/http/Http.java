package openag.shopify.client.http;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Iterator;
import java.util.List;

/**
 * HTTP client abstraction
 */
public interface Http {

  Gson gson = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
      .create();

  /**
   * Performs the request-response exchange, expecting single object in response
   */
  <E> E exchange(Request rq, ResponseType.Obj<E> rt);

  /**
   * Performs the request-response exchange, expecting single object in response
   */
  <E> List<E> exchange(Request rq, ResponseType.Arr<E> rt);

  /**
   * Performs the request-response exchange ignoring the response
   */
  void exchange(Request rq);

  /**
   * Page request
   */
  <E> Page<E> page(Request rq, ResponseType.Arr<E> rt);

  /**
   * Retrieves all available elements in a single list; pagination may be triggered under the hood if there are more
   * elements available
   */
  <E> List<E> list(Request rq, ResponseType.Arr<E> rt);

  /**
   * Iterate through all available elements applying lazy-loading for each subsequent page if needed
   */
  <T> Iterator<T> iterate(Request rq, ResponseType.Arr<T> rt);

  enum Method {
    GET, POST, DELETE
  }

  enum ContentType {
    JSON("application/json"),
    FORM_URL_ENCODED("application/x-www-form-urlencoded");

    private final String value;

    ContentType(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
}
