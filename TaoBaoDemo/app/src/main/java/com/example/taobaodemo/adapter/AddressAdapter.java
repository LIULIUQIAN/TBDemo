package com.example.taobaodemo.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.Address;

import java.util.List;

public class AddressAdapter extends SimpleAdapter<Address> {

    public AddressAdapter(Context context, List<Address> datas) {
        super(context, datas, R.layout.template_address);
    }

    @Override
    protected void convert(BaseViewHolder viewHodel, Address item) {

        TextView txt_name = viewHodel.getTextView(R.id.txt_name);
        TextView txt_phone = viewHodel.getTextView(R.id.txt_phone);
        TextView txt_address = viewHodel.getTextView(R.id.txt_address);

        txt_name.setText(item.getConsignee());
        txt_phone.setText(item.getPhone());
        txt_address.setText(item.getAddr()+" "+item.getDetailed());

    }
}
