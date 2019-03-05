package openag.shopify.client.inventory;

import openag.shopify.client.PaginatedRequest;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryLevelsRequest extends PaginatedRequest<InventoryLevelsRequest> {

  /**
   * list of inventory item IDs.
   */
  private long[] inventoryItemIds;

  /**
   * location IDs
   */
  private long[] locationIds;

  public InventoryLevelsRequest locations(long... locations) {
    if (locations != null) {
      this.locationIds = locations;
    }
    return this;
  }

  public InventoryLevelsRequest inventoryItems(long... items) {
    if (items != null) {
      this.inventoryItemIds = items;
    }
    return this;
  }

  @Override
  public Map<String, String> params() {
    final Map<String, String> params = super.params();
    if (this.inventoryItemIds != null) {
      params.put("inventory_item_ids", Arrays.stream(this.inventoryItemIds).mapToObj(String::valueOf)
          .collect(Collectors.joining(",")));
    }
    if (this.locationIds != null) {
      params.put("location_ids", Arrays.stream(this.locationIds).mapToObj(String::valueOf)
          .collect(Collectors.joining(",")));
    }
    return params;
  }
}
