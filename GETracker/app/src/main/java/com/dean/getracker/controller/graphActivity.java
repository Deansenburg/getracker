package com.dean.getracker.controller;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.dean.getracker.R;
import com.dean.getracker.binder.geControllerBinder;
import com.dean.getracker.helper.geDatabaseHelper;
import com.dean.getracker.model.axisInformation;
import com.dean.getracker.view.decorations.graph.IGraphDecoration;
import com.dean.getracker.view.decorations.graph.axis.AxisControlDecoration;
import com.dean.getracker.view.decorations.graph.axis.HorizontalAxis;
import com.dean.getracker.view.decorations.graph.axis.VerticalAxis;
import com.dean.getracker.view.decorations.line.ILineDecoration;
import com.dean.getracker.view.decorations.line.basicLine;
import com.dean.getracker.view.decorations.node.INodeDecoration;
import com.dean.getracker.view.decorations.node.basicNode;
import com.dean.getracker.view.graphView;


public class graphActivity extends ActionBarActivity implements View.OnTouchListener {

    SQLiteDatabase db;
    geDatabaseHelper helper;

    graphView view;
    geGraphController controller;

    PointF lastTouch;

    int sensitivity = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        sensitivity = sensitivity*sensitivity;

        view = (graphView)findViewById(R.id.view);

        AxisControlDecoration aControl;

        IGraphDecoration graphDecoration =
                aControl = new AxisControlDecoration(null);
        //axis
        axisInformation sharedAxis = new axisInformation(0, 0);
        aControl.add(new VerticalAxis(sharedAxis, null));
        aControl.add(new HorizontalAxis(sharedAxis, null));

        ILineDecoration lineDecoration = new basicLine(Color.BLACK, null);

        INodeDecoration nodeDecoration = new basicNode(Color.BLUE, null);

        Bundle graphBundle = getIntent().getExtras().getBundle(getString(R.string.extras_graphBundle));
        geControllerBinder controllerBinder = (geControllerBinder) graphBundle.getBinder(getString(R.string.extras_graphBinder));

        controller = controllerBinder.getService();

        view.setupRendering(graphDecoration, lineDecoration, nodeDecoration);
        view.setModel(controller.getModels());
        view.setOnTouchListener(this);
        view.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_input) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (lastTouch != null) {
            double d = Math.pow(lastTouch.x - event.getX(), 2);
            if (d > sensitivity)
            {
                float dx = lastTouch.x - event.getX();
                if (dx > 0)
                {
                    controller.move(1);
                }
                else
                {
                    controller.move(-1);
                }
                view.invalidate();
            }
        }

        lastTouch = new PointF(event.getX(), event.getY());
        return true;
    }
}
