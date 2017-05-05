package com.dean.getracker.helper;

import android.database.sqlite.SQLiteDatabase;

import com.dean.getracker.controller.geGraphController;
import com.dean.getracker.model.geEntry;

import java.util.ArrayList;

/**
 * Created by Dean on 04/05/17.
 */
public class geModelHelper {

    SQLiteDatabase db;
    geDatabaseHelper helper;

    public geModelHelper(SQLiteDatabase db, geDatabaseHelper h)
    {
        this.db = db;
        helper = h;
    }

    public geGraphController createFromDatabase()
    {
        geGraphController con = new geGraphController();
        return readFromDatabase(con);
    }

    public geGraphController readFromDatabase(geGraphController controller)
    {
        ArrayList<geEntry> entries = new ArrayList<geEntry>();

        db = helper.getReadableDatabase();
        entries = helper.executeQuery(db,
                "select * from " + geDatabaseHelper.ReadColumns.TABLE_NAME_E
                        +" order by date("+ geDatabaseHelper.ReadColumns.COLUMN_NAME_Date+")");
        controller.addModel(entries);

        entries = helper.executeQuery(db,
                "select * from " + geDatabaseHelper.ReadColumns.TABLE_NAME_G
                        +" order by date("+ geDatabaseHelper.ReadColumns.COLUMN_NAME_Date+")");
        controller.addModel(entries);
        return controller;
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
}
