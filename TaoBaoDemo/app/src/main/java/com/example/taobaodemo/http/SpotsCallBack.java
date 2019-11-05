package com.example.taobaodemo.http;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.taobaodemo.LoginActivity;
import com.example.taobaodemo.application.TBApplication;

import okhttp3.Request;
import okhttp3.Response;

public abstract class SpotsCallBack<T> extends BaseCallback<T> {

    private Context mContext;

    public SpotsCallBack(Context context) {
        mContext = context;
    }

    @Override
    public void onBeforeRequest(Request request) {
        System.out.println("开始加载数据");
    }

    @Override
    public void onFailure(Request request, Exception e) {
        System.out.println("加载出错");
    }

    @Override
    public void onResponse(Response response) {
        System.out.println("加载完成");
    }

    @Override
    public void onError(Response response, Exception e) {
        System.out.println("加载完成 出错了");
    }

    @Override
    public void onTokenError(Response response, int code) {

        Toast.makeText(mContext,"登录失效",Toast.LENGTH_SHORT).show();
        TBApplication.getInstance().clearUser();
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);

    }
}
