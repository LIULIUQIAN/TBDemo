package com.example.taobaodemo.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.taobaodemo.adapter.BaseAdapter;
import com.example.taobaodemo.adapter.CategoryAdapter;
import com.example.taobaodemo.adapter.RecyclerviewWaresAdapter;
import com.example.taobaodemo.bean.category.Category;
import com.example.taobaodemo.bean.home.Banner;
import com.example.taobaodemo.bean.hot.Page;
import com.example.taobaodemo.bean.hot.Wares;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.example.taobaodemo.utils.Pager;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements Pager.OnPageListener {


    private RecyclerView recyclerview_category;
    private SliderLayout slider_category;
    private RecyclerView recyclerview_wares;

    private CategoryAdapter categoryAdapter;
    private View rootView;
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    List<Banner> mBannerData;
    private RecyclerviewWaresAdapter waresAdapter;
    RefreshLayout refreshLayout;

    private Category currentCategory;
    Pager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category, container, false);
        initView();
        initData();
        return rootView;
    }

    private void initData() {

        pager = Pager.newBuilder()
                .setUrl(Contants.API.WARES_LIST)
                .setRefreshLayout(refreshLayout)
                .setOnPageListener(this)
                .build(getContext(), new TypeToken<Page<Wares>>(){}.getType());
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
                if (categories.size() > 0){
                    currentCategory = categories.get(0);
                    getWaresData(currentCategory.getId());
                }
            }
        });

    }
    private void getWaresData(long categoryId){

        pager.putParam("categoryId",categoryId);
        pager.refresh();
    }

    private void initView() {
        recyclerview_category = rootView.findViewById(R.id.recyclerview_category);
        slider_category = rootView.findViewById(R.id.slider_category);
        recyclerview_wares = rootView.findViewById(R.id.recyclerview_wares);

        recyclerview_category.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_category.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        categoryAdapter = new CategoryAdapter(getContext(),null);
        recyclerview_category.setAdapter(categoryAdapter);
        categoryAdapter.setmOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentCategory = categoryAdapter.getItem(position);
                getWaresData(currentCategory.getId());
            }
        });

        recyclerview_wares.setLayoutManager(new GridLayoutManager(getContext(),2));
        waresAdapter = new RecyclerviewWaresAdapter(getContext(),null);
        recyclerview_wares.setAdapter(waresAdapter);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
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

    @Override
    public void refreshData(Page page) {
        if (page.getCurrentPage() == 1) {
            waresAdapter.clear();
        }
        waresAdapter.addData(page.getList());
    }
}
