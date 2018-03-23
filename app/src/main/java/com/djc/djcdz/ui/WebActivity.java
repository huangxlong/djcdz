package com.djc.djcdz.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseActivity;
import com.djc.djcdz.view.CustomWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 服务协议
 */
public class WebActivity extends BaseActivity {

    public static final String KEY_TITLE = "title";

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.webView)
    CustomWebView mWebView;
    private String mUrl = "";
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web);
        unbinder = ButterKnife.bind(this);
        initView();
    }


    private void initView() {

        String title = getIntent().getStringExtra(KEY_TITLE);
        tvTitle.setText(title);

        initWebView();
//        mUrl = getIntent().getStringExtra(KEY_EXTRA);

        getWebData();
    }


    private void getWebData() {
//        HttpUtil.postParams(mUrl, null, new MCallback() {
//            @Override
//            public void onResponse(RspDto.BaseRsp rsp, final String data) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        LogUtils.d("webContent", new String(Base64.decode(data, Base64.DEFAULT)));
//                        mWebView.loadData(new String(Base64.decode(data, Base64.DEFAULT)), "text/html; charset=UTF-8", null);
//                    }
//                });
//            }
//        });
        mWebView.loadUrl("http://www.djc888.com");
    }

    private void initWebView() {
        mWebView.clearCache(true);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
//        settings.setSupportZoom(true);//是否可以缩放，默认true
//        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setAppCacheEnabled(false);//是否使用缓存
        settings.setDomStorageEnabled(true);//DOM Storage
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.setWebViewClient(new MyWebClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());

    }

    @OnClick({R.id.layout_back})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                onBackPressed();
                break;
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        public MyWebChromeClient() {

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mWebView.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
//            LogUtils.d("shouldOverrideUrlLoading", "mUrl=" + url);
//            return super.shouldOverrideUrlLoading(view, url);
//
//        }
//
//        @Override
//        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed();
//        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.clearCache(true);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebViewCache();
    }


    public void clearWebViewCache() {
        // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
    }
}
