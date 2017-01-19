package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FulicenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;

public class CollectsActivity extends AppCompatActivity {

    private static final String TAG =CollectsActivity.class.getSimpleName() ;
    @BindView(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    IModelUser model;
    User user;
    int pageId=1;

    GridLayoutManager gm;
    GoodsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        user= FulicenterApplication.getUser();
        if (user==null){
            finish();
        }else {
            initData();
        }
    }

    private void initData() {
        model=new ModelUser();
        model.getCollects(this, user.getMuserName(), pageId, I.PAGE_SIZE_DEFAULT,
                new OnCompleteListener<CollectBean[]>() {
                    @Override
                    public void onSuccess(CollectBean[] result) {
                        if (result==null){

                        }else {
                            ArrayList<CollectBean>list=ConvertUtils.array2List(result);
                            L.e(TAG,"list====>"+list.size());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        L.e(TAG,"error==>"+error);
                    }
                });
    }
    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_blue)
        );
        gm=new GridLayoutManager(this, I.COLUM_NUM);
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);
        mAdapter=new GoodsAdapter(this,new ArrayList<NewGoodsBean>());
        rv.setAdapter(mAdapter);
    }
}
