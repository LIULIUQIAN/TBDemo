package com.example.taobaodemo.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.cart.ShoppingCart;
import com.example.taobaodemo.widget.NumberAddSubView;

import java.util.List;

public class WareOrderAdapter extends SimpleAdapter<ShoppingCart> {

    public WareOrderAdapter(Context context, List<ShoppingCart> datas) {
        super(context, datas, R.layout.template_order_wares);
    }

    @Override
    protected void convert(BaseViewHolder viewHodel, ShoppingCart item) {

        ImageView drawee_view = viewHodel.getImageView(R.id.drawee_view);
        TextView text_title = viewHodel.getTextView(R.id.text_title);
        TextView text_price = viewHodel.getTextView(R.id.text_price);
        TextView text_count = viewHodel.getTextView(R.id.text_count);

        Glide.with(context).load(item.getImgUrl()).into(drawee_view);
        text_title.setText(item.getName());
        text_price.setText("￥ "+String.valueOf(item.getPrice()));
        text_count.setText(String.valueOf(item.getCount())+"件");

    }
}
