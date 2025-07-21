import deque.ArrayDeque61B;
import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addFirst("back");
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle");
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front");
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    @Test
    public void removeFirstTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.removeFirst()).isNull();

        lld1.addFirst(114);
        assertThat(lld1.removeFirst()).isEqualTo(114);
        assertThat(lld1.toList()).isEmpty();

        lld1.addFirst(-514);
        lld1.addLast(1919);
        lld1.addFirst(810);
        assertThat(lld1.removeFirst()).isEqualTo(810);
        assertThat(lld1.toList()).containsExactly(-514, 1919).inOrder();
    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.removeLast()).isNull();

        lld1.addFirst(114);
        assertThat(lld1.removeLast()).isEqualTo(114);
        assertThat(lld1.toList()).isEmpty();

        lld1.addLast(-514);
        lld1.addLast(1919);
        lld1.addFirst(810);
        assertThat(lld1.removeLast()).isEqualTo(1919);
        assertThat(lld1.toList()).containsExactly(810, -514).inOrder();
    }

    @Test
    public void resizeTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        for (int i = 0; i < 100; ++i) {
            lld1.addLast(i);
        }
        for (int i = 0; i < 100; ++i) {
            lld1.removeFirst();
        }

        Deque61B<Integer> lld2 = new ArrayDeque61B<>();
        for (int i = 0; i < 100; ++i) {
            lld2.addFirst(i);
        }
        for (int i = 0; i < 100; ++i) {
            lld2.removeLast();
        }
    }

    @Test
    public void isEmptyTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(114);
        assertThat(lld1.isEmpty()).isFalse();

        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addLast(-514);
        assertThat(lld1.isEmpty()).isFalse();

        lld1.addFirst(-1919);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void sizeTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.addFirst(114);
        assertThat(lld1.size()).isEqualTo(1);

        lld1.addLast(-514);
        assertThat(lld1.size()).isEqualTo(2);

        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(1);

        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.addLast(1919);
        lld1.addFirst(810);
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    public void getTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.get(0)).isNull();

        lld1.addFirst(114);
        assertThat(lld1.get(0)).isEqualTo(114);
        assertThat(lld1.get(1)).isNull();

        lld1.addLast(-514);
        lld1.addLast(1919);
        lld1.addFirst(810);
        assertThat(lld1.get(0)).isEqualTo(810);
        assertThat(lld1.get(1)).isEqualTo(114);
        assertThat(lld1.get(2)).isEqualTo(-514);
        assertThat(lld1.get(3)).isEqualTo(1919);
        assertThat(lld1.get(4)).isNull();

        assertThat(lld1.get(114514)).isNull();
        assertThat(lld1.get(-1919810)).isNull();
    }

    @Test
    public void integrationTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.toList()).isEmpty();

        lld1.addFirst(1);
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.toList()).containsExactly(1).inOrder();

        lld1.addLast(2);
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(2);
        assertThat(lld1.toList()).containsExactly(1, 2).inOrder();

        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.toList()).containsExactly(2).inOrder();

        lld1.removeLast();
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.toList()).isEmpty();

        lld1.addLast(3);
        lld1.addLast(4);
        assertThat(lld1.get(0)).isEqualTo(3);
    }

}
