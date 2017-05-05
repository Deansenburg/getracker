package com.dean.getracker.view.decorations.graph;

import android.graphics.Canvas;

import com.dean.getracker.helper.ViewHelper;
import com.dean.getracker.model.axisInformation;

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
    public void render(Canvas c, axisInformation axis, ViewHelper helper) {
        super.render(c, axis, helper);
        c.drawColor(color);
    }
}
