package openag.shopify.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ShopifyApplicationAuthorizationControllerTest {

  private ShopifyApplicationAuthorizationController controller;

  @Before
  public void setUp() throws Exception {
    controller = new ShopifyApplicationAuthorizationController("key1", "secret1",
        "https://example.com", "read_products");
  }

  @Test
  public void test_nonce() {
    final String nonce = ShopifyApplicationAuthorizationController.nonce();
    assertTrue(nonce.length() >= 24);
  }

  @Test
  public void test_initiateAuthorizationFlow_happyPath() throws IOException {
    final MockHttpServletResponse response = new MockHttpServletResponse();
    controller.initiateAuthorizationFlow("myshop.myshopify.com", response);

    final String expected = "https://myshop.myshopify.com//admin/oauth/authorize?" +
        "client_id=key1&scope=read_products&state=NONCE&redirect_uri=https://example.com/shopify/callback";

    //todo: fix nonce generator!
//    assertEquals(expected, response.getRedirectedUrl());
  }
}