package com.example.taobaodemo.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.taobaodemo.R;
import com.example.taobaodemo.adapter.HomeCatgoryAdapter;
import com.example.taobaodemo.bean.HomeCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private View rootView;
    private SliderLayout sliderShow;
    private RecyclerView recyclerView;
    private HomeCatgoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        sliderShow = rootView.findViewById(R.id.slider);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        initSlider();
        initRecyclerView();

    }

    private void initRecyclerView() {

        List<HomeCategory> datas = new ArrayList<>(15);

        HomeCategory category = new HomeCategory("热门活动",R.drawable.img_big_1,R.drawable.img_1_small1,R.drawable.img_1_small2);
        datas.add(category);

        category = new HomeCategory("有利可图",R.drawable.img_big_4,R.drawable.img_4_small1,R.drawable.img_4_small2);
        datas.add(category);
        category = new HomeCategory("品牌街",R.drawable.img_big_2,R.drawable.img_2_small1,R.drawable.img_2_small2);
        datas.add(category);

        category = new HomeCategory("金融街 包赚翻",R.drawable.img_big_1,R.drawable.img_3_small1,R.drawable.imag_3_small2);
        datas.add(category);

        category = new HomeCategory("超值购",R.drawable.img_big_0,R.drawable.img_0_small1,R.drawable.img_0_small2);
        datas.add(category);

        adapter = new HomeCatgoryAdapter(getContext(),datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initSlider() {
        TextSliderView textSliderView = new TextSliderView(getContext());
        textSliderView
                .description("Game of Thrones")
                .image("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1572424824&di=3c561ec4443aabc223049822c71a7de4&src=http://5b0988e595225.cdn.sohucs.com/images/20180425/eba53f4eb48e4c81a9de070577dc287c.jpeg");

        sliderShow.addSlider(textSliderView);

        TextSliderView textSliderView2 = new TextSliderView(getContext());
        textSliderView2
                .description("Game of Thrones")
                .image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572434917577&di=559ea449550666ec9d420def2eadae55&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F6b0018f329217b50b48d9478776a24c9825cbef7cd86-IrED2R_fw658");

        sliderShow.addSlider(textSliderView2);

        TextSliderView textSliderView3 = new TextSliderView(getContext());
        textSliderView3
                .description("Game of Thrones")
                .image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572434917576&di=e41b42efc6c2a9e78bd9626b0ef38db0&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fb4364e79ca2797d74b8134b4bb48fea40af428ef48d40-imG7pw_fw658");

        sliderShow.addSlider(textSliderView3);
    }

}
