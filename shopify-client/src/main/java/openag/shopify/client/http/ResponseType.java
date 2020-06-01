package openag.shopify.client.http;

/**
 * Encapsulates expected REST API call response type/structure
 */
public abstract class ResponseType<E> {
  private final String wrappingElementName;
  private final Class<E> elementType;

  private ResponseType(String wrappingElementName, Class<E> elementType) {
    this.wrappingElementName = wrappingElementName;
    this.elementType = elementType;
  }

  public String getWrappingElementName() {
    return wrappingElementName;
  }

  public Class<E> getElementType() {
    return elementType;
  }

  public boolean isWrapped() {
    return this.wrappingElementName != null;
  }

  public abstract boolean isCollection();

  public static class Obj<E> extends ResponseType<E> {
    private Obj(String wrappingElementName, Class<E> elementType) {
      super(wrappingElementName, elementType);
    }

    @Override
    public boolean isCollection() {
      return false;
    }
  }

  public static class Arr<E> extends ResponseType<E> {
    private Arr(String wrappingElementName, Class<E> elementType) {
      super(wrappingElementName, elementType);
    }

    @Override
    public boolean isCollection() {
      return true;
    }
  }

  /**
   * Indicates that response json is wrapped into JSON object with the only key 'wrappingElementName' pointing to the
   * object. An example could be product details response wrapped JSON with wrapping element name 'product':
   * <pre>
   *   {
   *      "product": { ... }
   *   }
   * </pre>
   */
  public static <T> ResponseType.Obj<T> wrappedObject(String wrappingElementName, Class<T> elementType) {
    return new Obj<>(wrappingElementName, elementType);
  }

  /**
   * Indicates that response json is wrapped into JSON object with the only key 'wrappingElementName' pointing to the
   * object, and the response json is a list (json array) of elements of type 'elementType'. An example could be product
   * list response wrapped JSON with wrapping element name 'products':
   * <pre>
   *   {
   *      "products": [
   *          { product_json_1},
   *          { product_json_2},
   *          ...
   *      ]
   *   }
   * </pre>
   */
  public static <T> ResponseType.Arr<T> wrappedList(String wrappingElementName, Class<T> elementType) {
    return new ResponseType.Arr<>(wrappingElementName, elementType);
  }

  /**
   * Response is a simple json object that can be converted to the requested type without any additional processing
   */
  public static <T> ResponseType.Obj<T> jsonObject(Class<T> elementType) {
    return new ResponseType.Obj<>(null, elementType);
  }
}
