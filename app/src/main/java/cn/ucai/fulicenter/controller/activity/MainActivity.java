package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FulicenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.BoutiqueGoodsFragment;
import cn.ucai.fulicenter.controller.fragment.CategoryFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.controller.fragment.PersonalFragment;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.MFGT;

public class MainActivity extends AppCompatActivity {
    private static String TAG=MainActivity.class.getSimpleName();
    int index, currentIndex;
    RadioButton[] rbs = new RadioButton[5];


    @BindView(R.id.rbNewGoods)
    RadioButton rbNewGoods;
    @BindView(R.id.rbBoutique)
    RadioButton rbBoutique;
    @BindView(R.id.rbCategory)
    RadioButton rbCategory;
    @BindView(R.id.rbCart)
    RadioButton rbCart;
    @BindView(R.id.rbPersonalCenter)
    RadioButton rbPersonalCenter;

    Fragment[] mFragment=new Fragment[5];
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueGoodsFragment mBoutiqueGoodsFragment;
    CategoryFragment mCategoryFragment;
    PersonalFragment mPersonalFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        rbs[0] = rbNewGoods;
        rbs[1] = rbBoutique;
        rbs[2] = rbCategory;
        rbs[3] = rbCart;
        rbs[4] = rbPersonalCenter;

        mNewGoodsFragment=new NewGoodsFragment();
        mBoutiqueGoodsFragment=new BoutiqueGoodsFragment();
        mCategoryFragment=new CategoryFragment();
        mPersonalFragment=new PersonalFragment();

        mFragment[0]=mNewGoodsFragment;
        mFragment[1]=mBoutiqueGoodsFragment;
        mFragment[2]=mCategoryFragment;
        mFragment[4]=mPersonalFragment;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,mNewGoodsFragment)
                .add(R.id.fragment_container,mBoutiqueGoodsFragment)
                .add(R.id.fragment_container,mCategoryFragment)
                .show(mNewGoodsFragment)
                .hide(mBoutiqueGoodsFragment)
                .hide(mCategoryFragment)
                .commit();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.rbNewGoods:
                index = 0;
                break;
            case R.id.rbBoutique:
                index = 1;
                break;
            case R.id.rbCategory:
                index = 2;
                break;
            case R.id.rbCart:
                index = 3;
                break;
            case R.id.rbPersonalCenter:
                if (FulicenterApplication.getUser()==null){
                    MFGT.gotoLogin(this);
                }else{
                    index = 4;
                }
                break;
        }
        setFragment();
        if (index != currentIndex) {
            setRadioStatus();
        }

    }

    private void setFragment() {
        L.e(TAG,"setFragment,index====="+index);
        L.e(TAG,"setFragment,currentindex====="+currentIndex);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.hide(mFragment[currentIndex]);
        if (!mFragment[index].isAdded()){
            ft.add(R.id.fragment_container,mFragment[index]);
        }
            ft.show(mFragment[index]).commit();


       /* getSupportFragmentManager().beginTransaction()
                //.add(R.id.fragment_container,mFragment[index])
                .show(mFragment[index])
                .hide(mFragment[currentIndex])
                .commit();*/
    }

    private void setRadioStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;
    }
    @Override
    public void onResume() {
        super.onResume();
        L.e(TAG,"onResume,index====>"+index+
                "currentindex====="+currentIndex+
                ",user===>"+FulicenterApplication.getInstance());
        if (index==4 && FulicenterApplication.getUser()==null){
            index=0;
        }
        setFragment();
        setRadioStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.e(TAG,"onActivityResult,resultCode===>"+requestCode+",resultCode"+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode== I.REQUEST_CODE_LOGIN){
            index=4;
            setFragment();
            setRadioStatus();
        }
    }
}
