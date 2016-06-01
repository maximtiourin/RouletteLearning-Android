package com.tiourinsolutions.roulettelearning.core.roulette;


import com.tiourinsolutions.roulettelearning.util.Color;

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
}
