package com.dean.getracker.helper;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Dean on 05/05/17.
 */
public class ViewHelper {
    int width, height;
    public ViewHelper(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public Point translateToScreen(float x, float y)
    {
        return new Point((int)(x*width), (int)(y*height));
    }
    public PointF translateFromScreen(int x, int y)
    {
        return new PointF((float)x/(float)width, (float)y/(float)height);
    }

}
