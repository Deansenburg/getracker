package com.dean.getracker.view.decorations.node;

import android.graphics.Canvas;
import android.graphics.Point;

import com.dean.getracker.helper.ViewHelper;

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
    public void renderNode(Canvas c, Point p, ViewHelper helper) {
        if (decoration != null)
        {
            decoration.renderNode(c, p, helper);
        }
    }
}
