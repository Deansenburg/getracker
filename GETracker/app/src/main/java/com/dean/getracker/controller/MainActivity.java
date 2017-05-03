package com.dean.getracker.controller;

import android.content.ContentValues;
import android.database.Cursor;
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
import com.dean.getracker.helper.GEDatabaseHelper;
import com.dean.getracker.model.GEEntry;
import com.dean.getracker.view.GraphView;
import com.dean.getracker.view.decorations.graph.IGraphDecoration;
import com.dean.getracker.view.decorations.graph.basicBackground;
import com.dean.getracker.view.decorations.line.ILineDecoration;
import com.dean.getracker.view.decorations.line.basicLine;
import com.dean.getracker.view.decorations.node.INodeDecoration;
import com.dean.getracker.view.decorations.node.basicNode;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    SQLiteDatabase db;
    GEDatabaseHelper helper;

    Cursor cursor;

    GraphView view;
    GEGraphController model;

    PointF lastTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new GEDatabaseHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        view = (GraphView)findViewById(R.id.view);
//
//        int day = 1, month = 1, year = 2017;
//        double angle = 0;
//        while(month < 12)
//        {
//            addToDB(year+"/"+month+"/"+day, (int)(Math.sin(angle)*100) + 150);
//            angle+=0.1;
//            day+= 3;
//            if (day >= 25)
//            {
//                day = 1;
//                month += 1;
//            }
//        }

        IGraphDecoration graphDecoration = new basicBackground(Color.WHITE, null);

        ILineDecoration lineDecoration = new basicLine(Color.BLACK, null);

        INodeDecoration nodeDecoration = new basicNode(Color.BLUE, null);

        model = readFromDatabase();

        view.setupRendering(graphDecoration, lineDecoration, nodeDecoration);
        view.updateModel(model);
        view.setOnTouchListener(this);
    }

    private void addToDB(String date, int value)
    {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GEDatabaseHelper.ReadColumns.COLUMN_NAME_Date, date);
        values.put(GEDatabaseHelper.ReadColumns.COLUMN_NAME_Value, value);

        db.insert(GEDatabaseHelper.ReadColumns.TABLE_NAME, null, values);
    }

    private GEGraphController readFromDatabase()
    {
        ArrayList<GEEntry> entries = new ArrayList<GEEntry>();

        db = helper.getReadableDatabase();
        entries = helper.executeQuery(db,
                "select * from " + GEDatabaseHelper.ReadColumns.TABLE_NAME
                        +" order by date("+ GEDatabaseHelper.ReadColumns.COLUMN_NAME_Date+")");
        GEGraphController model = new GEGraphController();
        model.setModel(entries);
        return model;
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
            double d = Math.sqrt(Math.pow(lastTouch.x - event.getX(), 2));
            if (d > 25)
            {
                float dx = lastTouch.x - event.getX();
                if (dx > 0)
                {
                    model.move(1);
                }
                else
                {
                    model.move(-1);
                }
                view.invalidate();
            }
        }

        lastTouch = new PointF(event.getX(), event.getY());
        return true;
    }
}
