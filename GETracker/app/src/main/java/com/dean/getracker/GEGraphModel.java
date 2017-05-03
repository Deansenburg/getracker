package com.dean.getracker;

import android.graphics.PointF;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dean on 31/03/17.
 */
public class GEGraphModel {

    ArrayList<GEEntry> mEntries;

//    long maxDate, minDate;
    int maxValue, minValue;

    long month = 1000 * 60 * 60 * 60 * 24 * 30;

    public long size = month * 24;
    public long position = 0;

    public GEGraphModel()
    {
    }

    public void setModel(ArrayList<GEEntry> entries)
    {
        mEntries = entries;
        for (GEEntry e:mEntries)
        {
            Log.d("DEBUG", e.toString());
        }
//        normaliseDate();
        normaliseValue();
        jumpToLast();
    }

    private void jumpToLast()
    {
        position = mEntries.get(mEntries.size()-2).getLongDate();
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


//    private void normaliseDate()
//    {
//        long low=Long.MAX_VALUE, high = Long.MIN_VALUE;
//        for (GEEntry e:mEntries)
//        {
//            if (e.getLongDate() < low)
//            {
//                low = e.getLongDate();
//            }
//            if(e.getLongDate() > high)
//            {
//                high = e.getLongDate();
//            }
//        }
//        maxDate = high;
//        minDate = low;;
//    }


    public void move(int direction)
    {
        position += direction*(month);
        Log.d("Position", position+"");
    }

    private int getMonthFromDate(Date date){
        SimpleDateFormat toMonths = new SimpleDateFormat("MM");
        return Integer.parseInt(
                toMonths.format(date));
    }

    private int getMonthFromDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        try {
            return getMonthFromDate(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float norm(float x, float max, float min)
    {
        return (x - min) / (max - min);
    }
    public float norm(int x, int max, int min)
    {
        return norm((float)x, (float)max, (float)min);
    }
    public float norm(long x, long max, long min)
    {
        return norm((float)x, (float)max, (float)min);
    }


    public ArrayList<PointF> getPoints()
    {
        ArrayList<PointF> points= new ArrayList<>();
        for (GEEntry e:mEntries)
        {
            long date = e.getLongDate();
            if (date > (position-size) && date < (position+size))
            {
                float x, y;
//                x = norm(date, maxDate, minDate);
                y = norm(e.Value(), maxValue, minValue);
                points.add(new PointF(date, y));
            }
        }
        return points;
    }
}
