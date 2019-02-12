package openag.shopify.client;

import java.io.InputStream;

/**
 * Reference to downloaded file with opened stream
 */
public class FileRef {

  private final String contentType;
  private final long contentLength;
  private final InputStream in;

  FileRef(String contentType, long contentLength, InputStream in) {
    this.contentType = contentType;
    this.contentLength = contentLength;
    this.in = in;
  }

  public String getContentType() {
    return contentType;
  }

  public long getContentLength() {
    return contentLength;
  }

  public InputStream getIn() {
    return in;
  }
}
