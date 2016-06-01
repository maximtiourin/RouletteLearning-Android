package com.tiourinsolutions.roulettelearning.core.roulette.statistics;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultEvent;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultListener;

/**
 * Listens for result events, and simply tracks the frequency that they occur
 * @author Maxim Tiourin
 */
public abstract class Frequency implements ResultListener {
    public static final String PREFIX_ID_FREQUENCY = "frequency_";
    public static final String PREFIX_ID_FREQUENCY_NOT = "frequency_not_";
    public static final String ID_FREQUENCY_GREEN = "frequency_green";
    public static final String ID_FREQUENCY_RED = "frequency_red";
    public static final String ID_FREQUENCY_BLACK = "frequency_black";
    public static final String ID_FREQUENCY_NOT_GREEN = "frequency_not_green";
    public static final String ID_FREQUENCY_NOT_RED = "frequency_not_red";
    public static final String ID_FREQUENCY_NOT_BLACK = "frequency_not_black";

    protected String id;
    protected String eventName;
    protected int count;

    public Frequency(String id, String eventName) {
        this.id = id;
        this.eventName = eventName;
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

    public String getEventName() { return eventName; }

    public int getCount() {
        return count;
    }
}
