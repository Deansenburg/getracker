package com.dean.getracker.model;

import android.graphics.PointF;

import com.dean.getracker.helper.normaliseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dean on 03/05/17.
 */
public class geModel {
    ArrayList<geEntry> entries;

    long maxDate, minDate;
    int maxValue, minValue;

    int maxValueTotal, minValueTotal;
    long maxDateTotal, minDateTotal;

    long position, width;

    public geModel(ArrayList<geEntry> e)
    {
        entries = e;
        normaliseValue();
    }

    public void update(long pos, long width)
    {
        position = pos;
        this.width = width;
        normaliseDate();
    }


    private void normaliseDate()
    {
        minDate = position - (width /2);
        maxDate = position + (width /2);
    }

    private void normaliseValue()
    {
        int low = 0, high = 0;
        for (geEntry e:entries) {
            while (e.Value() >= high)
            {
                high += 50;
            }
        }
        maxValue = high;
        minValue = low;
    }

    public int lowerValue(int val)
    {
        if (minValue < val)
        {
            return minValue;
        }
        return val;
    }
    public int higherValue(int val)
    {
        if (maxValue > val)
        {
            return maxValue;
        }
        return val;
    }
    public long higherDate(long date)
    {
        if (maxDate > date)
        {
            return maxDate;
        }
        return date;
    }
    public long lowerDate(long date)
    {
        if (minDate < date)
        {
            return minDate;
        }
        return date;
    }

    public void setMaxValues(int min, int max)
    {
        minValueTotal = min;
        maxValueTotal = max;
    }
    public void setMaxDates(long min, long max)
    {
        minDateTotal = min;
        maxDateTotal = max;
    }

    public ArrayList<PointF> getPoints()
    {
        ArrayList<PointF> points= new ArrayList<>();
        for (geEntry e:entries)
        {
            long date = e.LongDate();
            if (date > (position- width) && date < (position+ width))
            {
                float x, y;
                x = normaliseHelper.norm(date, maxDateTotal, minDateTotal);

                y = 1 - normaliseHelper.norm(e.Value(), maxValueTotal, minValueTotal);
                points.add(new PointF(x, y));
            }
        }
        return points;
    }

    public List<geEntry> getEntries()
    {
        return entries;
    }

    public axisInformation getAxisInfo()
    {
        axisInformation axis = new axisInformation(minDate, maxDate, minValueTotal, maxValueTotal);
        return axis;
    }

}
