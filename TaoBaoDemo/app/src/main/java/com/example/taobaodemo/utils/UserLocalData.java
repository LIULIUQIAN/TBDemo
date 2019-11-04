package com.example.taobaodemo.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.taobaodemo.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserLocalData {

    public static final String USER_JSON="user_json";
    public static final String TOKEN="user_token";

    public static void putUser(Context context, User user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        PreferencesUtils.putString(context,USER_JSON,json);
    }

    public static void putToken(Context context, String token){
        PreferencesUtils.putString(context,TOKEN,token);
    }

    public static User getUser(Context context){
        String json = PreferencesUtils.getString(context,USER_JSON);
        if (TextUtils.isEmpty(json)){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json,User.class);
    }

    public static String getToken(Context context){
        return PreferencesUtils.getString(context,TOKEN);
    }

    public static void clearUser(Context context){

        PreferencesUtils.putString(context, USER_JSON,"");
    }

    public static void clearToken(Context context){

        PreferencesUtils.putString(context, TOKEN,"");
    }

}
