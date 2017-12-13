package com.learnacad.cashgo.Models;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sahil Malhotra on 14-11-2017.
 */


public class SharedPrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;

    // MODE OF SHARED PREF
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "CashGo";
    private static final String IS_FIRST_TIME_LAUNCH = "isFirstTime";
    private static final String USER_ID = "firebase_UserId";
    private static final String USER_NAME = "firebase_UserName";

    public SharedPrefManager(Context context){
        this.mContext = context;
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setUserId(String userId){

        editor.putString(USER_ID,userId);
        editor.commit();
    }

    public void setUserName(String userName){

        editor.putString(USER_NAME,userName);
        editor.commit();
    }

    public String getUserId(){

        return pref.getString(USER_ID,null);
    }

    public String getUserName(){

        return pref.getString(USER_NAME,null);
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}

