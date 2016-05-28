package com.tiourinsolutions.roulettelearning.core.roulette.statistics;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultEvent;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultListener;

/**
 * @author Maxim Tiourin
 */
public class ColorChainFrequency implements ResultListener {
    public static final String ID_FREQUENCY_CHAIN_COLOR_GREEN = "frequency_chain_color_green";
    public static final String ID_FREQUENCY_CHAIN_COLOR_RED = "frequency_chain_color_red";
    public static final String ID_FREQUENCY_CHAIN_COLOR_BLACK = "frequency_chain_color_black";
    private static final int MAX_TABLE_COUNT = 1000; //Seeing a chain length of 1000 is highly improbably
    private String id;
    private int colorType; //NumberColor index
    private int countTable[];
    private int maxCount;
    private int minCount;
    private int count;

    public ColorChainFrequency(String id, int colorType) {
        this.id = id;
        this.colorType = colorType;
        maxCount = 0;
        minCount = 0;
        count = 0;
        countTable = new int[MAX_TABLE_COUNT];
    }

    public void notify(ResultEvent re) {
        Number n = re.getNumber();

        if (n.getColorId() == colorType) {
            count++;
        }
        else if (count != 0) {
            if (count < MAX_TABLE_COUNT) {
                countTable[count]++;
            }
            else {
                //Will most likely never occur
                countTable[MAX_TABLE_COUNT - 1]++;
                System.out.println("A chain length greater than length 1000 has occurred for colorType = " + colorType);
            }
            if (count > maxCount) maxCount = count;
            if (minCount > count) minCount = count;
            count = 0;
        }
    }

    public String getId() {
        return id;
    }

    public int getHighestChainLength() {
        return maxCount;
    }

    /*
     * This will be 0 when first starting a series, and will almost certainly be set to
     * (and remain at) 1 after results start coming in.
     */
    public int getLowestChainLength() {
        return minCount;
    }

    /**
     * Returns how often the 'n' chain length occurred
     */
    public int getChainLengthCount(int n) {
        return countTable[n];
    }
}
