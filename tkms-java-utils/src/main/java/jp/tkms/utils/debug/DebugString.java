package jp.tkms.utils.debug;

import java.util.Arrays;
import java.util.List;

public class DebugString {
  public static String toString(Object o) {
    return o == null ? "null" : o.toString();
  }

  public static String toString(List l) {
    return Arrays.toString(l.stream().map(o -> DebugString.toString(o)).toArray());
  }

  public static String toString(int[] a) {
    return Arrays.toString(a);
  }

  public static String toString(byte[] a) {
    return Arrays.toString(a);
  }

  public static String toString(long[] a) {
    return Arrays.toString(a);
  }

  public static String toString(char[] a) {
    return Arrays.toString(a);
  }

  public static String toString(float[] a) {
    return Arrays.toString(a);
  }

  public static String toString(short[] a) {
    return Arrays.toString(a);
  }

  public static String toString(double[] a) {
    return Arrays.toString(a);
  }

  public static String toString(boolean[] a) {
    return Arrays.toString(a);
  }

  public static String toString(Object[] a) {
    return Arrays.toString(a);
  }
}
