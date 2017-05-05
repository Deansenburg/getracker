package com.dean.getracker.view.decorations.graph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.dean.getracker.helper.ViewHelper;
import com.dean.getracker.helper.normaliseHelper;
import com.dean.getracker.model.axisInformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Dean on 05/05/17.
 * defines both vertical and x axis in accordance with AxisInformation
 */
public class BasicAxis extends baseGraphDecoration{
    Paint axisColor;

    Paint textColor;

    int vMarks = 5;

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

    private void drawAtYAxis(Canvas c, axisInformation axis, ViewHelper helper, int value)
    {
        float y = 1 - normaliseHelper.norm(value, axis.MaxYAxis(), axis.MinYAxis());
        Point p = helper.translateToScreen(0.1f, y);
        c.drawText(value+"", p.x, p.y, textColor);
    }

    private void drawAtXAxis(Canvas c, axisInformation axis, ViewHelper helper, long value)
    {
        float x = normaliseHelper.norm(value, axis.MaxXAxis(), axis.MinXAxis());
        Point p = helper.translateToScreen(x, 0.95f);
        SimpleDateFormat f = new SimpleDateFormat("MMM");
        Date d = new Date(value);
        String s = f.format(d);
        int offset = helper.translateToScreen(0, 0.03f).y;
        c.drawText(s, p.x, p.y+offset, textColor);
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

        int start = (int)axis.MinYAxis();
        float step = (axis.MaxYAxis() - axis.MinYAxis()) / vMarks;

        textColor.setTextAlign(Paint.Align.RIGHT);
        for (int i=1;i<vMarks;i++) {
            drawAtYAxis(c, axis, helper, (int)(start + (step*i)));
        }
        Date minDate = new Date(axis.MinXAxis());

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(minDate);
        while(calendar.getTimeInMillis() < axis.MaxXAxis())
        {
            drawAtXAxis(c, axis, helper, calendar.getTimeInMillis());
            calendar.add(Calendar.MONTH, 1);
        }

    }
}

