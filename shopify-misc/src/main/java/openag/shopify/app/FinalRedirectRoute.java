package openag.shopify.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Possibility to perform a final redirect after access token is acquired. Typically show some information about
 * successful authorization or redirect back to shopify admin
 */
public interface FinalRedirectRoute {

  void execute(String shop, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
