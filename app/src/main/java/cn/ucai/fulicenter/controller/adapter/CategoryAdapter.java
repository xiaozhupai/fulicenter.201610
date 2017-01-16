package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/13.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupBean;
    ArrayList<ArrayList<CategoryChildBean>> mChildBean;

    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> groupBean, ArrayList<ArrayList<CategoryChildBean>> childBean) {
        this.mContext = mContext;
        this.mGroupBean = new ArrayList<>();
        mGroupBean.addAll(groupBean);
        this.mChildBean = new ArrayList<>();
        mChildBean.addAll(childBean);
    }


    @Override
    public int getGroupCount() {
        return mGroupBean != null ? mGroupBean.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildBean != null && mChildBean.get(groupPosition) != null ?
                mChildBean.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupBean.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if (mChildBean != null && mChildBean.get(groupPosition) != null) {
            return mChildBean.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        GroupViewHolder vh;
        if (view==null){
            view=View.inflate(mContext,R.layout.item_category_group,null);
            vh=new GroupViewHolder(view);
            view.setTag(vh);
        }else {
            vh= (GroupViewHolder) view.getTag();
        }
        ImageLoader.downloadImg(mContext,vh.ivGoodThumb,mGroupBean.get(groupPosition).getImageUrl());
        vh.tvGroupName.setText(mGroupBean.get(groupPosition).getName());
        vh.ivIndicator.setImageResource(isExpanded?R.mipmap.expand_off:R.mipmap.expand_on);
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder vh=null;
        if (convertView==null){
            convertView=View.inflate(mContext,R.layout.item_category_child,null);
            vh=new ChildViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh= (ChildViewHolder) convertView.getTag();
        }
        ImageLoader.downloadImg(mContext,vh.ivCategoryChildThumb,getChild(groupPosition,childPosition).getImageUrl());
        vh.tvCategoryChildName.setText(getChild(groupPosition,childPosition).getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoCategoryChild(mContext,mChildBean.get(groupPosition).get(childPosition).getId());
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupBean,
                         ArrayList<ArrayList<CategoryChildBean>> childBean) {
        mGroupBean.clear();
        mGroupBean.addAll(groupBean);
        mChildBean.clear();
        mChildBean.addAll(childBean);
        notifyDataSetChanged();

    }


    static class GroupViewHolder {
        @BindView(R.id.iv_good_thumb)
        ImageView ivGoodThumb;
        @BindView(R.id.tv_group_name)
        TextView tvGroupName;
        @BindView(R.id.iv_indicator)
        ImageView ivIndicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.iv_category_child_thumb)
        ImageView ivCategoryChildThumb;
        @BindView(R.id.tv_category_child_name)
        TextView tvCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout layoutCategoryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
