package com.tiourinsolutions.roulettelearning.core.com.tiourinsolutions.roulettelearning.core.roulette;

import java.util.ArrayList;

/**
 * Created by Maxim on 5/24/2016.
 */
public abstract class NumberConfiguration {
    private static class AmericanConfiguration extends NumberConfiguration {
        public AmericanConfiguration() {
            super();
        }

        @Override
        protected void initNumbers() {
            numbers.add(new Number("00", Number.NumberColor.Green));
            numbers.add(new Number("27", Number.NumberColor.Red));
            numbers.add(new Number("10", Number.NumberColor.Black));
            numbers.add(new Number("25", Number.NumberColor.Red));
            numbers.add(new Number("29", Number.NumberColor.Black));
            numbers.add(new Number("12", Number.NumberColor.Red));
            numbers.add(new Number("8", Number.NumberColor.Black));
            numbers.add(new Number("19", Number.NumberColor.Red));
            numbers.add(new Number("31", Number.NumberColor.Black));
            numbers.add(new Number("18", Number.NumberColor.Red));
            numbers.add(new Number("6", Number.NumberColor.Black));
            numbers.add(new Number("21", Number.NumberColor.Red));
            numbers.add(new Number("33", Number.NumberColor.Black));
            numbers.add(new Number("16", Number.NumberColor.Red));
            numbers.add(new Number("4", Number.NumberColor.Black));
            numbers.add(new Number("23", Number.NumberColor.Red));
            numbers.add(new Number("35", Number.NumberColor.Black));
            numbers.add(new Number("14", Number.NumberColor.Red));
            numbers.add(new Number("2", Number.NumberColor.Black));
            numbers.add(new Number("0", Number.NumberColor.Green));
            numbers.add(new Number("28", Number.NumberColor.Black));
            numbers.add(new Number("9", Number.NumberColor.Red));
            numbers.add(new Number("26", Number.NumberColor.Black));
            numbers.add(new Number("30", Number.NumberColor.Red));
            numbers.add(new Number("11", Number.NumberColor.Black));
            numbers.add(new Number("7", Number.NumberColor.Red));
            numbers.add(new Number("20", Number.NumberColor.Black));
            numbers.add(new Number("32", Number.NumberColor.Red));
            numbers.add(new Number("17", Number.NumberColor.Black));
            numbers.add(new Number("5", Number.NumberColor.Red));
            numbers.add(new Number("22", Number.NumberColor.Black));
            numbers.add(new Number("34", Number.NumberColor.Red));
            numbers.add(new Number("15", Number.NumberColor.Black));
            numbers.add(new Number("3", Number.NumberColor.Red));
            numbers.add(new Number("24", Number.NumberColor.Black));
            numbers.add(new Number("36", Number.NumberColor.Red));
            numbers.add(new Number("13", Number.NumberColor.Black));
            numbers.add(new Number("1", Number.NumberColor.Red));
        }
    }

    private static class EuropeanConfiguration extends NumberConfiguration {
        public EuropeanConfiguration() {
            super();
        }

        @Override
        protected void initNumbers() {
            //TODO populate numbers
        }
    }

    public static NumberConfiguration AMERICAN = new AmericanConfiguration();
    public static NumberConfiguration EUROPEAN = new EuropeanConfiguration();
    protected ArrayList<Number> numbers;

    protected NumberConfiguration() {
        numbers = new ArrayList<Number>();
    }

    protected abstract void initNumbers();

    /**
     * Returns the number in the list at the given index offset.
     * Equivalent to list[size % offset]
     * @param offset the index offset to lookup the number at
     * @return the number at the index offset
     */
    public Number getNumberAtIndexOffset(int offset) {
        return numbers.get(numbers.size() % offset);
    }
}
