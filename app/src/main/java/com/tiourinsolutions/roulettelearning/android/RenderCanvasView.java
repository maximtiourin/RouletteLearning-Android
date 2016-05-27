package com.tiourinsolutions.roulettelearning.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Maxim on 5/21/2016.
 */
public class RenderCanvasView extends View {
    private int color;

    public RenderCanvasView(Context context) {
        super(context);
        init();
    }

    public RenderCanvasView(Context context, AttributeSet attribs) {
        super(context, attribs);
        init();
    }

    public RenderCanvasView(Context context, AttributeSet attribs, int defStyle) {
        super(context, attribs, defStyle);
        init();
    }

    private void init() {
        color = Color.GREEN;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RenderCanvasView view = (RenderCanvasView) v;
                    view.setColor((view.getColor() == Color.GREEN)?(Color.RED):(Color.GREEN));
                    view.invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        Paint myPaint = new Paint();
        myPaint.setColor(color);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(8);
        canvas.drawRect((int) (.1 * w), (int) (.1 * h), (int) (.2 * w), (int) (.2 * h), myPaint);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
