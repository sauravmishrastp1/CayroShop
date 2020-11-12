package com.codetrex.cayroshop.sharedprefrence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.codetrex.cayroshop.model.LoginUser;
import com.codetrex.cayroshop.ui.LoginActivity;

public class SharedPrefManager {
    public static String SHARED_PREF_NAME ="cayrosharedprefrence";
    public static String CLIENT_ID =  "keyclientid";
    public static String ACCES_CODE = "access_codekey";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void getlogin(LoginUser loginUser){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CLIENT_ID,loginUser.getClientid());
        editor.putString(ACCES_CODE,loginUser.getAccescode());
        editor.commit();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ACCES_CODE, null) != null;
    }

    public LoginUser getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new LoginUser(
                sharedPreferences.getString(CLIENT_ID,null),
                sharedPreferences.getString(ACCES_CODE, null));

    }
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(mCtx, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }
}
