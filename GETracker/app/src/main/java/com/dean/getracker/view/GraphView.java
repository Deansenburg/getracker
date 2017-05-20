package com.dean.getracker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.dean.getracker.helper.ViewHelper;
import com.dean.getracker.model.axisInformation;
import com.dean.getracker.model.geModel;
import com.dean.getracker.view.decorations.graph.IGraphDecoration;
import com.dean.getracker.view.decorations.line.ILineDecoration;
import com.dean.getracker.view.decorations.node.INodeDecoration;

import java.util.List;

/**
 * Created by Dean on 31/03/17.
 */
public class graphView extends View {
    List<geModel> models;

    IGraphDecoration graphDecorator;
    INodeDecoration nodeDecorator;
    ILineDecoration lineDecorator;

    public graphView(Context context) {
        super(context);
    }
    public graphView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setupRendering(IGraphDecoration gd, ILineDecoration ld, INodeDecoration nd)
    {
        graphDecorator = gd;
        lineDecorator = ld;
        nodeDecorator = nd;
    }

    public void setModel(List<geModel> models)
    {
        if (this.models == null) {
            this.models = models;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (models.size() != 0)
        {
            int width = getWidth();
            int height = getHeight();

            ViewHelper helper = new ViewHelper(width, height);

            geModel m = models.get(0);
            axisInformation axis = m.getAxisInfo();

            //line rendering
            for (geModel model:models) {
                PointF lastPoint = null;
                List<PointF> points = model.getPoints();
                for (int i = 0; i < points.size(); i++) {
                    int x = (int) (width * points.get(i).x);
                    int y = (int) (height * points.get(i).y);
                    PointF currentPoint = new PointF(x, y);
                    if (lastPoint != null) {
                        lineDecorator.renderLine(canvas, currentPoint, lastPoint);
                    }
                    lastPoint = currentPoint;
                }
            }
            //node rendering
            for (geModel model:models) {
                for (PointF p : model.getPoints()) {
                    int x = (int) (width * p.x);
                    int y = (int) (height * p.y);
                    nodeDecorator.renderNode(canvas, new PointF(x, y));
                }
            }

            //graph rendering
            graphDecorator.render(canvas, axis, helper);
        }
    }
}
