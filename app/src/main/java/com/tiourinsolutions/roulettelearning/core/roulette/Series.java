package com.tiourinsolutions.roulettelearning.core.roulette;

import java.util.ArrayList;
import java.util.List;

/**
 * A series is a collection of results of consecutive "spins" of a roulette table.
 * @author Maxim Tiourin
 */
public class Series {
    protected ArrayList<Result> series;

    public Series() {
        series = new ArrayList<Result>();
    }

    public void addResult(Result result) {
        series.add(result);
    }

    public List<Result> getResults() {
        return series;
    }
}
