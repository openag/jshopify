package openag.shopify;

public class Constants {

  /**
   * Usually indicates the type of webhook fired
   * <p>
   * https://shopify.dev/docs/admin-api/rest/reference/events/webhook
   */
  public static final String HTTP_HEADER_SHOPIFY_TOPIC = "x-shopify-topic";

  public static final String HTTP_HEADER_SHOPIFY_SHOP_DOMAIN = "x-shopify-shop-domain";

  public static final String HTTP_HEADER_SHOPIFY_API_VERSION = "x-shopify-api-version";

  public static final String HTTP_HEADER_SHOPIFY_SHOPIFY_HMAC_SHA_256 = "x-shopify-hmac-sha256";
}
