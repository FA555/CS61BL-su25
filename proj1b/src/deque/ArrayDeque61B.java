package deque;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private static final int INITIAL_CAPACITY = 8;
    private static final int RESIZE_FACTOR = 2;
    private static final double SHRINK_FACTOR = 0.25;

    private T[] data;
    private int size = 0;
    private int nextFirst = 0;
    private int nextLast = 1;

    @SuppressWarnings("unchecked")
    public ArrayDeque61B() {
        data = (T[]) new Object[INITIAL_CAPACITY];
    }

    public int capacity() {
        return data.length;
    }

    private void resize(int newCapacity) {
        @SuppressWarnings("unchecked")
        T[] newData = (T[]) new Object[newCapacity];

        for (int i = 0; i < size; ++i) {
            newData[i] = data[(nextFirst + 1 + i) % capacity()];
        }
        data = newData;
        nextFirst = newCapacity - 1;
        nextLast = size;
    }

    private void checkResize() {
        if (size == capacity()) {
            resize(size * RESIZE_FACTOR);
        } else if (size < capacity() * SHRINK_FACTOR && capacity() > INITIAL_CAPACITY) {
            resize(size * RESIZE_FACTOR);
        }
    }

    private int indexOffset(int index, int offset) {
        return (index + offset + capacity()) % capacity();
    }

    private int indexInc(int index) {
        return indexOffset(index, 1);
    }

    private int indexDec(int index) {
        return indexOffset(index, -1);
    }

    @Override
    public void addFirst(T x) {
        checkResize();
        data[nextFirst] = x;
        nextFirst = indexDec(nextFirst);
        ++size;
    }

    @Override
    public void addLast(T x) {
        checkResize();
        data[nextLast] = x;
        nextLast = indexInc(nextLast);
        ++size;
    }

    @Override
    public List<T> toList() {
        List<T> list = new java.util.ArrayList<>(size);
        for (int i = indexInc(nextFirst); i != nextLast; i = indexInc(i)) {
            list.add(data[i]);
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        nextFirst = indexInc(nextFirst);
        --size;
        T removedItem = data[nextFirst];
        data[nextFirst] = null;
        checkResize();
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        nextLast = indexDec(nextLast);
        --size;
        T removedItem = data[nextLast];
        data[nextLast] = null;
        checkResize();
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return data[indexOffset(nextFirst, 1 + index)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B");
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque61B<?> other) || size != other.size()) {
            return false;
        }

        Iterator<T> iter = iterator();
        Iterator<?> otherIter = other.iterator();
        while (iter.hasNext() && otherIter.hasNext()) {
            if (!iter.next().equals(otherIter.next())) {
                return false;
            }
        }
        return !iter.hasNext() && !otherIter.hasNext();
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int currentIndex = indexInc(nextFirst);

        @Override
        public boolean hasNext() {
            return currentIndex != nextLast;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            T item = data[currentIndex];
            currentIndex = indexInc(currentIndex);
            return item;
        }
    }
}
