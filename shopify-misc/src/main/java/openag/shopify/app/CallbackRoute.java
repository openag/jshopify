package openag.shopify.app;

import openag.shopify.web.HttpRequestSignatureValidator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * The "Callback Route" of the Shopify app based on Java Servlet API
 */
public class CallbackRoute {

  protected final String apiSecret;

  /**
   * @param apiSecret the custom application API secret key
   */
  public CallbackRoute(String apiSecret) {
    this.apiSecret = apiSecret;
  }

  /**
   * The OAuth authorization code callback. The eventual result is permanent access token that will be published via
   * provided {@link TokenCallback} instance. The token can be used then to access shop data within the provided scopes
   *
   * @return true if authorization callback was successfully executed; false otherwise
   */
  public boolean authorizationCallback(
      final String shop,
      final String hmac,
      final String code,
      final String state,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    final Optional<Cookie> cookieOptional = findStateCookie(request);
    if (cookieOptional.isEmpty()) {
      response.sendError(403, "Request origin cannot be verified");
      return false;
    }

    if (!cookieOptional.get().getValue().equals(state)) {
      response.sendError(403, "Request origin cannot be verified");
      return false;
    }

    //todo: res.status(400).send('Required parameters missing');

    /* First verify the request signature */
    if (!HttpRequestSignatureValidator.validateQueryParametersSignature(request, apiSecret, hmac)) {
      response.sendError(400, "HMAC validation failed");
      return false;
    }

    authorizationSuccessful(shop, hmac, code, state);
    return true;
  }

  /**
   * Extension point, called on authorization; can be used to fetch access token, perform final redirect, show response
   * page etc
   */
  protected void authorizationSuccessful(String shop, String hmac, String authorizationCode, String state) {
    //no-op, override if needed
  }

  private static Optional<Cookie> findStateCookie(HttpServletRequest request) {
    return Arrays.stream(request.getCookies()).filter(cookie -> "state".equals(cookie.getName())).findAny();
  }
}
