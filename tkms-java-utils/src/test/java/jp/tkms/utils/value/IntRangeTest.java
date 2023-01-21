package jp.tkms.utils.value;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IntRangeTest {

  /*
  @BeforeAll static void beforeAll() {
  }

  @BeforeEach void beforeEach() {
  }``
   */

    @Test
    void success() {
        test(new int[]{5});
        test(new int[]{2, 3, 4, 5, 6});
        test(new int[]{9, 8, 7, 6});
    }

    void test(int[] list) {
        ArrayList<Integer> l = new ArrayList<>();
        IntRange.range(list[0], list[list.length - 1] + (list.length == 1 ? 0 : 1)).forEach(i -> l.add(i));
        assertArrayEquals(l.stream().mapToInt(i -> i).toArray(), list);
        l.clear();
        IntRange.rangeClosed(list[0], list[list.length - 1]).forEach(i -> l.add(i));
        assertArrayEquals(l.stream().mapToInt(i -> i).toArray(), list);
        l.clear();
        ArrayList<Integer> l2 = new ArrayList<>();
        IntRange.range(list[list.length - 1]).forEach(i -> l.add(i));
        IntRange.range(0, list[list.length - 1]).forEach(i -> l2.add(i));
        assertArrayEquals(l.stream().mapToInt(i -> i).toArray(), l2.stream().mapToInt(i -> i).toArray());
        l.clear();
        l2.clear();
        IntRange.rangeClosed(list[list.length - 1]).forEach(i -> l.add(i));
        IntRange.rangeClosed(0, list[list.length - 1]).forEach(i -> l2.add(i));
        assertArrayEquals(l.stream().mapToInt(i -> i).toArray(), l2.stream().mapToInt(i -> i).toArray());
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
