package com.example.taobaodemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taobaodemo.application.TBApplication;
import com.example.taobaodemo.bean.User;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.example.taobaodemo.msg.LoginRespMsg;
import com.example.taobaodemo.utils.DESUtil;
import com.example.taobaodemo.widget.CnToolbar;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

import static com.example.taobaodemo.msg.BaseRespMsg.STATUS_SUCCESS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private CnToolbar toolbar;
    private TextView etxtPhone;
    private TextView etxtPwd;
    private Button btnLogin;
    private TextView regUser;

    OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        etxtPhone = findViewById(R.id.etxt_phone);
        etxtPwd = findViewById(R.id.etxt_pwd);
        btnLogin = findViewById(R.id.btn_login);
        regUser = findViewById(R.id.reg_user);
        btnLogin.setOnClickListener(this);
        regUser.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_login){
            login();
        }else if(v.getId() == R.id.reg_user){
            startActivity(new Intent(this,RegActivity.class));
        }
    }

    private void login(){
        String phone = etxtPhone.getText().toString().trim();
        String pwd = etxtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this,"请输入手机号码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,Object> params = new HashMap<>();
        params.put("phone",phone);
        params.put("password", DESUtil.encode(Contants.DES_KEY,pwd));
        okHttpHelper.post(Contants.API.LOGIN, params, new SpotsCallBack<LoginRespMsg<User>>(this) {

            @Override
            public void onSuccess(Response response, LoginRespMsg<User> userLoginRespMsg) {

                if (userLoginRespMsg.getStatus() != STATUS_SUCCESS){
                    Toast.makeText(LoginActivity.this,userLoginRespMsg.getMessage(),Toast.LENGTH_SHORT).show();
                    return;
                }

                TBApplication application = TBApplication.getInstance();
                application.putUser(userLoginRespMsg.getData(),userLoginRespMsg.getToken());

                if (application.getIntent() != null){
                    application.jumpToTargetActivity(LoginActivity.this);
                }

                finish();
            }
        });
    }
}
