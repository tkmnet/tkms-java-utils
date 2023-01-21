package jp.tkms.utils.abbreviation;

import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class Simple {
    public static void waitUntil(BooleanSupplier condition, TimeUnit timeUnit, int duration) throws InterruptedException {
        while (condition.getAsBoolean()) {
            timeUnit.sleep(duration);
        }
    }

    public static void waitFor(BooleanSupplier condition, TimeUnit timeUnit, int duration) throws InterruptedException {
        while (!condition.getAsBoolean()) {
            timeUnit.sleep(duration);
        }
    }

    public static void sleep(TimeUnit timeUnit, long time) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            //NOP
        }
    }

    public static void printSimpleStackTrace(Throwable e) {
        StringBuilder builder = new StringBuilder();
        builder.append(e.getClass().getSimpleName());
        builder.append('[');
        builder.append(e.getMessage());
        builder.append(']');
        String prevClassName = "";
        for (StackTraceElement element : e.getStackTrace()) {
            builder.append("->(");
            if (prevClassName.equals(element.getClassName())) {
                builder.append("\".");
                builder.append(element.getMethodName());
            } else {
                builder.append(element.getClassName().replaceFirst(".*\\.", ""));
                builder.append('.');
                builder.append(element.getMethodName());
                prevClassName = element.getClassName();
            }
            builder.append(':');
            builder.append(element.getLineNumber());
            builder.append(')');
        }
        System.err.println(builder.toString());
    }
}
