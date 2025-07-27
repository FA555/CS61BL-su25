package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.stream.Collectors;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngm;

    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        return q.words().stream().map(
                word -> word + ": " + ngm.weightHistory(word, q.startYear(), q.endYear())
        ).collect(Collectors.joining("\n", "", "\n"));
    }
}
