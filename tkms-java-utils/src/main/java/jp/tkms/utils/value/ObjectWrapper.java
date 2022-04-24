package jp.tkms.utils.value;

import java.util.concurrent.Callable;

public class ObjectWrapper<T> extends Object {
  private T value;

  public ObjectWrapper(T value) {
    this.value = value;
  }

  public ObjectWrapper() {
    this(null);
  }

  public void set(T value) {
    this.value = value;
  }

  public T get() {
    return value;
  }

  public T get(Callable<T> defaultValue) throws Exception {
    if (value == null) {
      synchronized (this) {
        if (value == null) {
          value = defaultValue.call();
        }
      }
    }
    return value;
  }

  public T get(T defaultValue) throws Exception {
    return get(() -> defaultValue);
  }
}
