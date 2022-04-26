package jp.tkms.utils.value;

import jp.tkms.utils.concurrent.FutureArrayList;
import jp.tkms.utils.concurrent.StaticExecutorService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanArrayListTest {

  /*
  @BeforeAll static void beforeAll() {
  }

  @BeforeEach void beforeEach() {
  }``
   */

  @Test void success() {
    BooleanArrayList list = new BooleanArrayList();
    list.add(false);
    list.add(true);
    list.add(false);
    list.add(false);
    list.add(true);
    list.add(true);
    list.add(true);
    list.add(true);
    list.add(false);
    list.add(true);
    assertFalse(list.get(0));
    assertTrue(list.get(1));
    assertFalse(list.get(2));
    assertFalse(list.get(3));
    assertTrue(list.get(4));
    assertTrue(list.get(5));
    assertTrue(list.get(6));
    assertTrue(list.get(7));
    assertFalse(list.get(8));
    assertTrue(list.get(9));
    assertEquals(list.size(), 10);
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
