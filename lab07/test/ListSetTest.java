import org.junit.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.google.common.truth.Truth.assertThat;


public class ListSetTest {

    @Test
    public void testBasics() {
        ListSet aSet = new ListSet();
        assertWithMessage("Size is not zero upon instantiation").that(aSet.size()).isEqualTo(0);
        for (int i = -50; i < 50; i += 2) {
            aSet.add(i);
            assertWithMessage("aSet should contain " + i).that(aSet.contains(i)).isTrue();
        }

        assertWithMessage("Size is not 50 after 50 calls to add").that(aSet.size()).isEqualTo(50);
        for (int i = -50; i < 50; i += 2) {
            aSet.remove(i);
            assertWithMessage("aSet should not contain " + i).that(!aSet.contains(i)).isTrue();
        }

        assertWithMessage("aSet is not empty after removing all elements").that(aSet.isEmpty()).isTrue();
        assertWithMessage("Size is not zero after removing all elements").that(aSet.size()).isEqualTo(0);
    }

    @Test
    public void testIntegrated() {
        ListSet set = new ListSet();
        assertWithMessage("Set should be empty").that(set.isEmpty()).isTrue();
        assertWithMessage("Set size should be 0").that(set.size()).isEqualTo(0);

        for (int i = 1; i < 100; i++) {
            set.add(i);
            assertWithMessage("Set should contain " + i).that(set.contains(i)).isTrue();
            assertWithMessage("Set size should be " + i).that(set.size()).isEqualTo(i);
        }

        for (int i = 1; i < 100; i++) {
            set.remove(i);
            assertWithMessage("Set should not contain " + i).that(!set.contains(i)).isTrue();
            assertWithMessage("Set size should be " + (100 - i - 1)).that(set.size()).isEqualTo(100 - i - 1);
        }

        set.add(1);
        set.add(1);
        set.add(4);
        assertWithMessage("Set should contain 1 after adding it multiple times").that(set.contains(1)).isTrue();

        assertThat(set.toIntArray()).asList().containsExactly(1, 4);
    }

}
