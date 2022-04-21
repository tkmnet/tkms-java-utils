package jp.tkms.utils.parallel;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class ParallelProcess {
  private ExecutorService executorService;
  private ArrayList<Future> list;

  public ParallelProcess(ExecutorService executorService) {
    this.executorService = executorService;
    this.list = new ArrayList<>();
  }

  public ParallelProcess() {
    this(StaticExecutorService.get());
  }

  public void submit(Runnable runnable) {
    list.add(executorService.submit(runnable));
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
    submitAndWait(StaticExecutorService.get(), consumer);
  }
}
