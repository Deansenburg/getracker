package com.dean.getracker.view.decorations.node;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Created by Dean on 03/05/17.
 */
public class baseNodeDecoration implements INodeDecoration {

    protected INodeDecoration decoration;
    public baseNodeDecoration(INodeDecoration dec)
    {
        decoration = dec;
    }
    @Override
    public void renderNode(Canvas c, PointF p) {
        if (decoration != null)
        {
            decoration.renderNode(c, p);
        }
    }
}
