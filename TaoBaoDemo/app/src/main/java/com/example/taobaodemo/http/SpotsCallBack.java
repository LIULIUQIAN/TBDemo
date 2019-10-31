package com.example.taobaodemo.http;

import android.content.Context;

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
}
