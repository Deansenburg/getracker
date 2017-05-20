package com.dean.getracker.view.decorations.graph.axis;

import com.dean.getracker.helper.ViewHelper;
import com.dean.getracker.view.decorations.graph.IGraphDecoration;
import com.dean.getracker.view.decorations.graph.baseGraphDecoration;

/**
 * Created by Dean on 20/05/17.
 * defines a decoration with a subtype of axisList
 */
public abstract class Axis extends baseGraphDecoration {
    public Axis(IGraphDecoration dec) {
        super(dec);
    }

    abstract void updateAxis(ViewHelper helper);
}
