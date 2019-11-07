package com.example.taobaodemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.hot.Wares;

import java.util.List;

public class CollectionAdapter extends SimpleAdapter<Wares> {

    private CollectionLisneter collectionLisneter;
    public CollectionAdapter(Context context, List<Wares> datas) {
        super(context, datas, R.layout.template_collection_cell);
    }

    public void setCollectionLisneter(CollectionLisneter collectionLisneter) {
        this.collectionLisneter = collectionLisneter;
    }

    @Override
    protected void convert(BaseViewHolder viewHodel, final Wares item) {

        ImageView drawee_view = viewHodel.getImageView(R.id.drawee_view);
        TextView text_title = viewHodel.getTextView(R.id.text_title);
        TextView text_price = viewHodel.getTextView(R.id.text_price);
        Button btn_cancel = (Button) viewHodel.retrieveView(R.id.btn_cancel);

        Glide.with(context).load(item.getImgUrl()).into(drawee_view);
        text_title.setText(item.getName());
        text_price.setText(item.getPrice().toString());

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (collectionLisneter != null){
                    collectionLisneter.cancelCollection(item);
                }
            }
        });

    }

    public interface CollectionLisneter{
        public void cancelCollection(Wares wares);
    }
}
