package openag.shopify.client;

public class ShopifyClientException extends RuntimeException {

  private int httpResponseCode;

  public ShopifyClientException(Throwable cause) {
    super(cause);
  }

  public ShopifyClientException(int httpResponseCode) {
    super("HTTP Error: " + httpResponseCode);
    this.httpResponseCode = httpResponseCode;
  }

  public int getHttpResponseCode() {
    return httpResponseCode;
  }
}
