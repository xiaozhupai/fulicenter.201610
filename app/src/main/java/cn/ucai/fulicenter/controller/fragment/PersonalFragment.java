package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FulicenterApplication;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {
    private static String TAG = PersonalFragment.class.getSimpleName();
    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    IModelUser moder;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, layout);
        initData();
        return layout;
    }

    private void initData() {
        User user = FulicenterApplication.getUser();
        L.e(TAG, "user=====>>" + user);
        if (user != null) {
            loadUserInfo(user);
            getCollectCount();
        }
        /* else {
            MFGT.gotoLogin(getActivity());
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void loadUserInfo(User user) {
        ImageLoader.downloadImg(getContext(), ivUserAvatar, user.getAvatarPath());
        tvUserName.setText(user.getMuserNick());
        loadCollectCount("0");
    }

    private void getCollectCount(){
        moder=new ModelUser();
        moder.collectCount(getContext(), FulicenterApplication.getUser().getMuserName(),
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null && result.isSuccess()){
                            loadCollectCount(result.getMsg());
                        }else {
                            loadCollectCount("0");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        L.e(TAG,"error=="+error);
                    }
                });
    }

    private void loadCollectCount(String count) {
        tvCollectCount.setText(String.valueOf(count));
    }


    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void onClick() {
        MFGT.gotoSettings(getActivity());
    }
    @OnClick(R.id.layout_center_collect)
    public void collects(){
        MFGT.gotoCollects(getActivity());
    }


}
