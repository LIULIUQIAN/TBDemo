package com.example.taobaodemo.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.cart.ShoppingCart;
import com.example.taobaodemo.utils.CartProvider;
import com.example.taobaodemo.widget.NumberAddSubView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartAdapter extends SimpleAdapter<ShoppingCart> implements BaseAdapter.OnItemClickListener {

    private CartProvider cartProvider;
    private CheckBox checkBox;
    private TextView textView;

    public CartAdapter(Context context, final List<ShoppingCart> datas, final CheckBox checkBox, TextView textView) {
        super(context, datas, R.layout.template_cart);
        this.checkBox = checkBox;
        this.textView = textView;
        setmOnItemClickListener(this);
        cartProvider = new CartProvider(context);
        showTotalPrice();

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (datas != null && datas.size() > 0) {
                    for (ShoppingCart cart : datas) {
                        cart.setChecked(checkBox.isChecked());
                    }
                    notifyDataSetChanged();
                    showTotalPrice();
                }
            }
        });


    }

    @Override
    protected void convert(BaseViewHolder viewHodel, final ShoppingCart item) {

        ImageView drawee_view = viewHodel.getImageView(R.id.drawee_view);
        TextView text_title = viewHodel.getTextView(R.id.text_title);
        TextView text_price = viewHodel.getTextView(R.id.text_price);
        NumberAddSubView numberAddSubView = (NumberAddSubView) viewHodel.retrieveView(R.id.num_control);
        CheckBox checkBox = (CheckBox) viewHodel.retrieveView(R.id.checkbox);

        checkBox.setChecked(item.isChecked());
        Glide.with(context).load(item.getImgUrl()).into(drawee_view);
        text_title.setText(item.getName());
        text_price.setText(item.getPrice().toString());
        numberAddSubView.setValue(item.getCount());

        numberAddSubView.setOnNumChangeListener(new NumberAddSubView.OnNumChangeListener() {
            @Override
            public void OnNumChange(View view, int value) {
                item.setCount(value);
                cartProvider.update(item);
                showTotalPrice();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        ShoppingCart cart = getItem(position);
        cart.setChecked(!cart.isChecked());
        notifyItemChanged(position);
        cartProvider.update(cart);
        showTotalPrice();
    }

    public void showTotalPrice() {

        float sum = 0;
        int checkedCount = 0;
        if (datas != null && datas.size() > 0) {
            for (ShoppingCart cart : datas) {
                if (cart.isChecked()) {
                    sum += cart.getCount() * cart.getPrice();
                    checkedCount++;
                }
            }
        }
        textView.setText(Html.fromHtml("合计 <span style='color:#eb4f38'>￥" + sum + "</span>"), TextView.BufferType.SPANNABLE);
        this.checkBox.setChecked(checkedCount == datas.size());
    }

    public void delCart(){

        if (datas != null && datas.size() > 0) {

            for(Iterator iterator = datas.iterator(); iterator.hasNext();){

                ShoppingCart cart = (ShoppingCart) iterator.next();
                if(cart.isChecked()){
                    int position = datas.indexOf(cart);
                    cartProvider.delete(cart);
                    iterator.remove();
                    notifyItemRemoved(position);
                    showTotalPrice();
                }

            }
        }



    }
}
