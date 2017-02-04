package com.iven.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.iven.app.R;

/**
 * JS和Java互调
 */
public class WebActivity extends AppCompatActivity {

    private WebView mWebView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);
        init();
    }

    private void init() {
        mWebView = (WebView) findViewById(R.id.webview_basic);
        mEditText = (EditText) findViewById(R.id.edit_web);
        setWebView(mWebView);
    }

    /**
     * WebView的设置
     */
    private void setWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(false);//禁止缩放
        //设置自适应屏幕
        settings.setUseWideViewPort(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        CookieManager.getInstance().setAcceptCookie(true);
        webView.setWebChromeClient(new WebChromeClient());//添加客户端支持
        //第二个参数，一定要跟JS中一致
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");
    }

    //返回键处理
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void webviewClick(View view) {
        mWebView.loadUrl("file:///android_asset/login.html");
    }

    //javav调用JS
    public void javaTurn(View view) {
        String string = mEditText.getText().toString();
        String javaName = "name";
        String javaPwd = "password";
        mWebView.loadUrl("javascript:setData('" + javaName + "','" + javaPwd + "')");
    }

    /**
     * JS调用java的方法，需要用到的内部类
     */
    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            mContext = context;
        }

        /**
         * 注意：此处必须添加注解；方法名字一定要跟JS代码中的function一致
         *
         * @param message
         */
        @JavascriptInterface
        public void showInfoFromJs(String message) {
            Toast.makeText(mContext, "message = " + message, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void getNameAndPwdFromJS(String name, String pwd) {
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                Toast.makeText(mContext, "不能为空...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "name = " + name + "           pwd = " + pwd, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
