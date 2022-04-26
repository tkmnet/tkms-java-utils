package jp.tkms.utils.concurrent;

import jp.tkms.utils.value.ObjectWrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StaticExecutorService {
    private static boolean hookAdded = false;
    private static ObjectWrapper<ExecutorService> executorService = new ObjectWrapper<>();

    public static ExecutorService get() {
        synchronized (executorService) {
            if (executorService.get() == null || (executorService.get() != null && executorService.get().isShutdown())) {
                executorService.set(Executors.newWorkStealingPool());
                if (!hookAdded) {
                    hookAdded = true;
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> executorService.get().shutdown()));
                }
            }
            return executorService.get();
        }
    }
}
