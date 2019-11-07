package com.example.taobaodemo.utils;

import android.content.Context;
import android.util.SparseArray;

import com.example.taobaodemo.bean.hot.Wares;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CollectionData {

    public static final String COLLECTION_JSON = "collection_json";
    private SparseArray<Wares> datas = null;
    private Context mContext;
    private Gson gson;

    public CollectionData(Context context) {
        this.mContext = context;
        datas = new SparseArray<>(20);
        gson = new Gson();
        listToSparse();
    }

    public void put(Wares wares) {
        Wares temp = datas.get(wares.getId().intValue());
        if (temp == null) {
            datas.put(wares.getId().intValue(), wares);
            commit();
        }
    }

    public void delete(Wares wares) {
        datas.delete(wares.getId().intValue());
        commit();
    }

    public List<Wares> getAll() {
        return getDataFromLocal();
    }

    public void commit() {
        int size = datas.size();
        List<Wares> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(datas.valueAt(i));
        }
        PreferencesUtils.putString(mContext, COLLECTION_JSON, gson.toJson(list));
    }

    public List<Wares> getDataFromLocal() {
        String json = PreferencesUtils.getString(mContext, COLLECTION_JSON);
        List<Wares> list = null;
        if (json != null) {
            list = gson.fromJson(json, new TypeToken<List<Wares>>() {
            }.getType());
        }
        return list;
    }

    private void listToSparse() {
        List<Wares> list = getDataFromLocal();
        if (list != null && list.size() > 0) {
            for (Wares wares : list) {
                datas.put(wares.getId().intValue(), wares);
            }
        }
    }
}
