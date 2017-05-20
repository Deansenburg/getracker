package com.dean.getracker.view.decorations.graph.axis;

import android.graphics.Canvas;

import com.dean.getracker.helper.ViewHelper;
import com.dean.getracker.model.axisInformation;
import com.dean.getracker.view.decorations.graph.IGraphDecoration;
import com.dean.getracker.view.decorations.graph.baseGraphDecoration;

import java.util.ArrayList;

/**
 * Created by Dean on 20/05/17.
 */
public class AxisControlDecoration extends baseGraphDecoration {

    ArrayList<Axis> axisList = new ArrayList<>();

    public AxisControlDecoration(IGraphDecoration dec) {
        super(dec);
    }

    public void add(Axis a)
    {
        axisList.add(a);
    }

    @Override
    public void render(Canvas c, axisInformation axis, ViewHelper helper) {
        super.render(c, axis, helper);
        for (Axis a:axisList)
        {
            a.updateAxis(axis, helper);
        }
        for (Axis a:axisList)
        {
            a.render(c, axis, helper);
        }
    }
}
