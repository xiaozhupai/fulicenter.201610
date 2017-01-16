package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/16.
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.btLogin, R.id.btRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                break;
            case R.id.btLogin:
                break;
            case R.id.btRegister:
                MFGT.gotoRegister(this);
                break;
        }
    }
}
