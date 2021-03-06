package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModelNewGoods;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    private static final  String TAG=NewGoodsFragment.class.getSimpleName();
    @BindView(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    GridLayoutManager gm;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList=new ArrayList<>();
    IModelNewGoods model;
    int pageId=1;

    public NewGoodsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        initView();
        model=new ModelNewGoods();
        initData(I.ACTION_DOWNLOAD);
        setPullDownListener();
        setPullUpListener();
        return layout;
    }

    private void initData(final int action) {
        int catId=getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID,I.CAT_ID);
        model.downData(getContext(),catId ,pageId, new OnCompleteListener<NewGoodsBean[]>(){
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                L.e(TAG,"result.length="+result.length);
                srl.setRefreshing(false);
                tvRefreshHint.setVisibility(GONE);
                mAdapter.setMore(true);
               if (result!=null&&result.length>0){
                    ArrayList<NewGoodsBean> list= ConvertUtils.array2List(result);
                  //  mList.addAll(list);
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
                L.e(TAG,"error"+error);
                srl.setRefreshing(false);
                tvRefreshHint.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showLongToast(error);
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
        gm=new GridLayoutManager(getContext(), I.COLUM_NUM);
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);
        mAdapter=new GoodsAdapter(getContext(),mList);
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
    public void sortGoods(int sortBy){
        mAdapter.sortGoods(sortBy);
    }
}
