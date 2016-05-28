package com.tiourinsolutions.roulettelearning.core.roulette.statistics;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultEvent;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultListener;

/**
 * @author Maxim Tiourin
 */
public class ColorFrequency implements ResultListener {
    public static final String ID_FREQUENCY_COLOR_GREEN = "frequency_color_green";
    public static final String ID_FREQUENCY_COLOR_RED = "frequency_color_red";
    public static final String ID_FREQUENCY_COLOR_BLACK = "frequency_color_black";
    private String id;
    private int colorType; //NumberColor index
    private int count;

    public ColorFrequency(String id, int colorType) {
        this.id = id;
        this.colorType = colorType;
        count = 0;
    }

    public void notify(ResultEvent re) {
        Number n = re.getNumber();

        if (n.getColorId() == colorType) {
            count++;
        }
    }

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }
}
