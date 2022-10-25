package jp.tkms.utils.debug;

import jp.tkms.utils.value.IntRange;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebugStringTest {
  @Test
  void success() {
    ArrayList list = new ArrayList();
    ArrayList list1 = new ArrayList();
    ArrayList list2 = new ArrayList();
    for (int n : IntRange.rangeClosed(10, 15)) {
      list1.add(n);
      list2.add(n);
    }
    list.add(list1);
    list.add(list2);

    assertEquals("[[10, 11, 12, 13, 14, 15], [10, 11, 12, 13, 14, 15]]", DebugString.toString(list));
  }
}
