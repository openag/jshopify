package openag.shopify.apps.cl.webhooks;

import openag.shopify.Signed;
import openag.shopify.apps.cl.events.ProductEvent;
import openag.shopify.domain.Product;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Receives changes for {@link Product} entity
 */
@RestController
@RequestMapping("/webhook/product")
public class ProductWebHook {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ProductWebHook(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @PostMapping("/create")
  public void productCreated(@Signed Product product) {
    applicationEventPublisher.publishEvent(new ProductEvent.Created(product));
  }

  @PostMapping("/update")
  public void productUpdated(@Signed Product product) {
    applicationEventPublisher.publishEvent(new ProductEvent.Updated(product));
  }

  @PostMapping("/delete")
  public void productDeleted(@Signed Product product) {
    applicationEventPublisher.publishEvent(new ProductEvent.Deleted(product));
  }
}
