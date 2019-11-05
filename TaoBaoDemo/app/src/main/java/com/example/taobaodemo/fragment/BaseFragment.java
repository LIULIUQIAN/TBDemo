package com.example.taobaodemo.fragment;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taobaodemo.LoginActivity;
import com.example.taobaodemo.application.TBApplication;
import com.example.taobaodemo.bean.User;

public class BaseFragment extends Fragment {

    public void startActivity(Intent intent, boolean isNeedLogin){

        if (isNeedLogin){
            User user = TBApplication.getInstance().getUser();
            if (user == null){

                TBApplication.getInstance().setIntent(intent);
                super.startActivity(new Intent(getActivity(), LoginActivity.class));

            }else {
                super.startActivity(intent);
            }
        }else {
            super.startActivity(intent);
        }

    }
}
