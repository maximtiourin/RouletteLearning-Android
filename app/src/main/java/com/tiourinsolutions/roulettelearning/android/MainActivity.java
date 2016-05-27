package com.tiourinsolutions.roulettelearning.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.tiourinsolutions.roulettelearning.R;
import com.tiourinsolutions.roulettelearning.core.roulette.Number;
import com.tiourinsolutions.roulettelearning.core.roulette.NumberConfiguration;
import com.tiourinsolutions.roulettelearning.core.roulette.Roulette;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Roulette r = new Roulette(NumberConfiguration.AMERICAN);
        r.startNewSeries();

        ArrayList<Number> results = new ArrayList<Number>();
        for (int i = 0; i < 1000; i++) {
            Number n = r.simulateSpin();
            results.add(n);
        }

        RouletteSeriesAdapter rouletteAdapter = new RouletteSeriesAdapter(this, R.layout.list_item_1, R.id.textView, results);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(rouletteAdapter);
    }
}
