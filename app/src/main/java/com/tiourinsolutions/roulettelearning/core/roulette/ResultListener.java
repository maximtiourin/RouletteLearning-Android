package com.tiourinsolutions.roulettelearning.core.roulette;

/**
 * A result listener listens for result events, and performs specific actions when it is told about
 * a result event it cares about.
 * @author Maxim Tiourin
 */
public interface ResultListener {
    public void notify(ResultEvent re);
    public String getId();
}
