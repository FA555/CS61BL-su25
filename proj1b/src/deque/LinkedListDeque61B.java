package deque;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private final Node sentinel = new Node(null, null, null);
    private int size = 0;

    public LinkedListDeque61B() {
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        ++size;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        ++size;
    }

    @Override
    public List<T> toList() {
        List<T> list = new java.util.ArrayList<>();
        for (Node current = sentinel.next; current != sentinel; current = current.next) {
            list.add(current.item);
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

        Node firstNode = sentinel.next;
        sentinel.next = firstNode.next;
        firstNode.next.prev = sentinel;
        --size;

        return firstNode.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        Node lastNode = sentinel.prev;
        sentinel.prev = lastNode.prev;
        lastNode.prev.next = sentinel;
        --size;

        return lastNode.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        return getRecursiveHelper(sentinel.next, index);
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque61B<?> other) || size != other.size()) {
            return false;
        }

        Iterator<T> iter = iterator();
        Iterator<?> thatIter = other.iterator();
        while (iter.hasNext() && thatIter.hasNext()) {
            if (!iter.next().equals(thatIter.next())) {
                return false;
            }
        }
        return !iter.hasNext() && !thatIter.hasNext();
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node current = sentinel.next;

        @Override
        public boolean hasNext() {
            return current != sentinel;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        T item;
        Node prev;
        Node next;

        Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
