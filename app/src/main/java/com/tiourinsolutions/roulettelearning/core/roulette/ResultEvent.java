package com.tiourinsolutions.roulettelearning.core.roulette;

/**
 * A result event is an event that carries relevant information about a roulette result in a series.
 * @author Maxim Tiourin
 */
public class ResultEvent {
    private Number number;

    public ResultEvent(Number number) {
        this.number = number;
    }

    public Number getNumber() {
        return number;
    }
}
