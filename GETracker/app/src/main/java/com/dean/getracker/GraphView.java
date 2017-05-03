package com.dean.getracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Dean on 31/03/17.
 */
public class GraphView extends View {
    GEGraphModel model;

    Paint linePaint = new Paint();

    int x = 0;

    public GraphView(Context context) {
        super(context);
        setup();
    }
    public GraphView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setup();
    }

    private void setup()
    {
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(10);
    }

    public void updateModel(GEGraphModel m)
    {
        model = m;
        this.invalidate();
    }

    public void move(int direction)
    {
        x += direction*(getWidth()/100);
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (model != null)
        {
            int width = getWidth();
            int height = getHeight();
            ArrayList<PointF> points = model.getPoints();

            int lastX=-1, lastY=-1;
            for (int i=0; i < points.size();i++)
            {
                long min = model.position - (model.size/2);
                long max = model.position + (model.size/2);
                float dx = model.norm(points.get(i).x, max, min);

                int x = (int)(width * dx);
                int y = (int)(height * points.get(i).y);
                if (lastX != -1 || lastY != -1)
                {
                    canvas.drawLine(lastX, lastY, x, y, linePaint);
                }
                lastX = x;
                lastY = y;
            }
        }
    }
}
