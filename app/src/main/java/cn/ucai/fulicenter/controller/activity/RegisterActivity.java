package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/16.
 */
public class RegisterActivity extends AppCompatActivity {
    private static final String TAG=LoginActivity.class.getSimpleName();
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userNick)
    EditText userNick;
    @BindView(R.id.userPassword)
    EditText userPassword;
    @BindView(R.id.confirmPassword)
    EditText confirmPassword;

    IModelUser model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backClickArea, R.id.btRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backClickArea:
                MFGT.finish(this);
                break;
            case R.id.btRegister:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        String username =userName.getText().toString().trim();
        String usernick=userNick.getText().toString().trim();
        String password=userPassword.getText().toString().trim();
        String confirmpassword=confirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            userName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            userName.requestFocus();
        }else if (!username.matches("[a-zA-Z]\\w{5,15}")){
            userName.setError(getResources().getString(R.string.illegal_user_name));
            userName.requestFocus();

        }else if (TextUtils.isEmpty(usernick)){
            userNick.setError(getResources().getString(R.string.nick_name_connot_be_empty));
            userNick.requestFocus();

        }else if (TextUtils.isEmpty(password)){
            userPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            userPassword.requestFocus();

        }else if (TextUtils.isEmpty(confirmpassword)){
            confirmPassword.setError(getResources().getString(R.string.confirm_password_connot_be_empty));
            confirmPassword.requestFocus();
        }else if (!password.equals(confirmpassword)){
            confirmPassword.setError(getResources().getString(R.string.two_input_password));
            confirmPassword.requestFocus();
        }
        else {
            register(username,usernick,password);
        }
    }

    private void register(String username, String usernick, String password) {
        model=new ModelUser();
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage(getString(R.string.registering));
        model.register(this, username, usernick, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s!=null){
                    Result result= ResultUtils.getResultFromJson(s,Result.class);
                    Log.e(TAG,"result"+result);
                    if (result!=null){
                        if (result.isRetMsg()){
                            CommonUtils.showLongToast(R.string.register_success);
                            MFGT.finish(RegisterActivity.this);
                        }else {
                            CommonUtils.showLongToast(R.string.register_fail_exists);
                        }
                    }else {
                        CommonUtils.showLongToast(R.string.register_fail);
                    }
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
