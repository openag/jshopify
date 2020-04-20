package openag.shopify.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("shopify")
public class ShopifyProperties {

  /**
   * Webhook signature key (HMAC key at the moment of writing this code), can be found on Shopify admin Notification
   * configuration page. The key is used to verify webhook call integrity. Define this parameter if you're intended to
   * handle webhook calls from single shopify domain; otherwise declare bean of type {@link
   * openag.shopify.SigningKeyResolver}
   */
  private String webhookSignKey;

  public String getWebhookSignKey() {
    return webhookSignKey;
  }

  public void setWebhookSignKey(String webhookSignKey) {
    this.webhookSignKey = webhookSignKey;
  }

}
