package jp.tkms.utils.debug;

import jp.tkms.utils.value.IntRange;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DebugStringTest {
    @Test
    void success() {
        ArrayList list = new ArrayList();
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        for (int n : IntRange.rangeClosed(10, 20)) {
            list1.add(n);
            list2.add(n);
        }
        list.add(list1);
        list.add(list2);
        System.out.println(DebugString.toString(list));
    }
}
