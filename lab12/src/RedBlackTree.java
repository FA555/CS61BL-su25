public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given 2-3 TREE. */
    public RedBlackTree(TwoThreeTree<T> tree) {
        Node<T> ttTreeRoot = tree.root;
        root = buildRedBlackTree(ttTreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }

        if (r.getItemCount() == 1) {
            // DONE: Replace with code to create a 2-node equivalent
            return new RBTreeNode<>(
                    true,
                    r.getItemAt(0),
                    buildRedBlackTree(r.getChildAt(0)),
                    buildRedBlackTree(r.getChildAt(1))
            );
        } else {
            // DONE: Replace with code to create a 3-node equivalent
            return new RBTreeNode<>(
                    true,
                    r.getItemAt(1),
                    new RBTreeNode<>(
                            false,
                            r.getItemAt(0),
                            buildRedBlackTree(r.getChildAt(0)),
                            buildRedBlackTree(r.getChildAt(1))
                    ),
                    buildRedBlackTree(r.getChildAt(2))
            );
        }


    }    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // DONE: YOUR CODE HERE
        assert node != null && node.left != null && node.right != null;

        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // DONE: YOUR CODE HERE
        assert node != null && node.left != null;

        RBTreeNode<T> newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        // Swap colors
        boolean tempColor = node.isBlack;
        root.isBlack = newRoot.isBlack;
        newRoot.isBlack = tempColor;

        return newRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param root
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // DONE: YOUR CODE HERE
        assert node != null && node.right != null;

        RBTreeNode<T> newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        // Swap colors
        boolean tempColor = node.isBlack;
        root.isBlack = newRoot.isBlack;
        newRoot.isBlack = tempColor;

        return newRoot;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // DONE: Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }

        // DONE: Handle normal binary search tree insertion. The below line may help.
        int comp = item.compareTo(node.item);
        if (comp <= 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }

        // DONE: Rotate left operation (handle "middle of three" and "right-leaning red" structures)
        if (isRed(node.right) && !isRed(node.left)) {
            // right-leaning red
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.right)) {
            // middle of three
            node = rotateRight(node);
        }

        // DONE: Rotate right operation (handle "smallest of three" structure)
        if (isRed(node.left) && isRed(node.left.left)) {
            // smallest of three
            node = rotateRight(node);
        }

        // DONE: Color flip (handle "largest of three" structure)
        if (isRed(node.left) && isRed(node.right)) {
            // largest of three
            flipColors(node);
        }

        return node; // DONE: fix this return statement
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

}
