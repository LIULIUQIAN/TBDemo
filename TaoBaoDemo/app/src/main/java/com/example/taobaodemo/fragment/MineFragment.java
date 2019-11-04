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

import com.bumptech.glide.Glide;
import com.example.taobaodemo.LoginActivity;
import com.example.taobaodemo.R;
import com.example.taobaodemo.application.TBApplication;
import com.example.taobaodemo.bean.User;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private CircleImageView imgHead;
    private TextView txtUsername;
    private Button btnLogout;

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

        imgHead.setOnClickListener(this);
        txtUsername.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
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
