package com.tiourinsolutions.roulettelearning.core.roulette;

import android.graphics.Color;

/**
 * @author Maxim Tiourin
 */
public class Number {
    public enum NumberColor {
        Green(0, "Green", Color.GREEN),
        Red(1, "Red", Color.RED),
        Black(2, "Black", Color.BLACK);

        private int id;
        private String name;
        private int color;

        NumberColor(int id, String name, int color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }

        public int getId() { return id; }
        public String getName() { return name; }
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

    public String getId() {
        return id;
    }

    public int getColor() {
        return color.getColor();
    }

    public int getColorId() {
        return color.getId();
    }

    public String getColorName() {
        return color.getName();
    }

    @Override
    public String toString() {
        return "(" + getId() + ", " + getColorName() + ")";
    }
}
