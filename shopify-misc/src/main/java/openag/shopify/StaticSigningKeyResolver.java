package openag.shopify;

/**
 * SigningKeyResolver that always returns the same provided key for all resolve calls
 */
public class StaticSigningKeyResolver implements SigningKeyResolver {

  private final String key;

  public StaticSigningKeyResolver(String key) {
    this.key = key;
  }

  @Override
  public String getKey(String domain) {
    return key;
  }
}
