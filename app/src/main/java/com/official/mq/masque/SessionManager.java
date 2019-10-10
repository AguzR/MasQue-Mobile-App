package com.official.mq.masque;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String UNAME = "UNAME";
    public static final String PHONE = "PHONE";
    public static final String ID = "ID";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String uname, String phone, String user_id){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(UNAME, uname);
        editor.putString(PHONE, phone);
        editor.putString(ID, user_id);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent intent = new Intent(context, WelcomeActivity.class);
            context.startActivity(intent);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(UNAME, sharedPreferences.getString(UNAME, null));
        user.put(PHONE, sharedPreferences.getString(PHONE, null));
        user.put(ID, sharedPreferences.getString(ID, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, WelcomeActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }
}
