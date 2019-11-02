package com.example.taobaodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    protected static final String TAG = BaseAdapter.class.getSimpleName();

    protected Context context;
    protected List<T> datas;
    protected int layoutResId;
    protected LayoutInflater mInflater;

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public BaseAdapter(Context context, int layoutResId) {
        this(context, null, layoutResId);
    }

    public BaseAdapter(Context context, List<T> datas, int layoutResId) {
        this.context = context;
        this.datas = datas == null ? new ArrayList<T>() : datas;
        this.layoutResId = layoutResId;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layoutResId, parent, false);
        return new BaseViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        T item = getItem(position);
        convert((H) holder, item);
    }

    @Override
    public int getItemCount() {
        if (datas == null || datas.size() <= 0) {
            return 0;
        }
        return datas.size();
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return datas;
    }

    public void addData(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (position >= datas.size()) {
            return null;
        }
        return datas.get(position);
    }

    protected abstract void convert(H viewHodel, T item);

    public void setmOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
