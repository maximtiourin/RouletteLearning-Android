package com.tiourinsolutions.roulettelearning.core.roulette.statistics;

import com.tiourinsolutions.roulettelearning.core.roulette.ResultEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Tiourin
 */
public abstract class ChainFrequency extends Frequency {
    protected int maxCount;
    protected int overallDistance; //A distance counter that just tracks the amount of results.
    protected ArrayList<Integer> countTable;
    protected ArrayList<Integer> distance; //The current tracking distance for each chain length
    protected ArrayList<ArrayList<Integer>> distanceTable; //The 2 dimensional array list tracking all of the distances of a chain length

    public ChainFrequency(String id) {
        super(id);

        maxCount = 0;
        overallDistance = 0;

        countTable = new ArrayList<Integer>();
        distance = new ArrayList<Integer>();
        distanceTable = new ArrayList<ArrayList<Integer>>();
    }

    public void notify(ResultEvent re) {
        super.notify(re);

        if (!evaluateResultEvent(re) && count != 0) {
            //Track Chain Length Frequency
            incrementCountTable(count);

            //Track Distance
            addToDistanceTable(count);
            setDistance(count, 0);

            if (count > maxCount) maxCount = count; //Determine highest chain length
            count = 0;
        }

        //Increment all chain length distances
        for (int i = 1; i < distance.size(); i++) {
            incrementDistance(i);
        }

        overallDistance++; //Increment overall distance tracker (value is used when new chain length distances are allocated)
    }

    /**
     * Increments the count of how many times the chain length has occurred.
     * Will perform an allocation if 'index' is essentially greater than getMaxChainLength()
     */
    protected void incrementCountTable(int index) {
        allocateCountTable(index);

        countTable.set(index, countTable.get(index) + 1);
    }

    /**
     * Increments the current tracking distance between two occurrences of the 'index' chain length
     * Will perform an allocation if 'index' is essentially greater than getMaxChainLength()
     */
    protected void incrementDistance(int index) {
        allocateDistance(index);

        distance.set(index, distance.get(index) + 1);
    }

    /**
     * Adds the current chain length occurrence distance to the list of such occurrence distances for the 'count' chain length
     * Will perform an allocation if 'count' is essentially greater than getMaxChainLength()
     */
    protected void addToDistanceTable(int count) {
        allocateDistanceTable(count);

        distanceTable.get(count).add(getDistance(count) - count);
    }

    /**
     * Returns the occurrence count of the 'index' chain length
     * Will perform an allocation if 'index' is essentially greater than getMaxChainLength()
     *
     * There is a public method getChainLengthCount(n) which will call this method, but will
     * fail fast and return 0 if the chain length hasn't occurred, without the possibility of performing any allocation.
     */
    protected int getCountTable(int index) {
        allocateCountTable(index);

        return countTable.get(index);
    }

    /**
     * Returns the current chain length occurrence distance for the 'index' chain length
     * Will perform an allocation if 'index' is essentially greater than getMaxChainLength()
     */
    protected int getDistance(int index) {
        allocateDistance(index);

        return distance.get(index);
    }

    /**
     * Sets the current chain length occurrence distance for the 'index' chain length to 'value'
     * Will perform an allocation if 'index' is essentially greater than getMaxChainLength()
     */
    protected void setDistance(int index, int value) {
        allocateDistance(index);

        distance.set(index, value);
    }

    /**
     * Returns a list of chain length occurrence distances for the 'index' chain length in order of their occurrence
     * Will perform an allocation if 'index' is essentially greater than getMaxChainLength()
     */
    protected List<Integer> getDistanceTable(int index) {
        allocateDistanceTable(index);

        return distanceTable.get(index);
    }

    /**
     * Allocates extra 'int 0' entries for countTable up to 'count', if necessary
     */
    protected boolean allocateCountTable(int count) {
        int size = countTable.size();

        if (count > size - 1) {
            for (int i = size; i < count + 1; i++) {
                countTable.add(0);
            }

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Allocates extra 'overallDistance' entries for distance up to 'count', if necessary
     */
    protected boolean allocateDistance(int count) {
        int size = distance.size();

        if (count > size - 1) {
            for (int i = size; i < count + 1; i++) {
                distance.add(overallDistance);
            }

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Allocates extra 'empty ArrayList of Integer' entries for distanceTable up to 'count', if necessary
     */
    protected boolean allocateDistanceTable(int count) {
        int size = distanceTable.size();

        if (count > size - 1) {
            for (int i = size; i < count + 1; i++) {
                distanceTable.add(new ArrayList<Integer>());
            }

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns the maximum chain length encountered so far.
     */
    public int getMaxChainLength() {
        return maxCount;
    }

    /**
     * Returns how often the 'n' chain length occurred
     * Returns 0 if the chain length hasn't occurred, even if it isn't allocated
     */
    public int getChainLengthCount(int n) {
        if (n >= 0 && n < countTable.size()) {
            return getCountTable(n);
        }
        else {
            return 0;
        }
    }

    /**
     * Returns the maximum occurrence distance for an 'n' chain length
     * Returns -1 if 'n' chain length hasn't occurred
     */
    public int getMaxChainLengthDistance(int n) {
        if (n >= 0 && n < distanceTable.size()) {
            ArrayList<Integer> table = (ArrayList<Integer>) getDistanceTable(n);

            if (table.size() > 0) {
                int max = 0;

                for (Integer i : table) {
                    if (i > max) max = i;
                }

                return max;
            }
            else {
                return -1;
            }
        }
        else {
            return -1;
        }
    }

    /**
     * Returns the minimum occurrence distance for an 'n' chain length
     * Returns -1 if 'n' chain length hasn't occurred
     */
    public int getMinChainLengthDistance(int n) {
        if (n >= 0 && n < distanceTable.size()) {
            ArrayList<Integer> table = (ArrayList<Integer>) getDistanceTable(n);

            if (table.size() > 0) {
                int min = Integer.MAX_VALUE;

                for (Integer i : table) {
                    if (min > i) min = i;
                }

                return min;
            }
            else {
                return -1;
            }
        }
        else {
            return -1;
        }
    }

    /**
     * Returns the average occurrence distance for an 'n' chain length
     * Returns Positive.Infinity if chain length hasn't occurred.
     */
    public float getAvgChainLengthDistance(int n) {
        if (n >= 0 && n < distanceTable.size()) {
            ArrayList<Integer> table = (ArrayList<Integer>) getDistanceTable(n);

            if (table.size() > 0) {
                int sum = 0;

                for (Integer i : table) {
                    sum += i;
                }

                return sum / (float) table.size();
            }
            else {
                return Float.POSITIVE_INFINITY;
            }
        }
        else {
            return Float.POSITIVE_INFINITY;
        }
    }

    /**
     * Returns a list of chain length occurrence distances for the 'n' chain length.
     * A chain length occurrence distance is the amount of 'other' results between
     * two chains of the same length or between one chain length and the start of the series,
     * IE: abbaabb, the first occurrence distance of 'bb' is 1, the second occurrence distance of 'bb' is 2.
     * Returns empty list if 'n' chain length hasn't been encountered
     */
    public List<Integer> getChainLengthDistances(int n) {
        if (n >= 0 && n < distanceTable.size()) {
            return getDistanceTable(n);
        }
        else {
            return new ArrayList<Integer>();
        }
    }
}
