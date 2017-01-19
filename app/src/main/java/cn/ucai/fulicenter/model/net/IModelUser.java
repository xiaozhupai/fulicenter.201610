package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String userName, String password, OkHttpUtils.OnCompleteListener<String> listener);
    void register(Context context, String userName, String userNick,String password, OkHttpUtils.OnCompleteListener<String> listener);
    void updateNick(Context context, String username, String usernick, OkHttpUtils.OnCompleteListener<String> listener);
}
