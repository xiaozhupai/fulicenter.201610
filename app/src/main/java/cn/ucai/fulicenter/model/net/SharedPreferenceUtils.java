package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/16.
 */

public class SharedPreferenceUtils {
    private static final String SHARE_PREFRENCE_NAME="cn.user.fulicenter_user";
    private static final String SHARE_PREFRENCE_NAME_USERNAME="cn.user.fulicenter_user_username";
    private static SharedPreferenceUtils instance;
    private static SharedPreferences sp;

    public SharedPreferenceUtils(Context context){
        sp=context.getSharedPreferences(SHARE_PREFRENCE_NAME,Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtils getInstance(Context context){
        if (instance==null){
            instance=new SharedPreferenceUtils(context);
        }
        return instance;
    }
    public  void saveUser(String username){
        sp.edit().putString(SHARE_PREFRENCE_NAME_USERNAME,username).commit();
    }
    public  String getUser(){
        return sp.getString(SHARE_PREFRENCE_NAME_USERNAME,null);
    }
}
