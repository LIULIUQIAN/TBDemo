package com.example.taobaodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.hot.Wares;
import com.example.taobaodemo.utils.CartProvider;

import java.util.List;

public class HotWaresAdapter extends SimpleAdapter<Wares> {

    private CartProvider cartProvider;
    public HotWaresAdapter(Context context, List<Wares> datas) {
        super(context, datas, R.layout.template_hot_wares);
        cartProvider = new CartProvider(context);
    }

    @Override
    protected void convert(BaseViewHolder viewHodel, final Wares item) {
        ImageView drawee_view = viewHodel.getImageView(R.id.drawee_view);
        TextView text_title = viewHodel.getTextView(R.id.text_title);
        TextView text_price = viewHodel.getTextView(R.id.text_price);
        Button btn_add = (Button) viewHodel.retrieveView(R.id.btn_add);

        Glide.with(context).load(item.getImgUrl()).into(drawee_view);
        text_title.setText(item.getName());
        text_price.setText(item.getPrice().toString());

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartProvider.put(item);
                Toast.makeText(context,"已添加购物车",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
