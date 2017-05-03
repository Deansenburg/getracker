package com.dean.getracker.view.decorations.line;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

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
    public void renderLine(Canvas c, PointF p1, PointF p2) {
        super.renderLine(c, p1, p2);
        c.drawLine(p1.x, p1.y, p2.x, p2.y, lineColor);
    }
}
