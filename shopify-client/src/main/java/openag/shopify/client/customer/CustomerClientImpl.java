package openag.shopify.client.customer;

import openag.shopify.client.Http;
import openag.shopify.domain.Customer;

import java.util.Optional;

public class CustomerClientImpl implements CustomerClient {

  private final Http http;

  public CustomerClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public Optional<Customer> getCustomer(long id) {
    return http.get("/customers/#{customer_id}.json").pathVariable(id)
        .getOptional("customer", Customer.class);
  }
}
