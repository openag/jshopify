package openag.shopify.web;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpRequestSignatureValidatorTest {

  @Test
  public void testValidateQueryParametersSignature() {
    final MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("code", "c246c92e2af52afd311cbdbac94e95cd");
    request.addParameter("hmac", "d2b47dcff737be57eb99117626071000a16370b805b5c47f40fbf7157ad7e496");
    request.addParameter("shop", "maus-dev-store.myshopify.com");
    request.addParameter("state", "1535400343315");
    request.addParameter("timestamp", "1535400621");

    assertTrue(
        HttpRequestSignatureValidator.validateQueryParametersSignature(request,
            "d1cc75c3992a4fc6c40d384b081a6034",
            "d2b47dcff737be57eb99117626071000a16370b805b5c47f40fbf7157ad7e496"));
  }

  @Test
  public void testValidateBodySignature() throws IOException {
    final MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("x-shopify-hmac-sha256", "5GHsO3ki8WYNFpeAIpwMX8P3CPyPagd1HFA5iLrGSXY=");
    request.setContent(StreamUtils.copyToByteArray(getClass().getResourceAsStream("sample_webhook_1.json")));

    final Optional<String> optional = HttpRequestSignatureValidator.validateBodySignature(request,
        "6f93b5a75023fb4756c46c056399ba2028c640461cc57c3efcb06a31bcf8c5d4");

    assertTrue(optional.isPresent());
  }

}
