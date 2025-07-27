package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // DONE: Add any necessary static/instance variables.
    HashMap<String, TimeSeries> wordCounts;
    TimeSeries totalCounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // DONE: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordCounts = loadWordCounts(wordsFilename);
        totalCounts = loadTotalCounts(countsFilename);
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // DONE: Fill in this method.
        return new TimeSeries(wordCounts.getOrDefault(word, new TimeSeries()), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // DONE: Fill in this method.
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // DONE: Fill in this method.
        return new TimeSeries(totalCounts, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // DONE: Fill in this method.
        return countHistory(word, startYear, endYear).dividedBy(totalCountHistory());
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // DONE: Fill in this method.
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // DONE: Fill in this method.
        return words.stream().map(
                word -> weightHistory(word, startYear, endYear)
        ).reduce(new TimeSeries(), TimeSeries::plus); // broken format to make CS61B style checker happy
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // DONE: Fill in this method.
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    // DONE: Add any private helper methods.
    // DONE: Remove all TO$DO comments before submitting.
    private HashMap<String, TimeSeries> loadWordCounts(String wordsFilename) {
        HashMap<String, TimeSeries> result = new HashMap<>();
        In in = new In(wordsFilename);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split("\t");

            String word = parts[0];
            int year = Integer.parseInt(parts[1]);
            double count = Double.parseDouble(parts[2]);

            TimeSeries ts = result.get(word);
            if (ts == null) {
                ts = new TimeSeries();
                result.put(word, ts);
            }
            ts.put(year, count);
        }
        return result;
    }

    private TimeSeries loadTotalCounts(String countsFilename) {
        TimeSeries result = new TimeSeries();
        In in = new In(countsFilename);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split(",");

            int year = Integer.parseInt(parts[0]);
            double count = Double.parseDouble(parts[1]);
            result.put(year, count);
        }
        return result;
    }
}
