package jp.tkms.utils.debug;

import java.util.Arrays;
import java.util.List;

public class DebugString {
    public static String toString(Object o) {
        return o.toString();
    }

    public static String toString(List list) {
        return Arrays.toString(list.stream().map(o -> DebugString.toString(o)).toArray());
    }
}
