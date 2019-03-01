package openag.shopify.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShopifyApplicationAuthorizationControllerTest {

  private ShopifyApplicationAuthorizationController controller;

  @Before
  public void setUp() throws Exception {
    controller = new ShopifyApplicationAuthorizationController("key1", "secret1",
        "https://example.com", "read_products");
  }

  @Test
  public void test_default_nonce() {
    final String nonce = controller.getNonceGenerator().get();
    assertTrue(nonce.length() >= 24);
  }

  @Test
  public void test_initiateAuthorizationFlow_happyPath() throws IOException {
    final MockHttpServletResponse response = new MockHttpServletResponse();

    controller.setNonceGenerator(() -> "NONCE");
    controller.initiateAuthorizationFlow("myshop.myshopify.com", response);

    final String expected = "https://myshop.myshopify.com/admin/oauth/authorize?" +
        "client_id=key1&scope=read_products&state=NONCE&redirect_uri=https://example.com/shopify/callback";

    assertEquals(expected, response.getRedirectedUrl());
  }
}