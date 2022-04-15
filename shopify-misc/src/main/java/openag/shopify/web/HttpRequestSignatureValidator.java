package openag.shopify.web;

import openag.shopify.Constants;
import openag.shopify.HmacHash;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static openag.shopify.ShopifyUtils.hmac;

/**
 * HTTP Request signature validation routines for requests issued by Shopify (webhooks, oauth)
 */
public class HttpRequestSignatureValidator {

  private HttpRequestSignatureValidator() {
  }

  /**
   * Verifies HMAC signature using request query parameters. This verification typically applied to HTTP GET requests
   * originated from Shopify (Authentication, Proxies, etc)
   *
   * @param request {@link HttpServletRequest} instance
   * @param secret  the secret to be used to create signature
   * @param hmac    the valid hash to be compared with
   * @return true if request signature is correct; false otherwise
   */
  public static boolean validateQueryParametersSignature(HttpServletRequest request, String secret, HmacHash hmac) {
    final Set<String> keys = new TreeSet<>(request.getParameterMap().keySet());
    keys.remove("signature");
    keys.remove("hmac");

    final String message = keys.stream()
        .map(key -> String.format("%s=%s", key, request.getParameter(key)))
        .collect(Collectors.joining("&"));

    return hmac(message, secret).equals(hmac);
  }

  /**
   * Verifies HMAC signature using request body. This verification typically applied to HTTP POST requests originated
   * from Shopify, like WebHooks
   *
   * @param request {@link HttpServletRequest} instance
   * @param secret  the secret to be used to create signature
   * @return response body if validation was successful; empty optional otherwise
   */
  public static Optional<String> validateBodySignature(HttpServletRequest request, String secret) throws IOException {
    final HmacHash hmac = HmacHash.fromBase64(request.getHeader(Constants.HTTP_HEADER_SHOPIFY_SHOPIFY_HMAC_SHA_256));
    final String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
    if (hmac(body, secret).equals(hmac)) {
      return Optional.of(body);
    }
    return Optional.empty();
  }
}
