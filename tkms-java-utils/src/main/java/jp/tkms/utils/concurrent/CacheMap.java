package jp.tkms.utils.concurrent;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.*;

public class CacheMap<K, V> implements Map<K, V> {
  private ExecutorService executorService;
  private HashMap<K, InitializableReference<V>> map;

  public CacheMap(ExecutorService executorService) {
    this.executorService = executorService;
    map = new HashMap<>();
  }

  public CacheMap(int parallelism) {
    this(Executors.newWorkStealingPool(parallelism));
  }

  public CacheMap() {
    this(StaticExecutorService.get());
  }

  public void shutdown() {
    executorService.shutdown();
  }

  public void release(K key) {
    InitializableReference<V> reference = map.get(key);
    if (reference != null) {
      reference.release();
    }
  }

  public void release() {
    for (K key : map.keySet()) {
      release(key);
    }
  }

  public void load(K key) {
    InitializableReference<V> reference = map.get(key);
    if (reference != null) {
      reference.load();
    }
  }

  public void pin(K key) {
    InitializableReference<V> reference = map.get(key);
    if (reference != null) {
      reference.pin();
    }
  }

  public void unpin(K key) {
    InitializableReference<V> reference = map.get(key);
    if (reference != null) {
      reference.unpin();
    }
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean containsKey(Object o) {
    return map.containsKey(o);
  }

  @Override
  public boolean containsValue(Object o) {
    for (InitializableReference<V> v : map.values()) {
      try {
        if (o != null && v.reference.get() != null && o.equals(v.reference.get().get())) {
          return true;
        }
      } catch (InterruptedException | ExecutionException e) {
        continue;
      }
    }
    return false;
  }

  @Override
  public V get(Object o) {
    InitializableReference<V> ref = map.get(o);
    if (ref == null) {
      return null;
    }
    try {
      return ref.get();
    } catch (ExecutionException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public V put(K k, Callable<V> callable) {
    map.put(k, new InitializableReference<>(callable));
    return null;
  }

  @Override
  public V put(K k, V v) {
    return put(k, () -> v);
  }

  @Override
  public V remove(Object o) {
    try {
      InitializableReference<V> ref = map.remove(o);
      Future<V> future = ref.reference.get();
      if (future.isDone()) {
        return future.get();
      } else {
        future.cancel(true);
      }
    } catch (Exception e) {
      return null;
    }
    return null;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> map) {
    for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public Set<K> keySet() {
    return map.keySet();
  }

  @Override
  public Collection<V> values() {
    ArrayList<V> list = new ArrayList<>();
    for (InitializableReference<V> r : map.values()) {
      try {
        Future<V> future = r.reference.get();
        if (future != null && future.isDone()) {
          list.add(future.get());
        }
      } catch (ExecutionException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return list;
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    HashSet<Entry<K, V>> set = new HashSet<>();
    for (Entry<K, InitializableReference<V>> entry : map.entrySet()) {
      V v = null;
      try {
        Future<V> future = entry.getValue().reference.get();
        if (future != null && future.isDone()) {
          v = future.get();
        }
        V finalV = v;
        set.add(new Entry<K, V>() {
          @Override
          public K getKey() {
            return entry.getKey();
          }

          @Override
          public V getValue() {
            return finalV;
          }

          @Override
          public V setValue(V v) {
            return null;
          }
        });
      } catch (ExecutionException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return set;
  }

  private class InitializableReference<V> {
    WeakReference<Future<V>> reference;
    Callable<V> initializer;
    Future<V> pinnedFuture = null;

    public InitializableReference(Callable<V> initializer) {
      this.initializer = initializer;
      release();
      load();
    }

    public Future<V> load() {
      Future<V> value = reference.get();
      if (pinnedFuture != null) {
        value = pinnedFuture;
      }
      if (value == null) {
        value = executorService.submit(initializer);
        reference = new WeakReference<>(value);
      }
      return value;
    }

    public V get() throws ExecutionException, InterruptedException {
      return load().get();
    }

    public void release() {
      unpin();
      reference = new WeakReference<>(null);
    }

    public void pin() {
      pinnedFuture = load();
    }

    public void unpin() {
      pinnedFuture = null;
    }
  }
}
