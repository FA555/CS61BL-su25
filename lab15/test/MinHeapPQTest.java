import org.junit.Test;

import java.util.NoSuchElementException;

import static com.google.common.truth.Truth.*;


public class MinHeapPQTest {

    @Test
    public void testAddOneThing() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("l", 2);
        String item = pq.poll();
        assertThat("l").isEqualTo(item);
    }

    @Test
    public void testAddThenRemove() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("h", 100);
        pq.insert("i", 0);
        String item = pq.poll();
        assertThat("i").isEqualTo(item);
        assertThat("h").isEqualTo(pq.poll());
    }

    /**
     * Tests that a MinHeapPQ can add and remove a single element.
     */
    @Test
    public void testOneThing() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        assertThat(pq.poll()).isNull();
        pq.insert("l", 2);
        assertThat(1).isEqualTo(pq.size());
        String item = pq.poll();
        assertThat("l").isEqualTo(item);
        assertThat(0).isEqualTo(pq.size());
    }

    // TODO: add some of your own tests here!

    @Test
    public void testPollEmpty() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        assertThat(pq.poll()).isNull();
    }

    @Test
    public void testPeekDoesNotRemove() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("a", 1);
        assertThat(pq.peek()).isEqualTo("a");
        assertThat(pq.size()).isEqualTo(1);
        assertThat(pq.poll()).isEqualTo("a");
    }

    @Test
    public void testInsertAndPollPriorityOrder() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("c", 3.0);
        pq.insert("a", 1.0);
        pq.insert("b", 2.0);
        assertThat(pq.poll()).isEqualTo("a");
        assertThat(pq.poll()).isEqualTo("b");
        assertThat(pq.poll()).isEqualTo("c");
    }

    @Test
    public void testInsertDuplicateThrows() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("x", 5);
        try {
            pq.insert("x", 10);
            assert_().fail();
        } catch (IllegalArgumentException expected) {
            // pass
        }
    }

    @Test
    public void testSizeTracksCorrectly() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        assertThat(pq.size()).isEqualTo(0);
        pq.insert("a", 1);
        assertThat(pq.size()).isEqualTo(1);
        pq.insert("b", 2);
        assertThat(pq.size()).isEqualTo(2);
        pq.poll();
        assertThat(pq.size()).isEqualTo(1);
        pq.poll();
        assertThat(pq.size()).isEqualTo(0);
    }

    @Test
    public void testContains() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("alpha", 1);
        pq.insert("beta", 2);
        assertThat(pq.contains("alpha")).isTrue();
        assertThat(pq.contains("gamma")).isFalse();
    }

    @Test
    public void testChangePriorityBubblesUp() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("a", 5);
        pq.insert("b", 3);
        pq.insert("c", 4);
        pq.changePriority("a", 1); // Now "a" should be at the top
        assertThat(pq.poll()).isEqualTo("a");
    }

    @Test(expected = NoSuchElementException.class)
    public void testChangePriorityThrowsWhenMissing() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("x", 1);
        pq.changePriority("y", 2); // Should throw
    }

    @Test
    public void testChangePriorityChangesOrder() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("x", 10);
        pq.insert("y", 5);
        pq.insert("z", 7);
        pq.changePriority("z", 1);
        assertThat(pq.poll()).isEqualTo("z");
        assertThat(pq.poll()).isEqualTo("y");
        assertThat(pq.poll()).isEqualTo("x");
    }
}