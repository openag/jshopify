package openag.shopify.spring;

import openag.shopify.web.ShopifyWebUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

//todo: implement Step 5: Request new access tokens https://help.shopify.com/en/api/getting-started/authentication/oauth/api-credential-rotation


//todo request throttling

/**
 * todo:
 * <p>
 * https://help.shopify.com/en/api/tutorials/building-node-app
 */
@RestController
public class ShopifyController {

  private final RestTemplate restTemplate = new RestTemplate();

  private final SecureRandom random = new SecureRandom();

  //todo: move to config
  String forwardingAddress = "https://"; // Replace this with your HTTPS Forwarding address
  String apiKey = "";
  String apiSecret = "";
  String scopes = "read_products";

  @GetMapping("/test")
  public String test() {
    return "OK " + System.currentTimeMillis();
  }

  /**
   * The "Install Route" of the Shopify app; triggered directly for manual installation if needed. The method initiates
   * OAuth Authorization Flow between app and the specified shop
   * <p>
   * Method generates initial Authorization Flow request URL and sends redirect to it. The redirected screen should show
   * the Shopify authorization screen on the specified shop
   */
  @GetMapping("/shopify")
  public void initiateAuthorizationFlow(@RequestParam("shop") String shop,
                                        HttpServletResponse response) throws IOException {
    if (StringUtils.isEmpty(shop)) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST,
          "Missing shop parameter. Please add ?shop=your-development-shop.myshopify.com to your request");
      return;
    }

    final String state = nonce();
    final String redirectUri = forwardingAddress + "/shopify/callback";
    final String installUrl = "https://" + shop +
        "/admin/oauth/authorize?client_id=" + apiKey +
        "&scope=" + scopes +
        "&state=" + state +
        "&redirect_uri=" + redirectUri;

    response.addCookie(new Cookie("state", state));
    response.sendRedirect(installUrl);
  }

  /**
   * todo:
   *
   * @param shop
   * @param hmac
   * @param code
   * @param state
   * @param request
   * @param response
   */
  @GetMapping("/shopify/callback")
  public void authorizationCallback(
      @RequestParam final String shop,
      @RequestParam final String hmac,
      @RequestParam final String code,
      @RequestParam final String state,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    /*
    First, the hmac parameter is removed from the hash.
    Next, the remaining keys in the hash are sorted lexicographically,
    and each key and value pair is joined with an ampersand (‘&’).
    The resulting string is hashed with the SHA256 algorithm using the application’s secret key as the encryption key.
    The resulting digest is compared against the hmac parameter provided in the request. If the comparison is successful, you can be assured that this is a legitimate request from Shopify.


     */

    //todo:
//      const stateCookie = cookie.parse(req.headers.cookie).state;
//    if (state !== stateCookie) {
//      return res.status(403).send('Request origin cannot be verified');
//    }

    //todo: check required parameters

    /* todo: validate hmac */

//    if (!hmac(message, apiSecret).equals(hmac)) {
//      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "HMAC validation failed");
//      return;
//    }

//    fetchAccessToken(shop, code).ifPresent(ShopifyUtils::sout);
//c6ea93adffc1014987019be2f5682a28
  }


  @PostMapping("/webhook")
  public void webhook(HttpServletRequest request) {
    ShopifyWebUtils.printRequest(request);
  }

  private Optional<String> fetchAccessToken(@RequestParam String shop, @RequestParam String code) {
    final Map map = restTemplate.postForObject(
        "https://" + shop + "/admin/oauth/access_token",
        Map.of("client_id", apiKey, "client_secret", apiSecret, "code", code), Map.class);

    if (map != null) {
      return Optional.ofNullable(String.valueOf(map.get("access_token")));
    }
    return Optional.empty();
  }

  private String nonce() { //todo: use SecureRandom!
    return String.valueOf(System.currentTimeMillis());
  }


}
