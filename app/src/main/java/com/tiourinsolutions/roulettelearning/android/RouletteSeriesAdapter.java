package com.tiourinsolutions.roulettelearning.android;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tiourinsolutions.roulettelearning.R;
import com.tiourinsolutions.roulettelearning.core.roulette.Number;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying text of a roulette series in a list, coloring the text according to the
 * color of the result.
 * @author Maxim Tiourin
 */
public class RouletteSeriesAdapter extends ArrayAdapter<Number> {
    public RouletteSeriesAdapter(Context context, @LayoutRes int layres, @IdRes int res, @NonNull List<Number> objects) {
        super(context, layres, res, objects);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View v = super.getView(pos, convertView, parent);

        Number item = getItem(pos);

        TextView textView = (TextView) v.findViewById(R.id.textView);

        textView.setText(item.getId());
        textView.setTextColor(item.getColor());

        return v;
    }
}
