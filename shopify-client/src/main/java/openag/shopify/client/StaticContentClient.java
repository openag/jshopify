package openag.shopify.client;

import java.util.function.Consumer;

/**
 * Client for Shopify's public static content, like product images.
 */
public interface StaticContentClient {

  /**
   * Attempts to fetch the provided file throwing exception if http error code returned
   */
  FileRef downloadFile(String url);

  void downloadFile(String url, Consumer<FileRef> refConsumer);
}
