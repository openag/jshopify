package openag.shopify.events;

/**
 * Marker interface for all Shopify events
 */
public interface ShopifyEvent<T> {

  T getTarget();

}
