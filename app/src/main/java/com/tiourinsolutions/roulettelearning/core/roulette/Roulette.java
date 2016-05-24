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
    private static final int REVOLUTIONS_MIN = 26;
    private static final int REVOLUTIONS_MAX = 36;
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

    public void simulateSpin() {
        spinWheel();

        Number n = numberConfiguration.getNumberAtIndexOffset(wheelPosition);

        System.out.println(n);
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
     * Spins the wheel to a random position, taking into account the current wheel starting position.
     */
    private void spinWheel() {
        float numberOfRotations = (float) REVOLUTIONS_MIN + ((REVOLUTIONS_MAX - REVOLUTIONS_MIN) * rng.nextFloat());

        wheelPosition = numberConfiguration.getIndexAtIndexOffset((int) (wheelPosition * numberOfRotations));
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
