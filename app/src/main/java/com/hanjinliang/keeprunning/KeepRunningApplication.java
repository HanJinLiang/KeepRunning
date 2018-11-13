package com.hanjinliang.keeprunning;

import android.app.Application;

/**
 * Created by hjl on 2018-11-13 17:22.
 * Describe:
 */

public class KeepRunningApplication extends Application {
    private static KeepRunningApplication mKeepRunningApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mKeepRunningApplication=this;
    }

    public static KeepRunningApplication getAppContext(){
        return mKeepRunningApplication;
    }
}
