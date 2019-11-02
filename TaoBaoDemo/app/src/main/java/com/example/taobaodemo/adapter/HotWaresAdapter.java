package com.example.taobaodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.hot.Wares;

import java.util.ArrayList;
import java.util.List;

public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {

    private Context mContext;
    private List<Wares> mData;
    private LayoutInflater mInflater;

    public HotWaresAdapter(Context context, List<Wares> mData) {
        this.mContext = context;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.template_hot_wares, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wares wares = mData.get(position);
        Glide.with(mContext).load(wares.getImgUrl()).into(holder.drawee_view);
        holder.text_title.setText(wares.getName());
        holder.text_price.setText(wares.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView drawee_view;
        private TextView text_title;
        private TextView text_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drawee_view = itemView.findViewById(R.id.drawee_view);
            text_title = itemView.findViewById(R.id.text_title);
            text_price = itemView.findViewById(R.id.text_price);
        }
    }

    public void addData(List<Wares> datas){

        if (mData == null){
            mData = new ArrayList<>();
        }
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }
}
