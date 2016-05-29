package com.tiourinsolutions.roulettelearning.core.roulette.statistics;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultEvent;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Tiourin
 */
public class ColorChainFrequency implements ResultListener {
    public static final String ID_FREQUENCY_CHAIN_COLOR_GREEN = "frequency_chain_color_green";
    public static final String ID_FREQUENCY_CHAIN_COLOR_RED = "frequency_chain_color_red";
    public static final String ID_FREQUENCY_CHAIN_COLOR_BLACK = "frequency_chain_color_black";
    public static final int MAX_TABLE_COUNT = 100; //Seeing a chain length of 100 is highly improbable
    private String id;
    private int colorType; //NumberColor index
    private int maxCount;
    private int count;
    private int countTable[];
    private int distance[]; //The current tracking distance for each chain length
    private ArrayList<ArrayList<Integer>> distanceTable; //The 2 dimensional array list tracking all of the distances of a chain length

    public ColorChainFrequency(String id, int colorType) {
        this.id = id;
        this.colorType = colorType;
        maxCount = 0;
        count = 0;

        countTable = new int[MAX_TABLE_COUNT];
        distance = new int[MAX_TABLE_COUNT];
        distanceTable = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < MAX_TABLE_COUNT; i++) {
            distanceTable.add(new ArrayList<Integer>());
        }
    }

    public void notify(ResultEvent re) {
        Number n = re.getNumber();

        if (n != null && n.getColorId() == colorType) {
            count++;
        }
        else if (count != 0) {
            if (count < MAX_TABLE_COUNT) {
                //Track Chain Length Frequency
                countTable[count]++;

                //Track Distance
                distanceTable.get(count).add(distance[count] - count);
                distance[count] = 0;
            }
            else {
                //Will most likely never occur
                System.out.println("A chain length greater than length " + (MAX_TABLE_COUNT - 1) + " has occurred for colorType = " + colorType + " : " + count);
            }
            if (count > maxCount) maxCount = count; //Determine highest chain length
            count = 0;
        }

        for (int i = 1; i < MAX_TABLE_COUNT; i++) {
            distance[i]++;
        }
    }

    public String getId() {
        return id;
    }

    public int getMaxChainLength() {
        return maxCount;
    }

    /**
     * Returns how often the 'n' chain length occurred
     * Returns -1 if 'n' chain length is out of bounds of [0, MAX_TABLE_COUNT)
     */
    public int getChainLengthCount(int n) {
        if (n >= 0 && n <= countTable.length) {
            return countTable[n];
        }
        else {
            return -1;
        }
    }

    /**
     * Returns the maximum occurrence distance for an 'n' chain length
     * Returns -1 if 'n' chain length is out of bounds of [0, MAX_TABLE_COUNT), or hasn't occurred.
     */
    public int getMaxChainLengthDistance(int n) {
        if (n >= 0 && n <= distanceTable.size()) {
            ArrayList<Integer> table = distanceTable.get(n);

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
     * Returns -1 if 'n' chain length is out of bounds of [0, MAX_TABLE_COUNT), or hasn't occurred.
     */
    public int getMinChainLengthDistance(int n) {
        if (n >= 0 && n <= distanceTable.size()) {
            ArrayList<Integer> table = distanceTable.get(n);

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
     * Returns NaN if 'n' chain length is out of bounds of [0, MAX_TABLE_COUNT)
     * Returns Positive.Infinity if chain length hasn't occurred.
     */
    public float getAvgChainLengthDistance(int n) {
        if (n >= 0 && n <= distanceTable.size()) {
            ArrayList<Integer> table = distanceTable.get(n);

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
            return Float.NaN;
        }
    }

    /**
     * Returns a list of chain length occurrence distances for the 'n' chain length.
     * A chain length occurrence distance is the amount of 'other' results between
     * two chains of the same length or between one chain length and the start of the series,
     * IE: abbaabb, the first occurrence distance of 'bb' is 1, the second occurrence distance of 'bb' is 2.
     * Returns null if 'n' chain length is out of bounds of [0, MAX_TABLE_COUNT)
     */
    public List<Integer> getChainLengthDistances(int n) {
        if (n >= 0 && n <= distanceTable.size()) {
            return distanceTable.get(n);
        }
        else {
            return null;
        }
    }

    public int getColorType() {
        return colorType;
    }
}
