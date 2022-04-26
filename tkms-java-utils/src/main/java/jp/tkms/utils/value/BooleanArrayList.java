package jp.tkms.utils.value;

import java.util.*;

public class BooleanArrayList implements List<Boolean> {
    private int size;
    private int cursor;
    private ArrayList<Byte> list;

    public BooleanArrayList() {
        list = new ArrayList<>();
        size = 0;
        cursor = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public boolean contains(Object o) {
        return true;
    }

    @Override
    public Iterator<Boolean> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }

    @Override
    public boolean add(Boolean aBoolean) {
        byte value = (byte)((aBoolean ? (byte)0x1 : (byte)0x0) << cursor);
        if (cursor == 0) {
            list.add(value);
        } else {
            int i = size /8;
            list.set(i, (byte)(list.get(i) | value));
        }
        size += 1;
        cursor = (cursor +1) %8;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Boolean> collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, Collection<? extends Boolean> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Boolean get(int i) {
        if (i >= size) {
            throw new IndexOutOfBoundsException(i);
        }
        byte pos = (byte)(0x1 << (i %8));
        return (list.get(i /8) & pos) != 0x0;
    }

    @Override
    public Boolean set(int i, Boolean aBoolean) {
        return null;
    }

    @Override
    public void add(int i, Boolean aBoolean) {

    }

    @Override
    public Boolean remove(int i) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Boolean> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Boolean> listIterator(int i) {
        return null;
    }

    @Override
    public List<Boolean> subList(int i, int i1) {
        return null;
    }
}
