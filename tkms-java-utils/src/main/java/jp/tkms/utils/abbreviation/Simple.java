package jp.tkms.utils.abbreviation;

import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class Simple {
  public static void waitUntil(BooleanSupplier condition, TimeUnit timeUnit, int duration) throws InterruptedException {
    while (condition.getAsBoolean()) {
      timeUnit.sleep(duration);
    }
  }

  public static void waitFor(BooleanSupplier condition, TimeUnit timeUnit, int duration) throws InterruptedException {
    while (!condition.getAsBoolean()) {
      timeUnit.sleep(duration);
    }
  }

  public static void sleep(TimeUnit timeUnit, long time) {
    try {
      timeUnit.sleep(time);
    } catch (InterruptedException e) {
      //NOP
    }
  }
}
