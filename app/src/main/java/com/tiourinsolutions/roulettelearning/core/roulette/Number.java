package com.tiourinsolutions.roulettelearning.core.roulette;

import android.graphics.Color;

/**
 * @author Maxim Tiourin
 */
public class Number {
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

    private String id; //The string identifier representing this number
    private NumberColor color; //The NumberColor value of this number

    public Number(String id, NumberColor color) {
        this.id = id;
        this.color = color;
    }
}
