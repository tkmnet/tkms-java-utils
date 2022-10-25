package jp.tkms.utils.value;

import java.util.function.Supplier;

public class Values {
  public static Object convertString(String s) {
    try {
      if (s.indexOf('.') < 0) {
        return Long.parseLong(s);
      } else {
        return Double.parseDouble(s);
      }
    } catch (NumberFormatException e) {
      return s;
    }
  }

  public static Object defaults(Object value, Object defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static String defaults(String value, String defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static int defaults(Integer value, int defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static short defaults(Short value, short defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static long defaults(Long value, long defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static float defaults(Float value, float defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static double defaults(Double value, double defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static char defaults(Character value, char defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static byte defaults(Byte value, byte defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static boolean defaults(Boolean value, boolean defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static String unlessNull(Object object, Supplier<String> supplier) {
    return (object == null ? null : supplier.get());
  }

  public static String ifNull(Object object, String caseNull, Supplier<String> supplierCaseFalse) {
    return (object == null ? caseNull : supplierCaseFalse.get());
  }
}
