package openag.shopify.spring;

import openag.shopify.client.AccessScope;
import openag.shopify.web.HttpRequestSignatureValidator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//todo: implement Step 5: Request new access tokens https://help.shopify.com/en/api/getting-started/authentication/oauth/api-credential-rotation


/**
 * Controller typically used on the application server-side to perform initial installation and authorization of custom
 * Shopify application
 * <p>
 * Based on tutorial: https://help.shopify.com/en/api/tutorials/building-node-app
 */
@RestController
public class ShopifyApplicationAuthorizationController {

  private final RestTemplate restTemplate = new RestTemplate();

  private static final SecureRandom random = new SecureRandom();

  private final String apiKey;
  private final String apiSecret;

  private final String scopes;
  private final String callbackBaseUrl;

  /**
   * The called when permanent token is obtained
   */
  private TokenCallback tokenCallback;

  /**
   * The nonce generator for the OAuth redirect url
   */
  private Supplier<String> nonceGenerator = () -> {
    final byte[] bytes = new byte[8];
    random.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes) + System.currentTimeMillis();
  };

  /**
   * Creates new instance of the controller. All parameters are mandatory
   *
   * @param apiKey          the custom application API key. You can find the app credentials on your partner dashboard
   *                        on "App setup" page -> "App credentials"
   * @param apiSecret       the custom application API secret key
   * @param callbackBaseUrl the base url of this server-side instance that will be used by Shopify to perform final
   *                        callback after user confirms app access details. The callback should be used to obtain
   *                        permanent token to access the registering shop details
   * @param scopes          list of Shopify OAuth scopes required by this application (for example:  read_products)
   */
  public ShopifyApplicationAuthorizationController(String apiKey,
                                                   String apiSecret,
                                                   String callbackBaseUrl,
                                                   AccessScope... scopes) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
    this.callbackBaseUrl = callbackBaseUrl;
    this.scopes = Arrays.stream(scopes)
        .map(Enum::name)
        .collect(Collectors.joining(","));
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
  @GetMapping("/shopify")
  public void initiateAuthorizationFlow(@RequestParam("shop") String shop,
                                        HttpServletResponse response) throws IOException {
    if (StringUtils.isEmpty(shop)) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST,
          "Missing shop parameter. Please add ?shop=your-shop.myshopify.com to your request");
      return;
    }

    final String state = nonceGenerator.get();
    final String redirectUri = callbackBaseUrl + "/shopify/callback";
    final String installUrl = "https://" + shop +
        "/admin/oauth/authorize?client_id=" + apiKey +
        "&scope=" + scopes +
        "&state=" + state +
        "&redirect_uri=" + redirectUri;

    response.addCookie(new Cookie("state", state));
    response.sendRedirect(installUrl);
  }

  /**
   * The OAuth authorization code callback. The eventual result is permanent access token that will be published via
   * provided {@link TokenCallback} instance. The token can be used then to access shop data within the provided scopes
   */
  @GetMapping("/shopify/callback")
  public void authorizationCallback(
      @RequestParam final String shop,
      @RequestParam final String hmac,
      @RequestParam final String code,
      @RequestParam final String state,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, URISyntaxException {

    //todo: res.status(400).send('Required parameters missing');

    final Optional<Cookie> cookieOptional = findStateCookie(request);
    if (cookieOptional.isEmpty()) {
      response.sendError(403, "Request origin cannot be verified");
      return;
    }

    if (!cookieOptional.get().getValue().equals(state)) {
      response.sendError(403, "Request origin cannot be verified");
      return;
    }


    /* First verify the request signature */
    if (!HttpRequestSignatureValidator.validateQueryParametersSignature(request, apiSecret, hmac)) {
      response.sendError(400, "HMAC validation failed");
      return;
    }

    final String token = fetchAccessToken(shop, code).orElseThrow(() -> new IOException("Failed to obtain token"));
    if (tokenCallback != null) {
      tokenCallback.tokenObtained(shop, token);
    }
  }

  private Optional<Cookie> findStateCookie(HttpServletRequest request) {
    return Arrays.stream(request.getCookies()).filter(cookie -> "state".equals(cookie.getName())).findAny();
  }

  private Optional<String> fetchAccessToken(@RequestParam String shop, @RequestParam String code) throws URISyntaxException {
    final Map map = restTemplate.postForObject(
        new URI("https", shop, "/admin/oauth/access_token", null),
        Map.of("client_id", apiKey, "client_secret", apiSecret, "code", code), Map.class);

    if (map != null) {
      return Optional.ofNullable(String.valueOf(map.get("access_token")));
    }
    return Optional.empty();
  }

  public void setTokenCallback(TokenCallback tokenCallback) {
    this.tokenCallback = tokenCallback;
  }

  public void setNonceGenerator(Supplier<String> nonceGenerator) {
    if (nonceGenerator != null) {
      this.nonceGenerator = nonceGenerator;
    }
  }

  Supplier<String> getNonceGenerator() {
    return nonceGenerator;
  }

  /**
   * Called when permanent token is obtained, which is the very last step of authentication process
   */
  public interface TokenCallback {
    void tokenObtained(String shop, String token);
  }
}
