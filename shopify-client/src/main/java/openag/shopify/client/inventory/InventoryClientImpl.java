package openag.shopify.client.inventory;

import openag.shopify.client.http.Http;
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
    return http.list(e -> e.path("/locations.json"), Location.class);
  }

  @Override
  public Optional<Location> getLocation(long id) {
    return http.getOptional(e -> e.path("/locations/#{location_id}.json").pathVariable(id), Location.class);
  }

  @Override
  public List<InventoryLevel> getInventoryLevels(InventoryLevelsRequest request) {
    return http.list(e -> e.path("/inventory_levels.json").queryParams(request.params()), InventoryLevel.class);
  }
}
