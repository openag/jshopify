package openag.shopify.spring;

import openag.shopify.ShopifyUtils;
import openag.shopify.Signed;
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
 * must be annotated with {@link Signed}
 */
public class ValidatingJsonBodyResolver implements HandlerMethodArgumentResolver {

  private final String secret;

  public ValidatingJsonBodyResolver(String secret) {
    this.secret = secret;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(Signed.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) throws Exception {

    final Optional<String> optional = HttpRequestSignatureValidator.validateBodySignature((HttpServletRequest) webRequest.getNativeRequest(), secret);
    if (optional.isPresent()) {
      return ShopifyUtils.gson.fromJson(optional.get(), parameter.getParameterType());
    }
    return null;
  }
}
