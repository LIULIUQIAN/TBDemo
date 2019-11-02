package com.example.taobaodemo.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SparseArray<View> views;
    private BaseAdapter.OnItemClickListener mOnItemClickListener;

    public BaseViewHolder(@NonNull View itemView, BaseAdapter.OnItemClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.views = new SparseArray<View>();
        this.mOnItemClickListener = listener;
    }

    public TextView getTextView(int viewId) {
        return (TextView) retrieveView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) retrieveView(viewId);
    }


    protected View retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return view;
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }
}
