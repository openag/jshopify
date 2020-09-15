package openag.shopify.client;

import java.util.function.Consumer;

class StaticContentClientImpl implements StaticContentClient {

  private final DownloadUtils downloadUtils = new DownloadUtils();

  @Override
  public FileRef downloadFile(String url) {
    return downloadUtils.download(url);
  }

  @Override
  public void downloadFile(String url, Consumer<FileRef> refConsumer) {
    try (final FileRef ref = downloadFile(url)) {
      refConsumer.accept(ref);
    }
  }
}
