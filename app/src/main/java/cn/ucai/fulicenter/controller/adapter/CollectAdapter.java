package cn.ucai.fulicenter.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FulicenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelGoods;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelGoods;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/11.
 */

public class CollectAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CollectBean> mList;
    boolean isMore;
    IModelGoods model;
    User user;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public CollectAdapter(Context context, ArrayList<CollectBean> list) {
        this.mContext = context;
        this.mList = new ArrayList<>();
        mList.addAll(list);
        model=new ModelGoods();
        user= FulicenterApplication.getUser();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new CollectViewHolder(View.inflate(mContext, R.layout.item_collect, null));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) holder;
            vh.setFooterString(mContext.getString(getFooterString()));
        } else {
            CollectViewHolder vh = (CollectViewHolder) holder;
            vh.bind(position);
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

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getFooterString() {

        return isMore ? R.string.load_more : R.string.no_more;
    }


    int getPrice(String price) {
        int p = 0;
        p = Integer.valueOf(price.substring(price.indexOf("￥") + 1));
        return p;
    }

    class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.iv_collect_del)
        ImageView ivCollectDel;
        @BindView(R.id.layout_goods)
        RelativeLayout layoutGoods;

        int itemPosition;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final int position) {
            ImageLoader.downloadImg(mContext, ivGoodsThumb, mList.get(position).getGoodsThumb());
            tvGoodsName.setText(mList.get(position).getGoodsName());
            itemPosition=position;

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.gotoGoodsDetails((Activity) mContext, mList.get(position).getGoodsId());
                }
            });*/

        }
        @OnClick(R.id.layout_goods)
        public void details(){
            MFGT.gotoGoodsDetails((Activity) mContext,mList.get(itemPosition).getGoodsId());
        }
        @OnClick(R.id.iv_collect_del)
        public void delCollect(){
            model.setCollect((Activity) mContext, mList.get(itemPosition).getGoodsId(),
                    user.getMuserName(), I.ACTION_DELETE_COLLECT, new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result!=null && result.isSuccess()){
                                mList.remove(itemPosition);
                                notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
        }
    }
}
