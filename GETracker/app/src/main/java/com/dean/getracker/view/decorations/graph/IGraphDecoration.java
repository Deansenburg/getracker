package com.dean.getracker.view.decorations.graph;

import android.graphics.Canvas;

/**
 * Created by Dean on 03/05/17.
 * should provide functionality for layer based render of the graph:
 * -this should include an axis work, background or other
 */
public interface IGraphDecoration {
    void render(Canvas c);
}
