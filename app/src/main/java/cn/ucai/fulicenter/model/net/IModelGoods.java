package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface IModelGoods {
    void downData(Context context,int goodsId ,OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener);
    void isCollect(Context context, int goodsId, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener);
}
