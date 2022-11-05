package jp.tkms.utils.debug;

public class DebugElapsedTime {
  long prevCheckPoint;
  long checkPoint;
  String globalPrefix;

  public DebugElapsedTime() {
    this("");
  }

  public DebugElapsedTime(String prefix) {
    checkPoint = System.currentTimeMillis();
    globalPrefix = prefix;
  }

  public long next() {
    prevCheckPoint = checkPoint;
    checkPoint = System.currentTimeMillis();
    return checkPoint - prevCheckPoint;
  }

  public void print(String prefix) {
    System.out.println(globalPrefix + prefix + next());
  }

  public void printToError(String prefix) {
    System.err.println(globalPrefix + prefix + next());
  }

  public void print() {
    System.out.println(globalPrefix + next());
  }

  public void printToError() {
    System.err.println(globalPrefix + next());
  }
}
