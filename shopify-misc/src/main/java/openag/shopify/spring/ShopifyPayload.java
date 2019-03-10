package openag.shopify.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a Shopify request object. Works together with {@link openag.shopify.spring.ShopifyPayloadResolver}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ShopifyPayload {

  /**
   * If set to true, request HMAC signature will be verified
   */
  boolean verifySignature() default true;

  /**
   * Handles the case where Shopify domain entity is wrapped into parent object. If specified, the resolver will attempt
   * to unwrap the object before converting json to entity
   */
  boolean wrapped() default false;
}
