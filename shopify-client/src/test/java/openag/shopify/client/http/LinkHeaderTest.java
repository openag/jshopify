package openag.shopify.client.http;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LinkHeaderTest {

  @Test
  public void test_parse() {
    final LinkHeader linkHeader = LinkHeader.parse("<https://my-dev-store.myshopify.com/admin/api/2020-04/products.json?limit=50&page_info=eyJkaXJlY3Rpb24iOiJwcmV2IiwibGFzdF9pZCI6MTY0ODczOTA5MDQ5OCwibGFzdF92YWx1ZSI6IlwiT3B0aW1hXCIgTXVsdGktVGVuc2UgQW50aS1Xcmlua2xlIEV5ZSBTZXJ1bSAyNSBtbCJ9>; rel=\"previous\"");
    assertEquals("https://my-dev-store.myshopify.com/admin/api/2020-04/products.json?limit=50&page_info=eyJkaXJlY3Rpb24iOiJwcmV2IiwibGFzdF9pZCI6MTY0ODczOTA5MDQ5OCwibGFzdF92YWx1ZSI6IlwiT3B0aW1hXCIgTXVsdGktVGVuc2UgQW50aS1Xcmlua2xlIEV5ZSBTZXJ1bSAyNSBtbCJ9", linkHeader.getUrl());
    assertEquals("previous", linkHeader.getRel());
  }

  @Test
  public void test_parseAll() {
    final List<LinkHeader> links = LinkHeader.parseAll("<https://my-dev-store.myshopify.com/admin/api/2020-04/products.json?limit=50&page_info=eyJkaXJlY3Rpb24iOiJwcmV2IiwibGFzdF9pZCI6MTY0ODczOTA5MDQ5OCwibGFzdF92YWx1ZSI6IlwiT3B0aW1hXCIgTXVsdGktVGVuc2UgQW50aS1Xcmlua2xlIEV5ZSBTZXJ1bSAyNSBtbCJ9>; rel=\"previous\", <https://skinlab-dev-store.myshopify.com/admin/api/2020-04/products.json?limit=50&page_info=eyJkaXJlY3Rpb24iOiJuZXh0IiwibGFzdF9pZCI6MTY0ODY5MzYwODUxNCwibGFzdF92YWx1ZSI6IjI1LjA2LjIwMTggdW0gMTQuMzAgVWhyIFdvcmtzaG9wIFwiTU9ERUxJTkcgQk9EWSBTWVNURU0g4oCTIERJRSBLVU5TVCBERVIgTU9ERUxMSUVSVU5HIERFUiBTSUxIT1VFVFRFXCIifQ>; rel=\"next\"");
    assertEquals(2, links.size());

    LinkHeader link = links.get(0);
    assertEquals("https://my-dev-store.myshopify.com/admin/api/2020-04/products.json?limit=50&page_info=eyJkaXJlY3Rpb24iOiJwcmV2IiwibGFzdF9pZCI6MTY0ODczOTA5MDQ5OCwibGFzdF92YWx1ZSI6IlwiT3B0aW1hXCIgTXVsdGktVGVuc2UgQW50aS1Xcmlua2xlIEV5ZSBTZXJ1bSAyNSBtbCJ9", link.getUrl());
    assertEquals("previous", link.getRel());

    link = links.get(1);
    assertEquals("https://skinlab-dev-store.myshopify.com/admin/api/2020-04/products.json?limit=50&page_info=eyJkaXJlY3Rpb24iOiJuZXh0IiwibGFzdF9pZCI6MTY0ODY5MzYwODUxNCwibGFzdF92YWx1ZSI6IjI1LjA2LjIwMTggdW0gMTQuMzAgVWhyIFdvcmtzaG9wIFwiTU9ERUxJTkcgQk9EWSBTWVNURU0g4oCTIERJRSBLVU5TVCBERVIgTU9ERUxMSUVSVU5HIERFUiBTSUxIT1VFVFRFXCIifQ", link.getUrl());
    assertEquals("next", link.getRel());
  }
}
