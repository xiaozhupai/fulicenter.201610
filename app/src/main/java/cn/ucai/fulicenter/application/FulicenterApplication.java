package cn.ucai.fulicenter.application;

import android.app.Application;

import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by Administrator on 2017/1/10.
 */

public class FulicenterApplication extends Application {
    private static FulicenterApplication instance;
    public static  FulicenterApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FulicenterApplication.user = user;
    }


}
