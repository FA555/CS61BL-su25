import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

import static com.google.common.truth.Truth.assertWithMessage;

public class MSTTest {

    private final static String SEPARATOR = System.getProperty("file.separator");
    private final static String INPUT_FOLDER = System.getProperty("user.dir") + SEPARATOR + "test" + SEPARATOR + "inputs";
    private final static String NORMAL = INPUT_FOLDER + SEPARATOR + "graphTestNormal.in";
    private final static String ALL_DISJOINT = INPUT_FOLDER + SEPARATOR + "graphTestAllDisjoint.in";
    private final static String SOME_DISJOINT = INPUT_FOLDER + SEPARATOR + "graphTestSomeDisjoint.in";
    private final static String MULTI_EDGE = INPUT_FOLDER + SEPARATOR + "graphTestMultiEdge.in";

//    @Test
//    public void testExample() {
//        Graph input = loadFromText(NORMAL);
//        Graph output = new Graph();
//        output.addEdge(0, 2, 1);
//        output.addEdge(1, 2, 2);
//        output.addEdge(2, 3, 6);
//
//        Graph mst1 = input.prims(0);
//        Graph mst2 = input.kruskals();
//        assertThat(mst1).isEqualTo(output);
//        assertThat(mst2).isEqualTo(output);
//    }

//    @Test
//    public void test1() {
//         DONE: write more tests!
//    }

    private final HashMap<String, Graph> graphs = new HashMap<>();

    public MSTTest() {
        {
            Graph normalAnswer = new Graph();
            normalAnswer.addEdge(0, 2, 1);
            normalAnswer.addEdge(1, 2, 2);
            normalAnswer.addEdge(2, 3, 6);
            graphs.put(NORMAL, normalAnswer);
        }
        graphs.put(ALL_DISJOINT, null);
        graphs.put(SOME_DISJOINT, null);
        {
            Graph multiEdgeAnswer = new Graph();
            multiEdgeAnswer.addEdge(0, 1, 0);
            multiEdgeAnswer.addEdge(1, 2, 1);
            graphs.put(MULTI_EDGE, multiEdgeAnswer);
        }
    }

    @Test
    public void testPrims() {
        for (String filename : graphs.keySet()) {
            Graph input = loadFromText(filename);
            Graph expected = graphs.get(filename);
            Graph output = input.prims(0);
            if (expected == null) {
                assertWithMessage("Graph " + filename + " is not connected, so MST should be null.").that(output).isNull();
            } else {
                assertWithMessage("MST for " + filename + " does not match expected output.").that(output).isEqualTo(expected);
            }
        }
    }

    @Test
    public void testKruskals() {
        for (String filename : graphs.keySet()) {
            Graph input = loadFromText(filename);
            Graph expected = graphs.get(filename);
            Graph output = input.kruskals();
            if (expected == null) {
                assertWithMessage("Graph " + filename + " is not connected, so MST should be null.").that(output).isNull();
            } else {
                assertWithMessage("MST for " + filename + " does not match expected output.").that(output).isEqualTo(expected);
            }
        }
    }

    /* Returns a randomly generated graph with VERTICES number of vertices and
       EDGES number of edges with max weight WEIGHT. */
    public static Graph randomGraph(int vertices, int edges, int weight) {
        Graph g = new Graph();
        Random rng = new Random();
        for (int i = 0; i < vertices; i += 1) {
            g.addVertex(i);
        }
        for (int i = 0; i < edges; i += 1) {
            Edge e = new Edge(rng.nextInt(vertices), rng.nextInt(vertices), rng.nextInt(weight));
            g.addEdge(e);
        }
        return g;
    }

    /* Returns a Graph object with integer edge weights as parsed from
       FILENAME. Talk about the setup of this file. */
    public static Graph loadFromText(String filename) {
        Charset cs = Charset.forName("US-ASCII");
        try (BufferedReader r = Files.newBufferedReader(Paths.get(filename), cs)) {
            Graph g = new Graph();
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(", ");
                if (fields.length == 3) {
                    int from = Integer.parseInt(fields[0]);
                    int to = Integer.parseInt(fields[1]);
                    int weight = Integer.parseInt(fields[2]);
                    g.addEdge(from, to, weight);
                } else if (fields.length == 1) {
                    g.addVertex(Integer.parseInt(fields[0]));
                } else {
                    throw new IllegalArgumentException("Bad input file!");
                }
            }
            return g;
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
}

