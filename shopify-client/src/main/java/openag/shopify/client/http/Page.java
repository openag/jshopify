package openag.shopify.client.http;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Encapsulation of paginated response
 */
public class Page<T> implements Iterable<T> {
  private static final int DEFAULT_PAGE_LIMIT = 50;

  @SuppressWarnings("unchecked")
  private static final Page<?> EMPTY = new Page(null, null, Collections.emptyList(), Void.class);

  private final String nextUrl;
  private final String prevUrl;
  private final List<T> items;
  private final Class<T> elementType;
  private final int limit;

  Page(String nextUrl, String prevUrl, List<T> items, Class<T> elementType) {
    this(nextUrl, prevUrl, items, elementType, DEFAULT_PAGE_LIMIT);
  }

  Page(String nextUrl, String prevUrl, List<T> items, Class<T> elementType, int limit) {
    this.nextUrl = nextUrl;
    this.prevUrl = prevUrl;
    this.items = (items == null ? Collections.emptyList() : items);
    this.elementType = elementType;
    this.limit = limit;
  }

  public boolean hasNext() {
    return this.nextUrl != null;
  }

  public boolean hasPrevious() {
    return this.prevUrl != null;
  }

  public List<T> items() {
    return Collections.unmodifiableList(this.items);
  }

  public boolean isEmpty() {
    return this.items.isEmpty();
  }

  @Override
  public Iterator<T> iterator() {
    return this.items.iterator();
  }

  String getNextUrl() {
    return nextUrl;
  }

  String getPrevUrl() {
    return prevUrl;
  }

  Class<T> getElementType() {
    return elementType;
  }

  @SuppressWarnings("unchecked")
  public static <T> Page<T> empty() {
    return (Page<T>) EMPTY;
  }
}
