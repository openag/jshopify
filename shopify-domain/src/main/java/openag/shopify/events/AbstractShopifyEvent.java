package openag.shopify.events;

public abstract class AbstractShopifyEvent<T> implements ShopifyEvent<T> {

  private final T target;
  private final String domain;

  AbstractShopifyEvent(T target) {
    this(target, null);
  }

  AbstractShopifyEvent(T target, String domain) {
    this.target = target;
    this.domain = domain;
  }

  @Override
  public T getTarget() {
    return target;
  }

  @Override
  public String getDomain() {
    return this.domain;
  }
}
