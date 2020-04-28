package openag.shopify.client.product;

import java.util.HashMap;
import java.util.Map;

public class CollectListRequest {

  /**
   * Retrieve only collects for a certain product
   */
  private Long productId;

  /**
   * Retrieve only collects for a certain collection
   */
  private Long collectionId;

  public static CollectListRequest forCollection(long collectionId) {
    return new CollectListRequest().collectionId(collectionId);
  }

  public CollectListRequest productId(long productId) {
    this.productId = productId;
    return this;
  }

  public CollectListRequest collectionId(long collectionId) {
    this.collectionId = collectionId;
    return this;
  }

  public Map<String, String> params() {
    final Map<String, String> params = new HashMap<>();
    if (productId != null) {
      params.put("product_id", String.valueOf(productId));
    }
    if (collectionId != null) {
      params.put("collection_id", String.valueOf(collectionId));
    }
    return params;
  }
}
