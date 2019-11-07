package com.example.taobaodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taobaodemo.bean.Address;
import com.example.taobaodemo.utils.AddressData;
import com.example.taobaodemo.widget.CnToolbar;

public class AddressAddActivity extends AppCompatActivity {

    public final static String DEFAULT_ADS_KEY = "default_address";
    private CnToolbar toolbar;
    private EditText edittxt_consignee;
    private EditText edittxt_phone;
    private EditText txt_address;
    private EditText edittxt_add;

    private Address defaultAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);

        if (getIntent().getSerializableExtra(DEFAULT_ADS_KEY) != null){
            defaultAddress = (Address) getIntent().getSerializableExtra(DEFAULT_ADS_KEY);
        }
        initView();
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        edittxt_consignee = findViewById(R.id.edittxt_consignee);
        edittxt_phone = findViewById(R.id.edittxt_phone);
        txt_address = findViewById(R.id.txt_address);
        edittxt_add = findViewById(R.id.edittxt_add);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAds();
            }
        });

        if (defaultAddress != null){
            edittxt_consignee.setText(defaultAddress.getConsignee());
            edittxt_phone.setText(defaultAddress.getPhone());
            txt_address.setText(defaultAddress.getAddr());
            edittxt_add.setText(defaultAddress.getDetailed());
        }
    }

    private void addAds() {

        String consignee = edittxt_consignee.getText().toString().trim();
        String phone = edittxt_phone.getText().toString().trim();
        String address = txt_address.getText().toString().trim();
        String detailed = edittxt_add.getText().toString().trim();

        if (TextUtils.isEmpty(consignee)) {
            Toast.makeText(this, "请输入收件人姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入联系电话", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请输入收件地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(detailed)) {
            Toast.makeText(this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            return;
        }

        AddressData addressData = new AddressData(this);
        Address ads = new Address(consignee, phone, address, detailed);
        if (defaultAddress != null){
            ads.setId(defaultAddress.getId());
        }
        addressData.put(ads);

        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        finish();

    }
}
