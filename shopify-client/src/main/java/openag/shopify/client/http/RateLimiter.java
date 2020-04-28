package openag.shopify.client.http;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Encapsulation of rate limit counters
 */
class RateLimiter {
  private final AtomicInteger rateNow = new AtomicInteger();
  private final AtomicInteger rateMax = new AtomicInteger(40);

  void acquireExecutionSlot() {
    if (rateMax.get() - rateNow.get() < 2) {
      sleepSeconds(1); //todo: random delay here
    }
  }

  void updateRates(int current, int max) {
    rateNow.set(current);
    rateMax.set(max);
  }

  void waitFor(double seconds) {
    sleepSeconds(seconds);
  }

  private static void sleepSeconds(double seconds) {
    try {
      Thread.sleep((long) (seconds + 1_000d));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
