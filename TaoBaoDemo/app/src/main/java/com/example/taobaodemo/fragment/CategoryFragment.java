package com.example.taobaodemo.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.taobaodemo.Contants;
import com.example.taobaodemo.R;
import com.example.taobaodemo.adapter.CategoryAdapter;
import com.example.taobaodemo.bean.category.Category;
import com.example.taobaodemo.bean.home.Banner;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;

import java.util.Collections;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    private RecyclerView recyclerview_category;
    private SliderLayout slider_category;

    private CategoryAdapter categoryAdapter;
    private View rootView;
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    List<Banner> mBannerData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category, container, false);
        initView();
        initData();
        return rootView;
    }

    private void initData() {
        getCategoryData();
        getBannerData();
    }

    private void getBannerData() {
        httpHelper.get(Contants.API.BANNER_HOME, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBannerData = banners;
                initSlider();
            }
        });
    }

    private void getCategoryData(){
        httpHelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Category> categories) {
                categoryAdapter.clear();
                categoryAdapter.addData(categories);
            }
        });

    }

    private void initView() {
        recyclerview_category = rootView.findViewById(R.id.recyclerview_category);
        slider_category = rootView.findViewById(R.id.slider_category);

        recyclerview_category.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_category.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        categoryAdapter = new CategoryAdapter(getContext(),null);
        recyclerview_category.setAdapter(categoryAdapter);

    }
    private void initSlider() {

        if (mBannerData == null) {
            return;
        }
        for (Banner banner : mBannerData) {
            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            textSliderView.image(banner.getImgUrl());
            slider_category.addSlider(textSliderView);
        }

    }

}
