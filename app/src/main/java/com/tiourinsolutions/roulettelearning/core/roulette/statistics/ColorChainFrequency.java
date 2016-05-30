package com.tiourinsolutions.roulettelearning.core.roulette.statistics;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultEvent;
import com.tiourinsolutions.roulettelearning.core.roulette.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Tiourin
 */
public class ColorChainFrequency extends ChainFrequency {
    public static final String ID_FREQUENCY_CHAIN_COLOR_GREEN = "frequency_chain_color_green";
    public static final String ID_FREQUENCY_CHAIN_COLOR_RED = "frequency_chain_color_red";
    public static final String ID_FREQUENCY_CHAIN_COLOR_BLACK = "frequency_chain_color_black";
    public static final String ID_FREQUENCY_CHAIN_COLOR_NOT_RED = "frequency_chain_color_not_red";
    public static final String ID_FREQUENCY_CHAIN_COLOR_NOT_BLACK = "frequency_chain_color_not_black";
    private boolean notFlag; //If true, this frequency listener counts events where it is NOT the color specified
    private int colorType; //NumberColor index

    public ColorChainFrequency(String id, int colorType) {
        super(id);
        init(colorType, false);
    }

    public ColorChainFrequency(String id, int colorType, boolean notFlag) {
        super(id);
        init(colorType, notFlag);
    }

    protected void init(int colorType, boolean notFlag) {
        this.colorType = colorType;
        this.notFlag = notFlag;
    }

    @Override
    public boolean evaluateResultEvent(ResultEvent re) {
        Number n = re.getNumber();

        if (n != null) {
            if (!notFlag && n.getColorId() == colorType) {
                return true;
            }
            else if (notFlag && n.getColorId() != colorType) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the proper name of the color event this frequency listener is tracking.
     * If notFlag is true, then prepends the name with 'Non-', ex: 'Non-Red', 'Non-Black',
     * otherwise just returns ex: 'Red', 'Black', etc.
     */
    public String getProperColorName() {
        String s = "";
        if (notFlag) s += "Non-";
        s += Number.NumberColor.getNumberColorFromId(colorType).getName();
        return s;
    }

    public int getColorType() {
        return colorType;
    }

    public boolean isNotFlagged() {
        return notFlag;
    }
}
