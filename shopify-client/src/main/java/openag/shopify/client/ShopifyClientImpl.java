package openag.shopify.client;

import openag.shopify.client.customer.CustomerClient;
import openag.shopify.client.http.Page;
import openag.shopify.client.inventory.InventoryClient;
import openag.shopify.client.inventory.InventoryLevelsRequest;
import openag.shopify.client.product.CollectListRequest;
import openag.shopify.client.product.ProductClient;
import openag.shopify.client.product.ProductListRequest;
import openag.shopify.client.saleschannel.ProductListingsRequest;
import openag.shopify.client.saleschannel.SalesChannelClient;
import openag.shopify.client.webhook.WebhookClient;
import openag.shopify.client.webhook.WebhookListRequest;
import openag.shopify.domain.*;

import java.util.List;
import java.util.Optional;

class ShopifyClientImpl implements ShopifyClient {

  private final ProductClient productClient;
  private final InventoryClient inventoryClient;
  private final WebhookClient webhookClient;
  private final SalesChannelClient salesChannelClient;
  private final CustomerClient customerClient;

  ShopifyClientImpl(ProductClient productClient,
                    InventoryClient inventoryClient,
                    WebhookClient webhookClient,
                    SalesChannelClient salesChannelClient,
                    CustomerClient customerClient) {
    this.productClient = productClient;
    this.inventoryClient = inventoryClient;
    this.webhookClient = webhookClient;
    this.salesChannelClient = salesChannelClient;
    this.customerClient = customerClient;
  }

  @Override
  public Optional<Product> getProduct(long id) {
    return productClient.getProduct(id);
  }

  @Override
  public List<Product> findProducts(ProductListRequest request) {
    return productClient.findProducts(request);
  }

  @Override
  public List<Collect> findCollects(CollectListRequest request) {
    return productClient.findCollects(request);
  }

  @Override
  public Optional<Collect> getCollect(long id) {
    return productClient.getCollect(id);
  }

  @Override
  public void deleteCollect(long id) {
    productClient.deleteCollect(id);
  }

  @Override
  public Collect createCollect(Collect collect) {
    return productClient.createCollect(collect);
  }

  @Override
  public List<Location> getLocations() {
    return inventoryClient.getLocations();
  }

  @Override
  public Optional<Location> getLocation(long id) {
    return inventoryClient.getLocation(id);
  }

  @Override
  public Page<InventoryLevel> getInventoryLevels(InventoryLevelsRequest request) {
    return inventoryClient.getInventoryLevels(request);
  }

  @Override
  public List<Webhook> getWebhooks(WebhookListRequest request) {
    return webhookClient.getWebhooks(request);
  }

  @Override
  public Webhook createWebhook(Webhook webhook) {
    return webhookClient.createWebhook(webhook);
  }

  @Override
  public void deleteWebhook(long id) {
    webhookClient.deleteWebhook(id);
  }

  @Override
  public Page<ProductListing> getProductListings(ProductListingsRequest request) {
    return salesChannelClient.getProductListings(request);
  }

  @Override
  public Optional<Customer> getCustomer(long id) {
    return customerClient.getCustomer(id);
  }
}
