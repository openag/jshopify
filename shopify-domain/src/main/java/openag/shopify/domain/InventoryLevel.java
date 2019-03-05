package openag.shopify.domain;

import java.util.Date;

public class InventoryLevel {

  /**
   * The quantity of inventory items available for sale. Returns null if the inventory item is not tracked.
   */
  private Integer available;

  /**
   * The ID of the inventory item that the inventory level belongs to.
   */
  private long inventoryItemId;

  /**
   * The ID of the location that the inventory level belongs to. To find the ID of the location, use the Location
   * resource.
   */
  private long locationId;

  /**
   * The date and time (ISO 8601 format) when the inventory level was last modified.
   */
  private Date updatedAt;

  public Integer getAvailable() {
    return available;
  }

  public void setAvailable(Integer available) {
    this.available = available;
  }

  public long getInventoryItemId() {
    return inventoryItemId;
  }

  public void setInventoryItemId(long inventoryItemId) {
    this.inventoryItemId = inventoryItemId;
  }

  public long getLocationId() {
    return locationId;
  }

  public void setLocationId(long locationId) {
    this.locationId = locationId;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "InventoryLevel{" +
        "available=" + available +
        ", inventoryItemId=" + inventoryItemId +
        ", locationId=" + locationId +
        ", updatedAt=" + updatedAt +
        '}';
  }
}
