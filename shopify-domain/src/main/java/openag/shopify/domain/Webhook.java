package openag.shopify.domain;

import java.util.Arrays;
import java.util.Date;

public class Webhook {

  /**
   * URI where the webhook subscription should send the POST request when the event occurs.
   */
  private String address;

  /**
   * Date and time when the webhook subscription was created. The API returns this value in ISO 8601 format.
   */
  private Date createdAt;

  /**
   * Optional array of fields that should be included in the webhook subscription.
   */
  private String[] fields;

  /**
   * Format in which the webhook subscription should send the data. Valid values are "json" and "xml".
   */
  private String format = "json";

  /**
   * Unique numeric identifier for the webhook subscription.
   */
  private long id;

  /**
   * Optional array of namespaces for any metafields that should be included the webhook subscription.
   */
  private String[] metafieldNamespaces;

  /**
   * Event that triggers the webhook.
   */
  private String topic;

  /**
   * Date and time when the webhook subscription was updated.
   */
  private Date updatedAt;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String[] getFields() {
    return fields;
  }

  public void setFields(String[] fields) {
    this.fields = fields;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String[] getMetafieldNamespaces() {
    return metafieldNamespaces;
  }

  public void setMetafieldNamespaces(String[] metafieldNamespaces) {
    this.metafieldNamespaces = metafieldNamespaces;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "Webhook{" +
        "address='" + address + '\'' +
        ", createdAt=" + createdAt +
        ", fields=" + Arrays.toString(fields) +
        ", format='" + format + '\'' +
        ", id=" + id +
        ", metafieldNamespaces=" + Arrays.toString(metafieldNamespaces) +
        ", topic='" + topic + '\'' +
        ", updatedAt=" + updatedAt +
        '}';
  }

  /**
   * All events that triggers the webhook.
   */
  public enum Topic {
    app_uninstalled("app/uninstalled"),
    carts_create("carts/create"),
    carts_update("carts/update"),
    checkouts_create("checkouts/create"),
    checkouts_delete("checkouts/delete"),
    checkouts_update("checkouts/update"),
    collection_listings_add("collection_listings/add"),
    collection_listings_remove("collection_listings/remove"),
    collection_listings_update("collection_listings/update"),
    collections_create("collections/create"),
    collections_delete("collections/delete"),
    collections_update("collections/update"),
    customer_groups_create("customer_groups/create"),
    customer_groups_delete("customer_groups/delete"),
    customer_groups_update("customer_groups/update"),
    customers_create("customers/create"),
    customers_delete("customers/delete"),
    customers_disable("customers/disable"),
    customers_enable("customers/enable"),
    customers_update("customers/update"),
    draft_orders_create("draft_orders/create"),
    draft_orders_delete("draft_orders/delete"),
    draft_orders_update("draft_orders/update"),
    fulfillment_events_create("fulfillment_events/create"),
    fulfillment_events_delete("fulfillment_events/delete"),
    fulfillments_create("fulfillments/create"),
    fulfillments_update("fulfillments/update"),
    order_transactions_create("order_transactions/create"),
    orders_cancelled("orders/cancelled"),
    orders_create("orders/create"),
    orders_delete("orders/delete"),
    orders_fulfilled("orders/fulfilled"),
    orders_paid("orders/paid"),
    orders_partially_fulfilled("orders/partially_fulfilled"),
    orders_updated("orders/updated"),
    product_listings_add("product_listings/add"),
    product_listings_remove("product_listings/remove"),
    product_listings_update("product_listings/update"),
    products_create("products/create"),
    products_delete("products/delete"),
    products_update("products/update"),
    refunds_create("refunds/create"),
    shop_update("shop/update"),
    themes_create("themes/create"),
    themes_delete("themes/delete"),
    themes_publish("themes/publish"),
    themes_update("themes/update"),
    inventory_levels_connect("inventory_levels/connect"),
    inventory_levels_update("inventory_levels/update"),
    inventory_levels_disconnect("inventory_levels/disconnect"),
    inventory_items_create("inventory_items/create"),
    inventory_items_update("inventory_items/update"),
    inventory_items_delete("inventory_items/delete"),
    locations_create("locations/create"),
    locations_update("locations/update"),
    locations_delete("locations/delete"),
    unknown("");

    private final String topic;

    Topic(String topic) {
      this.topic = topic;
    }

    public String getTopic() {
      return topic;
    }

    public static Topic parse(String s) {
      for (Topic topic : values()) {
        if (topic.topic.equalsIgnoreCase(s)) {
          return topic;
        }
      }
      return unknown;
    }
  }

}
