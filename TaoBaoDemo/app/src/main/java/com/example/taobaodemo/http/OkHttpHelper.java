package com.example.taobaodemo.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHelper {

    public static final String TAG = "OkHttpHelper";

    private static OkHttpHelper mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;
    private Handler mHandler;

    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        mHttpClient = builder.build();

        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());

    }

    public static OkHttpHelper getInstance() {
        return mInstance;
    }

    public void get(String url, BaseCallback callback) {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        doRequest(request,callback);
    }

    public void post(String url, Map<String, Object> param, BaseCallback callback) {

        Request request = buildRequest(url, param, HttpMethodType.POST);
        doRequest(request,callback);

    }

    private void doRequest(final Request request, final BaseCallback callback) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onBeforeRequest(request);
            }
        });

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull final Call call, @NotNull final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(call.request(), e);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                final String resultStr = response.body().string();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(response);

                        if (response.isSuccessful()) {

                            if (callback.mType == String.class) {
                                callback.onSuccess(response, resultStr);
                            } else {
                                try {
                                    Object object = mGson.fromJson(resultStr, callback.mType);
                                    callback.onSuccess(response, object);
                                } catch (Exception e) {
                                    callback.onError(response, e);
                                }
                            }

                        } else {
                            callback.onError(response, null);
                        }
                    }
                });
            }
        });
    }

    private Request buildRequest(String url, Map<String, Object> param, HttpMethodType methodType) {

        Request.Builder builder = new Request.Builder().url(url);
        if (methodType == HttpMethodType.POST) {
            builder.post(builderFormData(param));
        } else if (methodType == HttpMethodType.GET) {
            builder.get();
        }
        return builder.build();
    }

    private RequestBody builderFormData(Map<String, Object> param) {

        FormBody.Builder body = new FormBody.Builder();

        if (param != null) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                body.add(entry.getKey(), entry.getValue().toString());
            }
        }
        return body.build();
    }

    enum HttpMethodType {
        GET,
        POST
    }
}
