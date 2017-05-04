package com.dean.getracker.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dean on 31/03/17.
 */
public class geEntry {

    private String date;
    private Date parsedDate;
    private int value;
    private long longDate;

    public geEntry(String date, int value)
    {
        this.date = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.parsedDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        longDate = parsedDate.getTime();
        this.value = value;
    }

    public String StringDate()
    {
        return date;
    }
    public Date Date(){return parsedDate;}
    public Integer Value()
    {
        return value;
    }
    public long LongDate(){return  longDate;}

    @Override
    public String toString() {
        return date+" "+value;
    }
}
