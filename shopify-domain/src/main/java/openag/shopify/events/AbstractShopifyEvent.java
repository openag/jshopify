package openag.shopify.events;

abstract class AbstractShopifyEvent<T> implements ShopifyEvent<T> {

  private final T target;

  AbstractShopifyEvent(T target) {
    this.target = target;
  }

  @Override
  public T getTarget() {
    return target;
  }
}
