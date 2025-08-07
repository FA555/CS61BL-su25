package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.stream.Collectors;

public class WordNet {
    // Guarantee that the graph is a DAG
    private final Graph<String[]> graph = new Graph<>();
    private final HashMap<String, ArrayList<Integer>> reverseIndex = new HashMap<>();

    public WordNet(String synsetFilename, String hyponymFilename) {
        loadSynsets(synsetFilename);
        loadHyponyms(hyponymFilename);
    }

    private void loadSynsets(String synsetFilename) {
        In in = new In(synsetFilename);
        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(",");

            int id = Integer.parseInt(parts[0]);
            String[] words = parts[1].split(" ");

            // Guarantee that the vertex is read in ascending order, begins at 0
            graph.setVertex(id, words);
            for (String word : words) {
                reverseIndex.computeIfAbsent(word, s -> new ArrayList<>()).add(id);
            }
        }
    }

    private void loadHyponyms(String hyponymFilename) {
        In in = new In(hyponymFilename);
        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(",");
            int from = Integer.parseInt(parts[0]);

            for (int i = 1; i < parts.length; ++i) {
                int to = Integer.parseInt(parts[i]);
                graph.addEdge(from, to);
            }
        }
    }

    private Set<String> hyponymsHelper(int synsetId) {
        HashSet<String> hyponyms = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(synsetId);

        while (!queue.isEmpty()) {
            int currentId = queue.poll();
            Collections.addAll(hyponyms, graph.getVertex(currentId));
            queue.addAll(graph.getEdges(currentId));
        }

        return hyponyms;
    }

    public Set<String> hyponyms(String word) {
        // Making CS61B style checker happy
         return reverseIndex.getOrDefault(word, new ArrayList<>())
                 .stream()
                 .flatMap(synsetId -> hyponymsHelper(synsetId).stream())
                 .collect(Collectors.toCollection(HashSet::new));
    }
}
