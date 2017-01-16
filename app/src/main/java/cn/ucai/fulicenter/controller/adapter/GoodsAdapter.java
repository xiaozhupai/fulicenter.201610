package cn.ucai.fulicenter.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/11.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<NewGoodsBean> mList;
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public GoodsAdapter(Context mContext, ArrayList<NewGoodsBean> mList) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
        mList.addAll(mList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder=new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder vh= (FooterViewHolder) holder;
            vh.setFooterString(mContext.getString(getFooterString()));
        }else {
            GoodsViewHolder vh = (GoodsViewHolder) holder;
            ImageLoader.downloadImg(mContext, vh.ivGoodsThumb, mList.get(position).getGoodsThumb());
            vh.tvGoodsName.setText(mList.get(position).getGoodsName());
            vh.tvGoodsPrice.setText(mList.get(position).getCurrencyPrice());
            vh.itemView.setTag(position);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getFooterString() {

        return isMore?R.string.load_more:R.string.no_more;
    }

    public void sortGoods(final int sortBy){
        Collections.sort(mList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean leftBean, NewGoodsBean rightBean) {
                int result=0;
                switch (sortBy){
                    case I.SORT_BY_ADDTIME_ASC:
                        result= (int) (leftBean.getAddTime()-rightBean.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result= (int) (rightBean.getAddTime()-leftBean.getAddTime());
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result=getPrice(leftBean.getCurrencyPrice())-getPrice(rightBean.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result=getPrice(rightBean.getCurrencyPrice())-getPrice(leftBean.getCurrencyPrice());
                        break;
                }
                return result;
            }
        });
        notifyDataSetChanged();
    }
    int getPrice(String price){
        int p=0;
        p=Integer.valueOf(price.substring(price.indexOf("￥")+1));
        return p;
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        @OnClick(R.id.layout_goods)
        public void onItemClick(View view){
            int position= (int) view.getTag();
            MFGT.gotoGoodsDetails((Activity) mContext,mList.get(position).getGoodsId());
        }

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
