package com.tiourinsolutions.roulettelearning.core;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultListener;
import com.tiourinsolutions.roulettelearning.core.roulette.Roulette;
import com.tiourinsolutions.roulettelearning.core.roulette.Series;
import com.tiourinsolutions.roulettelearning.core.roulette.statistics.ChainFrequency;
import com.tiourinsolutions.roulettelearning.core.roulette.statistics.Frequency;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Maxim Tiourin
 */
public class TestMain {
    public static void main(String args[]) {
        Roulette r = new Roulette(NumberConfiguration.AMERICAN);

        r.startNewSeries();

        Series series = r.getCurrentSeries();
        int spins = 1000000;
        int formatOffset = (spins + "").length();
        boolean filter = true;
        String[] ffl = new String[]{"Green", "Red", "Black", "Even", "Odd", "1 TO 18", "19 TO 36", "Column 1", "Column 2", "Column 3"};
        String[] cffl = new String[]{"Non-Red", "Non-Black", "Non-Even", "Non-Odd", "Not 1 TO 18", "Not 19 TO 36", "Not Column 1", "Not Column 2", "Not Column 3"};
        List<String> freqFilterList = Arrays.asList(ffl);
        List<String> chainFreqFilterList = Arrays.asList(cffl);

        for (int i = 0; i < spins; i++) {
            Number n = r.simulateSpin();
            //System.out.println(n.toString());
        }

        r.concludeSeries();

        System.out.println("--------------------------");
        System.out.println("Series (" + spins + " spins)");
        System.out.println("--------------------------");

        ArrayList<Frequency> frequencies = new ArrayList<Frequency>();
        ArrayList<ChainFrequency> chainFrequencies = new ArrayList<ChainFrequency>();

        for (ResultListener rl : series.getResultListeners()) {
            if (rl instanceof ChainFrequency) {
                chainFrequencies.add((ChainFrequency) rl);
            }
            else if (rl instanceof Frequency) {
                frequencies.add((Frequency) rl);
            }
        }

        for (Frequency f : frequencies) {
            if (!(filter && freqFilterList.contains(f.getEventName()))) continue;
            System.out.printf("%-15s Frequency: %" + ("" + spins).length() + "d (%5s%%)%n",
                    f.getEventName(), f.getCount(), new DecimalFormat("#0.00").format((f.getCount() / (float) spins) * 100.0f));
        }

        System.out.println("--------------------------");

        for (ChainFrequency f : chainFrequencies) {
            if (!(filter && chainFreqFilterList.contains(f.getEventName()))) continue;
            System.out.printf("Max %15s Chain: %5d%n", f.getEventName(), f.getMaxChainLength());
        }

        System.out.println("--------------------------");

        for (ChainFrequency f : chainFrequencies) {
            if (!(filter && chainFreqFilterList.contains(f.getEventName()))) continue;
            for (int i = 1; i <= f.getMaxChainLength(); i++) {
                int n = f.getChainLengthCount(i);

                if (n > 0) {
                    System.out.printf("Amount of %15s x %-5d Chain: %" + formatOffset +
                                    "d  ::  (Min: %" + formatOffset + "d, Avg: %" + (formatOffset + 7) +
                                    ".2f, Max: %" + formatOffset + "d)%n",
                            f.getEventName(), i, n,
                            f.getMinChainLengthDistance(i), f.getAvgChainLengthDistance(i), f.getMaxChainLengthDistance(i));
                }
            }
            System.out.println("--------------------------");
        }

        //while(true) {} //Dirty memory usage testing
    }
}
