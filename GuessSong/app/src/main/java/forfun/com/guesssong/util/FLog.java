/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.util;

import android.util.Log;

/**
 * Created by huangwei05 on 16/7/22.
 */
public class FLog {
    public static final boolean DEBUG = true;
    public static final String TAG = "Guess_Song";
    //    public static final

    public static void i(String msg) {
        i(msg, null);
    }

    public static void i(String msg, Throwable t) {
        if (DEBUG) {
            Log.i(TAG, msg, t);
        }
    }

    public static void d(String msg) {
        d(msg, null);
    }

    public static void d(String msg, Throwable t) {
        if (DEBUG) {
            Log.d(TAG, msg, t);
        }
    }

    public static void w(String msg) {
        w(msg, null);
    }

    public static void w(String msg, Throwable t) {
        if (DEBUG) {
            Log.w(TAG, msg, t);
        }
    }

    public static void e(String msg) {
        e(msg, null);
    }

    public static void e(String msg, Throwable t) {
        if (DEBUG) {
            Log.e(TAG, msg, t);
        }
    }
}
