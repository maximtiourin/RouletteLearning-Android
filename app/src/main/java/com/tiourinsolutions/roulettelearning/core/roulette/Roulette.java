package com.tiourinsolutions.roulettelearning.core.roulette;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the roulette table, and manages "playing" the game by conducting spins and tracking series.
 * @author Maxim Tiourin
 */
public class Roulette {
    private SecureRandom rng;
    private NumberConfiguration numberConfiguration;
    private ArrayList<Series> seriesHistory;
    private Series currentSeries;
    private int wheelPosition;

    public Roulette(NumberConfiguration numberConfiguration) {
        rng = new SecureRandom();
        this.numberConfiguration = numberConfiguration;
        seriesHistory = new ArrayList<Series>();
        currentSeries = null;
        wheelPosition = 0;
    }

    public Number simulateSpin() {
        wheelPosition = spinWheel();

        Number n = numberConfiguration.getNumberAtIndexOffset(wheelPosition);

        if (currentSeries != null) currentSeries.addResult(n);

        return n;
    }

    /**
     * Concludes the current series if there is one, adds it to the seriesList history,
     * and starts tracking a new series.
     */
    public void startNewSeries() {
        if (currentSeries != null) {
            seriesHistory.add(currentSeries);
        }

        currentSeries = new Series();
    }

    /**
     * Returns a random wheel position taking into account current wheelPosition, for a possible
     * configuration edge over time.
     */
    private int spinWheel() {
        return numberConfiguration.getIndexAtIndexOffset(wheelPosition + rng.nextInt(Integer.MAX_VALUE - wheelPosition));
    }

    public Series getCurrentSeries() {
        return currentSeries;
    }

    /**
     * Concludes the current series if there is one.
     */
    public void concludeSeries() {
        if (currentSeries != null) {
            seriesHistory.add(currentSeries);
        }

        currentSeries = null;
    }

    public List<Series> getSeriesHistory() {
        return seriesHistory;
    }
}
