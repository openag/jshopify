package openag.shopify.spring;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Predicate;

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
    return domainPredicate.test(request.getHeader("x-shopify-shop-domain"));
  }
}
