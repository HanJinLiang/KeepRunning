package com.hanjinliang.keeprunning;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by hjl on 2018-11-13 17:38.
 * Describe:
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            HooliganActivity. startHooligan();
        } else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            HooliganActivity. killHooligan();
        }
    }
}
