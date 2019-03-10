package openag.shopify.webhooks;

import openag.shopify.domain.ProductListing;
import openag.shopify.events.ProductListingEvent;
import openag.shopify.spring.ShopifyPayload;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/productlisting")
public class ProductListingWebHook {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ProductListingWebHook(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @PostMapping("/create")
  public void created(@ShopifyPayload(wrapped = true) ProductListing product) {
    applicationEventPublisher.publishEvent(new ProductListingEvent.Created(product));
  }

  @PostMapping("/update")
  public void updated(@ShopifyPayload(wrapped = true) ProductListing product) {
    applicationEventPublisher.publishEvent(new ProductListingEvent.Updated(product));
  }

  @PostMapping("/delete")
  public void deleted(@ShopifyPayload(wrapped = true) ProductListing product) {
    applicationEventPublisher.publishEvent(new ProductListingEvent.Deleted(product));
  }
}
