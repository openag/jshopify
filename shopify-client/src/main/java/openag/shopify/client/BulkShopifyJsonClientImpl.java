package openag.shopify.client;

import com.google.gson.JsonObject;
import openag.shopify.client.http.Http;
import openag.shopify.client.http.ResponseType;

import java.util.Iterator;
import java.util.function.Supplier;

import static openag.shopify.client.http.Request.get;


class BulkShopifyJsonClientImpl implements BulkShopifyJsonClient {

  private final Http http;

  BulkShopifyJsonClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public Iterable<JsonObject> products() {
    return wrap(() -> http.iterate(get("/products.json"),
        ResponseType.wrappedList("products", JsonObject.class)));
  }

  @Override
  public Iterable<JsonObject> customers() {
    return wrap(() -> http.iterate(get("/customers.json"),
        ResponseType.wrappedList("customers", JsonObject.class)));
  }

  @Override
  public Iterable<JsonObject> orders() {
    return wrap(() -> http.iterate(get("/orders.json"),
        ResponseType.wrappedList("orders", JsonObject.class)));
  }

  private <T> Iterable<T> wrap(Supplier<Iterator<T>> supplier) {
    return supplier::get;
  }

}
