package com.dean.getracker.helper;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Dean on 05/05/17.
 */
public class ViewHelper {
    int width, height;
    int xOffset = 0, yOffset = 0;
    public void setBounds(int width, int height)
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

    int scale(int x){
        return x;
    }
    int offsetX(int x)
    {
        return x - xOffset;
    }
    int offsetY(int y)
    {
        return y - yOffset;
    }
    public Point getPoint(int x, int y)
    {
        return new Point(offsetX(scale(x)), offsetY(scale(y)));
    }

    public void addXOffset(int x)
    {
        xOffset += x;
    }
    public void addYOffset(int y)
    {
        yOffset += y;
    }

}
