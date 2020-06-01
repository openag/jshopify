package openag.shopify.client.admin;

import com.google.gson.JsonObject;
import openag.shopify.client.AccessScope;
import openag.shopify.client.http.Http;
import openag.shopify.client.http.HttpFactory;
import openag.shopify.client.http.UrlBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static openag.shopify.client.http.Request.post;
import static openag.shopify.client.http.ResponseType.jsonObject;

public class AdminClientImpl implements AdminClient {
  private final Http http;

  private final String apiKey;
  private final String apiSecret;

  public AdminClientImpl(final String shop,
                         final String apiKey,
                         final String apiSecret) {
    http = HttpFactory.newHttp(UrlBuilder.absolute(shop), null);
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }

  @Override
  public Optional<AccessTokenResponse> fetchAccessToken(String authorizationCode) {
    final Map<String, String> body = Map.of(
        "client_id", apiKey,
        "client_secret", apiSecret,
        "code", authorizationCode);

    final JsonObject json = http.exchange(post("/admin/oauth/access_token")
            .body(body)
            .contentType(Http.ContentType.FORM_URL_ENCODED),
        jsonObject(JsonObject.class));

    if (json == null) {
      return Optional.empty();
    }

    final List<AccessScope> scopes = Arrays.stream(json.get("scope").getAsString().split(","))
        .map(AccessScope::parse)
        .collect(Collectors.toList());

    return Optional.of(new AccessTokenResponse(
        json.get("access_token").getAsString(), scopes));
  }
}
