package com.example.taobaodemo.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.taobaodemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private View rootView;
    private SliderLayout sliderShow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        sliderShow = rootView.findViewById(R.id.slider);
        initSlider();

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
