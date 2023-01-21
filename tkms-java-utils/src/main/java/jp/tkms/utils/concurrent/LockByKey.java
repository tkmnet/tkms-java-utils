package jp.tkms.utils.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LockByKey implements AutoCloseable {
    private static final int BASEMAP_SIZE = 64;
    private static SafeConcurrentHashMap<Integer, SafeConcurrentHashMap<String, ReservableLock>> map = new SafeConcurrentHashMap<>(BASEMAP_SIZE);

    public static LockByKey acquire(Object key) {
        return new LockByKey(key.toString());
    }

    private SafeConcurrentHashMap<String, ReservableLock> localMap;
    private String key;
    private ReservableLock lock;

    public LockByKey(String key) {
        this.key = key;
        try {
            localMap = map.get(key.hashCode() % BASEMAP_SIZE, () -> {
                return new SafeConcurrentHashMap<>();
            });
            synchronized (localMap) {
                lock = localMap.get(key, () -> new ReservableLock());
                lock.reserve();
            }
            lock.lock();
        } catch (Exception e) {
            //NOP
        }
    }

    @Override
    public void close() {
        synchronized (localMap) {
            lock.unlock();
            if (lock.isNonReserved()) {
                localMap.remove(key);
            }
        }
    }

    public static boolean trySyncAll () {
        for (SafeConcurrentHashMap<String, ReservableLock> localMap : map.values()) {
            synchronized (localMap) {
                if (!localMap.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void syncAll () {
        while (!trySyncAll()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                //NOP
            }
        }
    }

    static class ReservableLock extends ReentrantLock {
        private AtomicInteger reserve = new AtomicInteger(0);

        void reserve() {
            reserve.incrementAndGet();
        }

        boolean isNonReserved() {
            return reserve.get() <= 0;
        }


        @Override
        public void unlock() {
            super.unlock();
            reserve.decrementAndGet();
        }
    }
}
