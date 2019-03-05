package openag.shopify.domain;

import java.util.Date;

/**
 * A location represents a geographical location where your stores, pop-up stores, headquarters, and warehouses exist.
 * You can use the Location resource to track sales, manage inventory, and configure the tax rates to apply at
 * checkout.
 */
public class Location {

  /**
   * Whether the location is active. If true, then the location can be used to sell products, stock inventory, and
   * fulfill orders. Merchants can deactivate locations from the Shopify admin.
   */
  private boolean active;

  /**
   * The first line of the address.
   */
  private String address1;

  /**
   * The second line of the address.
   */
  private String address2;

  /**
   * The city the location is in.
   */
  private String city;

  /**
   * The country the location is in.
   */
  private String country;

  /**
   * The two-letter code (ISO 3166-1 alpha-2 format) corresponding to country the location is in.
   */
  private String countryCode;

  /**
   * The date and time (ISO 8601 format) when the location was created.
   */
  private Date createdAt;

  /**
   * The ID for the location.
   */
  private long id;

  /**
   * Whether this is a fulfillment service location. If true, then the location is a fulfillment service location. If
   * false, then the location was created by the merchant and isn't tied to a fulfillment service.
   */
  private boolean legacy;

  /**
   * The name of the location.
   */
  private String name;

  /**
   * The phone number of the location. This value can contain special characters like - and +
   */
  private String phone;

  /**
   * The province the location is in.
   */
  private String province;

  /**
   * The two-letter code corresponding to province or state the location is in.
   */
  private String provinceCode;

  /**
   * The date and time (ISO 8601 format) when the location was last updated.
   */
  private Date updatedAt;

  /**
   * The zip or postal code.
   */
  private String zip;

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public boolean isLegacy() {
    return legacy;
  }

  public void setLegacy(boolean legacy) {
    this.legacy = legacy;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getProvinceCode() {
    return provinceCode;
  }

  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @Override
  public String toString() {
    return "Location{" +
        "active=" + active +
        ", address1='" + address1 + '\'' +
        ", address2='" + address2 + '\'' +
        ", city='" + city + '\'' +
        ", country='" + country + '\'' +
        ", countryCode='" + countryCode + '\'' +
        ", createdAt=" + createdAt +
        ", id=" + id +
        ", legacy=" + legacy +
        ", name='" + name + '\'' +
        ", phone='" + phone + '\'' +
        ", province='" + province + '\'' +
        ", provinceCode='" + provinceCode + '\'' +
        ", updatedAt=" + updatedAt +
        ", zip='" + zip + '\'' +
        '}';
  }
}
