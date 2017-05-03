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

    public geGraphController()
    {
    }

    public void addModel(ArrayList<geEntry> entries)
    {
        models.add(new geModel(entries));

        for (geEntry e:entries)
        {
            Log.d("DEBUG", e.toString());
        }
        jumpToLast();
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

    public void move(int direction)
    {
        position += direction*(month);
        for (geModel model:models)
        {
            model.update(position, width);
        }
    }

    public List<geModel> getModels()
    {
        return models;
    }

}
