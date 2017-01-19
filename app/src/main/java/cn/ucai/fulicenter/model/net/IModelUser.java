package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String userName, String password, OkHttpUtils.OnCompleteListener<String> listener);
    void register(Context context, String userName, String userNick,String password, OkHttpUtils.OnCompleteListener<String> listener);
    void updateNick(Context context, String username, String usernick, OkHttpUtils.OnCompleteListener<String> listener);
    void uploadAvatar(Context context, String username, File file, OkHttpUtils.OnCompleteListener<String> listener);
    void collectCount(Context context,String username, OkHttpUtils.OnCompleteListener<MessageBean> listener);
    void getCollects(Context context,String username,int pageId,int pageSize, OkHttpUtils.OnCompleteListener<CollectBean[]> listener);
}
