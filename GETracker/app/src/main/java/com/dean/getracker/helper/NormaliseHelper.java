package com.dean.getracker.helper;

/**
 * Created by Dean on 03/05/17.
 */
public class NormaliseHelper {
    public static float norm(float x, float max, float min)
    {
        return (x - min) / (max - min);
    }
    public static float norm(int x, int max, int min)
    {
        return norm((float)x, (float)max, (float)min);
    }
    public static float norm(long x, long max, long min)
    {
        return norm((float)x, (float)max, (float)min);
    }
}
