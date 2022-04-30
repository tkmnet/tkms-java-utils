package jp.tkms.utils.value;

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
}
