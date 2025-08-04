import java.util.*;

public class Graph {
    private final LinkedList<Edge>[] adjLists;
    private final int[] inDegrees;
    private final int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    @SuppressWarnings("unchecked")
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        inDegrees = new int[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<>();
        }
        vertexCount = numVertices;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertexCount) {
            throw new IllegalArgumentException("Vertex " + v + " is not in the graph.");
        }
    }

    private void validateVertex(int... vertices) {
        for (int v : vertices) {
            validateVertex(v);
        }
    }

    private void trulyAddNewEdge(int v1, int v2, int weight) {
        adjLists[v1].add(new Edge(v1, v2, weight));
        ++inDegrees[v2];
    }

    /* Adds a directed Edge (V1, V2) to the graph. That is, adds an edge
       in ONE directions, from v1 to v2. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        // DONE: YOUR CODE HERE
        validateVertex(v1, v2);

        for (Edge e : adjLists[v1]) {
            if (e.to == v2) {
                e.weight = weight;
                return;
            }
        }
        trulyAddNewEdge(v1, v2, weight);
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        // DONE: YOUR CODE HERE
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        // DONE: YOUR CODE HERE
        validateVertex(from, to);
        for (Edge e : adjLists[from]) {
            if (e.to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        // DONE: YOUR CODE HERE
        validateVertex(v);

        return adjLists[v].stream().map(Edge::to).toList();
    }

    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        // DONE: YOUR CODE HERE
        validateVertex(v);

        return inDegrees[v];
    }

    private ArrayList<Integer> reconstructPath(int[] previous, int start, int stop) {
        // Ensures that the path exists
        ArrayList<Integer> path = new ArrayList<>();
        for (int at = stop; at != -1; at = previous[at]) {
            path.add(at);
        }
        Collections.reverse(path); // path.reversed() returns a List rather than an ArrayList
        return path;
    }

    /* Returns a list of the vertices that lie on the shortest path from start to stop. 
    If no such path exists, you should return an empty list. If START == STOP, returns a List with START. */
    public ArrayList<Integer> shortestPath(int start, int stop) {
        // DONE: YOUR CODE HERE
        validateVertex(start, stop);

        boolean[] visited = new boolean[vertexCount];
        int[] previous = new int[vertexCount];
        Arrays.fill(previous, -1);
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(i -> distances[i]));
        pq.add(start);
        distances[start] = 0;
        while (!pq.isEmpty()) {
            int current = pq.poll();
            if (visited[current]) {
                continue;
            }
            visited[current] = true;

            for (Edge e : adjLists[current]) {
                int next = e.to;
                int newDist = distances[current] + e.weight;
                if (newDist < distances[next]) {
                    distances[next] = newDist;
                    previous[next] = current;
                    pq.add(next);
                }
            }
        }

        if (distances[stop] == Integer.MAX_VALUE) {
            return new ArrayList<>();
        }
        return reconstructPath(previous, start, stop);
    }

    private Edge getEdge(int v1, int v2) {
        // DONE: YOUR CODE HERE
        validateVertex(v1, v2);

        for (Edge e : adjLists[v1]) {
            if (e.to == v2) {
                return e;
            }
        }
        return null;
    }

    private class Edge {
        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

        public int to() {
            return this.to;
        }
    }
}
