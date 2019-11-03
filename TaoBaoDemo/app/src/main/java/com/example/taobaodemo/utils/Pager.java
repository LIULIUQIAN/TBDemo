package com.example.taobaodemo.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.taobaodemo.bean.hot.Page;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class Pager {

    private static Builder builder;
    private OkHttpHelper httpHelper;

    private Pager() {
        httpHelper = OkHttpHelper.getInstance();
        initRefreshLayout();
    }

    public static Builder newBuilder() {
        builder = new Builder();
        return builder;
    }

    public void putParam(String key, Object value) {
        builder.params.put(key, value);
    }

    /**
     * 请求数据
     */
    public void requestData() {

        builder.params.put("curPage", builder.pageIndex);
        builder.params.put("pageSize", builder.pageSize);
        httpHelper.post(builder.mUrl, builder.params, new RequestCallBack(builder.mContext));
    }

    /**
     * 刷新数据
     */
    public void refresh() {
        builder.pageIndex = 1;
        requestData();
    }


    private void initRefreshLayout() {

        builder.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh();
            }
        });
        builder.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                requestData();
            }
        });

    }

    public static class Builder {

        private Context mContext;
        private Type mType;
        private RefreshLayout refreshLayout;
        private boolean canLoadMore = true;

        private int pageIndex = 1;
        private int pageSize = 10;

        private String mUrl;
        private Map<String, Object> params = new HashMap<>(10);
        private OnPageListener onPageListener;

        public Builder setUrl(String url) {
            this.mUrl = url;
            return builder;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return builder;
        }

        public Builder putParam(String key, Object value) {
            this.params.put(key, value);
            return builder;
        }

        public Builder setLoadMore(boolean loadMore) {
            this.canLoadMore = loadMore;
            return builder;
        }

        public Builder setRefreshLayout(RefreshLayout refreshLayout) {
            this.refreshLayout = refreshLayout;
            return builder;
        }

        public Builder setOnPageListener(OnPageListener onPageListener) {
            this.onPageListener = onPageListener;
            return builder;
        }

        public Pager build(Context context, Type type) {
            this.mContext = context;
            this.mType = type;

            valid();
            return new Pager();
        }

        private void valid() {
            if (this.mContext == null)
                throw new RuntimeException("content can't be null");

            if (this.mUrl == null || "".equals(this.mUrl))
                throw new RuntimeException("url can't be  null");

            if (this.refreshLayout == null)
                throw new RuntimeException("MaterialRefreshLayout can't be  null");
        }
    }

    class RequestCallBack<T> extends SpotsCallBack<Page<T>> {

        public RequestCallBack(Context context) {
            super(context);
            super.mType = builder.mType;
        }

        @Override
        public void onFailure(Request request, Exception e) {
            builder.refreshLayout.finishRefresh();
            builder.refreshLayout.finishLoadMore();
            Toast.makeText(builder.mContext, "请求出错", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Response response, Exception e) {
            builder.refreshLayout.finishRefresh();
            builder.refreshLayout.finishLoadMore();
            Toast.makeText(builder.mContext, "请求失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(Response response, Page<T> tPage) {
            builder.refreshLayout.finishRefresh();
            builder.refreshLayout.finishLoadMore();

            if (tPage.getCurrentPage() == 1) {
                builder.refreshLayout.resetNoMoreData();
            }
            if (tPage.getList().size() < builder.pageSize) {
                builder.refreshLayout.finishLoadMoreWithNoMoreData();
            }

            if (builder.onPageListener != null) {
                builder.onPageListener.refreshData(tPage);
            }
            builder.pageIndex++;

        }
    }

    public interface OnPageListener<T> {
        void refreshData(Page<T> page);

    }
}
