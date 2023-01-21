package jp.tkms.utils.concurrent;

import jp.tkms.utils.value.ObjectWrapper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class ParallelProcess {
    private int parallelism = -1;
    private ObjectWrapper<ExecutorService> executorService = new ObjectWrapper<>();
    private ArrayList<Future> list;

    public ParallelProcess(ExecutorService executorService) {
        this.executorService.set(executorService);
        this.list = new ArrayList<>();
    }

    public ParallelProcess(int parallelism) {
        this(null);
        this.parallelism = parallelism;
    }

    public ParallelProcess() {
        this(ForkJoinPool.commonPool());
    }

    private ExecutorService getExecutorService() {
        try {
            return executorService.get(() -> Executors.newWorkStealingPool(parallelism));
        } catch (Exception e) {
            ExecutorService service = Executors.newWorkStealingPool(parallelism);
            executorService.set(service);
            return service;
        }
    }

    public void submit(Runnable runnable) {
        synchronized (list) {
            list.add(getExecutorService().submit(runnable));
        }
    }

    public void waitFor() throws Exception {
        Exception lastException = null;
        for (Future future : list) {
            try {
                future.get();
            } catch (Exception e) {
                lastException = e;
            }
        }
        list.clear();
        if (parallelism > 0) {
            getExecutorService().shutdown();
            executorService.set(null);
        }
        if (lastException != null) {
            throw lastException;
        }
    }

    public static void submitAndWait(ExecutorService executorService, Consumer<ParallelProcess> consumer) throws Exception {
        ParallelProcess process = new ParallelProcess(executorService);
        consumer.accept(process);
        process.waitFor();
    }

    public static void submitAndWait(Consumer<ParallelProcess> consumer) throws Exception {
        submitAndWait(ForkJoinPool.commonPool(), consumer);
    }
}
