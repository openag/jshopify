package openag.shopify;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HmacHashTest {

  @Test
  void test_hex_conversion_loop() {
    final String hex = "d2b47dcff737be57eb99117626071000a16370b805b5c47f40fbf7157ad7e496";
    assertEquals(hex, HmacHash.fromHex(hex).toHexString());
  }
}
