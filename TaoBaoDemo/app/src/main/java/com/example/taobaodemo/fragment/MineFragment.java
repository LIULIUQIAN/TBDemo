package com.example.taobaodemo.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taobaodemo.AddressListActivity;
import com.example.taobaodemo.CollectionActivity;
import com.example.taobaodemo.LoginActivity;
import com.example.taobaodemo.R;
import com.example.taobaodemo.application.TBApplication;
import com.example.taobaodemo.bean.User;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private CircleImageView imgHead;
    private TextView txtUsername;
    private Button btnLogout;
    private TextView txtMyAddress;
    private TextView txt_collection;
    private TextView txt_my_orders;

    TBApplication application = TBApplication.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        setUserInfo();
        return rootView;
    }

    private void initView() {
        imgHead = rootView.findViewById(R.id.img_head);
        txtUsername = rootView.findViewById(R.id.txt_username);
        btnLogout = rootView.findViewById(R.id.btn_logout);
        txtMyAddress = rootView.findViewById(R.id.txt_my_address);
        txt_collection = rootView.findViewById(R.id.txt_collection);
        txt_my_orders = rootView.findViewById(R.id.txt_my_orders);

        imgHead.setOnClickListener(this);
        txtUsername.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        txtMyAddress.setOnClickListener(this);
        txt_collection.setOnClickListener(this);
        txt_my_orders.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_head:
            case R.id.txt_username:
                if (application.getUser() == null){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.btn_logout:
                application.clearUser();
                setUserInfo();
                break;
            case R.id.txt_my_address:
                Intent intent = new Intent(getActivity(), AddressListActivity.class);
                startActivity(intent,true);
                break;
            case R.id.txt_collection:
                Intent collectionIntent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(collectionIntent,true);
                break;
            case R.id.txt_my_orders:
                Toast.makeText(getContext(),"功能开发中",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            setUserInfo();
        }
    }

    private void setUserInfo() {
        User user = application.getUser();
        if (user != null) {
            Glide.with(getContext()).load(user.getLogo_url()).into(imgHead);
            txtUsername.setText(user.getUsername());
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            txtUsername.setText("请登录");
            imgHead.setImageResource(R.drawable.default_head);
            btnLogout.setVisibility(View.GONE);
        }

    }
}
