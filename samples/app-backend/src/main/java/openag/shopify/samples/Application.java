package openag.shopify.samples;

import openag.shopify.app.AccessTokenCallbackRoute;
import openag.shopify.app.CallbackRoute;
import openag.shopify.app.InstallRoute;
import openag.shopify.app.StaticScopesProvider;
import openag.shopify.client.AccessScope;
import openag.shopify.spring.ShopifyApplicationAuthorizationController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

  private final String apiKey;
  private final String apiSecret;
  private final String callbackUrl;

  public Application(@Value("${shopify.api-key}") String apiKey,
                     @Value("${shopify.api-secret}") String apiSecret,
                     @Value("${shopify.callback-url}") String callbackUrl) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
    this.callbackUrl = callbackUrl;
  }

  @Bean
  public ShopifyApplicationAuthorizationController shopifyApplicationAuthorizationController() {

    // choose scopes needed for your app
    final StaticScopesProvider requestedScopes = new StaticScopesProvider(
        AccessScope.read_products,
        AccessScope.read_customers);

    final InstallRoute installRoute = new InstallRoute(apiKey, requestedScopes, callbackUrl);

    final CallbackRoute callbackRoute = new AccessTokenCallbackRoute(apiKey, apiSecret,
        (shop, accessToken, scopes) -> {
          //store the access token in order to access shop data later (withing requested scopes)
          System.out.println("Application successfully authorized with shop '" + shop + "', " +
              "access token retrieved: '" + accessToken + "' for scopes: " + scopes);
        });

    return new ShopifyApplicationAuthorizationController(
        installRoute,
        callbackRoute,
        (shop, request, response) -> response.sendRedirect("/authorized?shop=" + shop));
  }

  @RestController
  public static class AuthorizedController {

    @GetMapping("/authorized")
    public String successfullyAuthenticated(@RequestParam("shop") final String shop) {
      return "Shop " + shop + " successfully authorized!";
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
