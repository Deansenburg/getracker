package com.dean.getracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dean on 31/03/17.
 */
public class GEEntry {

    private String date;
    private Date parsedDate;
    private int value;
    private long longDate;

    public GEEntry(String date, int value)
    {
        this.date = date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
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
    public long getLongDate(){return  longDate;}

    @Override
    public String toString() {
        return date+" "+value;
    }
}
