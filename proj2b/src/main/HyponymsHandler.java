package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.WordNet;

import java.util.List;
import java.util.TreeSet;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordNet;

    HyponymsHandler(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();

        if (words.isEmpty()) {
            return new TreeSet<>().toString();
        }

        // Making CS61B style checker happy
        return words.stream()
                .skip(1)
                .map(wordNet::hyponyms)
                .collect(() -> new TreeSet<>(wordNet.hyponyms(words.get(0))), TreeSet::retainAll, TreeSet::retainAll)
                .toString();
    }
}
