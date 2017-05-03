package com.dean.getracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    SQLiteDatabase db;
    GEDatabaseHelper helper;

    Cursor cursor;

    GraphView view;
    GEGraphModel model;

    PointF lastTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new GEDatabaseHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        view = (GraphView)findViewById(R.id.view);

//        addToDB("01/01/2017", 10);
//        addToDB("01/02/2017", 20);
//        addToDB("01/03/2017", 30);
//        addToDB("01/04/2017", 40);

//        int day = 1, month = 1, year = 2017;
//        double angle = 0;
//        while(month < 12)
//        {
//            addToDB(year+"/"+month+"/"+day, (int)(Math.sin(angle)*100));
//            angle+=0.01;
//            day+= 5;
//            if (day >= 25)
//            {
//                day = 1;
//                month += 1;
//            }
//        }

        model = readFromDatabase();
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

    private GEGraphModel readFromDatabase()
    {
        ArrayList<GEEntry> entries = new ArrayList<GEEntry>();

        db = helper.getReadableDatabase();
        entries = helper.executeQuery(db,
                "select * from " + GEDatabaseHelper.ReadColumns.TABLE_NAME
                        +" order by date("+ GEDatabaseHelper.ReadColumns.COLUMN_NAME_Date+")");
        GEGraphModel model = new GEGraphModel();
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
//                    view.move(1);
                    Log.d("", "adding");
                }
                else
                {
                    model.move(-1);
//                    view.move(-1);
                    Log.d("", "subbing");
                }
                view.invalidate();
            }
        }

        lastTouch = new PointF(event.getX(), event.getY());
        return true;
    }
}
