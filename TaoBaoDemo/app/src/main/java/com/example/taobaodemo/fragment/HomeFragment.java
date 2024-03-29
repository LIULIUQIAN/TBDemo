package com.example.taobaodemo.fragment;


import android.content.Intent;
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
import com.example.taobaodemo.Contants;
import com.example.taobaodemo.R;
import com.example.taobaodemo.WareListActivity;
import com.example.taobaodemo.adapter.HomeCatgoryAdapter;
import com.example.taobaodemo.bean.home.Banner;
import com.example.taobaodemo.bean.home.Campaign;
import com.example.taobaodemo.bean.home.HomeCampaign;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.google.gson.Gson;

import java.util.List;

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
    List<Banner> mBannerData;
    Handler mHandler = new Handler(Looper.getMainLooper());

    OkHttpHelper httpHelper = OkHttpHelper.getInstance();

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

        httpHelper.get(Contants.API.BANNER_HOME, new SpotsCallBack<List<Banner>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBannerData = banners;
                initSlider();
            }
        });
    }

    private void initRecyclerView() {

        httpHelper.get(Contants.API.CAMAIGN_HOME, new SpotsCallBack<List<HomeCampaign>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                adapter = new HomeCatgoryAdapter(getContext(), homeCampaigns);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                adapter.setOnCampaignClickListener(new HomeCatgoryAdapter.OnCampaignClickListener() {
                    @Override
                    public void onClick(View view, Campaign campaign) {

                        Intent intent = new Intent(getActivity(),WareListActivity.class);
                        intent.putExtra(WareListActivity.COMPAINGAIN_ID,campaign.getId());
                        startActivity(intent);
                    }
                });
            }
        });


    }

    private void initSlider() {

        if (mBannerData == null) {
            return;
        }
        for (Banner banner : mBannerData) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.description(banner.getName()).image(banner.getImgUrl());
            sliderShow.addSlider(textSliderView);
        }

    }

}
