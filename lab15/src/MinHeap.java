import java.util.ArrayList;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;
    // TODO: YOUR CODE HERE (no code should be needed here if not implementing the more optimized version)

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        // DONE: YOUR CODE HERE
        return index * 2;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        // DONE: YOUR CODE HERE
        return index * 2 + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        // DONE: YOUR CODE HERE
        return index / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        // DONE: YOUR CODE HERE
        E e1 = getElement(index1);
        if (e1 == null) {
            return index2;
        }

        E e2 = getElement(index2);
        if (e2 == null) {
            return index1;
        }

        return e1.compareTo(e2) <= 0 ? index1 : index2;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        // DONE: YOUR CODE HERE
        return getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        // DONE: YOUR CODE HERE
        while (index > 1) {
            int parent = getParentOf(index);
            E currentElement = getElement(index);
            E parentElement = getElement(parent);

            assert currentElement != null && parentElement != null;
            if (currentElement.compareTo(parentElement) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        // DONE: YOUR CODE HERE
        while (getLeftOf(index) < contents.size()) {
            int target = min(getLeftOf(index), getRightOf(index));
            E currentElement = getElement(index);
            E targetElement = getElement(target);

            assert currentElement != null && targetElement != null;
            if (currentElement.compareTo(targetElement) > 0) {
                swap(index, target);
                index = target;
            } else {
                break;
            }
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        // DONE: YOUR CODE HERE
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        // DONE: YOUR CODE HERE
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null into MinHeap.");
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Element already exists in MinHeap: " + element);
        }

        setElement(++size, element);
        bubbleUp(size);
    }

    /* Returns and removes the smallest element in the MinHeap, or null if there are none. */
    public E removeMin() {
        // DONE: YOUR CODE HERE
        if (size == 0) {
            return null;
        }

//        swap(1, size--);
//        E minElement = contents.removeLast();
//        bubbleDown(1);
//        return minElement;

        // The autograder runs an old version of Java which does not support removeLast.
        E minElement = getElement(1);
        swap(1, size);
        contents.remove(size--);
        bubbleDown(1);
        return minElement;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        // TODO: OPTIONAL
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        // OPTIONAL: OPTIMIZE THE SPEED OF THIS TO MAKE IT CONSTANT
        for (int i = 1; i < contents.size(); i++) {
            if (element.equals(contents.get(i))) {
                return true;
            }
        }
        return false;
    }
}
