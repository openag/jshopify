package openag.shopify.client.http;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * HTTP client abstraction
 */
public interface Http {

  Gson gson = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
      .create();

  <T> T exchange(Consumer<Exchange> ec, Class<T> elementType);

  void delete(Consumer<Exchange> ec);

  <T> List<T> list(Consumer<Exchange> ec, Class<T> elementType);

  <T> Iterator<T> iterate(Consumer<Exchange> ec, Class<T> elementType);

  <T> Optional<T> getOptional(Consumer<Exchange> ec, Class<T> elementType);

  <T> T getOne(Consumer<Exchange> ec, Class<T> elementType);
}
