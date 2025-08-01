package ngrams;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;
    public static final double EPSILON = 1e-10;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // DONE: Fill in this constructor.
        if (startYear == MIN_YEAR && endYear == MAX_YEAR) {
            putAll(ts);
            return;
        }

        putAll(ts.subMap(startYear, true, endYear, true));
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        // DONE: Fill in this method.
        return keySet().stream().toList();
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        // DONE: Fill in this method.
        return values().stream().toList();
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // DONE: Fill in this method.
        TimeSeries result = new TimeSeries(this, MIN_YEAR, MAX_YEAR);
        for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
            Integer year = entry.getKey();
            result.put(year, result.getOrDefault(year, 0.) + entry.getValue());
        }
        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // DONE: Fill in this method.
        TimeSeries result = new TimeSeries();
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            Integer year = entry.getKey();
            Double thisValue = entry.getValue();
            Double tsValue = ts.get(year);

            if (tsValue == null) {
                throw new IllegalArgumentException("Year " + year + " is missing in the provided TimeSeries.");
            }
            assert tsValue >= EPSILON : "Division by zero for year " + year;

            result.put(year, thisValue / tsValue);
        }
        return result;
    }

    // DONE: Add any private helper methods.
    // DONE: Remove all TO$DO comments before submitting.
}
