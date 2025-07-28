import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    /* TODO: Instance variables here */
    private final double loadFactorThreshold;
    private final int initialCapacity;

    private LinkedList<Entry<K, V>>[] buckets;
    private int size;

    /* TODO: Constructors here */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity, double loadFactor) {
        size = 0;
        this.initialCapacity = initialCapacity;
        loadFactorThreshold = loadFactor;
        buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public HashMap(int initialCapacity) {
        this(initialCapacity, LOAD_FACTOR_THRESHOLD);
    }

    public HashMap() {
        this(INITIAL_CAPACITY, LOAD_FACTOR_THRESHOLD);
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private double loadFactor() {
        return (double) size / capacity();
    }

    private void resize() {
        int newCapacity = buckets.length * 2;
        @SuppressWarnings("unchecked")
        LinkedList<Entry<K, V>>[] newBuckets = (LinkedList<Entry<K, V>>[]) new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<>();
        }

        for (LinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                int newIndex = Math.floorMod(entry.key.hashCode(), newCapacity);
                newBuckets[newIndex].add(entry);
            }
        }

        buckets = newBuckets;
    }

    private void try_resize() {
        if (loadFactor() > loadFactorThreshold) {
            resize();
        }
    }

    public int capacity() {
        return buckets.length;
    }

    /* TODO: Interface methods here */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        size = 0;
        buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    @Override
    public boolean containsKey(K key) {
        for (Entry<K, V> entry : buckets[hash(key)]) {
            if (entry.key == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        for (Entry<K, V> entry : buckets[hash(key)]) {
            if (entry.keyEquals(new Entry<>(key, null))) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        remove(key);
        buckets[hash(key)].add(new Entry<>(key, value));
        ++size;
        try_resize();
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        Iterator<Entry<K, V>> iter = buckets[index].iterator();
        while (iter.hasNext()) {
            Entry<K, V> entry = iter.next();
            if (entry.keyEquals(new Entry<>(key, null))) {
                iter.remove();
                --size;
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        V removedValue = remove(key);
        if (removedValue != null && removedValue.equals(value)) {
            return true;
        }
        if (removedValue != null) {
            put(key, removedValue);
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @Nonnull
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    private static class Entry<K, V> {

        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry<K, V> other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry<?, ?>) other).key)
                    && value.equals(((Entry<?, ?>) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

    private class HashMapIterator implements Iterator<K> {
        int currentBucket = 0;
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            while (currentBucket < INITIAL_CAPACITY) {
                if (currentIndex < buckets[currentBucket].size()) {
                    return true;
                }
                currentBucket++;
                currentIndex = 0;
            }
            return false;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Entry<K, V> entry = buckets[currentBucket].get(currentIndex);
            currentIndex++;
            return entry.key;
        }
    }
}
