package jp.tkms.utils.value;

import jp.tkms.utils.stream.Editor;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class Init<T> {
    public Init() {};

    public T call(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            return null;
        }
    }

    public T call(T v, Consumer<T> consumer) {
        try {
            consumer.accept(v);
            return v;
        } catch (Exception e) {
            return null;
        }
    }
}
