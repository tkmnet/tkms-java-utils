package jp.tkms.utils.tricky;

import jp.tkms.utils.value.IntRange;

public class GarbageCollectionAndTry {
  public static void process(int maxTrial, Process process) {
    boolean isDone = false;
    System.gc();
    for (int i : IntRange.range(0, maxTrial)) {
      try {
        process.run();
      } catch (Exception e) {
        System.gc();
        continue;
      }
      isDone = true;
      break;
    }
    if (!isDone) {
      throw new ProcessFailedException();
    }
  }

  @FunctionalInterface
  public interface Process {
    void run() throws Exception;
  }

  public static class ProcessFailedException extends RuntimeException {
    public ProcessFailedException() {
      super("The process in GarbageCollectionAndTry is failed after maximum GC trials.");
    }
  }
}
