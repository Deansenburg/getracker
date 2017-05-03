package com.dean.getracker.controller;

import android.graphics.PointF;
import android.util.Log;

import com.dean.getracker.helper.NormaliseHelper;
import com.dean.getracker.model.GEEntry;

import java.util.ArrayList;

/**
 * Created by Dean on 31/03/17.
 */
public class GEGraphController {

    ArrayList<GEEntry> mEntries;

    long maxDate, minDate;
    int maxValue, minValue;

    long month = 1000 * 60 * 60 * 60 * 24 * 30;

    public long size = month * 24;
    public long position = 0;

    public GEGraphController()
    {
    }

    public void setModel(ArrayList<GEEntry> entries)
    {
        mEntries = entries;
        for (GEEntry e:mEntries)
        {
            Log.d("DEBUG", e.toString());
        }
        normaliseValue();
        jumpToLast();
    }

    private void jumpToLast()
    {
        position = mEntries.get(mEntries.size()-2).LongDate();
    }

    private void normaliseDate()
    {
        minDate = position - (size/2);
        maxDate = position + (size/2);
    }

    private void normaliseValue()
    {
        int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;
        for (GEEntry e:mEntries) {
            if (e.Value() < low)
            {
                low = e.Value();
            }
            if (e.Value() > high)
            {
                high = e.Value();
            }
        }
        maxValue = high;
        minValue = low;
    }

    public void move(int direction)
    {
        position += direction*(month);
        Log.d("Position", position+"");
    }

    public ArrayList<PointF> getPoints()
    {
        ArrayList<PointF> points= new ArrayList<>();
        for (GEEntry e:mEntries)
        {
            long date = e.LongDate();
            if (date > (position-size) && date < (position+size))
            {
                float x, y;

                normaliseDate();
                x = NormaliseHelper.norm(date, maxDate, minDate);

                y = NormaliseHelper.norm(e.Value(), maxValue, minValue);
                points.add(new PointF(x, y));
            }
        }
        return points;
    }
}
