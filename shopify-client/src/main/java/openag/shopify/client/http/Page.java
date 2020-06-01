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
  private static final Page<?> EMPTY = new Page(null, null, Collections.emptyList(),
      ResponseType.wrappedList("", Void.class));

  private final String nextUrl;
  private final String prevUrl;

  private final List<T> items;
  private final ResponseType.Arr<T> rt;

  private final int limit;

  Page(String nextUrl, String prevUrl, List<T> items, ResponseType.Arr<T> rt) {
    this(nextUrl, prevUrl, items, rt, DEFAULT_PAGE_LIMIT);
  }

  Page(String nextUrl, String prevUrl, List<T> items, ResponseType.Arr<T> rt, int limit) {
    this.nextUrl = nextUrl;
    this.prevUrl = prevUrl;
    this.items = (items == null ? Collections.emptyList() : items);
    this.rt = rt;
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
    return rt.getElementType();
  }

  ResponseType.Arr<T> getRt() {
    return rt;
  }

  @SuppressWarnings("unchecked")
  public static <T> Page<T> empty() {
    return (Page<T>) EMPTY;
  }

  @Override
  public String toString() {
    return "Page{" +
        "items=" + items +
        '}';
  }
}
