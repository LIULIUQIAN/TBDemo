package com.example.taobaodemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.taobaodemo.adapter.AddressAdapter;
import com.example.taobaodemo.application.TBApplication;
import com.example.taobaodemo.bean.Address;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.example.taobaodemo.widget.CnToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class AddressListActivity extends AppCompatActivity {

    private CnToolbar toolbar;
    private RecyclerView recyclerView;
    private AddressAdapter mAddressAdapter;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        initView();
        initData();
    }

    private void initData() {
        Map<String,Object> params = new HashMap<>();
        params.put("user_id", TBApplication.getInstance().getUser().getId());

        okHttpHelper.post(Contants.API.ADDRESS_LIST, params, new SpotsCallBack<List<Address>>(this) {

            @Override
            public void onSuccess(Response response, List<Address> addresses) {
System.out.println("请求结果"+addresses.toString());
            }
        });

    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        List<Address> list = new ArrayList<>();
        for (int i = 0; i < 10;i++){
            list.add(new Address());
        }
        mAddressAdapter = new AddressAdapter(this,list);
        recyclerView.setAdapter(mAddressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
