package openag.shopify.client;

import openag.shopify.client.product.ProductClient;
import openag.shopify.client.product.ProductListRequest;
import openag.shopify.domain.Product;

import java.util.List;
import java.util.Optional;

class ShopifyClientImpl implements ShopifyClient {

  private final ProductClient productClient;

  private final DownloadUtils downloadUtils = new DownloadUtils();

  ShopifyClientImpl(ProductClient productClient) {
    this.productClient = productClient;
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
  public FileRef downloadFile(String url) {
    return downloadUtils.download(url);
  }
}
