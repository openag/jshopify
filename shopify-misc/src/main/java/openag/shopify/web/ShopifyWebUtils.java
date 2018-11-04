package openag.shopify.web;

import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

public class ShopifyWebUtils {

  /**
   * Prints HTTP request contents to the standard output, for debugging purposes mainly
   */
  public static void printRequest(HttpServletRequest request) {
    sout(request.getMethod() + " " + request.getRequestURI());

    final Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      final String name = headerNames.nextElement();
      sout(">> " + name + " : " + Collections.list(request.getHeaders(name)));
    }

    for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
      sout(">>>> " + entry.getKey() + " : " + Arrays.toString(entry.getValue()));
    }

    try {
      final String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName(request.getCharacterEncoding()));
      sout(">>>>>>>");
      sout(body);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void sout(String s) {
    System.out.println(s);
  }

}
