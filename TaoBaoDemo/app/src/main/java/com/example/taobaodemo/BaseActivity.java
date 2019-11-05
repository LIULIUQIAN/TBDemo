package com.example.taobaodemo;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taobaodemo.application.TBApplication;
import com.example.taobaodemo.bean.User;

public class BaseActivity extends AppCompatActivity {


    public void startActivity(Intent intent, boolean isNeedLogin){

        if (isNeedLogin){
            User user = TBApplication.getInstance().getUser();
            if (user == null){

                TBApplication.getInstance().setIntent(intent);
                super.startActivity(new Intent(this,LoginActivity.class));

            }else {
                super.startActivity(intent);
            }
        }else {
            super.startActivity(intent);
        }

    }
}
