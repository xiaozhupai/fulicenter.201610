package cn.ucai.fulicenter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2017/1/11.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder  {
    @BindView(R.id.tvFooter)
     TextView tvFooter;

    public  FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
    public void setFooterString(String str){
        tvFooter.setText(str);
    }
}
