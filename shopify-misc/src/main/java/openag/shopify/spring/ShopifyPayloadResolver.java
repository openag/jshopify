package openag.shopify.spring;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import openag.shopify.GsonUtils;
import openag.shopify.ShopifyUtils;
import openag.shopify.SigningKeyResolver;
import openag.shopify.web.HttpRequestSignatureValidator;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

/**
 * Spring MVC method argument resolver for HMAC-signed payloads (typically webhook calls). The resolver verifies the
 * HMAC signature on the request body and if it is correct converts json body to the desired object. The method argument
 * must be annotated with {@link ShopifyPayload}
 */
public class ShopifyPayloadResolver implements HandlerMethodArgumentResolver {

  private static final Gson gson = GsonUtils.gson;

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

    //todo: support verifySignature == false  ;;;  todo: key can be null!

    /* Extracting request body and validating request signature in one go */
    final Optional<String> optional = HttpRequestSignatureValidator.validateBodySignature(
        (HttpServletRequest) webRequest.getNativeRequest(),
        signingKeyResolver.getKey(webRequest.getHeader("x-shopify-shop-domain")));

    if (optional.isPresent()) {
      final Class<?> parameterType = parameter.getParameterType();
      final JsonObject json = gson.fromJson(optional.get(), JsonObject.class);

      final boolean returnJson = JsonObject.class.isAssignableFrom(parameterType);
      if (returnJson) {
        return json;
      }

      if (annotation != null && annotation.wrapped()) {
        return gson.fromJson(json.get(json.keySet().iterator().next()), parameterType);
      }

      return gson.fromJson(optional.get(), parameterType);
    }
    return null;
  }
}
