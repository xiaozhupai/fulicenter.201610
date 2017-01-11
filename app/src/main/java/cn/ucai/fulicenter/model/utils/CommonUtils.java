package cn.ucai.fulicenter.model.utils;

import android.widget.Toast;

import cn.ucai.fulicenter.application.FulicenterApplication;

public class CommonUtils {
    public static void showLongToast(String msg){
        Toast.makeText(FulicenterApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(String msg){
        Toast.makeText(FulicenterApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(int rId){
        showLongToast(FulicenterApplication.getInstance().getString(rId));
    }
    public static void showShortToast(int rId){
        showShortToast(FulicenterApplication.getInstance().getString(rId));
    }
}
