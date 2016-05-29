package com.tiourinsolutions.roulettelearning.core.roulette;

/**
 * A result listener listens for result events, and performs specific actions when it is told about
 * a result event it cares about.
 * @author Maxim Tiourin
 */
public interface ResultListener {
    /**
     * Perform relevant actions given a ResultEvent. Take care to account for a ResultEvent that
     * contains a null Number value (this occurs when a series concludes and notifies one last time).
     */
    public void notify(ResultEvent re);
    public String getId();
}
