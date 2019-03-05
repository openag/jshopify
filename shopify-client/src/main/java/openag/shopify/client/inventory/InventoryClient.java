package openag.shopify.client.inventory;

import openag.shopify.domain.InventoryLevel;
import openag.shopify.domain.Location;

import java.util.List;
import java.util.Optional;

public interface InventoryClient {

  /**
   * Retrieve a list of all locations
   */
  List<Location> getLocations();

  /**
   * Retrieves a single location by its ID
   */
  Optional<Location> getLocation(long id);

  /**
   * Retrieves a list of inventory levels. You must include inventory_item_ids, location_ids, or both as filter
   * parameters.
   */
  List<InventoryLevel> getInventoryLevels(InventoryLevelsRequest request);
}
