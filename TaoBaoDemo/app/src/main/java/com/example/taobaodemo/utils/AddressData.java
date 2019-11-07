package com.example.taobaodemo.utils;

import android.content.Context;
import android.util.SparseArray;


import com.example.taobaodemo.bean.Address;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddressData {

    public static final String ADDRESS_JSON="address_json";

    private SparseArray<Address> datas = null;
    private Context mContext;
    private Gson gson;

    public AddressData(Context context) {
        this.mContext = context;
        datas = new SparseArray<>(20);
        gson = new Gson();
        listToSparse();
    }

    public void put(Address address) {
        Address temp = datas.get(address.getId().intValue());
        if (temp == null) {
            temp = temp;
        } else {
            temp.setConsignee(address.getConsignee());
            temp.setPhone(address.getPhone());
            temp.setAddr(address.getAddr());
            temp.setDetailed(address.getDetailed());
            temp.setDefault(address.getDefault());

        }
        datas.put(temp.getId().intValue(), temp);

        commit();
    }


    public void update(Address address) {
        put(address);
        commit();
    }

    public void delete(Address address) {
        datas.delete(address.getId().intValue());
        commit();
    }

    public List<Address> getAll() {
        return getDataFromLocal();
    }


    public void commit() {

        int size = datas.size();
        List<Address> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(datas.valueAt(i));
        }
        PreferencesUtils.putString(mContext, ADDRESS_JSON, gson.toJson(list));
    }

    public List<Address> getDataFromLocal() {
        String json = PreferencesUtils.getString(mContext, ADDRESS_JSON);
        List<Address> ads = null;
        if (json != null) {
            ads = gson.fromJson(json, new TypeToken<List<Address>>() {
            }.getType());
        }
        return ads;
    }

    private void listToSparse() {
        List<Address> ads = getDataFromLocal();
        if (ads != null && ads.size() > 0) {
            for (Address a : ads) {
                datas.put(a.getId().intValue(), a);
            }
        }
    }

}
