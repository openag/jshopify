package openag.shopify.events;

import openag.shopify.domain.Customer;

public class CustomerEvent extends AbstractShopifyEvent<Customer> {
  CustomerEvent(Customer target) {
    super(target);
  }

  public Customer getCustomer() {
    return getTarget();
  }

  public static class Created extends CustomerEvent {
    Created(Customer target) {
      super(target);
    }
  }

  public static class Updated extends CustomerEvent {
    Updated(Customer target) {
      super(target);
    }
  }

  public static class Deleted extends CustomerEvent {
    Deleted(Customer target) {
      super(target);
    }
  }

  public static class Enabled extends CustomerEvent {
    Enabled(Customer target) {
      super(target);
    }
  }

  public static class Disabled extends CustomerEvent {
    Disabled(Customer target) {
      super(target);
    }
  }

}
