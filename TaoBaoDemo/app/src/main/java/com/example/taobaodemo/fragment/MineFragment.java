package com.example.taobaodemo.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taobaodemo.LoginActivity;
import com.example.taobaodemo.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private CircleImageView imgHead;
    private TextView txtUsername;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        imgHead = rootView.findViewById(R.id.img_head);
        txtUsername = rootView.findViewById(R.id.txt_username);

        imgHead.setOnClickListener(this);
        txtUsername.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.img_head:
            case R.id.txt_username:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }

    }
}
