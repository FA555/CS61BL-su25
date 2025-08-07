package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import wordnet.WordNet;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final NGramMap ngm;
    private final WordNet wn;

    HyponymsHandler(NGramMap ngm, WordNet wn) {
        this.ngm = ngm;
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int limit = q.k();

        if (limit < 0) {
            throw new IllegalArgumentException("Limit must be non-negative");
        }
        if (words.isEmpty()) {
            return new TreeSet<>().toString();
        }

        Set<String> hyponyms = wn.hyponyms(words.get(0));
        for (int i = 1; i < words.size(); ++i) {
            hyponyms.retainAll(wn.hyponyms(words.get(i)));
        }

        if (limit == 0) {
            return new TreeSet<>(hyponyms).toString();
        }

        HashMap<String, Double> wordCounts = new HashMap<>();
        for (String word : hyponyms) {
            List<Double> data = ngm.countHistory(word, startYear, endYear).data();
            if (data.isEmpty()) {
                continue;
            }

            double sum = 0.;
            for (Double count : ngm.countHistory(word, startYear, endYear).data()) {
                sum += count;
            }
            wordCounts.put(word, sum);
        }

        List<String> hyponymsList = new ArrayList<>(wordCounts.keySet());
        hyponymsList.sort(Comparator.comparingDouble(
                (String word) -> wordCounts.get(word)
        ).reversed().thenComparing(String::compareTo));
        if (limit < hyponymsList.size()) {
            hyponymsList = hyponymsList.subList(0, limit);
        }
        Collections.sort(hyponymsList);
        return hyponymsList.toString();
    }
}
