package com.dean.getracker.model;

/**
 * Created by Dean on 05/05/17.
 */
public class axisInformation {
    long maxY, maxX, minY, minX;

    public axisInformation(long minX, long maxX, long minY, long maxY)
    {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
    public axisInformation(long x, long y)
    {
        this.minX = x;
        this.maxX = x;
        this.minY = y;
        this.maxY = y;
    }

    public long MaxYAxis()
    {
        return maxY;
    }
    public long MinYAxis()
    {
        return minY;
    }
    public long MaxXAxis()
    {
        return maxX;
    }
    public long MinXAxis()
    {
        return minX;
    }

    public void setX(long x)
    {
        minX = x;
        maxX = x;
    }
    public void setY(long y)
    {
        minY = y;
        maxY = y;
    }

}
