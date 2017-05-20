package com.dean.getracker.view.decorations.graph.axis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.dean.getracker.helper.ViewHelper;
import com.dean.getracker.helper.normaliseHelper;
import com.dean.getracker.model.axisInformation;
import com.dean.getracker.view.decorations.graph.IGraphDecoration;

/**
 * Created by Dean on 20/05/17.
 */
public class VerticalAxis extends Axis{
    Paint axisColor;
    Paint textColor;
    Paint fillColor;

    int vMarks = 5;

    axisInformation axis;

    public VerticalAxis(axisInformation a, IGraphDecoration dec) {
        super(dec);

        axis = a;
        axisColor = new Paint();
        axisColor.setColor(Color.BLACK);
        axisColor.setStyle(Paint.Style.STROKE);
        axisColor.setStrokeWidth(10);

        textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(72);

        fillColor = new Paint();
        fillColor.setColor(Color.WHITE);
        fillColor.setStyle(Paint.Style.FILL);
    }

    private void drawAtYAxis(Canvas c, axisInformation axis, ViewHelper helper, int value)
    {
        float y = 1 - normaliseHelper.norm(value, axis.MaxYAxis(), axis.MinYAxis());
        Point p = helper.translateToScreen(0.1f, y);
        c.drawText(value+"", p.x, p.y, textColor);
    }

    @Override
    public void render(Canvas c, axisInformation axis, ViewHelper helper) {
        super.render(c, axis, helper);

        Point bottomLeft = helper.translateToScreen(0.1f, 0.95f);

        Point topLeft = helper.translateToScreen(0.1f, 0.05f);
        int bottomAxis = (int) this.axis.MaxYAxis();

        c.drawRect(0, 0, bottomLeft.x, bottomAxis, fillColor);

        c.drawLine(bottomLeft.x, bottomAxis, topLeft.x, topLeft.y, axisColor);

        int start = (int)axis.MinYAxis();
        float step = (axis.MaxYAxis() - axis.MinYAxis()) / vMarks;

        textColor.setTextAlign(Paint.Align.RIGHT);
        for (int i=1;i<vMarks;i++) {
            drawAtYAxis(c, axis, helper, (int)(start + (step*i)));
        }
    }
    @Override
    void updateAxis(ViewHelper helper) {

    }
}
