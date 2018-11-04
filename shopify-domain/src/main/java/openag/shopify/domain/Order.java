package openag.shopify.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

  private Long appId;
  private Address billingAddress;
  private String browserIp;
  private boolean buyerAcceptsMarketing;
  //todo: cancel_reason
  private Date cancelledAt; //todo: formatter:  "2018-08-29T12:42:04-04:00"  !
  private String cartToken;
  //todo: client_details
  private Date closedAt;
  private Date createdAt;
  private String currency;
  //todo: customer
  //todo: customer_locale
  //todo: discount_applications
  //todo: discount_codes
  private String email;
  private FinancialStatus financialStatus;
  //todo: fulfillments
  private Long id;
  private String landingSite;
  //todo; line_items
  private Long locationId;
  private String name;
  private String note;
  //todo: note_attributes
  //todo; number
  private Long orderNumber;
  //todo: payment_gateway_names
  private String phone;
  private Date processedAt;
  //todo: processing_method
  private String referringSite;
  //todo: refunds
  private Address shippingAddress;
  //todo: shipping_lines
  //todo: source_name
  private BigDecimal subtotalPrice;
  private String tags;
  //todo: tax_lines
  private boolean taxesIncluded;
  private String token;
  private BigDecimal totalDiscounts;
  private BigDecimal totalLineItemsPrice;
  private BigDecimal totalPrice;
  private BigDecimal totalTax;
  private BigDecimal totalTipReceived;
  private Integer totalWeight;
  private Date updatedAt;
  private Long userId;
  private String orderStatusUrl;

  public Long getAppId() {
    return appId;
  }

  public void setAppId(Long appId) {
    this.appId = appId;
  }

  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  public String getBrowserIp() {
    return browserIp;
  }

  public void setBrowserIp(String browserIp) {
    this.browserIp = browserIp;
  }

  public boolean isBuyerAcceptsMarketing() {
    return buyerAcceptsMarketing;
  }

  public void setBuyerAcceptsMarketing(boolean buyerAcceptsMarketing) {
    this.buyerAcceptsMarketing = buyerAcceptsMarketing;
  }

  public Date getCancelledAt() {
    return cancelledAt;
  }

  public void setCancelledAt(Date cancelledAt) {
    this.cancelledAt = cancelledAt;
  }

  public String getCartToken() {
    return cartToken;
  }

  public void setCartToken(String cartToken) {
    this.cartToken = cartToken;
  }

  public Date getClosedAt() {
    return closedAt;
  }

  public void setClosedAt(Date closedAt) {
    this.closedAt = closedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public FinancialStatus getFinancialStatus() {
    return financialStatus;
  }

  public void setFinancialStatus(FinancialStatus financialStatus) {
    this.financialStatus = financialStatus;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLandingSite() {
    return landingSite;
  }

  public void setLandingSite(String landingSite) {
    this.landingSite = landingSite;
  }

  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Long getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(Long orderNumber) {
    this.orderNumber = orderNumber;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Date getProcessedAt() {
    return processedAt;
  }

  public void setProcessedAt(Date processedAt) {
    this.processedAt = processedAt;
  }

  public String getReferringSite() {
    return referringSite;
  }

  public void setReferringSite(String referringSite) {
    this.referringSite = referringSite;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public BigDecimal getSubtotalPrice() {
    return subtotalPrice;
  }

  public void setSubtotalPrice(BigDecimal subtotalPrice) {
    this.subtotalPrice = subtotalPrice;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public boolean isTaxesIncluded() {
    return taxesIncluded;
  }

  public void setTaxesIncluded(boolean taxesIncluded) {
    this.taxesIncluded = taxesIncluded;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public BigDecimal getTotalDiscounts() {
    return totalDiscounts;
  }

  public void setTotalDiscounts(BigDecimal totalDiscounts) {
    this.totalDiscounts = totalDiscounts;
  }

  public BigDecimal getTotalLineItemsPrice() {
    return totalLineItemsPrice;
  }

  public void setTotalLineItemsPrice(BigDecimal totalLineItemsPrice) {
    this.totalLineItemsPrice = totalLineItemsPrice;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public BigDecimal getTotalTax() {
    return totalTax;
  }

  public void setTotalTax(BigDecimal totalTax) {
    this.totalTax = totalTax;
  }

  public BigDecimal getTotalTipReceived() {
    return totalTipReceived;
  }

  public void setTotalTipReceived(BigDecimal totalTipReceived) {
    this.totalTipReceived = totalTipReceived;
  }

  public Integer getTotalWeight() {
    return totalWeight;
  }

  public void setTotalWeight(Integer totalWeight) {
    this.totalWeight = totalWeight;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getOrderStatusUrl() {
    return orderStatusUrl;
  }

  public void setOrderStatusUrl(String orderStatusUrl) {
    this.orderStatusUrl = orderStatusUrl;
  }
}
