package com.tiourinsolutions.roulettelearning.core.roulette;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the roulette table, and manages "playing" the game by conducting spins and tracking series.
 * @author Maxim Tiourin
 */
public class Roulette {
    //TODO add event messaging system for roulette events such as got black, got number, got column, etc.
    public static final boolean DEBUG = false;
    private Random rng;
    private NumberConfiguration numberConfiguration;
    private ArrayList<Series> seriesHistory;
    private Series currentSeries;
    private int wheelPosition;

    public Roulette(NumberConfiguration numberConfiguration) {
        rng = new Random();
        this.numberConfiguration = numberConfiguration;
        seriesHistory = new ArrayList<Series>();
        currentSeries = null;
        wheelPosition = 0;
    }

    public Number simulateSpin() {
        wheelPosition = spinWheel();

        Number n = numberConfiguration.getNumberAtIndexOffset(wheelPosition);

        if (DEBUG) System.out.printf("%2s %s\n", n.getId(), n.getColorName());

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
