package com.example.taobaodemo.application;

import android.app.Application;

import com.example.taobaodemo.bean.User;
import com.example.taobaodemo.utils.UserLocalData;

public class TBApplication extends Application {

    private User user;
    private static TBApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initUser();
    }

    public static TBApplication getInstance(){
        return mInstance;
    }

    private void initUser(){
        this.user = UserLocalData.getUser(this);
    }

    public User getUser(){
        return user;
    }

    public void putUser(User user,String token){
        this.user = user;
        UserLocalData.putUser(this,user);
        UserLocalData.putToken(this,token);
    }

    public void clearUser(){
        this.user = null;
        UserLocalData.clearUser(this);
        UserLocalData.clearToken(this);
    }

    public String getToken(){
        return UserLocalData.getToken(this);
    }
}
