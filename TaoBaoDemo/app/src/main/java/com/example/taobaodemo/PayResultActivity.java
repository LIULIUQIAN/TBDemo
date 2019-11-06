package com.example.taobaodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PayResultActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String TITLE_KEY = "title_key";
    public final static String STATE_KEY = "state_key";

    private ImageView image_icon;
    private TextView tv_title;
    private Button bt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);

        initView();
        initData();
    }

    private void initData() {

        boolean state = getIntent().getBooleanExtra(STATE_KEY,false);
        String title = getIntent().getStringExtra(TITLE_KEY);

        image_icon.setImageResource(state?R.mipmap.icon_success_128:R.mipmap.icon_cancel_128);
        tv_title.setText(title);

    }

    private void initView() {
        image_icon = findViewById(R.id.image_icon);
        tv_title = findViewById(R.id.tv_title);
        bt_back = findViewById(R.id.bt_back);

        bt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        finish();

    }
}
