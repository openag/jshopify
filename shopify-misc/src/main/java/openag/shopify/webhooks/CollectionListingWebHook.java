package openag.shopify.webhooks;

import openag.shopify.domain.CollectionListing;
import openag.shopify.events.CollectionListingEvent;
import openag.shopify.spring.ShopifyPayload;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/collectionlisting")
public class CollectionListingWebHook {

  private final ApplicationEventPublisher applicationEventPublisher;

  public CollectionListingWebHook(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @PostMapping("/create")
  public void created(@ShopifyPayload(wrapped = true) CollectionListing collection) {
    applicationEventPublisher.publishEvent(new CollectionListingEvent.Created(collection));
  }

  @PostMapping("/update")
  public void updated(@ShopifyPayload(wrapped = true) CollectionListing collection) {
    applicationEventPublisher.publishEvent(new CollectionListingEvent.Updated(collection));
  }

  @PostMapping("/delete")
  public void deleted(@ShopifyPayload(wrapped = true) CollectionListing collection) {
    applicationEventPublisher.publishEvent(new CollectionListingEvent.Deleted(collection));
  }
}
