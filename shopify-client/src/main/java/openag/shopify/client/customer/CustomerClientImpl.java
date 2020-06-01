package openag.shopify.client.customer;

import openag.shopify.client.http.Http;
import openag.shopify.client.http.ResponseType;
import openag.shopify.domain.Customer;

import java.util.Optional;

import static openag.shopify.client.http.Request.get;

public class CustomerClientImpl implements CustomerClient {

  private final Http http;

  public CustomerClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public Optional<Customer> getCustomer(long id) {
    return Optional.of(http.exchange(get("/customers/#{customer_id}.json").pathVariable(id),
        ResponseType.wrappedObject("customer", Customer.class)));
  }
}
