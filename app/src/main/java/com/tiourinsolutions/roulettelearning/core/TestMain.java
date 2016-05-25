package com.tiourinsolutions.roulettelearning.core;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration;
import com.tiourinsolutions.roulettelearning.core.roulette.Roulette;

/**
 * @author Maxim Tiourin
 */
public class TestMain {
    public static void main(String args[]) {
        Roulette r = new Roulette(NumberConfiguration.AMERICAN);
        r.startNewSeries();

        int green = 0, red = 0, black = 0;
        int maxg = 0, maxr = 0, maxb = 0;

        for (int i = 0; i < 1000; i++) {
            Number n = r.simulateSpin();

            if (n.getColorId() == Number.NumberColor.Green.getId()) {
                green += 1;
                red = 0;
                black = 0;

                if (green > maxg) maxg = green;
            }
            else if (n.getColorId() == Number.NumberColor.Red.getId()) {
                green = 0;
                red += 1;
                black = 0;

                if (red > maxr) maxr = red;
            }
            else if (n.getColorId() == Number.NumberColor.Black.getId()) {
                green = 0;
                red = 0;
                black += 1;

                if (black > maxb) maxb = black;
            }
        }

        System.out.println("Max Green Chain: " + maxg);
        System.out.println("Max Red Chain: " + maxr);
        System.out.println("Max Black Chain: " + maxb);
    }
}
