package openag.shopify.spring;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.function.Predicate;

import static openag.shopify.Constants.HTTP_HEADER_SHOPIFY_SHOP_DOMAIN;

/**
 * Blocks any request that is not originated from the desired shop (based on 'x-shopify-shop-domain' request header)
 */
public class ShopDomainBlockingInterceptor implements HandlerInterceptor {

  private final Predicate<String> domainPredicate;

  public ShopDomainBlockingInterceptor(Predicate<String> domainPredicate) {
    if (domainPredicate == null) {
      throw new IllegalArgumentException("Domain predicate can't be null");
    }
    this.domainPredicate = domainPredicate;
  }

  public ShopDomainBlockingInterceptor(String domain) {
    this(s -> domain != null && domain.equals(s));
  }


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    return domainPredicate.test(request.getHeader(HTTP_HEADER_SHOPIFY_SHOP_DOMAIN));
  }
}
