package com.cn.runzhong.joke;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by CN.
 */

public class JokeManager {
    private static JokeManager INSTANCE;
    private static final long TIMEOUT = 8000;
    private OkHttpClient okHttpClient;

    public void init(Application application){
        if(!Fresco.hasBeenInitialized()){
            Fresco.initialize(application);
        }
    }
    public static JokeManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JokeManager();
        }
        return INSTANCE;
    }

    public OkHttpClient getOkInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder().readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                            .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS).build();
                }
            }
        }
        return okHttpClient;
    }

    public void cancelAll() {
        if (okHttpClient != null) {
            for (Call call : okHttpClient.dispatcher().queuedCalls()) {
                call.cancel();
            }
            for (Call call : okHttpClient.dispatcher().runningCalls()) {
                call.cancel();
            }
        }
    }

    /**
     * 通过tag撤销请求
     */
    public void cancelByTag(String tag) {
        if (tag == null || okHttpClient == null)
            return;
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
