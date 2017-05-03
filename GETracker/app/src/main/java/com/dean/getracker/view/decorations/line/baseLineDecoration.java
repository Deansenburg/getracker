package com.dean.getracker.view.decorations.line;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Created by Dean on 03/05/17.
 */
public class baseLineDecoration implements ILineDecoration{
    protected ILineDecoration decoration;

    public baseLineDecoration(ILineDecoration dec)
    {
        decoration = dec;
    }
    @Override
    public void renderLine(Canvas c, PointF p1, PointF p2) {
        if (decoration != null)
        {
            decoration.renderLine(c, p1, p2);
        }
    }
}
