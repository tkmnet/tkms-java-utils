package jp.tkms.utils.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class CacheMapTest {

  /*
  @BeforeAll static void beforeAll() {
  }

  @BeforeEach void beforeEach() {
  }``
   */

  @Test void success() {
    CacheMap<Integer, String> map = new CacheMap<>();
    map.pin(1);
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
