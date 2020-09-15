package openag.shopify.client;

import java.io.IOException;
import java.io.InputStream;

/**
 * Reference to downloaded file with opened stream
 */
public class FileRef implements AutoCloseable {

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

  @Override
  public void close() {
    try {
      this.in.close();
    } catch (IOException e) {
      //ignore for now
    }
  }
}
