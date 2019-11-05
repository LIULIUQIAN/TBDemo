package com.example.taobaodemo.http;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseCallback<T> {

    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public BaseCallback() {
        this.mType = getSuperclassTypeParameter(getClass());
    }

    /*
     * 开始请求
     * */
    public abstract void onBeforeRequest(Request request);

    /*
     * 请求出错
     * */
    public abstract void onFailure(Request request, Exception e);

    /*
     * 请求结束
     * */
    public abstract void onResponse(Response response);

    /*
     * 请求成功
     * */
    public abstract void onSuccess(Response response, T t);

    /**
     * 请求失败
     */
    public abstract void onError(Response response, Exception e);

    /**
     * Token 验证失败。状态码401,402,403 等时调用此方法
     * @param response
     * @param code

     */
    public abstract void onTokenError(Response response, int code);

}
