package openag.shopify.client;

import openag.shopify.client.inventory.InventoryClient;
import openag.shopify.client.inventory.InventoryLevelsRequest;
import openag.shopify.client.product.ProductClient;
import openag.shopify.client.product.ProductListRequest;
import openag.shopify.client.webhook.WebhookClient;
import openag.shopify.client.webhook.WebhookListRequest;
import openag.shopify.domain.InventoryLevel;
import openag.shopify.domain.Location;
import openag.shopify.domain.Product;
import openag.shopify.domain.Webhook;

import java.util.List;
import java.util.Optional;

class ShopifyClientImpl implements ShopifyClient {

  private final ProductClient productClient;
  private final InventoryClient inventoryClient;
  private final WebhookClient webhookClient;

  private final DownloadUtils downloadUtils = new DownloadUtils();

  ShopifyClientImpl(ProductClient productClient,
                    InventoryClient inventoryClient,
                    WebhookClient webhookClient) {
    this.productClient = productClient;
    this.inventoryClient = inventoryClient;
    this.webhookClient = webhookClient;
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
  public List<Location> getLocations() {
    return inventoryClient.getLocations();
  }

  @Override
  public Optional<Location> getLocation(long id) {
    return inventoryClient.getLocation(id);
  }

  @Override
  public List<InventoryLevel> getInventoryLevels(InventoryLevelsRequest request) {
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
  public FileRef downloadFile(String url) {
    return downloadUtils.download(url);
  }
}
