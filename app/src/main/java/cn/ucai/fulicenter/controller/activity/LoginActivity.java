package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FulicenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharedPreferenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/16.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG=LoginActivity.class.getSimpleName();
    @BindView(R.id.ivBackClickArea)
    ImageView ivBackClickArea;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.btRegister)
    Button btRegister;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userPassword)
    EditText userPassword;

    IModelUser model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.ivBackClickArea, R.id.btLogin, R.id.btRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackClickArea:
                MFGT.finish(this);
                break;
            case R.id.btLogin:
                checkInput();
                break;
            case R.id.btRegister:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        String username = userName.getText().toString().trim();
        String userpassword = userPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            userName.setError(getString(R.string.user_name_connot_be_empty));
            userName.requestFocus();
        } else if (TextUtils.isEmpty(userpassword)) {
            userPassword.setError(getString(R.string.password_connot_be_empty));
            userPassword.requestFocus();
        } else {
            login(username, userpassword);
        }
    }

    private void login(String username, String userpassword) {

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        model = new ModelUser();
        model.login(this, username, userpassword, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            User user= (User) result.getRetData();
                            boolean savaUser= UserDao.getInstance().saveUser(user);
                            L.e(TAG,"savaUser=="+savaUser);
                            if (savaUser){
                                SharedPreferenceUtils.getInstance(LoginActivity.this).saveUser(user.getMuserName());
                                FulicenterApplication.setUser(user);
                            }
                            MFGT.finish(LoginActivity.this);
                        } else {
                            if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_unknow_user));
                            }
                            if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_error_password));
                            }
                        }
                    } else {
                        CommonUtils.showLongToast(getString(R.string.login_fail));
                    }

                } else {
                    CommonUtils.showLongToast(getString(R.string.login_fail));

                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
                CommonUtils.showLongToast(error);
            }
        });
    }
}
