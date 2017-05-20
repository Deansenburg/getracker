package com.dean.getracker.view.decorations.line;

import android.graphics.Canvas;
import android.graphics.Point;

import com.dean.getracker.helper.ViewHelper;

/**
 * Created by Dean on 03/05/17.
 * should provide functionality for layer based render of lines.
 */
public interface ILineDecoration {
    void renderLine(Canvas c, Point p1, Point p2, ViewHelper helper);
}
