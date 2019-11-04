package com.example.taobaodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.taobaodemo.bean.hot.Wares;
import com.example.taobaodemo.utils.CartProvider;
import com.example.taobaodemo.widget.CnToolbar;

import java.io.Serializable;

public class WareDetailActivity extends AppCompatActivity {

    public static final String WARES_KEY = "WARES_KEY";

    private CnToolbar mToolBar;
    private WebView mWebView;
    private Wares wares;
    private WebAppInterface webAppInterface;
    private CartProvider cartProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);

        initView();
        initData();
    }

    private void initView() {

        mWebView = findViewById(R.id.web_view);
        mToolBar = findViewById(R.id.toolbar);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        mWebView.loadUrl(Contants.API.WARES_DETAIL);

        webAppInterface = new WebAppInterface(this);
        mWebView.addJavascriptInterface(webAppInterface,"appInterface");
        mWebView.setWebViewClient(new webViewClient());

        mToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData() {

        Serializable serializable = getIntent().getSerializableExtra(WARES_KEY);
        if (serializable == null) {
            finish();
        }
        wares = (Wares) serializable;

        cartProvider = new CartProvider(this);
    }

    class webViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webAppInterface.showDetail();
            imgReset();
        }

        private void imgReset() {
            mWebView.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                    "}" +
                    "})()");
        }

    }

    class WebAppInterface{

        private Context context;

        public WebAppInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void showDetail(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:showDetail("+wares.getId()+")");
                }
            });
        }

        @JavascriptInterface
        public void buy(long id){
            Toast.makeText(context,"已添加购物车",Toast.LENGTH_SHORT).show();
            cartProvider.put(wares);
        }
    }
}
