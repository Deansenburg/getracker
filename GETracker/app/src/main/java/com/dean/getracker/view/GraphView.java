package com.dean.getracker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.dean.getracker.controller.GEGraphController;
import com.dean.getracker.view.decorations.graph.IGraphDecoration;
import com.dean.getracker.view.decorations.line.ILineDecoration;
import com.dean.getracker.view.decorations.node.INodeDecoration;

import java.util.ArrayList;

/**
 * Created by Dean on 31/03/17.
 */
public class GraphView extends View {
    GEGraphController model;

    IGraphDecoration graphDecorator;
    INodeDecoration nodeDecorator;
    ILineDecoration lineDecorator;

    Paint linePaint = new Paint();

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

    }

    public void setupRendering(IGraphDecoration gd, ILineDecoration ld, INodeDecoration nd)
    {
        graphDecorator = gd;
        lineDecorator = ld;
        nodeDecorator = nd;
    }

    public void updateModel(GEGraphController m)
    {
        model = m;
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

            //graph rendering
            graphDecorator.render(canvas);
            //line rendering
            PointF lastPoint = null;
            for (int i=0; i < points.size();i++)
            {
                int x = (int)(width * points.get(i).x);
                int y = (int)(height * points.get(i).y);
                PointF currentPoint = new PointF(x, y);
                if (lastPoint != null)
                {
                    lineDecorator.renderLine(canvas, currentPoint, lastPoint);
                }
                lastPoint = currentPoint;
            }
            //node rendering
            for (PointF p:points)
            {
                int x = (int)(width * p.x);
                int y = (int)(height * p.y);
                nodeDecorator.renderNode(canvas, new PointF(x, y));
            }
        }
    }
}
