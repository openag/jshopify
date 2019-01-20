package openag.shopify.apps.cl.mongo;

import openag.shopify.apps.cl.events.ProductEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandler implements ApplicationListener<ProductEvent> {

  private final ProductRepository productRepository;

  public ProductEventHandler(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void onApplicationEvent(ProductEvent event) {
    if (event instanceof ProductEvent.Created || event instanceof ProductEvent.Updated) {
      productRepository.save(event.getProduct());
    }
    if (event instanceof ProductEvent.Deleted) {
      productRepository.delete(event.getProduct());
    }
  }
}
