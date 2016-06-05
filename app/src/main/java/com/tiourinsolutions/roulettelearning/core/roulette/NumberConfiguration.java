package com.tiourinsolutions.roulettelearning.core.roulette;

import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author Maxim Tiourin
 */
public abstract class NumberConfiguration {
    private static class AmericanConfiguration extends NumberConfiguration {
        public AmericanConfiguration() {
            super();
        }

        @Override
        protected void initNumbers() {
            numbers.add(new Number("00", 0, Number.NumberColor.Green));
            numbers.add(new Number("27", 27, Number.NumberColor.Red));
            numbers.add(new Number("10", 10, Number.NumberColor.Black));
            numbers.add(new Number("25", 25, Number.NumberColor.Red));
            numbers.add(new Number("29", 29, Number.NumberColor.Black));
            numbers.add(new Number("12", 12, Number.NumberColor.Red));
            numbers.add(new Number("8", 8, Number.NumberColor.Black));
            numbers.add(new Number("19", 19, Number.NumberColor.Red));
            numbers.add(new Number("31", 31, Number.NumberColor.Black));
            numbers.add(new Number("18", 18, Number.NumberColor.Red));
            numbers.add(new Number("6", 6, Number.NumberColor.Black));
            numbers.add(new Number("21", 21, Number.NumberColor.Red));
            numbers.add(new Number("33", 33, Number.NumberColor.Black));
            numbers.add(new Number("16", 16, Number.NumberColor.Red));
            numbers.add(new Number("4", 4, Number.NumberColor.Black));
            numbers.add(new Number("23", 23, Number.NumberColor.Red));
            numbers.add(new Number("35", 35, Number.NumberColor.Black));
            numbers.add(new Number("14", 14, Number.NumberColor.Red));
            numbers.add(new Number("2", 2, Number.NumberColor.Black));
            numbers.add(new Number("0", 0, Number.NumberColor.Green));
            numbers.add(new Number("28", 28, Number.NumberColor.Black));
            numbers.add(new Number("9", 9, Number.NumberColor.Red));
            numbers.add(new Number("26", 26, Number.NumberColor.Black));
            numbers.add(new Number("30", 30, Number.NumberColor.Red));
            numbers.add(new Number("11", 11, Number.NumberColor.Black));
            numbers.add(new Number("7", 7, Number.NumberColor.Red));
            numbers.add(new Number("20", 20, Number.NumberColor.Black));
            numbers.add(new Number("32", 32, Number.NumberColor.Red));
            numbers.add(new Number("17", 17, Number.NumberColor.Black));
            numbers.add(new Number("5", 5, Number.NumberColor.Red));
            numbers.add(new Number("22", 22, Number.NumberColor.Black));
            numbers.add(new Number("34", 34, Number.NumberColor.Red));
            numbers.add(new Number("15", 15, Number.NumberColor.Black));
            numbers.add(new Number("3", 3, Number.NumberColor.Red));
            numbers.add(new Number("24", 24, Number.NumberColor.Black));
            numbers.add(new Number("36", 36, Number.NumberColor.Red));
            numbers.add(new Number("13", 13, Number.NumberColor.Black));
            numbers.add(new Number("1", 1, Number.NumberColor.Red));
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

    public static final String ID_AMERICAN = "AMERICAN";
    public static final String ID_EUROPEAN = "EUROPEAN";
    public static NumberConfiguration AMERICAN = new AmericanConfiguration();
    public static NumberConfiguration EUROPEAN = new EuropeanConfiguration();
    protected ArrayList<Number> numbers;
    protected ArrayList<Number> sortedNumbers;

    protected NumberConfiguration() {
        numbers = new ArrayList<Number>();
        sortedNumbers = new ArrayList<Number>();

        initNumbers();

        for (Number n : numbers) {
            sortedNumbers.add(n);
        }

        Collections.sort(sortedNumbers, new Number.SpecialAscendingOrderComparator());
    }

    protected abstract void initNumbers();

    /**
     * Returns the number in the list at the given index offset.
     * Equivalent to list[size % offset]
     * @param offset the index offset to lookup the number at
     * @return the number at the index offset
     */
    public Number getNumberAtIndexOffset(int offset) {
        return numbers.get(getIndexAtIndexOffset(offset));
    }

    /**
     * Returns the number with the given string id, otherwise returns null.
     * This is primarily useful inside of Android activities to pass around numbers
     * using only strings.
     */
    public Number getNumberWithStringId(String id) {
        for (Number n : numbers) {
            if (n.getId().equals(id)) {
                return n;
            }
        }

        return null;
    }

    public int getIndexAtIndexOffset(int offset) {
        return offset % getConfigSize();
    }

    public int getConfigSize() {
        return numbers.size();
    }

    public int getConfigGreenCount() {
        int c = 0;
        for (Number n : numbers) {
            if (n.getColorId() == Number.NumberColor.Green.getId()) {
                c++;
            }
        }
        return c;
    }

    public int getConfigRedCount() {
        int c = 0;
        for (Number n : numbers) {
            if (n.getColorId() == Number.NumberColor.Red.getId()) {
                c++;
            }
        }
        return c;
    }

    public int getConfigBlackCount() {
        int c = 0;
        for (Number n : numbers) {
            if (n.getColorId() == Number.NumberColor.Black.getId()) {
                c++;
            }
        }
        return c;
    }

    public List<Number> getNumberList() {
        return numbers;
    }

    public List<Number> getSortedNumberList() { return sortedNumbers; }

    /**
     * Returns the static config given the config string id, returns null if no config
     * exists for the given id. This method is primarily used inside of Android activities
     * to reference number configurations just by handling string ids.
     */
    public static NumberConfiguration getConfigurationFromStringId(String id) {
        if (id.equals("AMERICAN")) {
            return AMERICAN;
        }
        else if (id.equals("EUROPEAN")) {
            return EUROPEAN;
        }
        else {
            return null;
        }
    }
}
