package openag.shopify.client.inventory;

import openag.shopify.client.http.Http;
import openag.shopify.client.http.Page;
import openag.shopify.client.http.ResponseType;
import openag.shopify.domain.InventoryLevel;
import openag.shopify.domain.Location;

import java.util.List;
import java.util.Optional;

import static openag.shopify.client.http.Request.get;

public class InventoryClientImpl implements InventoryClient {

  private final Http http;

  public InventoryClientImpl(Http http) {
    this.http = http;
  }

  @Override
  public List<Location> getLocations() {
    return http.exchange(get("/locations.json"),
        ResponseType.wrappedList("locations", Location.class));
  }

  @Override
  public Optional<Location> getLocation(long id) {
    return Optional.of(http.exchange(get("/locations/#{location_id}.json").pathVariable(id),
        ResponseType.wrappedObject("location", Location.class)));
  }

  @Override
  public Page<InventoryLevel> getInventoryLevels(InventoryLevelsRequest request) {
    return http.page(get("/inventory_levels.json").queryParams(request.params()),
        ResponseType.wrappedList("inventory_levels", InventoryLevel.class));
  }
}
