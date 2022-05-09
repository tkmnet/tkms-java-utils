package jp.tkms.utils.debug;

public class DebugElapsedTime {
    long prevCheckPoint;
    long checkPoint;

    public DebugElapsedTime() {
        checkPoint = System.currentTimeMillis();
    }

    public long next() {
        prevCheckPoint = checkPoint;
        checkPoint = System.currentTimeMillis();
        return checkPoint - prevCheckPoint;
    }

    public void print(String prefix) {
        System.out.println(prefix + next());
    }

    public void printToError(String prefix) {
        System.err.println(prefix + next());
    }

    public void print() {
        System.out.println(next());
    }

    public void printToError() {
        System.err.println(next());
    }
}
