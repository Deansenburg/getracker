package com.dean.getracker.view.decorations.line;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Created by Dean on 03/05/17.
 * should provide functionality for layer based render of lines.
 */
public interface ILineDecoration {
    void renderLine(Canvas c, PointF p1, PointF p2);
}
