package openag.shopify.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ProductVariant {

  private String barcode;
  private BigDecimal compareAtPrice;
  private Date createdAt;
  private String fulfillmentService;
  private Integer grams;
  private long id;
  private Long imageId;
  private Long inventoryItemId;
  private String inventoryManagement;
  //  private String inventory_policy; todo:
  //todo: metafields;
  private String option1;
  private String option2;
  private String option3;
  private int position;
  private BigDecimal price;
  private long productId;
  private boolean requiresShipping;
  private String sku;
  private boolean taxable;
  //todo: tax_code
  private String title;
  private Date updatedAt;
  private Integer weight;
  private String weightUnit;

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public BigDecimal getCompareAtPrice() {
    return compareAtPrice;
  }

  public void setCompareAtPrice(BigDecimal compareAtPrice) {
    this.compareAtPrice = compareAtPrice;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getFulfillmentService() {
    return fulfillmentService;
  }

  public void setFulfillmentService(String fulfillmentService) {
    this.fulfillmentService = fulfillmentService;
  }

  public Integer getGrams() {
    return grams;
  }

  public void setGrams(Integer grams) {
    this.grams = grams;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getImageId() {
    return imageId;
  }

  public void setImageId(Long imageId) {
    this.imageId = imageId;
  }

  public Long getInventoryItemId() {
    return inventoryItemId;
  }

  public void setInventoryItemId(Long inventoryItemId) {
    this.inventoryItemId = inventoryItemId;
  }

  public String getInventoryManagement() {
    return inventoryManagement;
  }

  public void setInventoryManagement(String inventoryManagement) {
    this.inventoryManagement = inventoryManagement;
  }

  public String getOption1() {
    return option1;
  }

  public void setOption1(String option1) {
    this.option1 = option1;
  }

  public String getOption2() {
    return option2;
  }

  public void setOption2(String option2) {
    this.option2 = option2;
  }

  public String getOption3() {
    return option3;
  }

  public void setOption3(String option3) {
    this.option3 = option3;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public boolean isRequiresShipping() {
    return requiresShipping;
  }

  public void setRequiresShipping(boolean requiresShipping) {
    this.requiresShipping = requiresShipping;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public boolean isTaxable() {
    return taxable;
  }

  public void setTaxable(boolean taxable) {
    this.taxable = taxable;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public String getWeightUnit() {
    return weightUnit;
  }

  public void setWeightUnit(String weightUnit) {
    this.weightUnit = weightUnit;
  }
}
