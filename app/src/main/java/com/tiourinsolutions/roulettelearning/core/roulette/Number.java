package com.tiourinsolutions.roulettelearning.core.roulette;


import com.tiourinsolutions.roulettelearning.util.Color;

import java.util.Comparator;

/**
 * @author Maxim Tiourin
 */
public class Number {
    public enum NumberColor {
        Invalid(-1, "!Invalid!", Color.rgbToAndroidColorInt(255, 255, 255)),
        Green(0, "Green", Color.rgbToAndroidColorInt(29, 148, 29)),
        Red(1, "Red", Color.rgbToAndroidColorInt(171, 0, 0)),
        Black(2, "Black", Color.rgbToAndroidColorInt(0, 0, 0));

        private int id;
        private String name;
        private int color;

        NumberColor(int id, String name, int color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }

        public static NumberColor getNumberColorFromId(int id) {
            if (NumberColor.Green.getId() == id) {
                return NumberColor.Green;
            }
            else if (NumberColor.Red.getId() == id) {
                return NumberColor.Red;
            }
            else if (NumberColor.Black.getId() == id) {
                return NumberColor.Black;
            }
            return NumberColor.Invalid;
        }


        public int getId() { return id; }
        public String getName() { return name; }
        public int getColor() {
            return color;
        }
    }

    private String id; //The string identifier representing this number
    private int numEval; //The integer this number evaluates as
    private NumberColor color; //The NumberColor value of this number

    public Number(String id, int numEval, NumberColor color) {
        this.id = id;
        this.numEval = numEval;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public int getNumEval() {
        return numEval;
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

    /**
     * Returns a comparator that helps sort numbers into ascending order.
     * EX: 0, 00, 1, 2, 3, 4, 5, etc
     */
    public static class AscendingOrderComparator implements Comparator<Number> {
        @Override
        public int compare(Number lhs, Number rhs) {
            if (lhs.getNumEval() < rhs.getNumEval()) {
                return -1;
            }
            else if (lhs.getNumEval() > rhs.getNumEval()) {
                return 1;
            }
            else {
                //Num evals are equal, check for 0/00 case
                if (lhs.getId().length() < rhs.getId().length()) {
                    return -1;
                }
                else if (lhs.getId().length() > rhs.getId().length()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        }
    }

    /**
     * Returns a comparator that helps sort numbers into ascending order, but puts 0/00 at end
     * EX: 1, 2, 3, 4, 5, ..., 36, 0, 00
     */
    public static class SpecialAscendingOrderComparator implements Comparator<Number> {
        @Override
        public int compare(Number lhs, Number rhs) {
            if (lhs.getNumEval() == 0) {
                if (rhs.getNumEval() == 0) {
                    if (lhs.getId().length() < rhs.getId().length()) {
                        return -1;
                    }
                    else if (lhs.getId().length() > rhs.getId().length()) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
                else {
                    return 1;
                }
            }
            else if (rhs.getNumEval() == 0) {
                if (lhs.getNumEval() == 0) {
                    if (lhs.getId().length() < rhs.getId().length()) {
                        return -1;
                    }
                    else if (lhs.getId().length() > rhs.getId().length()) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
                else {
                    return -1;
                }
            }
            else {
                if (lhs.getNumEval() < rhs.getNumEval()) {
                    return -1;
                }
                else if (lhs.getNumEval() > rhs.getNumEval()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        }
    }
}
