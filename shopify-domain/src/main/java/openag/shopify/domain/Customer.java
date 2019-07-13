package openag.shopify.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;

public class Customer {

  private boolean acceptsMarketing;
  private Date acceptsMarketingUpdatedAt;
  private Address[] addresses;
  private Currency currency;
  private Date createdAt;
  private Address defaultAddress;
  private String email;
  private String firstName;
  private long id;
  private String lastName;
  private long lastOrderId;
  private String lastOrderName;
  private Metafield[] metafields;
  private MarketingOptInLevel marketingOptInLevel;
  private String note;
  private int ordersCount;
  private String phone;
  private State state;
  private String tags;
  private boolean taxExempt;
  private BigDecimal totalSpent;
  private Date updatedAt;
  private boolean verifiedEmail;

  public boolean isAcceptsMarketing() {
    return acceptsMarketing;
  }

  public void setAcceptsMarketing(boolean acceptsMarketing) {
    this.acceptsMarketing = acceptsMarketing;
  }

  public Date getAcceptsMarketingUpdatedAt() {
    return acceptsMarketingUpdatedAt;
  }

  public void setAcceptsMarketingUpdatedAt(Date acceptsMarketingUpdatedAt) {
    this.acceptsMarketingUpdatedAt = acceptsMarketingUpdatedAt;
  }

  public Address[] getAddresses() {
    return addresses;
  }

  public void setAddresses(Address[] addresses) {
    this.addresses = addresses;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Address getDefaultAddress() {
    return defaultAddress;
  }

  public void setDefaultAddress(Address defaultAddress) {
    this.defaultAddress = defaultAddress;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public long getLastOrderId() {
    return lastOrderId;
  }

  public void setLastOrderId(long lastOrderId) {
    this.lastOrderId = lastOrderId;
  }

  public String getLastOrderName() {
    return lastOrderName;
  }

  public void setLastOrderName(String lastOrderName) {
    this.lastOrderName = lastOrderName;
  }

  public Metafield[] getMetafields() {
    return metafields;
  }

  public void setMetafields(Metafield[] metafields) {
    this.metafields = metafields;
  }

  public MarketingOptInLevel getMarketingOptInLevel() {
    return marketingOptInLevel;
  }

  public void setMarketingOptInLevel(MarketingOptInLevel marketingOptInLevel) {
    this.marketingOptInLevel = marketingOptInLevel;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public int getOrdersCount() {
    return ordersCount;
  }

  public void setOrdersCount(int ordersCount) {
    this.ordersCount = ordersCount;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public boolean isTaxExempt() {
    return taxExempt;
  }

  public void setTaxExempt(boolean taxExempt) {
    this.taxExempt = taxExempt;
  }

  public BigDecimal getTotalSpent() {
    return totalSpent;
  }

  public void setTotalSpent(BigDecimal totalSpent) {
    this.totalSpent = totalSpent;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isVerifiedEmail() {
    return verifiedEmail;
  }

  public void setVerifiedEmail(boolean verifiedEmail) {
    this.verifiedEmail = verifiedEmail;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "acceptsMarketing=" + acceptsMarketing +
        ", acceptsMarketingUpdatedAt=" + acceptsMarketingUpdatedAt +
        ", addresses=" + Arrays.toString(addresses) +
        ", currency=" + currency +
        ", createdAt=" + createdAt +
        ", defaultAddress=" + defaultAddress +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", id=" + id +
        ", lastName='" + lastName + '\'' +
        ", lastOrderId=" + lastOrderId +
        ", lastOrderName='" + lastOrderName + '\'' +
        ", metafields=" + Arrays.toString(metafields) +
        ", marketingOptInLevel=" + marketingOptInLevel +
        ", note='" + note + '\'' +
        ", ordersCount=" + ordersCount +
        ", phone='" + phone + '\'' +
        ", state=" + state +
        ", tags='" + tags + '\'' +
        ", taxExempt=" + taxExempt +
        ", totalSpent=" + totalSpent +
        ", updatedAt=" + updatedAt +
        ", verifiedEmail=" + verifiedEmail +
        '}';
  }

  private enum MarketingOptInLevel {
    single_opt_in, confirmed_opt_in, unknown
  }

  private enum State {
    disabled, invited, enabled, declined
  }

}
