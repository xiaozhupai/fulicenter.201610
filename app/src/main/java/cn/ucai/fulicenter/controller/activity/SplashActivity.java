package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FulicenterApplication;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.SharedPreferenceUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.MFGT;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG=SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /* 做成工具包
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                startActivity(new Intent(SplashActivity.this,MainActivity.class));*/
                String username= SharedPreferenceUtils.getInstance(SplashActivity.this).getUser();
                if (username!=null){
                    User user=UserDao.getInstance().getUser(username);
                    L.e(TAG,"user===="+user);
                    if (user!=null){
                        FulicenterApplication.setUser(user);
                    }
                }
                MFGT.startActivity(SplashActivity.this,MainActivity.class);

                MFGT.finish(SplashActivity.this);


            }
        },2000);
    }
}
