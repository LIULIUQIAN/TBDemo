package com.example.taobaodemo.fragment;


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
import com.example.taobaodemo.adapter.HotWaresAdapter;
import com.example.taobaodemo.bean.hot.Page;
import com.example.taobaodemo.bean.hot.Wares;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {


    private View rootView;
    private RecyclerView mRecyclerView;
    private HotWaresAdapter mAdapter;
    RefreshLayout refreshLayout;

    private Integer pageIndex = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hot, container, false);
        initView();
        initData();
        return rootView;
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize","10");
        params.put("curPage",pageIndex.toString());
        OkHttpHelper.getInstance().post(Contants.API.WARES_HOT, params, new SpotsCallBack<Page<Wares>>(getContext()) {
            @Override
            public void onSuccess(Response response, Page<Wares> page) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();

                if (pageIndex == 1){
                    mAdapter.clearData();
                    refreshLayout.resetNoMoreData();
                }
                if (page.getList().size() < 10){
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                mAdapter.addData(page.getList());
                pageIndex++;
            }

            @Override
            public void onResponse(Response response) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onError(Response response, Exception e) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    private void initView(){
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HotWaresAdapter(getContext(),new ArrayList<Wares>());
        mRecyclerView.setAdapter(mAdapter);
        //添加Android自带的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                initData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                initData();
            }
        });
    }

}
