package wordnet;

import java.util.ArrayList;

/**
 * A class representing a directed graph, where each edge is an instance of the Edge class.
 * Assumed that the vertices are represented by integers, and are zero-indexed.
 */
public class Graph<T> {
    private final ArrayList<T> words = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> edges = new ArrayList<>();

    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= vertexAmount()) {
            throw new IllegalArgumentException("Vertex " + vertex + " is out of bounds.");
        }
    }

    private void validateVertex(int... vertices) {
        for (int vertex : vertices) {
            validateVertex(vertex);
        }
    }

    public int vertexAmount() {
        return words.size();
    }

    public void addVertex(int vertex) {
        if (vertex < 0) {
            throw new IllegalArgumentException("Vertex index cannot be negative.");
        }
        while (vertex >= vertexAmount()) {
            words.add(null);
            edges.add(new ArrayList<>());
        }
    }

    public void setVertex(int vertex, T item) {
        addVertex(vertex);
        this.words.set(vertex, item);
    }

    public T getVertex(int vertex) {
        validateVertex(vertex);

        return words.get(vertex);
    }

    public void addEdge(int from, int to) {
        validateVertex(from, to);
        edges.get(from).add(to);
    }

    public ArrayList<Integer> getEdges(int vertex) {
        validateVertex(vertex);

        return edges.get(vertex);
    }
}
