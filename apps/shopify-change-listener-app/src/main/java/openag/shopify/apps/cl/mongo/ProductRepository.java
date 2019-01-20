package openag.shopify.apps.cl.mongo;

import openag.shopify.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Long> {
}
