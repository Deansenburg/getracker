package com.dean.getracker.view.decorations.graph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.dean.getracker.helper.ViewHelper;
import com.dean.getracker.model.axisInformation;

/**
 * Created by Dean on 05/05/17.
 * defines both vertical and x axis in accordance with AxisInformation
 */
public class BasicAxis extends baseGraphDecoration{
    Paint axisColor;

    Paint textColor;

    int vMarks = 5, hMarks = 5;

    private final Rect textBounds = new Rect();

    public BasicAxis(IGraphDecoration dec) {
        super(dec);
        axisColor = new Paint();
        axisColor.setColor(Color.BLACK);
        axisColor.setStyle(Paint.Style.STROKE);
        axisColor.setStrokeWidth(10);

        textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(72);
    }

    @Override
    public void render(Canvas c, axisInformation axis, ViewHelper helper) {
        super.render(c, axis, helper);

        //x axis
        Point bottomLeft = helper.translateToScreen(0.1f, 0.95f);
        Point bottomRight = helper.translateToScreen(0.95f, 0.95f);
        c.drawLine(bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.y, axisColor);

        //y axis
        Point topLeft = helper.translateToScreen(0.1f, 0.05f);
        c.drawLine(bottomLeft.x, bottomLeft.y, topLeft.x, topLeft.y, axisColor);


        long step = (axis.MaxYAxis() - axis.MinYAxis()) / vMarks;
        int screenStep = helper.translateToScreen (0, 0.9f / vMarks).y;
        long current = axis.MinYAxis();
        float currentScreen = bottomLeft.y;
        textColor.setTextAlign(Paint.Align.RIGHT);
        do {
            c.drawText(current+"", bottomLeft.x, currentScreen, textColor);
            current += step;
            currentScreen -= screenStep;
        }while (current < axis.MaxYAxis() + step);

        step = (axis.MaxXAxis() - axis.MinXAxis()) / hMarks;
        screenStep = helper.translateToScreen(0.9f / hMarks, 0).x;
        current = axis.MinXAxis();
        currentScreen = bottomLeft.x;
        textColor.setTextAlign(Paint.Align.LEFT);

        do {
            String s = current+"";
            textColor.getTextBounds(s, 0, s.length(), textBounds);
            c.drawText(s, currentScreen, bottomLeft.y + textBounds.height(), textColor);
            current += step;
            currentScreen += screenStep;
        }while (current < axis.MaxXAxis() + step);
    }
}

