package jp.tkms.utils.value;

import jp.tkms.utils.concurrent.FutureArrayList;
import jp.tkms.utils.concurrent.StaticExecutorService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class IntRangeTest {

  /*
  @BeforeAll static void beforeAll() {
  }

  @BeforeEach void beforeEach() {
  }``
   */

  @Test void success() {
    test(new int[]{5});
    test(new int[]{2,3,4,5,6});
    test(new int[]{6,7,8,9});
  }

  void test(int[] list) {
    ArrayList<Integer> l = new ArrayList<>();
    IntRange.range(list[0], list[list.length -1] + (list.length == 1 ? 0 : 1)).forEach(i -> l.add(i));
    assertArrayEquals(l.stream().mapToInt(i -> i).toArray(), list);
    l.clear();
    IntRange.rangeClosed(list[0], list[list.length -1]).forEach(i -> l.add(i));
    assertArrayEquals(l.stream().mapToInt(i -> i).toArray(), list);
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
