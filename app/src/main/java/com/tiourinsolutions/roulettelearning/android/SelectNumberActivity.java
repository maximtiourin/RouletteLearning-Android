package com.tiourinsolutions.roulettelearning.android;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tiourinsolutions.roulettelearning.R;
import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration;

import java.util.ArrayList;
import java.util.List;

//TODO add a confirmation dialog, which can be enabled/disabled in settings, to confirm number selection
public class SelectNumberActivity extends AppCompatActivity {
    public static final int COLOR_BUTTON_TEXT = Color.WHITE;
    public static final int COLOR_BUTTON_BORDER = Color.rgb(175, 175, 175);
    public static final float SCALE_BUTTON_TEXT = 1 / 2.2f;
    public static final int NUMBER_OF_COLUMNS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_number);

        //Set appbar
        Toolbar appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        //Get NumberConfiguration from intent
        Intent parentIntent = getIntent();
        String nconfig = parentIntent.getStringExtra("numberConfiguration");
        if (nconfig != null) {
            NumberConfiguration numberConfig = NumberConfiguration.getConfigurationFromStringId(nconfig);

            if (numberConfig != null) {
                //Determine number of rows to use for the table layout
                final int configSize = numberConfig.getConfigSize();
                final int numRows = (int) Math.ceil(configSize / (double) NUMBER_OF_COLUMNS);
                final int greenCount = numberConfig.getConfigGreenCount();
                final ArrayList<Button> buttons = new ArrayList<Button>();

                List<Number> sortedNumbers = numberConfig.getSortedNumberList();

                LinearLayout layout = (LinearLayout) findViewById(R.id.numberContainer);
                layout.setWeightSum(numRows);

                int col = 0;
                for (int i = 0; i < numRows; i++) {
                    LinearLayout row = new LinearLayout(this);
                    LinearLayout.LayoutParams rparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                    row.setLayoutParams(rparams);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setWeightSum(NUMBER_OF_COLUMNS);

                    //Add up to NUMBER_OF_COLUMNS children to this row, or any remaining numbers less than.
                    int count = 0;
                    while (count < NUMBER_OF_COLUMNS) {
                        if (col < configSize) {
                            final Number number = sortedNumbers.get(col);

                            Button button = new Button(this);

                            button.setText(number.getId());
                            button.setTextColor(COLOR_BUTTON_TEXT);

                            //Set background color (and border if supported)
                            if (Build.VERSION.SDK_INT > 15) {
                                GradientDrawable gd = new GradientDrawable();
                                gd.setColor(number.getColor());
                                gd.setCornerRadius(1);
                                gd.setStroke(2, COLOR_BUTTON_BORDER);
                                button.setBackground(gd);
                            }
                            else {
                                button.setBackgroundColor(number.getColor());
                            }

                            //Set layout param filling
                            if (col < configSize - greenCount) {
                                LinearLayout.LayoutParams bparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                button.setLayoutParams(bparams);
                            }
                            else {
                                LinearLayout.LayoutParams bparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, NUMBER_OF_COLUMNS / (float) greenCount);
                                button.setLayoutParams(bparams);
                            }

                            //button.setTextSize(TypedValue.COMPLEX_UNIT_PX, button.getHeight() / 2);

                            //Add click listener
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finishActivityWithNumberSelection(number);
                                }
                            });

                            //Add button to row and buttons
                            row.addView(button);
                            buttons.add(button);
                        }

                        count++;
                        col++;
                    }

                    layout.addView(row);
                }

                //Add layout change listener so that we can scale button text size to fit the button (then remove listener)
                final LinearLayout tlayout = layout;
                layout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        for (Button b : buttons) {
                            b.setTextSize(TypedValue.COMPLEX_UNIT_PX, b.getHeight() * SCALE_BUTTON_TEXT);
                        }
                        tlayout.removeOnLayoutChangeListener(this);
                    }
                });
            }
            else {
                finishActivityWithNumberSelection(null);
            }
        }
        else {
            finishActivityWithNumberSelection(null);
        }
    }

    protected void finishActivityWithNumberSelection(Number number) {
        if (number == null) {
            setResult(RESULT_CANCELED);
            finish();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("numberId", number.getId());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.appbar_barebones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finishActivityWithNumberSelection(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
