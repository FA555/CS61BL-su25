import java.util.ArrayList;

public class BinaryTree<T> {

    // Do not modify the TreeNode class.
    static class TreeNode<T> {
        T item;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T item) {
            this.item = item; left = right = null;
        }

        public TreeNode(T item, TreeNode<T> left, TreeNode<T> right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }

        public T getItem() {
            return item;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public TreeNode<T> getRight() {
            return right;
        }
    }

    // protected gives subclasses the ability to access this instance variable,
    // but not other classes.
    TreeNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(TreeNode<T> t) {
        root = t;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    /** Optional constructor, see optional exercise in lab (or last week's theoretical lab). */
    public BinaryTree(ArrayList<T> pre, ArrayList<T> in) {
        if (pre == null || in == null || pre.size() != in.size()) {
            throw new IllegalArgumentException("Invalid input arrays");
        }
        root = buildTree(pre, in, 0, pre.size(), 0, in.size());
    }

    private TreeNode<T> buildTree(ArrayList<T> pre, ArrayList<T> in, int preStart, int preEnd, int inStart, int inEnd) {
        if (preStart >= preEnd || inStart >= inEnd) {
            return null;
        }

        T rootValue = pre.get(preStart);
        int rootIndex = in.indexOf(rootValue);
        int leftSize = rootIndex - inStart;

        TreeNode<T> root = new TreeNode<>(rootValue);
        root.left = buildTree(pre, in, preStart + 1, preStart + leftSize + 1, inStart, rootIndex);
        root.right = buildTree(pre, in, preStart + leftSize + 1, preEnd, rootIndex + 1, inEnd);
        return root;
    }

    /* Print the values in the tree in preorder. */
    public void printPreorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            printPreorderHelper(root);
            System.out.println();
        }
    }

    private void printPreorderHelper(TreeNode<T> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.item + " ");
        printPreorderHelper(node.left);
        printPreorderHelper(node.right);
    }

    /* Print the values in the tree in inorder: values in the left subtree
       first (in inorder), then the root value, then values in the first
       subtree (in inorder). */
    public void printInorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            printInorderHelper(root);
            System.out.println();
        }
    }

    /* Prints the nodes of the BinaryTree in inorder. Used for your testing. */
    private void printInorderHelper(TreeNode<T> node) {
        if (node == null) {
            return;
        }
        printInorderHelper(node.left);
        System.out.print(node.item + " ");
        printInorderHelper(node.right);
    }

    /* Prints out the contents of a BinaryTree with a description in both
       preorder and inorder. */
    static void print(BinaryTree t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    /* Fills this BinaryTree with values a, b, and c. DO NOT MODIFY. */
    public static BinaryTree<String> sampleTree1() {
        TreeNode<String> root = new TreeNode("a",
                new TreeNode("b"),
                new TreeNode("c"));
        return new BinaryTree<>(root);
    }

    /* Fills this BinaryTree with values a, b, and c, d, e, f. DO NOT MODIFY. */
    public static BinaryTree<String> sampleTree2() {
        TreeNode root = new TreeNode("a",
                new TreeNode("b",
                        new TreeNode("d",
                                new TreeNode("e"),
                                new TreeNode("f")),
                        null),
                new TreeNode("c"));
        return new BinaryTree<>(root);
    }

    /* Fills this BinaryTree with the values a, b, c, d, e, f. DO NOT MODIFY. */
    public static BinaryTree<String> sampleTree3() {
        TreeNode<String> root = new TreeNode("a",
                new TreeNode("b"),
                new TreeNode("c",
                        new TreeNode("d",
                                new TreeNode("e"),
                                new TreeNode("f")),
                        null));
        return new BinaryTree<>(root);
    }

    /* Fills this BinaryTree with the same leaf TreeNode. DO NOT MODIFY. */
    public static BinaryTree<String> sampleTree4() {
        TreeNode<String> leafNode = new TreeNode("c");
        TreeNode<String> root = new TreeNode("a", new TreeNode("b", leafNode, leafNode),
                new TreeNode("d", leafNode, leafNode));
        return new BinaryTree<>(root);
    }

    /* Creates two BinaryTrees and prints them out in inorder. */
    public static void main(String[] args) {
        BinaryTree<String> t = new BinaryTree<>();
        print(t, "the empty tree");
        t = BinaryTree.sampleTree1();
        print(t, "sample tree 1");
        t = BinaryTree.sampleTree2();
        print(t, "sample tree 2");
        t = BinaryTree.sampleTree3();
        print(t, "sample tree 3");
        t = BinaryTree.sampleTree4();
        print(t, "sample tree 4");
    }

    /* Returns the height of the tree. */
    public int height() {
        // DONE: YOUR CODE HERE
        return heightHelper(root);
    }

    private int heightHelper(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(heightHelper(node.left), heightHelper(node.right));
    }

    /* Returns true if the tree's left and right children are the same height
       and are themselves completely balanced. */
    public boolean isCompletelyBalanced() {
        // DONE: YOUR CODE HERE
        return isCompletelyBalancedHelper(root) >= 0;
    }

    /**
     * Returns the height of the tree if it is completely balanced,
     * or a number < 0 if it is not.
     */
    private int isCompletelyBalancedHelper(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = isCompletelyBalancedHelper(node.left);
        if (leftHeight < 0) {
            return -1;
        }

        int rightHeight = isCompletelyBalancedHelper(node.right);
        if (rightHeight < 0 || leftHeight != rightHeight) {
            return -1;
        }

        return 1 + leftHeight;
    }

    /* Returns a BinaryTree representing the Fibonacci calculation for n. */
    public static BinaryTree<Integer> fibTree(int n) {
        BinaryTree<Integer> result = new BinaryTree<>();
        // TODO: YOUR CODE HERE
        if (n < 0) {
            return null;
        }
        return new BinaryTree<>(fibTreeHelper(n));
    }

    private static TreeNode<Integer> fibTreeHelper(int n) {
        if (n == 0) {
            return new TreeNode<>(0);
        }
        if (n == 1) {
            return new TreeNode<>(1);
        }
        TreeNode<Integer> left = fibTreeHelper(n - 1);
        TreeNode<Integer> right = fibTreeHelper(n - 2);
        return new TreeNode<>(left.item + right.item, left, right);
    }
}
