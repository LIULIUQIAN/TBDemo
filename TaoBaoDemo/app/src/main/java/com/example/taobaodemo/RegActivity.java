package com.example.taobaodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taobaodemo.widget.CnToolbar;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class RegActivity extends AppCompatActivity {

    private CnToolbar toolbar;
    private EditText etxtPhone;
    private EditText etxtPwd;

    private SMSEventHandler smsEventHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        initView();
        smsEventHandler = new SMSEventHandler();
        SMSSDK.registerEventHandler(smsEventHandler);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(smsEventHandler);
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        etxtPhone = findViewById(R.id.etxt_phone);
        etxtPwd = findViewById(R.id.etxt_pwd);

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
                nextStep();
            }
        });
    }

    private void nextStep() {

        String phone = etxtPhone.getText().toString().trim();
        String pwd = etxtPwd.getText().toString().trim();
        String rule = "^1(3|5|7|8|4)\\d{9}";

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this,"请输入手机号码",Toast.LENGTH_SHORT).show();
            return;
        }
        Pattern p = Pattern.compile(rule);
        if (!p.matcher(phone).matches()){
            Toast.makeText(this,"手机号码格式不正确",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }

        SMSSDK.getVerificationCode("86", phone);

    }

    class SMSEventHandler extends EventHandler {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    System.out.println("提交验证码成功");
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    System.out.println("获取验证码成功");
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                    System.out.println("返回支持发送验证码的国家列表");
                }
            }else{
                // 根据服务器返回的网络错误，给toast提示
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());

                    String des = object.optString("detail");
                    if (!TextUtils.isEmpty(des)) {
                       Toast.makeText(RegActivity.this,des,Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }

    }
}
