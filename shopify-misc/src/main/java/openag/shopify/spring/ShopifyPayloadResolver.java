package openag.shopify.spring;

import com.google.gson.JsonObject;
import openag.shopify.ShopifyUtils;
import openag.shopify.SigningKeyResolver;
import openag.shopify.web.HttpRequestSignatureValidator;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Spring MVC method argument resolver for HMAC-signed payloads (typically webhook calls). The resolver verifies the
 * HMAC signature on the request body and if it is correct converts json body to the desired object. The method argument
 * must be annotated with {@link ShopifyPayload}
 */
public class ShopifyPayloadResolver implements HandlerMethodArgumentResolver {

  private final SigningKeyResolver signingKeyResolver;

  public ShopifyPayloadResolver(SigningKeyResolver signingKeyResolver) {
    this.signingKeyResolver = signingKeyResolver;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(ShopifyPayload.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) throws Exception {

    final ShopifyPayload annotation = parameter.getParameterAnnotation(ShopifyPayload.class);

    //todo: support verifySignature == false

    /* Extracting request body and validating request signature in one go */
    final Optional<String> optional = HttpRequestSignatureValidator.validateBodySignature(
        (HttpServletRequest) webRequest.getNativeRequest(),
        signingKeyResolver.getKey(webRequest.getHeader("x-shopify-shop-domain")));

    if (optional.isPresent()) {
      if (annotation != null && annotation.wrapped()) {
        final JsonObject json = ShopifyUtils.gson.fromJson(optional.get(), JsonObject.class);
        return ShopifyUtils.gson.fromJson(json.get(json.keySet().iterator().next()), parameter.getParameterType());
      }
      return ShopifyUtils.gson.fromJson(optional.get(), parameter.getParameterType());
    }
    return null;
  }
}
