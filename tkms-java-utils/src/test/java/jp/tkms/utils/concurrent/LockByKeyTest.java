package jp.tkms.utils.concurrent;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LockByKeyTest {

  /*
  @BeforeAll static void beforeAll() {
  }

  @BeforeEach void beforeEach() {
  }``
   */

  @Test void success() {
    final Integer[] count = {0};
    IntStream.range(0, 100000).parallel().forEach((n) -> {
      try (LockByKey lock = LockByKey.acquire("")) {
        count[0] += 1;
      }
    });
    assertEquals(count[0], 100000);
  }

  /*
  @Test void fail() {
    assertNotNull(object.get(0), "app should have a greeting");
  }

  @AfterEach void afterEach() {
  }

  @AfterAll static void afterAll() {
  }
   */
}
