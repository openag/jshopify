package openag.shopify.client.http;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UrlBuilderTest {

  private final UrlBuilder urlBuilder =
      UrlBuilder.forApi("test.myshopify.com", "2019-07");

  @Test
  public void test_build_url_with_single_variable() {
    final String url = urlBuilder.url("/customers/#{customer_id}.json",
        Collections.singletonList(String.valueOf(17)), Collections.emptyMap());
    assertEquals("https://test.myshopify.com/admin/api/2019-07/customers/17.json", url);
  }

  @Test
  public void test_build_url_without_variables() {
    final String url = urlBuilder.url("/customers.json", Collections.emptyList(), Collections.emptyMap());
    assertEquals("https://test.myshopify.com/admin/api/2019-07/customers.json", url);
  }

  @Test
  public void test_build_url_with_query_parameters() {
    final String url = urlBuilder.url("/customers.json", Collections.emptyList(), Map.of("page", "2"));
    assertEquals("https://test.myshopify.com/admin/api/2019-07/customers.json?page=2", url);
  }

}
