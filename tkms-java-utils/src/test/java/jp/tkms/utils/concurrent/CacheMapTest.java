package jp.tkms.utils.concurrent;

import jp.tkms.utils.debug.DebugElapsedTime;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CacheMapTest {

  /*
  @BeforeAll static void beforeAll() {
  }

  @BeforeEach void beforeEach() {
  }``
   */

    @Test
    void success() {
        DebugElapsedTime t = new DebugElapsedTime();
        CacheMap<Integer, String> map = new CacheMap<>();
        map.put(1, () -> {
            TimeUnit.SECONDS.sleep(1);
            return "OK";
        });
        map.pin(1);
        assertEquals(true, t.next() < 100);
        assertEquals("OK", map.get(1));
        assertEquals(true, t.next() >= 1000);
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
