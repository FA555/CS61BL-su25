import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class MinHeapTest {

    @Test
    public void testEmptyHeap() {
        MinHeap<Integer> heap = new MinHeap<>();
        assertThat(heap.size()).isEqualTo(0);
        assertThat(heap.findMin()).isNull();
        assertThat(heap.removeMin()).isNull();
    }

    @Test
    public void testSingleInsert() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(5);
        assertThat(heap.size()).isEqualTo(1);
        assertThat(heap.findMin()).isEqualTo(5);
        assertThat(heap.contains(5)).isTrue();
    }

    @Test
    public void testMultipleInsertsAscendingOrder() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        assertThat(heap.size()).isEqualTo(3);
        assertThat(heap.findMin()).isEqualTo(1);
    }

    @Test
    public void testMultipleInsertsDescendingOrder() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(3);
        heap.insert(2);
        heap.insert(1);
        assertThat(heap.findMin()).isEqualTo(1);
        assertThat(heap.size()).isEqualTo(3);
    }

    @Test
    public void testRemoveMinSingleElement() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(42);
        Integer removed = heap.removeMin();
        assertThat(removed).isEqualTo(42);
        assertThat(heap.size()).isEqualTo(0);
        assertThat(heap.findMin()).isNull();
    }

    @Test
    public void testRemoveMinMultipleElements() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(5);
        heap.insert(3);
        heap.insert(4);
        assertThat(heap.removeMin()).isEqualTo(3);
        assertThat(heap.removeMin()).isEqualTo(4);
        assertThat(heap.removeMin()).isEqualTo(5);
        assertThat(heap.removeMin()).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertDuplicateThrows() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(7);
        heap.insert(7); // Should throw
    }

    @Test
    public void testContainsPositiveAndNegative() {
        MinHeap<String> heap = new MinHeap<>();
        heap.insert("apple");
        heap.insert("banana");
        assertThat(heap.contains("apple")).isTrue();
        assertThat(heap.contains("cherry")).isFalse();
    }
}
