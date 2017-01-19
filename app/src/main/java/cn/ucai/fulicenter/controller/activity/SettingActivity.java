package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FulicenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharedPreferenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.MFGT;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = SettingActivity.class.getSimpleName();

    @BindView(R.id.tv_user_profile_name)
    TextView tvUserProfileName;
    @BindView(R.id.tv_user_profile_nick)
    TextView tvUserProfileNick;
    @BindView(R.id.iv_user_profile_avatar)
    ImageView ivUserProfileAvatar;

    OnSetAvatarListener mOnsSetAvatarListener;

    IModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        User user = FulicenterApplication.getUser();
        L.e(TAG, "user=====>>" + user);
        if (user != null) {
            loadUserInfo(user);
        } else {
            MFGT.gotoLogin(this);
        }
    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, ivUserProfileAvatar);
        tvUserProfileName.setText(user.getMuserName());
        tvUserProfileNick.setText(user.getMuserNick());
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        FulicenterApplication.setUser(null);
        SharedPreferenceUtils.getInstance(this).removeUser();
        MFGT.gotoLogin(this);
        finish();
    }

    @OnClick(R.id.layout_user_profile_nickname)
    public void updateNick() {
        MFGT.gotoUpDataNick(this);
    }

    @OnClick(R.id.layout_user_profile_nickname)
    public void onClickUserName(){
        CommonUtils.showLongToast(R.string.username_connot_be_modify);
    }

    @OnClick(R.id.layout_user_profile_avatar)
    public void onClickAvatar(){
        mOnsSetAvatarListener=new OnSetAvatarListener(this,
                R.id.layout_user_profile_avatar,
                FulicenterApplication.getUser().getMuserName(),
                I.AVATAR_TYPE_USER_PATH);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK){
            return;
        }
        if (requestCode== I.REQUEST_CODE_NICK){
            tvUserProfileNick.setText(FulicenterApplication.getUser().getMuserNick());
        }else if (requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){
            uploadAvatar();
        }
        mOnsSetAvatarListener.setAvatar(requestCode,data,ivUserProfileAvatar);
    }

    private void uploadAvatar() {
        User user=FulicenterApplication.getUser();
        model=new ModelUser();
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_avatar));
        dialog.show();
        File file=null;
        file=new File(String.valueOf(OnSetAvatarListener.getAvatarFile(this,
                OnSetAvatarListener.getAvatarPath(this,I.AVATAR_TYPE_USER_PATH+"/"+
                user.getMuserName()+user.getMavatarSuffix()))));
        L.e(TAG,"file==="+file.getAbsolutePath());
        model.uploadAvatar(this,
                user.getMuserName(),
                file,
                new OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        L.e(TAG,"s==="+s);
                        int msg=R.string.update_user_avatar_fail;
                        if (s!=null){
                            Result result= ResultUtils.getResultFromJson(s,User.class);
                            if (result!=null){
                                if (result.isRetMsg()){
                                    msg=R.string.update_user_avatar_success;
                                }
                            }
                        }
                        CommonUtils.showLongToast(msg);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showLongToast(error);
                        dialog.dismiss();
                        L.e(TAG,"error==="+error);
                    }
                });
    }
}
