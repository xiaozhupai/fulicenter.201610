package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/11.
 */

public class ModelGoods implements IModelGoods {
    @Override
    public void downData(Context context,int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    @Override
    public void isCollect(Context context, int goodsId, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
