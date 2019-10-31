package com.example.taobaodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.HomeCategory;

import java.util.List;

public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private List<HomeCategory> mDatas;
    private Context mContext;

    public HomeCatgoryAdapter(Context context,List<HomeCategory> datas) {
        mDatas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeCategory category = mDatas.get(position);
        holder.text_title.setText(category.getName());
        holder.imgview_big.setImageResource(category.getImgBig());
        holder.imgview_small_top.setImageResource(category.getImgSmallTop());
        holder.imgview_small_bottom.setImageResource(category.getImgSmallBottom());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView text_title;
        private ImageView imgview_big;
        private ImageView imgview_small_top;
        private ImageView imgview_small_bottom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.text_title);
            imgview_big = itemView.findViewById(R.id.imgview_big);
            imgview_small_top = itemView.findViewById(R.id.imgview_small_top);
            imgview_small_bottom = itemView.findViewById(R.id.imgview_small_bottom);
        }
    }
}
