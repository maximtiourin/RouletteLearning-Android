package com.tiourinsolutions.roulettelearning.core.roulette;

import com.tiourinsolutions.roulettelearning.core.roulette.statistics.ChainFrequency;
import com.tiourinsolutions.roulettelearning.core.roulette.statistics.Frequency;

import java.util.ArrayList;
import java.util.List;

/**
 * A series is a collection of results of consecutive "spins" of a roulette table, as well as the
 * container for all statistics pertaining to the series.
 * @author Maxim Tiourin
 */
public class Series {
    protected ArrayList<Number> series;
    protected ArrayList<ResultListener> resultListeners;
    protected NumberConfiguration numberConfiguration;

    public Series(NumberConfiguration numberConfiguration) {
        series = new ArrayList<Number>();
        resultListeners = new ArrayList<ResultListener>();
        this.numberConfiguration = numberConfiguration;

        initResultListeners();
    }

    public void addResult(Number result) {
        series.add(result);

        notifyResultListeners(new ResultEvent(result));
    }

    public List<Number> getResults() {
        return series;
    }

    public void addResultListener(ResultListener rl) {
        resultListeners.add(rl);
    }

    private void notifyResultListeners(ResultEvent re) {
        for (ResultListener rl : resultListeners) {
            rl.notify(re);
        }
    }

    public ResultListener getResultListener(String id) {
        for (ResultListener rl : resultListeners) {
            if (rl.getId().equals(id)) {
                return rl;
            }
        }

        return null;
    }

    public List<ResultListener> getResultListeners() {
        return resultListeners;
    }

    public boolean removeResultListener(ResultListener rl) {
        return resultListeners.remove(rl);
    }

    /**
     * Must be called when the series is concluded in order to notify all resultEvent listeners
     * one last time.
     */
    public void concludeSeries() {
        notifyResultListeners(new ResultEvent(null));
    }

    private void initResultListeners() {
        addSpecialResultListeners_Color();
        addSpecialResultListeners_Even();
        addSpecialResultListeners_Odd();
        addSpecialResultListeners_1To18();
        addSpecialResultListeners_19To36();
        addSpecialResultListeners_Column1();
        addSpecialResultListeners_Column2();
        addSpecialResultListeners_Column3();
    }

    private void addSpecialResultListeners_Color() {
        Number.NumberColor colors[] = new Number.NumberColor[3];
        colors[0] = Number.NumberColor.Green;
        colors[1] = Number.NumberColor.Red;
        colors[2] = Number.NumberColor.Black;

        String[] colorNames = new String[]{colors[0].getName(), colors[1].getName(), colors[2].getName()};
        int[] colorIds = new int[]{colors[0].getId(), colors[1].getId(), colors[2].getId()};

        for (int i = 0; i < colors.length; i++) {
            final String colorName = colorNames[i];
            final int colorId = colorIds[i];

            //Frequency Listeners
            addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY + colorName.toLowerCase(), colorName) {
                @Override
                public boolean evaluateResultEvent(ResultEvent re) {
                    Number n = re.getNumber();

                    if (n != null && n.getColorId() == colorId) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            });
            addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY_NOT + colorName.toLowerCase(), "Non-" + colorName) {
                @Override
                public boolean evaluateResultEvent(ResultEvent re) {
                    Number n = re.getNumber();

                    if (n != null && n.getColorId() != colorId) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            });

