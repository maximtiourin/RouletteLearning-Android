package com.tiourinsolutions.roulettelearning.core.roulette.statistics;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultEvent;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultListener;

/**
 * Listens for result events, and simply tracks the frequency that they occur
 * @author Maxim Tiourin
 */
public abstract class Frequency implements ResultListener {
    protected String id;
    protected int count;

    public Frequency(String id) {
        this.id = id;
        count = 0;
    }

    public void notify(ResultEvent re) {
        if (evaluateResultEvent(re)) {
            count++;
        }
    }

    /**
     * Evaluates the result event and returns true/false, this is used
     * to determine if frequency count is increased.
     */
    public abstract boolean evaluateResultEvent(ResultEvent re);

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }
}
