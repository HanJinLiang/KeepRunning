package com.hanjinliang.keeprunning;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAllServices();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BootCompleteReceiver(),filter);
    }

    /**
     * 开启所有Service
     */
    private void startAllServices()
    {
        startService(new Intent(this, KeepRunningService.class));
        startService(new Intent(this, GuardService.class));
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP) {
            Log.d("MainActivity", "startAllServices: ");
            //版本必须大于5.0
            startService(new Intent(this, JobWakeUpService.class));
        }
    }

}
