package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;


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
        initData();
        return layout;
    }

    private void initData() {
        model.downData(getContext(),I.CAT_ID ,pageId, new OnCompleteListener<NewGoodsBean[]>(){
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                L.e(TAG,"result.length="+result.length);
               if (result!=null&&result.length>0){
                    ArrayList<NewGoodsBean> list= ConvertUtils.array2List(result);
                  //  mList.addAll(list);
                    mAdapter.initData(list);

                }
            }

            @Override
            public void onError(String error) {
                L.e(TAG,"error"+error);
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
}
