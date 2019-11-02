package com.example.taobaodemo.adapter;

import android.content.Context;

import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.category.Category;

import java.util.List;

public class CategoryAdapter extends SimpleAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> datas) {
        super(context, datas,R.layout.template_single_text);
    }

    @Override
    protected void convert(BaseViewHolder viewHodel, Category item) {

        viewHodel.getTextView(R.id.textview).setText(item.getName());
    }
}
