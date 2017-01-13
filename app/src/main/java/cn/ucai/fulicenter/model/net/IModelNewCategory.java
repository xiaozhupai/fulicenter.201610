package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface IModelNewCategory {
    void downData(Context context,OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener);
    void downData(Context context, int parentId,OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener);
}
