package jp.tkms.utils.value;

import java.util.Iterator;

public class IntRange implements Iterable<Integer> {
    boolean isReversed;
    int start, end;

    IntRange(int start, int end) {
        this.start = start;
        this.end = end;
        isReversed = start > end;
    }

    public static IntRange range(int startInclusive, int endExclusive) {
        if (startInclusive == endExclusive) endExclusive += 1;
        return new IntRange(startInclusive, endExclusive -1);
    }

    public static IntRange range(int endExclusive) {
        return range(0, endExclusive);
    }

    public static IntRange rangeClosed(int startInclusive, int endInclusive) {
        return new IntRange(startInclusive, endInclusive);
    }

    public static IntRange rangeClosed(int endInclusive) {
        return rangeClosed(0, endInclusive);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new IntRangeIterator();
    }

    public class IntRangeIterator implements Iterator<Integer> {
        int cur;

        IntRangeIterator() {
            cur = start;
        }

        @Override
        public boolean hasNext() {
            return isReversed ? (cur >= end) : (cur <= end);
        }

        @Override
        public Integer next() {
            return isReversed ? (cur--) : (cur++);
        }
    }
}
