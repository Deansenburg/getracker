package com.dean.getracker.view.decorations.node;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Created by Dean on 03/05/17.
 * should provide functionality for layer based render of nodes.
 */
public interface INodeDecoration {
    void renderNode(Canvas c, PointF p);
}
