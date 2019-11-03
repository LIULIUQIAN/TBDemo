package com.example.taobaodemo.utils;

import android.content.Context;
import android.util.SparseArray;

import com.example.taobaodemo.bean.cart.ShoppingCart;
import com.example.taobaodemo.bean.hot.Wares;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CartProvider {

    public static final String CART_JSON = "cart_json";
    private SparseArray<ShoppingCart> datas = null;
    private Context mContext;
    private Gson gson;

    public CartProvider(Context context) {
        this.mContext = context;
        datas = new SparseArray<>(20);
        gson = new Gson();
        listToSparse();
    }

    public void put(ShoppingCart cart) {
        ShoppingCart temp = datas.get(cart.getId().intValue());
        if (temp == null) {
            temp = cart;
        } else {
            temp.setCount(temp.getCount() + 1);
        }
        datas.put(temp.getId().intValue(), temp);

        commit();
    }

    public void put(Wares wares) {

        ShoppingCart cart = new ShoppingCart();
        cart.setId(wares.getId());
        cart.setDescription(wares.getDescription());
        cart.setImgUrl(wares.getImgUrl());
        cart.setName(wares.getName());
        cart.setPrice(wares.getPrice());

        put(cart);

    }

    public void update(ShoppingCart cart) {
        datas.put(cart.getId().intValue(), cart);
        commit();
    }

    public void delete(ShoppingCart cart) {
        datas.delete(cart.getId().intValue());
        commit();
    }

    public List<ShoppingCart> getAll() {
        return getDataFromLocal();
    }

    public void commit() {

        int size = datas.size();
        List<ShoppingCart> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(datas.valueAt(i));
        }
        PreferencesUtils.putString(mContext, CART_JSON, gson.toJson(list));
    }

    public List<ShoppingCart> getDataFromLocal() {
        String json = PreferencesUtils.getString(mContext, CART_JSON);
        List<ShoppingCart> carts = null;
        if (json != null) {
            carts = gson.fromJson(json, new TypeToken<List<ShoppingCart>>() {
            }.getType());
        }
        return carts;
    }

    private void listToSparse() {
        List<ShoppingCart> carts = getDataFromLocal();
        if (carts != null && carts.size() > 0) {
            for (ShoppingCart cart : carts) {
                datas.put(cart.getId().intValue(), cart);
            }
        }
    }
}
