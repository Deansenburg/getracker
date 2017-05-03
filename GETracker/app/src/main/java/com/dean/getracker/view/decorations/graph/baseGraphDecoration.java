package com.dean.getracker.view.decorations.graph;

import android.graphics.Canvas;

/**
 * Created by Dean on 03/05/17.
 */
public class baseGraphDecoration implements IGraphDecoration{

    protected IGraphDecoration decoration;

    public baseGraphDecoration(IGraphDecoration dec)
    {
        decoration = dec;
    }
    @Override
    public void render(Canvas c) {
        if (decoration != null)
        {
            decoration.render(c);
        }
    }
}
