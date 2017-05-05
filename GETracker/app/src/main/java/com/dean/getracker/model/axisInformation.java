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



}
