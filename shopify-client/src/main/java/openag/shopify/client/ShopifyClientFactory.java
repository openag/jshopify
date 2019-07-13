package openag.shopify.client;

import openag.shopify.client.customer.CustomerClientImpl;
import openag.shopify.client.inventory.InventoryClientImpl;
import openag.shopify.client.product.ProductClientImpl;
import openag.shopify.client.saleschannel.SalesChannelClientImpl;
import openag.shopify.client.webhook.WebhookClientImpl;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess", "unused"}) // public API
public class ShopifyClientFactory {

  private static final String DEFAULT_API_VERSION = "2019-07";

  /**
   * Full domain name of the Shopify shop in format xxxxxxx.myshopify.com
   */
  private final String domain;

  private String apiVersion = DEFAULT_API_VERSION;

  private Consumer<HttpRequest.Builder> authenticator;

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
    final HttpClient httpClient = HttpClient.newBuilder().build();

    final UrlBuilder urlBuilder = new UrlBuilder(domain, apiVersion);

    final Http http = new Http(httpClient, urlBuilder, authenticator);

    return new ShopifyClientImpl(
        new ProductClientImpl(http),
        new InventoryClientImpl(http),
        new WebhookClientImpl(http),
        new SalesChannelClientImpl(http),
        new CustomerClientImpl(http));
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

  static class UrlBuilder {
    private static final Pattern VARIABLE_MATCH = Pattern.compile("#\\{\\w+}");

    private final String baseUrl;

    UrlBuilder(String domain, String apiVersion) {
      this.baseUrl = "https://" + domain + "/admin/api/" + apiVersion;

    }

    String url(String path, List<String> pathParams, Map<String, String> queryParams) {
      if (pathParams.isEmpty() && queryParams.isEmpty()) {
        return baseUrl + path;
      }

      final StringBuilder sb = new StringBuilder(baseUrl);
      if (!pathParams.isEmpty()) {
        final Iterator<String> it = pathParams.iterator();
        sb.append(VARIABLE_MATCH.matcher(path).replaceAll(matchResult -> it.next()));
      } else {
        sb.append(path);
      }

      if (!queryParams.isEmpty()) {
        sb.append("?").append(
            queryParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&")));
      }
      return sb.toString();
    }
  }

}
