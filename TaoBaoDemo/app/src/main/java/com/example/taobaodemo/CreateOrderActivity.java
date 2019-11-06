package com.example.taobaodemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taobaodemo.adapter.WareOrderAdapter;
import com.example.taobaodemo.application.TBApplication;
import com.example.taobaodemo.bean.cart.ShoppingCart;
import com.example.taobaodemo.bean.cart.WareItem;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.example.taobaodemo.msg.CreateOrderRespMsg;
import com.example.taobaodemo.utils.CartProvider;
import com.example.taobaodemo.widget.CnToolbar;
import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class CreateOrderActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    private static final String CHANNEL_BFB = "bfb";

    private CnToolbar toolbar;
    private RecyclerView recyclerView;
    private WareOrderAdapter orderAdapter;
    private CartProvider provider;

    private RelativeLayout rl_alipay;
    private RadioButton rb_alipay;
    private RelativeLayout rl_wechat;
    private RadioButton rb_webchat;
    private RelativeLayout rl_bd;
    private RadioButton rb_bd;
    private TextView txt_total;
    private Button btn_createOrder;

    private String payChannel = CHANNEL_ALIPAY;
    private float totalPrice = 0;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        initview();
        showTotalPrice();
    }

    private void initview() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        rl_alipay = findViewById(R.id.rl_alipay);
        rb_alipay = findViewById(R.id.rb_alipay);
        rl_wechat = findViewById(R.id.rl_wechat);
        rb_webchat = findViewById(R.id.rb_webchat);
        rl_bd = findViewById(R.id.rl_bd);
        rb_bd = findViewById(R.id.rb_bd);
        txt_total = findViewById(R.id.txt_total);
        btn_createOrder = findViewById(R.id.btn_createOrder);

        provider = new CartProvider(this);
        orderAdapter = new WareOrderAdapter(this, provider.getOrderAll());
        recyclerView.setAdapter(orderAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        rl_alipay.setOnClickListener(this);
        rl_wechat.setOnClickListener(this);
        rl_bd.setOnClickListener(this);
        btn_createOrder.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_alipay:
                changePayChannel(CHANNEL_ALIPAY);
                break;
            case R.id.rl_wechat:
                changePayChannel(CHANNEL_WECHAT);
                break;
            case R.id.rl_bd:
                changePayChannel(CHANNEL_BFB);
                break;
            case R.id.btn_createOrder:
                createOrder();
                break;
        }

    }

    private void changePayChannel(String channel) {
        rb_alipay.setChecked(false);
        rb_webchat.setChecked(false);
        rb_bd.setChecked(false);
        payChannel = channel;

        switch (channel) {
            case CHANNEL_ALIPAY:
                rb_alipay.setChecked(true);
                break;
            case CHANNEL_WECHAT:
                rb_webchat.setChecked(true);
                break;
            case CHANNEL_BFB:
                rb_bd.setChecked(true);
                break;
        }

    }

    private void showTotalPrice() {

        for (ShoppingCart cart : provider.getOrderAll()) {
            if (cart.isChecked()) {
                totalPrice += cart.getCount() * cart.getPrice();
            }
        }
        txt_total.setText("合计 ￥" + totalPrice);
    }

    private void createOrder() {

        new PaymentTask().execute(new PaymentRequest());

//        List<ShoppingCart> carts = provider.getOrderAll();
//        List<WareItem> items = new ArrayList<>();
//        for (ShoppingCart cart : carts) {
//            items.add(new WareItem(cart.getId(), cart.getCount()));
//        }
//        Gson gson = new Gson();
//        String itemsJson = gson.toJson(items);
//
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("user_id", TBApplication.getInstance().getUser().getId());
//        params.put("item_json", itemsJson);
//        params.put("pay_channel", payChannel);
//        params.put("amount", String.valueOf(totalPrice));
//        params.put("addr_id", "1");
//        btn_createOrder.setEnabled(false);
//        okHttpHelper.post(Contants.API.ORDER_CREATE, params, new SpotsCallBack<CreateOrderRespMsg>(this) {
//
//            @Override
//            public void onSuccess(Response response, CreateOrderRespMsg createOrderRespMsg) {
//                btn_createOrder.setEnabled(true);
//
//                System.out.println("订单创建结果 ===" + createOrderRespMsg.toString());
//            }
//
//            @Override
//            public void onError(Response response, Exception e) {
//                btn_createOrder.setEnabled(true);
//            }
//        });
    }

    //    支付相关功能
    class PaymentRequest {
        //支付通道
        String channel;
        ///支付金额
        int amount;
        ///
        boolean livemode;
        String payUrl;

        public PaymentRequest() {
            this.amount = (int) totalPrice;
            this.channel = payChannel;
            this.livemode = false;
            this.payUrl = Contants.API.PAY_URL;
        }
    }

    class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {

        @Override
        protected void onPreExecute() {
            btn_createOrder.setOnClickListener(null);
        }

        @Override
        protected String doInBackground(PaymentRequest... pr) {

            PaymentRequest paymentRequest = pr[0];
            String data = null;
            try {
                JSONObject object = new JSONObject();
                object.put("channel", paymentRequest.channel);
                object.put("amount", paymentRequest.amount);
                object.put("livemode", paymentRequest.livemode);
                String json = object.toString();
                //向Your Ping++ Server SDK请求数据
                data = postJson(paymentRequest.payUrl, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        /**
         * 获得服务端的charge，调用ping++ sdk。
         */
        @Override
        protected void onPostExecute(String data) {
            if (null == data) {
                Toast.makeText(CreateOrderActivity.this,"请求出错 请检查URL URL无法获取charge",Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("charge", data);

            Pingpp.createPayment(CreateOrderActivity.this, data);
        }

    }

    /**
     * 获取charge
     * @param urlStr charge_url
     * @param json 获取charge的传参
     * @return charge
     * @throws IOException
     */
    private static String postJson(String urlStr, String json) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.getOutputStream().write(json.getBytes());

        if(conn.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
        return null;
    }


    /**
     *  获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        btn_createOrder.setOnClickListener(this);
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");

                Intent resultIntent = new Intent(CreateOrderActivity.this,PayResultActivity.class);

                if (TextUtils.equals(result,"success")){
                    resultIntent.putExtra(PayResultActivity.TITLE_KEY,"支付成功");
                    resultIntent.putExtra(PayResultActivity.STATE_KEY,true);
                }else if (TextUtils.equals(result,"fail")){
                    resultIntent.putExtra(PayResultActivity.TITLE_KEY,"支付失败");
                    resultIntent.putExtra(PayResultActivity.STATE_KEY,false);
                }else if (TextUtils.equals(result,"cancel")){
                    resultIntent.putExtra(PayResultActivity.TITLE_KEY,"支付取消");
                    resultIntent.putExtra(PayResultActivity.STATE_KEY,false);
                }else if (TextUtils.equals(result,"invalid")){
                    resultIntent.putExtra(PayResultActivity.TITLE_KEY,"付款APP未安装");
                    resultIntent.putExtra(PayResultActivity.STATE_KEY,false);
                }
                startActivity(resultIntent);
                finish();

            }
        }
    }

}
