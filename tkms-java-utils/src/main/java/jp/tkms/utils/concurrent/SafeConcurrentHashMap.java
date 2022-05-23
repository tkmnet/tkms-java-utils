package jp.tkms.utils.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class SafeConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {
    public V get(Object key, Callable<V> defaults) throws Exception {
        V value = get(key);
        if (value == null) {
            synchronized (this) {
                value = get(key);
                if (value == null) {
                    value = defaults.call();
                    put((K)key, value);
                }
            }
        }
        return value;
    }
}
