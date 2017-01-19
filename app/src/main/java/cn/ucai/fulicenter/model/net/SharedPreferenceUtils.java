package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * Created by Administrator on 2017/1/16.
 */

public class SharedPreferenceUtils {
    private static final String SHARE_PREFRENCE_NAME="cn.user.fulicenter_user";
    private static final String SHARE_PREFRENCE_NAME_USERNAME="cn.user.fulicenter_user_username";
    private static final String SHARE_NAME = "saveUserInfo";
    private static SharedPreferenceUtils instance;
    private static SharedPreferences sp;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;
    public static final String SHARE_KEY_USER_NAME="share_key_user_name";

    public SharedPreferenceUtils(Context context){
        //sp=context.getSharedPreferences(SHARE_PREFRENCE_NAME,Context.MODE_PRIVATE);
        mSharedPreferences=context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
    }

    public static SharedPreferenceUtils getInstance(Context context){
        if (instance==null){
            instance=new SharedPreferenceUtils(context);
        }
        return instance;
    }
    public  void saveUser(String username){
        //sp.edit().putString(SHARE_PREFRENCE_NAME_USERNAME,username).commit();

        mEditor.putString(SHARE_KEY_USER_NAME,username);
        mEditor.commit();
    }
    public  String getUser(){
       // return sp.getString(SHARE_PREFRENCE_NAME_USERNAME,null);

        return mSharedPreferences.getString(SHARE_KEY_USER_NAME,null);
    }

    public void removeUser() {
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }
}
