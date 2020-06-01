package openag.shopify.client;

/**
 * Shopify OAuth Access Scopes
 * <p>
 * https://help.shopify.com/en/api/getting-started/authentication/oauth/scopes#authenticated-access-scopes
 */
@SuppressWarnings("unused")
public enum AccessScope {

  /* Access to Article, Blog, Comment, Page, and Redirect. */
  read_content, write_content,

  /* Access to Asset and Theme. */
  read_themes, write_themes,

  /* Access to Product, Product Variant, Product Image, Collect, Custom Collection, and Smart Collection. */
  read_products, write_products,

  /* Access to Product Listing, and Collection Listing. */
  read_product_listings,

  /* Access to Customer and Saved Search. */
  read_customers, write_customers,

  /* Access to Order, Transaction and Fulfillment. */
  read_orders, write_orders,

  /* Grants access to all orders rather than the default window of 60 days worth of orders. This OAuth scope is used in conjunction with read_orders, or write_orders. You need to request this scope from your Partner Dashboard before adding it to your app. */
  read_all_orders,

  /* Access to Draft Order. */
  read_draft_orders, write_draft_orders,

  /* Access to Inventory Level and Inventory Item. */
  read_inventory, write_inventory,

  /* Access to Location. */
  read_locations,

  /* Access to Script Tag. */
  read_script_tags, write_script_tags,

  /* Access to Fulfillment Service. */
  read_fulfillments, write_fulfillments,

  /* Access to Carrier Service, Country and Province. */
  read_shipping, write_shipping,

  /* Access to Analytics API. */
  read_analytics,

  /* Access to User SHOPIFY PLUS. */
  read_users, write_users,

  /* Access to Checkouts. */
  read_checkouts, write_checkouts,

  /* Access to Reports. */
  read_reports, write_reports,

  /* Access to Price Rules. */
  read_price_rules, write_price_rules,

  /* Access to Marketing Event. */
  read_marketing_events, write_marketing_events,

  /* Access to ResourceFeedback. */
  read_resource_feedbacks, write_resource_feedbacks,

  /* Access to the Shopify Payments Payout, Balance, and Transaction resources. */
  read_shopify_payments_payouts,

  /* Access to the Shopify Payments Dispute resource. */
  read_shopify_payments_disputes,

  /* Unauthenticated access to read the Product and Collection objects. */
  unauthenticated_read_product_listings,

  /* Unauthenticated access to the Checkout object. */
  unauthenticated_write_checkouts,

  /* Unauthenticated access to the Customer object. */
  unauthenticated_write_customers,

  /* Unauthenticated access to read the tags field on the Customer object. */
  unauthenticated_read_customer_tags,

  /* Unauthenticated access to read storefront content, such as Article, Blog, and Comment objects. */
  unauthenticated_read_content;

  public static AccessScope parse(String s) {
    for (AccessScope scope : values()) {
      if (scope.name().equalsIgnoreCase(s)) {
        return scope;
      }
    }
    return null;
  }
}
