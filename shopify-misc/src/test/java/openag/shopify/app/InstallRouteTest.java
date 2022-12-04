package openag.shopify.app;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import openag.shopify.client.AccessScope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InstallRouteTest {

  private InstallRoute route;

  private final MockHttpServletResponse response = new MockHttpServletResponse();

  @BeforeEach
  public void setUp() {
    this.route = new InstallRoute("12345",
        shop -> List.of(AccessScope.read_products),
        "https://example.com/shopify/callback");
    route.setNonceGenerator(shop -> "NONCE1");
  }

  @Test
  public void test_happy_path() throws IOException {
    route.initiateAuthorizationFlow("testshop.myshopify.com", "", 0L, response);

    final String expected = "https://testshop.myshopify.com/admin/oauth/authorize?" +
        "client_id=12345&scope=read_products&state=NONCE1&redirect_uri=https://example.com/shopify/callback";

    assertEquals(expected, response.getRedirectedUrl());

    final Cookie cookie = response.getCookie("state");
    assertNotNull(cookie);
    assertEquals("NONCE1", cookie.getValue());
  }

  @Test
  public void test_shop_parameter_missing() throws IOException {
    route.initiateAuthorizationFlow("", "", 0L, response);
    assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    assertEquals("Missing shop parameter. Please add ?shop=your-shop.myshopify.com to your request", response.getErrorMessage());
  }
}
