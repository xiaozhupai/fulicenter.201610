package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.model.bean.CartBean;
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
    void getCart(Context context,String username,OkHttpUtils.OnCompleteListener<CartBean[]> listener);
//    void addCart(Context context,String username,int goodsId,int count, OkHttpUtils.OnCompleteListener<MessageBean> listener);
//    void delCart(Context context,int cartId, OkHttpUtils.OnCompleteListener<MessageBean> listener);
//    void updateCart(Context context,int cartId,int count, OkHttpUtils.OnCompleteListener<MessageBean> listener);
    void updateCart(Context context,int action,String username,int goodsId,int count,int cartId, OkHttpUtils.OnCompleteListener<MessageBean> listener);

}
