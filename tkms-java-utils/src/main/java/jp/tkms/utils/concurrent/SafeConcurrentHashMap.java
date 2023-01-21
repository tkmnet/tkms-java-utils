package jp.tkms.utils.concurrent;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class SafeConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {
    public SafeConcurrentHashMap() {
        super();
    }

    public SafeConcurrentHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SafeConcurrentHashMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public SafeConcurrentHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SafeConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        super(initialCapacity, loadFactor, concurrencyLevel);
    }

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
