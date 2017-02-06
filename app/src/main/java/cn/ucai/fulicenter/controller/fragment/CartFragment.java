package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FulicenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelBoutiqueGoods;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelBoutiqueGoods;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.MFGT;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private static final String TAG = CartFragment.class.getSimpleName();
    @BindView(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    LinearLayoutManager gm;
    BoutiqueAdapter mAdapter;
    ArrayList<NewGoodsBean> mList = new ArrayList<>();
    IModelUser model;

    @BindView(R.id.tv_nomore)
    TextView tvNomore;

    User user;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        initView();
        model = new ModelUser();
        user= FulicenterApplication.getUser();
        initData(I.ACTION_DOWNLOAD);
        setPullDownListener();
        return layout;
    }

    private void initData(final int action) {
        if (user==null){
            MFGT.gotoLogin(getActivity());
        }else {
            model.getCart(getContext(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    srl.setRefreshing(false);
                    tvRefreshHint.setVisibility(GONE);
                    srl.setVisibility(View.VISIBLE);
                    tvNomore.setVisibility(View.GONE);
                    if (result != null && result.length > 0) {
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
//                            mAdapter.initData(list);
                        } else {
//                            mAdapter.initData(list);
                        }
                    } else {
                        srl.setVisibility(View.GONE);
                        tvNomore.setVisibility(View.VISIBLE);
                    }
                }

                        @Override
                        public void onError(String error) {
                            L.e(TAG, "error" + error);
                            srl.setRefreshing(false);
                            tvRefreshHint.setVisibility(View.GONE);
                            srl.setVisibility(View.GONE);
                            tvNomore.setVisibility(View.VISIBLE);
                            CommonUtils.showLongToast(error);
                        }
            });
        }
    }

    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_blue)
        );
        gm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);
        mAdapter = new BoutiqueAdapter(getContext(), null);
        rv.setAdapter(mAdapter);
        srl.setVisibility(View.GONE);
        tvNomore.setVisibility(View.VISIBLE);
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }

    @OnClick(R.id.tv_nomore)
    public void onClick() {
        initData(I.ACTION_DOWNLOAD);
    }
}
