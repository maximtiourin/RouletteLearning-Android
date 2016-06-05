package com.tiourinsolutions.roulettelearning;

import com.tiourinsolutions.roulettelearning.core.roulette.Number;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class NumberConfiguration {
    private com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration configAmerican;
    private com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration configTester;

    public NumberConfiguration() {
        configAmerican = com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration.AMERICAN;
        configTester = configAmerican;
    }

    @Test
    public void AmericanConfig() throws Exception {
        assertEquals(configAmerican.getConfigSize(), 38);
        assertEquals(configAmerican.getConfigGreenCount(), 2);
        assertEquals(configAmerican.getConfigRedCount(), 18);
        assertEquals(configAmerican.getConfigBlackCount(), 18);
        assertEquals(configAmerican.getNumberAtIndexOffset(0).getId(), "00");
        assertEquals(configAmerican.getNumberAtIndexOffset(19).getId(), "0");
        for (Number n : configAmerican.getNumberList()) {
            assertEquals((int) Integer.valueOf(n.getId()), n.getNumEval());
        }
    }

    @Test
    public void getIndexAtIndexOffset() throws Exception {
        assertEquals(configTester.getIndexAtIndexOffset(0), 0);
        assertEquals(configTester.getIndexAtIndexOffset(37), 37);
        assertEquals(configTester.getIndexAtIndexOffset(38), 0);
        assertEquals(configTester.getIndexAtIndexOffset(16), 16);
    }
}