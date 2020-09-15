package openag.shopify.client;

import openag.shopify.client.customer.CustomerClient;
import openag.shopify.client.inventory.InventoryClient;
import openag.shopify.client.product.ProductClient;
import openag.shopify.client.saleschannel.SalesChannelClient;
import openag.shopify.client.webhook.WebhookClient;

/**
 * Shopify REST API Client
 */
public interface ShopifyClient extends
    ProductClient,
    InventoryClient,
    WebhookClient,
    SalesChannelClient,
    CustomerClient {
}
