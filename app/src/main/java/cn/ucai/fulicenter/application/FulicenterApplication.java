package cn.ucai.fulicenter.application;

import android.app.Application;

/**
 * Created by Administrator on 2017/1/10.
 */

public class FulicenterApplication extends Application {
    private static FulicenterApplication instance;
    public FulicenterApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
