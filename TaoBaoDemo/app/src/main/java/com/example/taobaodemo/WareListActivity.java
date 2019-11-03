package com.example.taobaodemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.taobaodemo.adapter.HotWaresAdapter;
import com.example.taobaodemo.bean.hot.Page;
import com.example.taobaodemo.bean.hot.Wares;
import com.example.taobaodemo.utils.Pager;
import com.example.taobaodemo.widget.CnToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

public class WareListActivity extends AppCompatActivity implements Pager.OnPageListener, View.OnClickListener {

    public static final String COMPAINGAIN_ID = "COMPAINGAIN_ID";

    private CnToolbar mToolbar;
    private TabLayout mTablayout;
    private RecyclerView mRecyclerview;
    private RefreshLayout refreshLayout;
    private TextView txtSummary;

    private HotWaresAdapter mAdapter;
    private Pager pager;

    private int orderBy = 0;
    private String campaignId;
    private boolean isListLayout = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_list);

        initView();
        initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {

        mTablayout = findViewById(R.id.tab_layout);
        mRecyclerview = findViewById(R.id.recycler_view);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dec = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.shape_question_diveder);
        dec.setDrawable(drawable);
//        dec.setDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.colorAccent)));
        mRecyclerview.addItemDecoration(dec);
        refreshLayout = findViewById(R.id.refreshLayout);
        txtSummary = findViewById(R.id.txt_summary);
        mToolbar = findViewById(R.id.toolbar);

        initTab();
        initToolBar();
    }

    private void initTab() {
        TabLayout.Tab tab = mTablayout.newTab();
        tab.setText("默认");
        tab.setTag(0);
        mTablayout.addTab(tab);

        tab = mTablayout.newTab();
        tab.setText("价格");
        tab.setTag(1);
        mTablayout.addTab(tab);

        tab = mTablayout.newTab();
        tab.setText("销量");
        tab.setTag(2);
        mTablayout.addTab(tab);

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.putParam("orderBy", tab.getTag());
                pager.refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolBar() {
        mToolbar.setRightButtonIcon(R.mipmap.icon_grid_32);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isListLayout = !isListLayout;
                mToolbar.setRightButtonIcon(isListLayout ? R.mipmap.icon_grid_32 : R.mipmap.icon_list_32);

                if (isListLayout){
                    mAdapter.resetLayout(R.layout.template_hot_wares);
                    mRecyclerview.setLayoutManager(new LinearLayoutManager(WareListActivity.this));
                }else {
                    mAdapter.resetLayout(R.layout.template_grid_wares);
                    mRecyclerview.setLayoutManager(new GridLayoutManager(WareListActivity.this,3));
                }

                mRecyclerview.setAdapter(mAdapter);
            }
        });
    }

    private void initData() {

        campaignId = getIntent().getStringExtra(COMPAINGAIN_ID);

        mAdapter = new HotWaresAdapter(this, null);
        mRecyclerview.setAdapter(mAdapter);

        pager = Pager.newBuilder()
                .setUrl(Contants.API.WARES_CAMPAIN_LIST)
                .putParam("campaignId", campaignId)
                .putParam("orderBy", orderBy)
                .setRefreshLayout(refreshLayout)
                .setOnPageListener(this)
                .build(this, new TypeToken<Page<Wares>>() {
                }.getType());
        pager.refresh();
    }

    @Override
    public void refreshData(Page page) {

        if (page.getCurrentPage() == 1) {
            mAdapter.clear();
        }
        mAdapter.addData(page.getList());
        txtSummary.setText("共有" + page.getTotalCount() + "件商品");
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tab_layout) {
            System.out.println("=========" + v.getTag());
        }

    }
}
