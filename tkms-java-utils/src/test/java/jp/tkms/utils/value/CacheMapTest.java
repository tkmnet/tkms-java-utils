package jp.tkms.utils.value;

import jp.tkms.utils.concurrent.CacheMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CacheMapTest {

  /*
  @BeforeAll static void beforeAll() {
  }

  @BeforeEach void beforeEach() {
  }``
   */

  @Test void success() {
    int list[] = {2, 4, 62, 9, 6};
    CacheMap<Integer, Integer> map = new CacheMap<>();
    for (int n : list) {
      map.put(n, n +5);
    }
    for (int n : list) {
      assertEquals(map.get(n), n +5);
    }
    map.clear();
    assertEquals(0, map.size());
    for (int n : list) {
      map.put(n, () -> {
        return n * n;
      });
    }
    for (int n : list) {
      assertEquals(map.get(n), n * n);
    }
    map.release();
    for (Integer n : map.values()) {
      assertNull(n);
    }
    for (int n : list) {
      assertEquals(map.get(n), n * n);
    }
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
