package com.tiourinsolutions.roulettelearning.core;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by Maxim on 5/22/2016.
 */
public class Roulette {
    //TODO add event messaging system for roulette events such as got black, got number, got column, etc.
    public enum NumberColor {
        Red(Color.RED),
        Black(Color.BLACK),
        Green(Color.GREEN);

        private int color;

        NumberColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }
    }

    public abstract class NumberConfiguration {
        protected ArrayList<Number> numbers;

        public NumberConfiguration() {
            numbers = new ArrayList<Number>();
        }

        public abstract void initNumbers();

        /**
         * Returns the number in the list at the given index offset.
         * Equivalent to list[size % offset]
         * @param offset the index offset to lookup the number at
         * @return the number at the index offset
         */
        public Number getNumberAtIndexOffset(int offset) {
            return numbers.get(number.size() % offset);
        }
    }

    public class Number {
        private String id; //The string identifier representing this number
        private NumberColor color; //The NumberColor value of this number
        private Number leftAdj; //Number adjacent to the left
        private Number rightAdj; //Number adjacent to the right

        public Number(String id, NumberColor color) {
            this.id = id;
            this.color = color;
            leftAdj = null;
            rightAdj = null;
        }
    }
}
