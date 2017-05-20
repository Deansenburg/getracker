package com.dean.getracker.view.decorations.line;

import android.graphics.Canvas;
import android.graphics.Point;

import com.dean.getracker.helper.ViewHelper;

/**
 * Created by Dean on 03/05/17.
 */
public class baseLineDecoration implements ILineDecoration{
    protected ILineDecoration decoration;

    public baseLineDecoration(ILineDecoration dec)
    {
        decoration = dec;
    }
    @Override
    public void renderLine(Canvas c, Point p1, Point p2, ViewHelper helper) {
        if (decoration != null)
        {
            decoration.renderLine(c, p1, p2, helper);
        }
    }
}
