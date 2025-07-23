import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class BinarySearchTreeTest {

    // DONE: add some of your own tests here to test your implementation!

    @Test
    public void containsTest() {
        BinarySearchTree<Integer> x = new BinarySearchTree<>();
        assertWithMessage("BST should not contain anything immediately after instantiation").that(x.contains(2)).isFalse();
        x.add(2);
        assertWithMessage("BST should contain 2 after adding 2").that(x.contains(2)).isTrue();
        x.add(3);
        assertWithMessage("BST should contain 3 after adding 3").that(x.contains(3)).isTrue();
        x.add(4);
        assertWithMessage("BST should contain 4 after adding 4").that(x.contains(4)).isTrue();
        x.add(2);
        x.delete(2);
        assertWithMessage("BST should not contain 2 after deleting 2").that(x.contains(2)).isFalse();
    }

    @Test
    public void basicContainsTest() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertThat(bst.contains(10)).isFalse();

        bst.add(10);
        assertThat(bst.contains(10)).isTrue();
        assertThat(bst.contains(5)).isFalse();
        assertThat(bst.contains(15)).isFalse();
    }

    @Test
    public void addAndContainsTest() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(10);
        bst.add(5);
        bst.add(15);
        bst.add(3);
        bst.add(7);
        bst.add(12);
        bst.add(17);

        assertThat(bst.contains(10)).isTrue();
        assertThat(bst.contains(5)).isTrue();
        assertThat(bst.contains(15)).isTrue();
        assertThat(bst.contains(3)).isTrue();
        assertThat(bst.contains(7)).isTrue();
        assertThat(bst.contains(12)).isTrue();
        assertThat(bst.contains(17)).isTrue();
        assertThat(bst.contains(100)).isFalse();
    }

    @Test
    public void deleteLeafNodeTest() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(10);
        bst.add(5);
        bst.add(15);
        bst.add(3); // leaf

        assertThat(bst.contains(3)).isTrue();
        bst.delete(3);
        assertThat(bst.contains(3)).isFalse();
    }

    @Test
    public void deleteNodeWithOneChildTest() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(10);
        bst.add(5);
        bst.add(3);
        bst.add(1); // 3 has only left child

        assertThat(bst.contains(3)).isTrue();
        bst.delete(3);
        assertThat(bst.contains(3)).isFalse();
        assertThat(bst.contains(1)).isTrue();
    }

    @Test
    public void deleteNodeWithTwoChildrenTest() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(20);
        bst.add(10);
        bst.add(30);
        bst.add(25);
        bst.add(35);

        assertThat(bst.contains(30)).isTrue();
        bst.delete(30);
        assertThat(bst.contains(30)).isFalse();
        assertThat(bst.contains(25)).isTrue(); // replacement candidate
        assertThat(bst.contains(35)).isTrue();
    }

    @Test
    public void deleteRootTest() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(10);
        bst.add(5);
        bst.add(15);

        assertThat(bst.contains(10)).isTrue();
        bst.delete(10);
        assertThat(bst.contains(10)).isFalse();
        assertThat(bst.contains(5)).isTrue();
        assertThat(bst.contains(15)).isTrue();
    }

    @Test
    public void deleteNonexistentElementTest() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(10);
        bst.add(5);

        assertThat(bst.contains(100)).isFalse();
        Integer result = bst.delete(100);
        assertThat(result).isNull();
    }

}
