package com.hanjinliang.keeprunning;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

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
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRunning();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startRunning() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                saveLog();
            }
        },0,10000);
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
