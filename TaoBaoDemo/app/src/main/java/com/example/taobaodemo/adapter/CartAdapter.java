package com.example.taobaodemo.adapter;

import android.content.Context;

import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.cart.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends SimpleAdapter<ShoppingCart> {

    public CartAdapter(Context context, List<ShoppingCart> datas) {
        super(context, datas, R.layout.template_cart);

        List<ShoppingCart> d =new ArrayList<ShoppingCart>();
        for (int i = 0;i < 20;i++){
            d.add(new ShoppingCart());
        }
        this.addData(d);
    }

    @Override
    protected void convert(BaseViewHolder viewHodel, ShoppingCart item) {

    }
}
