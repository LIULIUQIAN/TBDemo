package com.example.taobaodemo.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taobaodemo.Contants;
import com.example.taobaodemo.R;
import com.example.taobaodemo.WareDetailActivity;
import com.example.taobaodemo.adapter.BaseAdapter;
import com.example.taobaodemo.adapter.HotWaresAdapter;
import com.example.taobaodemo.bean.hot.Page;
import com.example.taobaodemo.bean.hot.Wares;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.example.taobaodemo.utils.Pager;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.reflect.TypeOfKt;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment implements Pager.OnPageListener {


    private View rootView;
    private RecyclerView mRecyclerView;
    private HotWaresAdapter mAdapter;
    private RefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hot, container, false);
        initView();
        initData();
        return rootView;
    }

    private void initData() {

        Pager pager = Pager.newBuilder()
                .setUrl(Contants.API.WARES_HOT)
                .setRefreshLayout(refreshLayout)
                .setOnPageListener(this)
                .build(getContext(), new TypeToken<Page<Wares>>(){}.getType());
        pager.refresh();
    }

    private void initView() {
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HotWaresAdapter(getContext(), null);
        mRecyclerView.setAdapter(mAdapter);
        //添加Android自带的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        refreshLayout = rootView.findViewById(R.id.refreshLayout);

        mAdapter.setmOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Wares wares = mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), WareDetailActivity.class);
                intent.putExtra(WareDetailActivity.WARES_KEY,wares);
                startActivity(intent);
            }
        });
    }


    @Override
    public void refreshData(Page page) {

        if (page.getCurrentPage() == 1) {
            mAdapter.clear();
        }
        mAdapter.addData(page.getList());
    }
}
