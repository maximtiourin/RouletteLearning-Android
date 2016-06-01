package com.tiourinsolutions.roulettelearning.core;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultListener;
import com.tiourinsolutions.roulettelearning.core.roulette.Roulette;
import com.tiourinsolutions.roulettelearning.core.roulette.Series;
import com.tiourinsolutions.roulettelearning.core.roulette.statistics.ChainFrequency;
import com.tiourinsolutions.roulettelearning.core.roulette.statistics.Frequency;

import java.util.ArrayList;

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
            System.out.println(f.getEventName() + " Frequency: " + f.getCount() + " (" + ((f.getCount() / (float) spins) * 100.0f) + "%)");
        }

        System.out.println("--------------------------");

        for (ChainFrequency f : chainFrequencies) {
            System.out.println("Max " + f.getEventName() + " Chain: " + f.getMaxChainLength());
        }

        System.out.println("--------------------------");

        for (ChainFrequency f : chainFrequencies) {
            for (int i = 1; i <= f.getMaxChainLength(); i++) {
                int n = f.getChainLengthCount(i);

                if (n > 0) {
                    System.out.printf("Amount of %10s x %3d Chain: %" + formatOffset +
                                    "d  ::  (Min: %" + formatOffset + "d, Avg: %" + (formatOffset + 7) +
                                    ".2f, Max: %" + formatOffset + "d)\n",
                            f.getEventName(), i, n,
                            f.getMinChainLengthDistance(i), f.getAvgChainLengthDistance(i), f.getMaxChainLengthDistance(i));
                }
            }
            System.out.println("--------------------------");
        }

        //while(true) {} //Dirty memory usage testing
    }
}
