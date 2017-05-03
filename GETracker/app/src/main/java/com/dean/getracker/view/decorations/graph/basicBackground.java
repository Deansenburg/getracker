package com.dean.getracker.view.decorations.graph;

import android.graphics.Canvas;

/**
 * Created by Dean on 03/05/17.
 */
public class basicBackground extends baseGraphDecoration{
    int color;
    public basicBackground(int c, IGraphDecoration dec) {
        super(dec);
        color = c;
    }

    @Override
    public void render(Canvas c) {
        super.render(c);
        c.drawColor(color);
    }
}
