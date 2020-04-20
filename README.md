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

# Features

## JSON WebHook
Possibility to receive Shopify WebHook calls in raw JSON format. 
See samples/sample-json-webhook

## JSON Client
REST API Client that works with raw JSON payload
```
openag.shopify.client.ShopifyJsonClient
```
Use ShopifyClientFactory#buildJsonClient() to create new client instance
