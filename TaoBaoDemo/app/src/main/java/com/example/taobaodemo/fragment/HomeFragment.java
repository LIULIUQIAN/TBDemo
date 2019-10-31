package com.example.taobaodemo.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.taobaodemo.R;
import com.example.taobaodemo.adapter.HomeCatgoryAdapter;
import com.example.taobaodemo.bean.Banner;
import com.example.taobaodemo.bean.HomeCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private View rootView;
    private SliderLayout sliderShow;
    private RecyclerView recyclerView;
    private HomeCatgoryAdapter adapter;

    Gson gson = new Gson();
    List<Banner> mBanner;
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        getBannerData();
        return rootView;
    }

    private void initView() {
        sliderShow = rootView.findViewById(R.id.slider);
        recyclerView = rootView.findViewById(R.id.recyclerview);

        initRecyclerView();

    }

    private void getBannerData() {

        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String json = response.body().string();
                Type type = new TypeToken<List<Banner>>() {
                }.getType();
                mBanner = gson.fromJson(json, type);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        initSlider();
                    }
                });
            }
        });

    }

    private void initRecyclerView() {

        List<HomeCategory> datas = new ArrayList<>(15);

        HomeCategory category = new HomeCategory("热门活动", R.drawable.img_big_1, R.drawable.img_1_small1, R.drawable.img_1_small2);
        datas.add(category);

        category = new HomeCategory("有利可图", R.drawable.img_big_4, R.drawable.img_4_small1, R.drawable.img_4_small2);
        datas.add(category);
        category = new HomeCategory("品牌街", R.drawable.img_big_2, R.drawable.img_2_small1, R.drawable.img_2_small2);
        datas.add(category);

        category = new HomeCategory("金融街 包赚翻", R.drawable.img_big_1, R.drawable.img_3_small1, R.drawable.imag_3_small2);
        datas.add(category);

        category = new HomeCategory("超值购", R.drawable.img_big_0, R.drawable.img_0_small1, R.drawable.img_0_small2);
        datas.add(category);

        adapter = new HomeCatgoryAdapter(getContext(), datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initSlider() {

        if (mBanner == null) {
            return;
        }
        for (Banner banner : mBanner) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.description(banner.getName()).image(banner.getImgUrl());
            sliderShow.addSlider(textSliderView);
        }

    }

}
