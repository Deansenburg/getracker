package com.dean.getracker.view.decorations.line;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.dean.getracker.helper.ViewHelper;

/**
 * Created by Dean on 03/05/17.
 */
public class basicLine extends baseLineDecoration {

    private Paint lineColor;
    public basicLine(int color, ILineDecoration dec) {
        super(dec);
        lineColor = new Paint();
        lineColor.setColor(color);
        lineColor.setStyle(Paint.Style.STROKE);
        lineColor.setStrokeWidth(10);
    }

    @Override
    public void renderLine(Canvas c, Point p1, Point p2, ViewHelper helper) {
        super.renderLine(c, p1, p2, helper);
        Point a1 = helper.getPoint(p1.x, p1.y);
        Point a2 = helper.getPoint(p2.x, p2.y);
        c.drawLine(a1.x, a1.y, a2.x, a2.y, lineColor);
    }
}
