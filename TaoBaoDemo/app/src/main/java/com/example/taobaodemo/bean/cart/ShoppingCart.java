package com.example.taobaodemo.bean.cart;

import com.example.taobaodemo.bean.hot.Wares;


public class ShoppingCart extends Wares {

    private int count = 1;
    private boolean isChecked=true;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
