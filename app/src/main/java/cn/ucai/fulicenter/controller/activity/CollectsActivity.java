package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FulicenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.CollectAdapter;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;

import static android.view.View.GONE;

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
    CollectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        user= FulicenterApplication.getUser();
        if (user==null){
            finish();
        }else {
            initView();
            initData(I.ACTION_PULL_DOWN);
            setPullDownListener();
            setPullUpListener();
        }
    }

    private void initData(final int action) {
        model=new ModelUser();
        model.getCollects(this, user.getMuserName(), pageId, I.PAGE_SIZE_DEFAULT,
                new OnCompleteListener<CollectBean[]>() {
                    @Override
                    public void onSuccess(CollectBean[] result) {
                        srl.setRefreshing(false);
                        tvRefreshHint.setVisibility(GONE);
                        mAdapter.setMore(true);
                        if (result!=null&&result.length>0){
                            ArrayList<CollectBean> list= ConvertUtils.array2List(result);
                            if (action==I.ACTION_DOWNLOAD||action==I.ACTION_PULL_DOWN){
                                mAdapter.initData(list);
                            }else {
                                mAdapter.addData(list);

                                if (list.size()<I.PAGE_SIZE_DEFAULT){
                                    mAdapter.setMore(false);
                                }
                                else {
                                    mAdapter.setMore(false);
                                }
                            }
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
        mAdapter=new CollectAdapter(this,new ArrayList<CollectBean>());
        rv.setAdapter(mAdapter);
    }
    private void setPullDownListener(){
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                pageId=1;
                initData(I.ACTION_PULL_DOWN);

            }
        });
    }
    private void setPullUpListener(){
        rv.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition=gm.findLastVisibleItemPosition();
                if (newState==RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == mAdapter.getItemCount() - 1
                        &&mAdapter.isMore()){
                    pageId++;
                    initData(I.ACTION_PULL_UP);
                }
            }
        });
    }
}
