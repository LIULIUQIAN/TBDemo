package com.example.taobaodemo.adapter;

import android.content.Context;

import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.Address;

import java.util.List;

public class AddressAdapter extends SimpleAdapter<Address> {

    public AddressAdapter(Context context, List<Address> datas) {
        super(context, datas, R.layout.template_address);
    }

    @Override
    protected void convert(BaseViewHolder viewHodel, Address item) {

    }
}
