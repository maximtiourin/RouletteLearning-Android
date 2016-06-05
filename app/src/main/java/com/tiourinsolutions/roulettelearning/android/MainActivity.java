package com.tiourinsolutions.roulettelearning.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tiourinsolutions.roulettelearning.R;

public class MainActivity extends AppCompatActivity {
    //ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Set appbar
        Toolbar appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);




        /*Roulette r = new Roulette(NumberConfiguration.AMERICAN);
        r.startNewSeries();

        ArrayList<Number> results = new ArrayList<Number>();
        for (int i = 0; i < 1000; i++) {
            Number n = r.simulateSpin();
            results.add(n);
        }

        RouletteSeriesAdapter rouletteAdapter = new RouletteSeriesAdapter(this, R.layout.list_item_1, R.id.textView, results);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(rouletteAdapter);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //TODO do something
            }
            else if (resultCode == RESULT_CANCELED) {
                //Do Nothing
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.appbar_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_testnumber:
                intent = new Intent(this, SelectNumberActivity.class);
                intent.putExtra("numberConfiguration", "AMERICAN");
                startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
