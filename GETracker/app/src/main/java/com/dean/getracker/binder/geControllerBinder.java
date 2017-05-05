package com.dean.getracker.binder;

import android.os.Binder;

import com.dean.getracker.controller.geGraphController;

/**
 * Created by Dean on 05/05/17.
 */
public class geControllerBinder extends Binder {

    private geGraphController controller;

    public geControllerBinder(geGraphController con)
    {
        controller = con;
    }

    public geGraphController getService()
    {
        return controller;
    }

}
