package org.timreynolds.slideshow.utility;

/**
 * Created by Tim Reynolds
 */

import android.util.Log;

public class Logger {
    static boolean loggingEnabled = true;

    public static void i(String tag, String msg) {
        if(loggingEnabled)
            Log.i(tag, msg);
    }
    public static void w(String tag, String msg) {
        if(loggingEnabled)
            Log.w(tag, msg);
    }
    public static void e(String tag, String msg) {
        if(loggingEnabled)
            Log.e(tag, msg);
    }
}
