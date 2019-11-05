package com.example.taobaodemo.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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
import com.example.taobaodemo.bean.home.Campaign;
import com.example.taobaodemo.bean.home.HomeCampaign;

import java.util.List;

public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private List<HomeCampaign> mDatas;
    private Context mContext;

    private OnCampaignClickListener mListener;

    public HomeCatgoryAdapter(Context context,List<HomeCampaign> datas) {
        mDatas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnCampaignClickListener(OnCampaignClickListener listener){

        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeCampaign category = mDatas.get(position);
        holder.text_title.setText(category.getTitle());
        Glide.with(mContext).load(category.getCpOne().getImgUrl()).into(holder.imgview_big);
        Glide.with(mContext).load(category.getCpTwo().getImgUrl()).into(holder.imgview_small_top);
        Glide.with(mContext).load(category.getCpThree().getImgUrl()).into(holder.imgview_small_bottom);

    }

    @Override
    public int getItemCount() {
        if (mDatas == null){
            return 0;
        }
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

            imgview_big.setOnClickListener(this);
            imgview_small_top.setOnClickListener(this);
            imgview_small_bottom.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {

            if(mListener == null){
                return;
            }

            ObjectAnimator animator = ObjectAnimator.ofFloat(v,"rotationY",0,-360).setDuration(500);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    HomeCampaign campaign = mDatas.get(getLayoutPosition());
                    switch (v.getId()){
                        case R.id.imgview_big:
                            mListener.onClick(v,campaign.getCpOne());
                            break;
                        case R.id.imgview_small_top:
                            mListener.onClick(v,campaign.getCpTwo());
                            break;
                        case R.id.imgview_small_bottom:
                            mListener.onClick(v,campaign.getCpThree());
                            break;
                    }
                }
            });
            animator.start();
        }
    }

    public interface OnCampaignClickListener{
        void onClick(View view, Campaign campaign);
    }
}
