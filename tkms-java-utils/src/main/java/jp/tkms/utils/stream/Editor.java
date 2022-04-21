package jp.tkms.utils.stream;

import java.util.function.Function;

@FunctionalInterface
public interface Editor<T> extends Function<T, T> {
}
