package openag.shopify.client.customer;

import openag.shopify.domain.Customer;

import java.util.Optional;

public interface CustomerClient {

  Optional<Customer> getCustomer(long id);

}
