import java.util.Arrays;
import java.util.stream.IntStream;

public class UnionFind {

    /* TODO: Add instance variables here. */
    private final int[] parent;
    private final int[] size;

    private void validateIndex(int x) {
        if  (x < 0 || x >= parent.length) {
            throw new IllegalArgumentException("index out of range");
        }
    }

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // DONE: YOUR CODE HERE
        parent = new int[N];
        Arrays.fill(parent, -1);

        size = new int[N];
        Arrays.fill(size, 1);
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // DONE: YOUR CODE HERE
        validateIndex(v);
        return size[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // DONE: YOUR CODE HERE
        validateIndex(v);
        if  (parent[v] == -1) {
            return -size[v];
        }
        return parent[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // DONE: YOUR CODE HERE
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // DONE: YOUR CODE HERE
        validateIndex(v);

        if (parent[v] == -1) {
            return v;
        }
        parent[v] = find(parent[v]);
        return parent[v];
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        validateIndex(v1);
        validateIndex(v2);

        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) {
            return;
        }

        if (size[p1] > size[p2]) {
            int temp = p1;
            p1 = p2;
            p2 = temp;
        }
        parent[p1] = p2;
        size[p2] += size[p1];
    }
}
