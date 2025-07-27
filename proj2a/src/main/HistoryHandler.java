package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap ngm;

    public HistoryHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        return Plotter.encodeChartAsString(
                Plotter.generateTimeSeriesChart(
                        words,
                        words.stream().map(
                                word -> ngm.weightHistory(word, q.startYear(), q.endYear())
                        ).collect(Collectors.toCollection(ArrayList::new))
                )
        );
    }
}
