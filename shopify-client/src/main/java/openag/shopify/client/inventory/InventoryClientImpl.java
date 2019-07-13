package openag.shopify.client.inventory;

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
    return http.get("/locations.json")
        .list("locations", Location.class);
  }

  @Override
  public Optional<Location> getLocation(long id) {
    return http.get("/locations/#{location_id}.json").pathVariable(id)
        .getOptional("location", Location.class);
  }

  @Override
  public List<InventoryLevel> getInventoryLevels(InventoryLevelsRequest request) {
    return http.get("/inventory_levels.json")
        .queryParams(request.params())
        .list("inventory_levels", InventoryLevel.class);
  }
}
