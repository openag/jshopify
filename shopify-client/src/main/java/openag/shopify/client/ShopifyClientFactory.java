package openag.shopify.client;

import openag.shopify.client.admin.AdminClient;
import openag.shopify.client.admin.AdminClientImpl;
import openag.shopify.client.customer.CustomerClientImpl;
import openag.shopify.client.http.Http;
import openag.shopify.client.http.HttpFactory;
import openag.shopify.client.http.UrlBuilder;
import openag.shopify.client.inventory.InventoryClientImpl;
import openag.shopify.client.product.ProductClientImpl;
import openag.shopify.client.saleschannel.SalesChannelClientImpl;
import openag.shopify.client.webhook.WebhookClientImpl;

import java.net.http.HttpRequest;
import java.util.Base64;
import java.util.function.Consumer;

@SuppressWarnings({"WeakerAccess", "unused"}) // public API
public class ShopifyClientFactory {

  public static final String DEFAULT_API_VERSION = "2020-04";

  /**
   * Full domain name of the Shopify shop in format xxxxxxx.myshopify.com
   */
  private final String domain;

  private String apiVersion = DEFAULT_API_VERSION;

  private Consumer<HttpRequest.Builder> authenticator; //todo: refactor authenticator

  public ShopifyClientFactory(String domain) {
    this.domain = domain.endsWith(".myshopify.com") ? domain : domain + ".myshopify.com";
  }

  /**
   * Applies api key/secret authentication scheme (suitable for private applications for example)
   * <p>
   * https://help.shopify.com/en/api/getting-started/authentication/private-authentication
   */
  public ShopifyClientFactory authenticateWithAPIKeyAndPassword(String apiKey, String password) {
    this.authenticator = new BasicAuthenticator(apiKey, password);
    return this;
  }

  /**
   * Applies access token authentication scheme  (ex. public installed apps after authorization)
   */
  public ShopifyClientFactory authenticateWithAccessToken(String accessToken) {
    this.authenticator = new AccessTokenAuthenticator(accessToken);
    return this;
  }

  /**
   * Specify API version to use; by default will use {@link #DEFAULT_API_VERSION}
   */
  public ShopifyClientFactory apiVersion(String version) {
    this.apiVersion = version;
    return this;
  }

  /**
   * Builds new instance of {@link ShopifyClient}
   */
  public ShopifyClient build() {
    final Http http = buildHttp();
    return new ShopifyClientImpl(
        new ProductClientImpl(http),
        new InventoryClientImpl(http),
        new WebhookClientImpl(http),
        new SalesChannelClientImpl(http),
        new CustomerClientImpl(http));
  }

  /**
   * Creates new instance of {@link ShopifyJsonClient}
   */
  public ShopifyJsonClient buildJsonClient() {
    return new ShopifyJsonClientImpl(buildHttp());
  }

  public static AdminClient newAdminClient(String shop, String apiKey, String apiSecret) {
    return new AdminClientImpl(shop, apiKey, apiSecret);
  }

  private Http buildHttp() {
    return HttpFactory.newHttp(UrlBuilder.forApi(domain, apiVersion), authenticator);
  }

  private static class AccessTokenAuthenticator implements Consumer<HttpRequest.Builder> {
    private final String accessToken;

    private AccessTokenAuthenticator(String accessToken) {
      this.accessToken = accessToken;
    }

    @Override
    public void accept(HttpRequest.Builder builder) {
      builder.header("X-Shopify-Access-Token", accessToken);
    }
  }

  private static class BasicAuthenticator implements Consumer<HttpRequest.Builder> {
    private final String token;

    private BasicAuthenticator(String user, String password) {
      this.token = "Basic " + Base64.getEncoder().encodeToString((user + ":" + password).getBytes());
    }

    @Override
    public void accept(HttpRequest.Builder builder) {
      builder.header("Authorization", token);
    }
  }
}
