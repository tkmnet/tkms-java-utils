package jp.tkms.utils.value;

import jp.tkms.utils.stream.Editor;
import java.util.concurrent.Callable;

public class Init<T> {
    public Init() {};

    public T call(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            return null;
        }
    }

    public T call(T v, Editor<T> editor) {
        try {
            return editor.apply(v);
        } catch (Exception e) {
            return null;
        }
    }
}
