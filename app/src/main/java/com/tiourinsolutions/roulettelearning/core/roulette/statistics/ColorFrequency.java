package com.tiourinsolutions.roulettelearning.core.roulette.statistics;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultEvent;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultListener;

/**
 * @author Maxim Tiourin
 */
public class ColorFrequency extends Frequency {
    public static final String ID_FREQUENCY_COLOR_GREEN = "frequency_color_green";
    public static final String ID_FREQUENCY_COLOR_RED = "frequency_color_red";
    public static final String ID_FREQUENCY_COLOR_BLACK = "frequency_color_black";
    protected int colorType; //NumberColor index

    public ColorFrequency(String id, int colorType) {
        super(id);
        this.colorType = colorType;
    }

    @Override
    public boolean evaluateResultEvent(ResultEvent re) {
        Number n = re.getNumber();

        if (n != null) {
            if (n.getColorId() == colorType) {
                return true;
            }
        }

        return false;
    }
}
