package openag.shopify.client.inventory;

import com.google.gson.reflect.TypeToken;
import openag.shopify.client.Http;
import openag.shopify.domain.InventoryLevel;
import openag.shopify.domain.Location;

import java.util.List;
import java.util.Optional;

public class InventoryClientImpl implements InventoryClient {

  private final Http http;

  public InventoryClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public List<Location> getLocations() {
    return http.get("/admin/locations.json")
        .list((gson, json) -> gson.fromJson(json.getAsJsonArray("locations"),
            new TypeToken<List<Location>>() {
            }.getType()));
  }

  @Override
  public Optional<Location> getLocation(long id) {
    return http.get("/admin/locations/" + id + ".json")
        .getOne((gson, json) -> gson.fromJson(json.getAsJsonObject("location"), Location.class));
  }

  @Override
  public List<InventoryLevel> getInventoryLevels(InventoryLevelsRequest request) {
    return http.get("/admin/inventory_levels.json")
        .params(request.params())
        .list((gson, json) -> gson.fromJson(json.getAsJsonArray("inventory_levels"),
            new TypeToken<List<InventoryLevel>>() {
            }.getType()));
  }
}
