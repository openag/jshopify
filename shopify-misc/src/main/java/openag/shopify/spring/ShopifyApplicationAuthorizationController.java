package openag.shopify.spring;

import openag.shopify.app.CallbackRoute;
import openag.shopify.app.FinalRedirectRoute;
import openag.shopify.app.InstallRoute;
import openag.shopify.app.TokenCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller typically used on the application server-side to perform initial installation and authorization of custom
 * Shopify application
 * <p>
 * Based on tutorial: https://help.shopify.com/en/api/tutorials/building-node-app
 */
@RestController
public class ShopifyApplicationAuthorizationController {

  private final InstallRoute installRoute;
  private final CallbackRoute callbackRoute;
  private final FinalRedirectRoute redirectRoute;

  public ShopifyApplicationAuthorizationController(InstallRoute installRoute,
                                                   CallbackRoute callbackRoute,
                                                   FinalRedirectRoute redirectRoute) {
    this.installRoute = installRoute;
    this.callbackRoute = callbackRoute;
    this.redirectRoute = redirectRoute;
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
                                        @RequestParam("hmac") String hmac,
                                        @RequestParam("timestamp") long timestamp,
                                        HttpServletResponse response) throws IOException {
    installRoute.initiateAuthorizationFlow(shop, hmac, timestamp, response);
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
      HttpServletResponse response) throws IOException {
    if (callbackRoute.authorizationCallback(shop, hmac, code, state, request, response)) {
      redirectRoute.execute(shop, request, response);
    }
  }
}
