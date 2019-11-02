package com.example.taobaodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.hot.Wares;

import java.util.List;

public class HotWaresAdapter extends SimpleAdapter<Wares> {

    public HotWaresAdapter(Context context, List<Wares> datas) {
        super(context, datas, R.layout.template_hot_wares);
    }

    @Override
    protected void convert(BaseViewHolder viewHodel, Wares item) {
        ImageView drawee_view = viewHodel.getImageView(R.id.drawee_view);
        TextView text_title = viewHodel.getTextView(R.id.text_title);
        TextView text_price = viewHodel.getTextView(R.id.text_price);

        Glide.with(context).load(item.getImgUrl()).into(drawee_view);
        text_title.setText(item.getName());
        text_price.setText(item.getPrice().toString());
    }
}
