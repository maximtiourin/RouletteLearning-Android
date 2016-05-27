package com.tiourinsolutions.roulettelearning.util;

/**
 * @author Maxim Tiourin
 */
public class Color {
    /**
     * Converts rgb values into a single integer used to describe a color in android.
     * @param r
     * @param g
     * @param b
     * @return
     */
    public static int rgbToAndroidColorInt(int r, int g, int b) {
        int res = 0;
        int mask = 255 << 24;
        res = mask;

        mask = r << 16;
        res = res | mask;

        mask = g << 8;
        res = res | mask;

        mask = b;
        res = res | mask;

        return res;
    }

}
