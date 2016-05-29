package com.tiourinsolutions.roulettelearning.core;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration;
import com.tiourinsolutions.roulettelearning.core.roulette.Roulette;
import com.tiourinsolutions.roulettelearning.core.roulette.Series;
import com.tiourinsolutions.roulettelearning.core.roulette.statistics.ColorChainFrequency;
import com.tiourinsolutions.roulettelearning.core.roulette.statistics.ColorFrequency;

/**
 * @author Maxim Tiourin
 */
public class TestMain {
    public static void main(String args[]) {
        Roulette r = new Roulette(NumberConfiguration.AMERICAN);

        r.startNewSeries();

        Series series = r.getCurrentSeries();
        int green = 0, red = 0, black = 0;
        int spins = 5000;
        int formatOffset = (spins + "").length();

        for (int i = 0; i < spins; i++) {
            Number n = r.simulateSpin();
            //System.out.println(n.toString());
        }

        r.concludeSeries();

        System.out.println("--------------------------");
        System.out.println("Series (" + spins + " spins)");
        System.out.println("--------------------------");

        ColorFrequency greencf = (ColorFrequency) series.getResultListener(ColorFrequency.ID_FREQUENCY_COLOR_GREEN);
        ColorFrequency redcf = (ColorFrequency) series.getResultListener(ColorFrequency.ID_FREQUENCY_COLOR_RED);
        ColorFrequency blackcf = (ColorFrequency) series.getResultListener(ColorFrequency.ID_FREQUENCY_COLOR_BLACK);

        if (greencf != null) System.out.println("Green Color Frequency: " + greencf.getCount() + " (" + ((greencf.getCount() / (float) spins) * 100.0f) + "%)");
        if (redcf != null) System.out.println("Red Color Frequency: " + redcf.getCount() + " (" + ((redcf.getCount() / (float) spins) * 100.0f) + "%)");
        if (blackcf != null) System.out.println("Black Color Frequency: " + blackcf.getCount() + " (" + ((blackcf.getCount() / (float) spins) * 100.0f) + "%)");


        ColorChainFrequency greenccf = (ColorChainFrequency) series.getResultListener(ColorChainFrequency.ID_FREQUENCY_CHAIN_COLOR_GREEN);
        ColorChainFrequency redccf = (ColorChainFrequency) series.getResultListener(ColorChainFrequency.ID_FREQUENCY_CHAIN_COLOR_RED);
        ColorChainFrequency blackccf = (ColorChainFrequency) series.getResultListener(ColorChainFrequency.ID_FREQUENCY_CHAIN_COLOR_BLACK);

        if (greenccf != null) System.out.println("Max Green Chain: " + greenccf.getMaxChainLength());
        if (redccf != null) System.out.println("Max Red Chain: " + redccf.getMaxChainLength());
        if (blackccf != null) System.out.println("Max Black Chain: " + blackccf.getMaxChainLength());

        if (redccf != null) {
            for (int i = 1; i <= redccf.getMaxChainLength(); i++) {
                int n = redccf.getChainLengthCount(i);

                System.out.printf("Amount of Red x %2d Chain: %" + formatOffset +
                        "d  ::  (Min: %" + formatOffset + "d, Avg: %" + (formatOffset + 7) +
                        ".2f, Max: %" + formatOffset + "d) %s\n",
                        i, n, redccf.getMinChainLengthDistance(i), redccf.getAvgChainLengthDistance(i), redccf.getMaxChainLengthDistance(i), redccf.getChainLengthDistances(i));
            }
        }
    }
}
