# JShopify
Java library to work with Shopify platform

# Components
 * **shopify-domain** - set of domain classes that model Shopify API entities
 * **shopify-client** - Shopify REST API HTTP Client based on Java java.net.http.HttpClient
 * **shopify-misc** - various tools and helper classes
 
# REST API Client
Create new **ShopifyClient** instance:
```java
  private final ShopifyClient client = new ShopifyClientFactory("example.myshopify.com")
      .authenticateWithAPIKeyAndPassword(API_KEY, API_PASSWORD)
      .build();
```
Or using the token authentication:
```java
  private final ShopifyClient client = new ShopifyClientFactory("example.myshopify.com")
      .authenticateWithAccessToken(ACCESS_TOKEN)
      .build();
```

