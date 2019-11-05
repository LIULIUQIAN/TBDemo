package com.example.taobaodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taobaodemo.application.TBApplication;
import com.example.taobaodemo.bean.User;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.example.taobaodemo.msg.LoginRespMsg;
import com.example.taobaodemo.utils.CountTimerView;
import com.example.taobaodemo.utils.DESUtil;
import com.example.taobaodemo.widget.CnToolbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import okhttp3.Response;

import static com.example.taobaodemo.msg.BaseRespMsg.STATUS_SUCCESS;

public class RegSecondActivity extends AppCompatActivity {

    public static final String PHONE_KEY = "RegSecond_phone";
    public static final String PWD_KEY = "RegSecond_pwd";
    private EditText edittxtCode;
    private Button btnReSend;
    private CnToolbar toolbar;

    private String phoneStr;
    private String pwdStr;
    private SMSEventHandler smsEventHandler;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private CountTimerView countTimerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_second);

        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(smsEventHandler);
    }

    private void initView() {

        edittxtCode = findViewById(R.id.edittxt_code);
        btnReSend = findViewById(R.id.btn_reSend);
        toolbar = findViewById(R.id.toolbar);

        countTimerView = new CountTimerView(btnReSend);
        countTimerView.start();

        btnReSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新发送验证码
                SMSSDK.getVerificationCode("86", phoneStr);
            }
        });

        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationCode();
            }
        });
    }

    private void initData() {
        phoneStr = getIntent().getStringExtra(PHONE_KEY);
        pwdStr = getIntent().getStringExtra(PWD_KEY);
        smsEventHandler = new SMSEventHandler();
        SMSSDK.registerEventHandler(smsEventHandler);
    }


    private void validationCode() {
        String smsCode = edittxtCode.getText().toString().trim();
        if (TextUtils.isEmpty(smsCode)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        SMSSDK.submitVerificationCode("86", phoneStr, smsCode);
    }

    private void doReg() {

        Map<String, Object> params = new HashMap<>(2);
        params.put("phone", phoneStr);
        params.put("password", DESUtil.encode(Contants.DES_KEY, pwdStr));
        okHttpHelper.post(Contants.API.REG, params, new SpotsCallBack<LoginRespMsg<User>>(this) {

            @Override
            public void onSuccess(Response response, LoginRespMsg<User> userLoginRespMsg) {

                if (userLoginRespMsg.getStatus() != STATUS_SUCCESS) {
                    Toast.makeText(RegSecondActivity.this, userLoginRespMsg.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                TBApplication application = TBApplication.getInstance();
                application.putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());
                startActivity(new Intent(RegSecondActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    class SMSEventHandler extends EventHandler {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    System.out.println("提交验证码成功");
                    doReg();
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    System.out.println("获取验证码成功");
                    countTimerView.start();
                }
            } else {
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    JSONObject object = new JSONObject(throwable.getMessage());
                    final String des = object.optString("detail");
                    if (!TextUtils.isEmpty(des)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegSecondActivity.this, des, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }

    }
}
