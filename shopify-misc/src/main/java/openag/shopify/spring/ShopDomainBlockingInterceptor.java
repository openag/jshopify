package openag.shopify.spring;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Blocks any request that is not originated from the desired shop (based on 'x-shopify-shop-domain' request header)
 */
public class ShopDomainBlockingInterceptor implements HandlerInterceptor {

  private final String domain;

  public ShopDomainBlockingInterceptor(String domain) {
    this.domain = domain;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    return domain != null && domain.equals(request.getHeader("x-shopify-shop-domain"));
  }
}
