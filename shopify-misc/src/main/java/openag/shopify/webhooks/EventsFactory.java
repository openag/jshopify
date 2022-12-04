package openag.shopify.webhooks;

import com.google.gson.JsonObject;
import openag.shopify.domain.Product;
import openag.shopify.domain.Webhook;
import openag.shopify.events.ProductEvent;
import openag.shopify.events.ShopifyEvent;

import static openag.shopify.GsonUtils.gson;

public class EventsFactory {

  public static <T> ShopifyEvent<T> fromJsonObject(String domain, Webhook.Topic topic, JsonObject json) {
    switch (topic) {
      case products_create:
        return cast(new ProductEvent.Created(gson.fromJson(json, Product.class), domain));
      case products_update:
        return cast(new ProductEvent.Updated(gson.fromJson(json, Product.class), domain));
      case products_delete:
        return cast(new ProductEvent.Deleted(gson.fromJson(json, Product.class), domain));
    }
    return null;
  }


  private static <T> T cast(Object o) {
    return (T) o;
  }
}
