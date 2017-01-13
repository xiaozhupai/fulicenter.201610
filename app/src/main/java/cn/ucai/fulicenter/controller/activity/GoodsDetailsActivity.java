package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.AlbumsBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.net.IModelGoods;
import cn.ucai.fulicenter.model.net.ModelGoods;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.MFGT;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {
    private static final String TAG=GoodsDetailsActivity.class.getSimpleName();
    int goodsId = 0;
    IModelGoods model;
    @BindView(R.id.tv_good_name_english)
    TextView tvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView tvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;
    @BindView(R.id.wv_good_brief)
    WebView wvGoodBrief;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            MFGT.finish(this);
        } else {
            initData();
        }
    }

    private void initData() {
        model = new ModelGoods();
        model.downData(this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result!=null){
                    showGoodsDetail(result);
                }else {
                    MFGT.finish(GoodsDetailsActivity.this);
                }
            }

            @Override
            public void onError(String error) {
                L.e(TAG,"error"+error);
            }
        });
    }

    private void showGoodsDetail(GoodsDetailsBean goods) {
        tvGoodName.setText(goods.getGoodsName());
        tvGoodNameEnglish.setText(goods.getGoodsEnglishName());
        tvGoodPriceCurrent.setText(goods.getCurrencyPrice());
        tvGoodPriceShop.setText(goods.getShopPrice());
        salv.startPlayLoop(indicator,getAlbumUrl(goods),getAlbumCount(goods));
        wvGoodBrief.loadDataWithBaseURL(null,goods.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);

    }

    private int getAlbumCount(GoodsDetailsBean goods) {
        if (goods!=null&& goods.getProperties()!=null && goods.getProperties().length>0){
            return goods.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumUrl(GoodsDetailsBean goods) {
        if (goods!=null&&goods.getProperties()!=null&& goods.getProperties().length>0){
            AlbumsBean[] albums= goods.getProperties()[0].getAlbums();
            if (albums!=null&&albums.length>0){
                String [] urls=new String[albums.length];
                for (int i=0;i<albums.length;i++){
                    urls[i]=albums[i].getImgUrl();
                }
                return urls;
            }
        }
        return new String[0];
    }

    @OnClick(R.id.backOnClickArea)
    public void onClick() {
        MFGT.finish(this);
    }
}
