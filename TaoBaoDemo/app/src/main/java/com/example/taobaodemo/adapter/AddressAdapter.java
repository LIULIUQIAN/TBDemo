package com.example.taobaodemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.taobaodemo.R;
import com.example.taobaodemo.bean.Address;

import java.util.List;

public class AddressAdapter extends SimpleAdapter<Address> {

    private AddressLisneter addressLisneter;

    public AddressAdapter(Context context, List<Address> datas) {
        super(context, datas, R.layout.template_address);
    }

    public void setAddressLisneter(AddressLisneter addressLisneter) {
        this.addressLisneter = addressLisneter;
    }

    @Override
    protected void convert(BaseViewHolder viewHodel, final Address item) {

        TextView txt_name = viewHodel.getTextView(R.id.txt_name);
        TextView txt_phone = viewHodel.getTextView(R.id.txt_phone);
        TextView txt_address = viewHodel.getTextView(R.id.txt_address);
        final CheckBox cbDefult = (CheckBox) viewHodel.retrieveView(R.id.cb_is_defualt);
        TextView txt_edit = viewHodel.getTextView(R.id.txt_edit);
        TextView txt_del = viewHodel.getTextView(R.id.txt_del);

        txt_name.setText(item.getConsignee());
        txt_phone.setText(item.getPhone());
        txt_address.setText(item.getAddr()+" "+item.getDetailed());

        cbDefult.setChecked(item.getDefault());
        if (item.getDefault()){
            cbDefult.setText("默认地址");
        }else {
            cbDefult.setText("设为默认");
            cbDefult.setClickable(true);
            cbDefult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked && addressLisneter != null){
                        cbDefult.setText("默认地址");
                        item.setDefault(true);
                        addressLisneter.setDefault(item);
                    }
                }
            });

        }

        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressLisneter != null){
                    addressLisneter.updateAddress(item);
                }
            }
        });

        txt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressLisneter != null){
                    addressLisneter.deleterAddress(item);
                }
            }
        });

    }

    public interface AddressLisneter{
        public void setDefault(Address address);
        public void updateAddress(Address address);
        public void deleterAddress(Address address);
    }
}
