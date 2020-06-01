package openag.shopify.client.http;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UrlBuilder {
  private static final Pattern VARIABLE_MATCH = Pattern.compile("#\\{\\w+}");

  private final String baseUrl;

  private UrlBuilder(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public static UrlBuilder forApi(String domain, String apiVersion) {
    return new UrlBuilder("https://" + domain + "/admin/api/" + apiVersion);
  }

  public static UrlBuilder absolute(String domain) {
    return new UrlBuilder("https://" + domain);
  }

  String url(String path, List<String> pathParams, Map<String, String> queryParams) {
    if (pathParams.isEmpty() && queryParams.isEmpty()) {
      return baseUrl + path;
    }

    final StringBuilder sb = new StringBuilder(baseUrl);
    if (!pathParams.isEmpty()) {
      final Iterator<String> it = pathParams.iterator();
      sb.append(VARIABLE_MATCH.matcher(path).replaceAll(matchResult -> it.next()));
    } else {
      sb.append(path);
    }

    if (!queryParams.isEmpty()) {
      sb.append("?").append(
          queryParams.entrySet().stream()
              .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
              .collect(Collectors.joining("&")));
    }
    return sb.toString();
  }
}
