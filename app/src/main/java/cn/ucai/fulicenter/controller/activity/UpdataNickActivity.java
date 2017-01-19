package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

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
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.ResultUtils;

public class UpdataNickActivity extends AppCompatActivity {
    private static final String TAG = UpdataNickActivity.class.getSimpleName();
    @BindView(R.id.et_update_user_name)
    EditText etUpdateUserName;
    User user;
    IModelUser model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_nick);
        ButterKnife.bind(this);
    }

    private void initData() {
        user = FulicenterApplication.getUser();
        L.e(TAG,"=====>user===>"+user);
        if (user == null) {
            finish();
        } else {
            etUpdateUserName.setText(user.getMuserNick());
        }
    }

    @OnClick(R.id.btn_save)
    public void checkInput() {
       // L.e(TAG,"userNick=====>>>>"+user.getMuserNick());
        String nick = etUpdateUserName.getText().toString().trim();
        if (TextUtils.isEmpty(nick)) {
            CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
        } else if (nick.equals(user.getMuserNick())) {
            CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
        } else {
            updateNick(nick);
        }
    }

    private void updateNick(final String nick) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_nick));
        dialog.show();
        model = new ModelUser();
        model.updateNick(this, user.getMuserName(), nick, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                int msg=R.string.update_fail;
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            msg = R.string.update_user_nick_success;
                            User user= (User) result.getRetData();
                            L.e(TAG,"update success,user===>"+user);
                            saveNewUser(user);
                            finish();
                            setResult(RESULT_OK);
                        } else {
                            if (result.getRetCode() == I.MSG_USER_SAME_NICK ||
                                    result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                msg = R.string.update_nick_fail_unmodify;
                            } else {
                                msg = R.string.update_fail;
                            }
                        }
                    }
                }
                CommonUtils.showLongToast(msg);
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.update_fail);
                dialog.dismiss();
                L.e(TAG, "error===>" + error);
            }
        });
    }

    private void saveNewUser(User user) {
        FulicenterApplication.setUser(user);
        UserDao.getInstance().saveUser(user);
    }
}
