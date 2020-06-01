package openag.shopify.app;

import openag.shopify.client.AccessScope;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The "Install Route" of the Shopify app based on Java Servlet API
 */
public class InstallRoute {

  private final String apiKey;
  private final ScopesProvider scopesProvider;
  private final String redirectUrl;

  /**
   * The nonce generator for the OAuth redirect url
   */
  private NonceGenerator nonceGenerator = new DefaultNonceGenerator();

  /**
   * @param apiKey         the custom application API key. You can find the app credentials on your partner dashboard on
   *                       "App setup" page -> "App credentials"
   * @param scopesProvider supplier for Shopify OAuth scopes required by this application (for example: read_products)
   * @param redirectUrl    full redirect URL (https://.../callback). This url will be called from Shopify once
   *                       application was authorized on the target shop
   */
  public InstallRoute(String apiKey, ScopesProvider scopesProvider, String redirectUrl) {
    this.apiKey = apiKey;
    this.scopesProvider = scopesProvider;
    this.redirectUrl = redirectUrl;
  }

  /**
   * The "Install Route" of the Shopify app; triggered directly for manual installation if needed. The method initiates
   * OAuth Authorization Flow between app and the specified shop
   * <p>
   * Method generates initial Authorization Flow request URL and sends redirect to it. The redirected screen should show
   * the Shopify authorization screen on the specified shop
   * <p>
   * The user browser is redirected to this endpoint right after choosing to install custom app from the Shopify app
   * store (or user is provided this link to install unlisted app manually)
   */
  public void initiateAuthorizationFlow(String shop,
                                        String hmac,
                                        long timestamp,
                                        HttpServletResponse response) throws IOException {
    if (shop == null || shop.isEmpty()) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST,
          "Missing shop parameter. Please add ?shop=your-shop.myshopify.com to your request");
      return;
    }

    final List<AccessScope> scopes = this.scopesProvider.scopesForShop(shop);
    final String scopesAsString = scopes.stream()
        .map(Enum::name)
        .collect(Collectors.joining(","));

    final String nonce = nonceGenerator.generate(shop);
    final String installUrl = "https://" + shop +
        "/admin/oauth/authorize?client_id=" + apiKey +
        "&scope=" + scopesAsString +
        "&state=" + nonce +
        "&redirect_uri=" + redirectUrl;

    successfulAuthorizationFlow(shop, hmac, timestamp, scopes, nonce);

    response.addCookie(new Cookie("state", nonce));
    response.sendRedirect(installUrl);
  }

  /**
   * Extension point, called on successful initial authorization request just before sending redirect to shopify
   */
  protected void successfulAuthorizationFlow(String shop,
                                             String hmac,
                                             long timestamp,
                                             List<AccessScope> scopes,
                                             String nonce) {
    //no-op, override if needed
  }

  public void setNonceGenerator(NonceGenerator nonceGenerator) {
    this.nonceGenerator = nonceGenerator;
  }
}
