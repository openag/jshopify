package openag.shopify.domain;

import java.util.Date;

public class Metafield {

  private Date createdAt;
  private Date updatedAt;
  private String description;
  private long id;
  private String key;
  private String namespace;
  private long ownerId;
  private String ownerResource;
  private String value;
  private String valueType;

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
  }

  public String getOwnerResource() {
    return ownerResource;
  }

  public void setOwnerResource(String ownerResource) {
    this.ownerResource = ownerResource;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValueType() {
    return valueType;
  }

  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  @Override
  public String toString() {
    return "Metafield{" +
        "createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", description='" + description + '\'' +
        ", id=" + id +
        ", key='" + key + '\'' +
        ", namespace='" + namespace + '\'' +
        ", ownerId=" + ownerId +
        ", ownerResource='" + ownerResource + '\'' +
        ", value='" + value + '\'' +
        ", valueType='" + valueType + '\'' +
        '}';
  }
}
