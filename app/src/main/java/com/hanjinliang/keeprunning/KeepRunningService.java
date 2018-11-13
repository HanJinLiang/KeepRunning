package com.hanjinliang.keeprunning;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.keeprunning.entity.ResultEntity;
import com.hanjinliang.keeprunning.net.RetrofitFactory;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HanJinLiang on 2018-11-09.
 * 后台持续运行的Service
 */

public class KeepRunningService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {};
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1,new Notification());
        //绑定建立链接
        bindService(new Intent(this,GuardService.class),
                mServiceConnection, Context.BIND_IMPORTANT);
        startRunning();
        return START_STICKY;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //链接上
            Log.e("test","StepService:建立链接");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //断开链接
            startService(new Intent(KeepRunningService.this,GuardService.class));
            //重新绑定
            bindService(new Intent(KeepRunningService.this,GuardService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };



    private void startRunning() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    saveLog();
                }
            }
        }).start();
    }
    private int count=0;
    private void saveLog() {
        count++;
        RetrofitFactory.getRetrofit()
                .saveLog("活了第"+count+"次","红米6Pro")
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultEntity<String>>() {
                    @Override
                    public void accept(ResultEntity<String> stringResultEntity) throws Exception {
                        if(stringResultEntity.getCode()==200){
                            ToastUtils.showLong("活了第"+count+"次");
                        }
                    }
                });

    }
}
