package com.hanjinliang.keeprunning.net;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by caijun on 2018/5/26.
 * 功能介绍：网络请求基类
 */
public class RetrofitFactory {
    private final static String BASE_API ="http://132.232.84.18:8080/MavenSpringmvcDemo/";

    private volatile static Retrofit retrofit;

    @NonNull
    public static Api getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {
                        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true);
                    // builder.sslSocketFactory(getSSLSocketFactory(new Buffer().writeUtf8(CER_L2018).inputStream()));
//                     //cookie持久化
//                    ClearableCookieJar cookieJar =
//                            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(L2018Application.getApp()));
//                    builder.cookieJar(cookieJar);
                    //使用自定义的Log拦截器
                    builder.addInterceptor(new LoggingInterceptor());
                    //拦截器实现持久化Cookie
                    builder.addInterceptor(new AddCookiesInterceptor()).addInterceptor(new ReceivedCookiesInterceptor());
                             //这部分
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_API)
                            .client(builder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();

                }
            }
        }
        return retrofit.create(Api.class);
    }


}
