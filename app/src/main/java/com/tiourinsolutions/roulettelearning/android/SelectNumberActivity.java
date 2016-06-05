package com.tiourinsolutions.roulettelearning.android;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.tiourinsolutions.roulettelearning.R;
import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration;

import java.util.List;

public class SelectNumberActivity extends AppCompatActivity {
    public static final int NUMBER_OF_COLUMNS = 4;

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

                List<Number> sortedNumbers = numberConfig.getSortedNumberList();

                //Create and populate table layout
                //TODO instead of using tablelayout, change to using tiered linear layouts so that entire screen space is used.
                TableLayout layout = (TableLayout) findViewById(R.id.tableView);

                int col = 0;
                for (int i = 0; i < numRows; i++) {
                    TableRow row = new TableRow(this);

                    //Add up to NUMBER_OF_COLUMNS children to this table row, or any remaining numbers less than.
                    int count = 0;
                    while (count < NUMBER_OF_COLUMNS) {
                        if (col < configSize) {
                            final Number number = sortedNumbers.get(col);

                            Button button = new Button(this);
                            button.setMinWidth(0);
                            button.setMinHeight(0);
                            button.setText(number.getId());
                            button.setTextColor(Color.WHITE);
                            button.setBackgroundColor(number.getColor());

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finishActivityWithNumberSelection(number);
                                }
                            });

                            row.addView(button);

                            TableRow.LayoutParams bparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f);
                            button.setLayoutParams(bparams);
                        }

                        count++;
                        col++;
                    }

                    layout.addView(row);
                }
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
