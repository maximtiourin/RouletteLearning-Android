package com.tiourinsolutions.roulettelearning.core.roulette;

import com.tiourinsolutions.roulettelearning.core.roulette.statistics.ColorChainFrequency;
import com.tiourinsolutions.roulettelearning.core.roulette.statistics.ColorFrequency;

import java.util.ArrayList;
import java.util.List;

/**
 * A series is a collection of results of consecutive "spins" of a roulette table, as well as the
 * container for all statistics pertaining to the series.
 * @author Maxim Tiourin
 */
public class Series {
    protected ArrayList<Number> series;
    protected ArrayList<ResultListener> resultListeners;
    protected NumberConfiguration numberConfiguration;

    public Series(NumberConfiguration numberConfiguration) {
        series = new ArrayList<Number>();
        resultListeners = new ArrayList<ResultListener>();
        this.numberConfiguration = numberConfiguration;

        initResultListeners();
    }

    private void initResultListeners() {
        //Green Color Frequency
        addResultListener(new ColorFrequency(ColorFrequency.ID_FREQUENCY_COLOR_GREEN, Number.NumberColor.Green.getId()));
        //Red Color Frequency
        addResultListener(new ColorFrequency(ColorFrequency.ID_FREQUENCY_COLOR_RED, Number.NumberColor.Red.getId()));
        //Black Color Frequency
        addResultListener(new ColorFrequency(ColorFrequency.ID_FREQUENCY_COLOR_BLACK, Number.NumberColor.Black.getId()));

        //Green Color Chain Frequency
        addResultListener(new ColorChainFrequency(ColorChainFrequency.ID_FREQUENCY_CHAIN_COLOR_GREEN, Number.NumberColor.Green.getId()));
        //Red Color Chain Frequency
        addResultListener(new ColorChainFrequency(ColorChainFrequency.ID_FREQUENCY_CHAIN_COLOR_RED, Number.NumberColor.Red.getId()));
        //Black Color Chain Frequency
        addResultListener(new ColorChainFrequency(ColorChainFrequency.ID_FREQUENCY_CHAIN_COLOR_BLACK, Number.NumberColor.Black.getId()));

        //Non-Red Color Chain Frequency
        addResultListener(new ColorChainFrequency(ColorChainFrequency.ID_FREQUENCY_CHAIN_COLOR_NOT_RED, Number.NumberColor.Red.getId(), true));
        //Non-Black Color Chain Frequency
        addResultListener(new ColorChainFrequency(ColorChainFrequency.ID_FREQUENCY_CHAIN_COLOR_NOT_BLACK, Number.NumberColor.Black.getId(), true));
    }

    public void addResult(Number result) {
        series.add(result);

        notifyResultListeners(new ResultEvent(result));
    }

    public List<Number> getResults() {
        return series;
    }

    public void addResultListener(ResultListener rl) {
        resultListeners.add(rl);
    }

    private void notifyResultListeners(ResultEvent re) {
        for (ResultListener rl : resultListeners) {
            rl.notify(re);
        }
    }

    public ResultListener getResultListener(String id) {
        for (ResultListener rl : resultListeners) {
            if (rl.getId().equals(id)) {
                return rl;
            }
        }

        return null;
    }

    public boolean removeResultListener(ResultListener rl) {
        return resultListeners.remove(rl);
    }

    /**
     * Must be called when the series is concluded in order to notify all resultEvent listeners
     * one last time.
     */
    public void concludeSeries() {
        notifyResultListeners(new ResultEvent(null));
    }
}
