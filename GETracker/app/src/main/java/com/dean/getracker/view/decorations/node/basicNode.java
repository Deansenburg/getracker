package com.dean.getracker.view.decorations.node;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Dean on 03/05/17.
 */
public class basicNode extends baseNodeDecoration {
    Paint nodeColor;
    public basicNode(int color, INodeDecoration dec) {
        super(dec);
        nodeColor = new Paint();
        nodeColor.setColor(color);
        nodeColor.setStyle(Paint.Style.STROKE);
        nodeColor.setStrokeWidth(10);
    }

    @Override
    public void renderNode(Canvas c, PointF p) {
        super.renderNode(c, p);
        c.drawCircle(p.x, p.y, 5, nodeColor);
    }

}
