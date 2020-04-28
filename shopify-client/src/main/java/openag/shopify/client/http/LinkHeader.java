package openag.shopify.client.http;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LinkHeader {
  private static Pattern LINK_HEADER_PATTERN = Pattern.compile("<.+?>;\\s*?rel=\"\\w+?\"");

  private final String url;
  private final String rel;

  private LinkHeader(String url, String rel) {
    this.url = url;
    this.rel = rel;
  }

  public String getUrl() {
    return url;
  }

  public String getRel() {
    return rel;
  }

  public static LinkHeader parse(String s) {
    final String link = s.substring(s.indexOf('<') + 1, s.indexOf('>'));

    final int i = s.indexOf("rel=\"") + 5;
    final String rel = s.substring(i, s.indexOf("\"", i));

    return new LinkHeader(link, rel);
  }

  public static List<LinkHeader> parseAll(String s) {
    final List<LinkHeader> result = new LinkedList<>();

    final Matcher matcher = LINK_HEADER_PATTERN.matcher(s);
    while (matcher.find()) {
      result.add(parse(matcher.group()));
    }

    return result;
  }

  @Override
  public String toString() {
    return "LinkHeader{" +
        "url='" + url + '\'' +
        ", rel='" + rel + '\'' +
        '}';
  }
}
