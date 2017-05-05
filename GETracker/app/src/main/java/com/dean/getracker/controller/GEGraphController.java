package com.dean.getracker.controller;

import android.util.Log;

import com.dean.getracker.model.geEntry;
import com.dean.getracker.model.geModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dean on 31/03/17.
 */
public class geGraphController {

    ArrayList<geModel> models = new ArrayList<>();

    long month = 1000 * 60 * 60 * 60 * 24 * 30;

    public long width = month * 24;
    public long position = 0;

    public void addModel(ArrayList<geEntry> entries)
    {
        geModel m = new geModel(entries);
        models.add(m);

        for (geEntry e:entries)
        {
            Log.d("DEBUG", e.toString());
        }
        jumpToLast();
        m.update(position, width);
    }

    private void jumpToLast()
    {
        for (geModel m:models)
        {
            for (geEntry e:m.getEntries())
            {
                if (position < e.LongDate())
                {
                    position = e.LongDate();
                }
            }
        }
    }

    private void update()
    {
        int minValue = Integer.MAX_VALUE, maxValue = 0;
        long minDate = Long.MAX_VALUE, maxDate = 0;
        for (geModel model:models)
        {
            model.update(position, width);
            minValue = model.lowerValue(minValue);
            maxValue = model.higherValue(maxValue);
            minDate = model.lowerDate(minDate);
            maxDate = model.higherDate(maxDate);
        }
        for (geModel model:models)
        {
            model.setMaxValues(minValue, maxValue);
            model.setMaxDates(minDate, maxDate);
        }
    }

    public void move(int direction)
    {
        position += direction*(month);
        update();
    }

    public List<geModel> getModels()
    {
        return models;
    }

}
