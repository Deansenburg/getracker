package com.dean.getracker.view.decorations.graph.axis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.dean.getracker.helper.ViewHelper;
import com.dean.getracker.helper.normaliseHelper;
import com.dean.getracker.model.axisInformation;
import com.dean.getracker.view.decorations.graph.IGraphDecoration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Dean on 20/05/17.
 */
public class HorizontalAxis extends Axis {

    Paint axisColor;

    Paint textColor;

    String heightString = "azAZgy";
    int height;
    float vPadPerc = 0.01f;

    axisInformation axis;

    public HorizontalAxis(axisInformation a, IGraphDecoration dec) {
        super(dec);
        axis = a;
        axisColor = new Paint();
        axisColor.setColor(Color.BLACK);
        axisColor.setStyle(Paint.Style.STROKE);
        axisColor.setStrokeWidth(10);

        textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(72);
        Rect bounds = new Rect();
        textColor.getTextBounds(heightString, 0, heightString.length(), bounds);
        height = bounds.height();


    }

    private void drawAtXAxis(Canvas c, axisInformation axis, ViewHelper helper, long value)
    {
        float x = normaliseHelper.norm(value, axis.MaxXAxis(), axis.MinXAxis());
        Point p = helper.translateToScreen(x, 1);

        SimpleDateFormat f = new SimpleDateFormat("MMM");
        Date d = new Date(value);
        String s = f.format(d);
        c.drawText(s, p.x, p.y, textColor);
    }

    @Override
    public void render(Canvas c, axisInformation axis, ViewHelper helper) {
        super.render(c, axis, helper);

        Point bottomLeft = helper.translateToScreen(0.1f, 0);
        Point bottomRight = helper.translateToScreen(0.95f, 0);

        c.drawLine(bottomLeft.x, this.axis.MinYAxis(), bottomRight.x, this.axis.MinYAxis(), axisColor);

        Date minDate = new Date(axis.MinXAxis());

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(minDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        while(calendar.getTimeInMillis() < axis.MaxXAxis())
        {
            drawAtXAxis(c, axis, helper, calendar.getTimeInMillis());
            calendar.add(Calendar.MONTH, 1);
        }
    }

    @Override
    public void updateAxis(ViewHelper helper) {
        float height = helper.translateToScreen(0, 1 - vPadPerc).y;
        height -= this.height;
        this.axis.setY((int) height);
    }
}
