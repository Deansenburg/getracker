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
import com.dean.getracker.helper.geDatabaseHelper;
import com.dean.getracker.helper.geModelHelper;
import com.dean.getracker.view.decorations.graph.IGraphDecoration;
import com.dean.getracker.view.decorations.graph.basicBackground;
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

        helper = new geDatabaseHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        view = (graphView)findViewById(R.id.view);

        IGraphDecoration graphDecoration = new basicBackground(Color.WHITE, null);

        ILineDecoration lineDecoration = new basicLine(Color.BLACK, null);

        INodeDecoration nodeDecoration = new basicNode(Color.BLUE, null);

//        createData(geDatabaseHelper.ReadColumns.TABLE_NAME_E, 0);
//        createData(geDatabaseHelper.ReadColumns.TABLE_NAME_G, Math.PI/2);

        geModelHelper h = new geModelHelper(db, helper);
        controller = h.createFromDatabase();

        view.setupRendering(graphDecoration, lineDecoration, nodeDecoration);
        view.setModel(controller.getModels());
        view.setOnTouchListener(this);
        view.invalidate();
    }

    private void createData(String table, double angle)
    {
        int day = 1, month = 1, year = 2017;

        geModelHelper h = new geModelHelper(db, helper);
        while(month < 12)
        {
            helper.addToDB(db, table,
                    year + "/" + month + "/" + day,
                    (int) (Math.sin(angle) * 100) + 150);
            angle+=0.1;
            day+= 3;
            if (day >= 25)
            {
                day = 1;
                month += 1;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
