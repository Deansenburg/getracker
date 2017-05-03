package com.dean.getracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by Dean on 31/03/17.
 */
public class GEDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Readings.db";

    public static class ReadColumns implements BaseColumns {
        public static final String TABLE_NAME = "readings";
        public static final String COLUMN_NAME_Date = "date";
        public static final String COLUMN_NAME_Value = "subtitle";
    }

    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + ReadColumns.TABLE_NAME + " (" +
                ReadColumns._ID + " INTEGER PRIMARY KEY," +
                ReadColumns.COLUMN_NAME_Date + " DATE," +
                ReadColumns.COLUMN_NAME_Value + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ReadColumns.TABLE_NAME;


    public GEDatabaseHelper(Context context) {
        super(context,DATABASE_NAME , null, DATABASE_VERSION);
    }

    public ArrayList<GEEntry> executeQuery(SQLiteDatabase db, String sql)
    {
        ArrayList<GEEntry> entries = new ArrayList<GEEntry>();

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()) {
            String date = "";
            int value = 0;
            for(int i=0; i<cursor.getColumnCount();i++)
            {
                String col = cursor.getColumnName(i);
                switch (col)
                {
                    case GEDatabaseHelper.ReadColumns.COLUMN_NAME_Date:
                        date = cursor.getString(i);
                        break;
                    case GEDatabaseHelper.ReadColumns.COLUMN_NAME_Value:
                        value = cursor.getInt(i);
                        break;
                }
            }
            entries.add(new GEEntry(date, value));
        }
        cursor.close();
        return entries;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
