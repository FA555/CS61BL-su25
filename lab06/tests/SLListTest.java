import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class SLListTest {

    @Test
    public void testSLListAdd() {
        SLList test1 = SLList.of(1, 3, 5); /* test1: {1, 3, 5} */
        SLList test2 = new SLList(); /* test2: {} */

        test1.add(1, 2); /* test1: {1, 2, 3, 5}*/
        test1.add(3, 4); /* test1: {1, 2, 3, 4, 5}*/
        assertWithMessage("test1 does not have a size of 5").that(test1.size()).isEqualTo(5);
        assertWithMessage("test1 does not have 3 at index 2 or 4 at index 3").that(test1.equals(SLList.of(1, 2, 3, 4, 5))).isTrue();

        test2.add(1, 1); /* test2: {1} */
        assertWithMessage("test2 does not contain 1").that(test2.equals(SLList.of(1))).isTrue();
        assertWithMessage("test2 does not have a size of 1").that(test2.size()).isEqualTo(1);

        test2.add(10, 10); /* test2: {1, 10} */
        assertWithMessage("test2 is incorrect after adding at an out-of-bounds index").that(test2.equals(SLList.of(1, 10))).isTrue();
        test1.add(0, 0); /* test1: {0, 1, 2, 3, 4, 5}*/
        assertWithMessage("test1 is incorrect after addition at the front").that(test1.equals(SLList.of(0, 1, 2, 3, 4, 5))).isTrue();
    }

    @Test
    public void testSLListReverse() {
        // DONE: Add tests
        SLList test = new SLList();

        test.reverse();
        assertWithMessage("Reversing an empty list should not change it").that(test.equals(new SLList())).isTrue();

        test.addFirst(1); // test: {1}
        test.reverse(); // test: {1}
        assertWithMessage("Reversing a single element list should not change it").that(test.equals(SLList.of(1))).isTrue();

        test.addFirst(2); // test: {2, 1}
        test.reverse(); // test: {1, 2}
        assertWithMessage("Reversing a two element list should swap the elements").that(test.equals(SLList.of(1, 2))).isTrue();

        test.addFirst(3); // test: {3, 1, 2}
        test.reverse(); // test: {2, 1, 3}
        assertWithMessage("Reversing a three element list should reverse the order").that(test.equals(SLList.of(2, 1, 3))).isTrue();

        test.addFirst(4); // test: {4, 2, 1, 3}
        test.reverse(); // test: {3, 1, 2, 4}
        assertWithMessage("Reversing a four element list should reverse the order").that(test.equals(SLList.of(3, 1, 2, 4))).isTrue();

        test.addFirst(5); // test: {5, 3, 1, 2, 4}
        test.addFirst(6); // test: {6, 5, 3, 1, 2, 4}
        test.reverse(); // test: {4, 2, 1, 3, 5, 6}
        assertWithMessage("Reversing a six element list should reverse the order").that(test.equals(SLList.of(4, 2, 1, 3, 5, 6))).isTrue();
    }
}