            //Chain Frequency Listeners
            addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN + colorName.toLowerCase(), colorName) {
                @Override
                public boolean evaluateResultEvent(ResultEvent re) {
                    Number n = re.getNumber();

                    if (n != null && n.getColorId() == colorId) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            });
            addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN_NOT + colorName.toLowerCase(), "Non-" + colorName) {
                @Override
                public boolean evaluateResultEvent(ResultEvent re) {
                    Number n = re.getNumber();

                    if (n != null && n.getColorId() != colorId) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            });
        }
    }

    private void addSpecialResultListeners_Even() {
        /* Even */
        final String evenName = "Even";
        //Frequency Listeners
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY + evenName.toLowerCase(), evenName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0 && eval % 2 == 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY_NOT + evenName.toLowerCase(), "Non-" + evenName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0 || eval % 2 != 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        //Chain Frequency Listeners
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN + evenName.toLowerCase(), evenName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0 && eval % 2 == 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN_NOT + evenName.toLowerCase(), "Non-" + evenName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0 || eval % 2 != 0) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void addSpecialResultListeners_Odd() {
        /* Odd */
        final String oddName = "Odd";
        //Frequency Listeners
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY + oddName.toLowerCase(), oddName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0 && eval % 2 != 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY_NOT + oddName.toLowerCase(), "Non-" + oddName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0 || eval % 2 == 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        //Chain Frequency Listeners
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN + oddName.toLowerCase(), oddName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0 && eval % 2 != 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN_NOT + oddName.toLowerCase(), "Non-" + oddName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0 || eval % 2 == 0) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void addSpecialResultListeners_1To18() {
        /* 1 TO 18 */
        final String oteidName = "1to18";
        final String oteName = "1 TO 18";
        //Frequency Listeners
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY + oteidName.toLowerCase(), oteName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval >= 1 && eval <= 18) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY_NOT + oteidName.toLowerCase(), "Not " + oteName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval < 1 || eval > 18) {
                        return true;
                    }
                }
                return false;
            }
        });
        //Chain Frequency Listeners
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN + oteidName.toLowerCase(), oteName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval >= 1 && eval <= 18) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN_NOT + oteidName.toLowerCase(), "Not " + oteName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval < 1 || eval > 18) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void addSpecialResultListeners_19To36() {
        /* 19 TO 36 */
        final String nttidName = "19to36";
        final String nttName = "19 TO 36";
        //Frequency Listeners
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY + nttidName.toLowerCase(), nttName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval >= 19 && eval <= 36) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY_NOT + nttidName.toLowerCase(), "Not " + nttName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval < 19 || eval > 36) {
                        return true;
                    }
                }
                return false;
            }
        });
        //Chain Frequency Listeners
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN + nttidName.toLowerCase(), nttName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval >= 19 && eval <= 36) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN_NOT + nttidName.toLowerCase(), "Not " + nttName) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval < 19 || eval > 36) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
    
    private void addSpecialResultListeners_Column1() {
        /* Column 1 */
        final String c1idName = "column1";
        final String c1Name = "Column 1";
        //Frequency Listeners
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY + c1idName.toLowerCase(), c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0  && eval % 3 == 1) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY_NOT + c1idName.toLowerCase(), "Not " + c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0  || eval % 3 != 1) {
                        return true;
                    }
                }
                return false;
            }
        });
        //Chain Frequency Listeners
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN + c1idName.toLowerCase(), c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0  && eval % 3 == 1) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN_NOT + c1idName.toLowerCase(), "Not " + c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0  || eval % 3 != 1) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void addSpecialResultListeners_Column2() {
        /* Column 2 */
        final String c1idName = "column2";
        final String c1Name = "Column 2";
        //Frequency Listeners
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY + c1idName.toLowerCase(), c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0  && eval % 3 == 2) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY_NOT + c1idName.toLowerCase(), "Not " + c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0  || eval % 3 != 2) {
                        return true;
                    }
                }
                return false;
            }
        });
        //Chain Frequency Listeners
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN + c1idName.toLowerCase(), c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0  && eval % 3 == 2) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN_NOT + c1idName.toLowerCase(), "Not " + c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0  || eval % 3 != 2) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void addSpecialResultListeners_Column3() {
        /* Column 3 */
        final String c1idName = "column3";
        final String c1Name = "Column 3";
        //Frequency Listeners
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY + c1idName.toLowerCase(), c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0  && eval % 3 == 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new Frequency(Frequency.PREFIX_ID_FREQUENCY_NOT + c1idName.toLowerCase(), "Not " + c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0  || eval % 3 != 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        //Chain Frequency Listeners
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN + c1idName.toLowerCase(), c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval > 0  && eval % 3 == 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        addResultListener(new ChainFrequency(ChainFrequency.PREFIX_ID_FREQUENCY_CHAIN_NOT + c1idName.toLowerCase(), "Not " + c1Name) {
            @Override
            public boolean evaluateResultEvent(ResultEvent re) {
                Number n = re.getNumber();

                if (n != null) {
                    int eval = n.getNumEval();

                    if (eval <= 0  || eval % 3 != 0) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
