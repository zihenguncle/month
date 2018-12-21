package jx.com.js;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_load_js).setOnClickListener(this);
        findViewById(R.id.btn_load_js_args).setOnClickListener(this);
        webView = findViewById(R.id.web);
        // 启用WebView对JavaScript的支持
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置JavascriptInterface
        // javainterface实际就是一个普通的java类，里面是我们本地实现的java代码
        // 将object 传递给webview，并指定别名，这样js脚本就可以通过我们给的这个别名来调用我们的方法
        // 在代码中，TestInterface是实例化的对象，testInterface是这个对象在js中的别名
        webView.addJavascriptInterface(new TestInterface(), "testInterface");

        // 触摸焦点起作用（如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件）
        webView.requestFocus();

        // 取消滚动条
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        // 允许网页缩放
        webView.getSettings().setSupportZoom(true);

        // 把图片加载放在最后来加载渲染
        webView.getSettings().setBlockNetworkImage(true);

        webView.setWebViewClient(new WebViewClient() {
            /**
             * 给WebView加一个事件监听对象（WebViewClient)并重写shouldOverrideUrlLoading，
             * 可以对网页中超链接按钮的响应
             * 当按下某个连接时WebViewClient会调用这个方法，并传递参数：当前响应的的url地址
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 此处可添加一些逻辑：是否拦截此url，自行处理
                // 下方2行代码是指在当前的webview中跳转到新的url
                view.loadUrl(url);
                Log.e("WebViewDemo", "shouldOverrideUrlLoading url:" + url);
                return true;
            }

            /**
             * WebView加载url完成时，会回调此api，可在这个api中隐藏加载进度框
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                // 此处可添加一些逻辑：隐藏加载进度框
                Log.e("WebViewDemo", "onPageFinished");
            }

            /**
             * WebView开始加载url时，会回调此api，可在这个api中显示加载进度框
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // 此处可添加一些逻辑：显示加载进度框
                Log.e("WebViewDemo", "onPageStarted");
            }
        });
        // 从assets目录下面的加载html
        webView.loadUrl("file:///android_asset/test.html");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_load_js: // Java调用JS
                // 无参数调用
                webView.loadUrl("javascript:javacalljs()");
                break;
            case R.id.btn_load_js_args: // Java调用JS并传递参数
                String content = "hello js, form Android code!";
                webView.loadUrl("javascript:javacalljswithargs('" + content + "')");
                break;
            default:
                break;
        }
    }

    /**
     * Js调用的JavascriptInterface
     */
    public class TestInterface {

        /**
         * 因为安全问题，在Android4.2以后(如果应用的android:targetSdkVersion数值为17+)
         * JS只能访问带有 @JavascriptInterface注解的Java函数。
         */
        @JavascriptInterface
        public void startCall() {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 10086));
            startActivity(intent);
        }

        @JavascriptInterface
        public void showToast(String content) {
            Toast.makeText(MainActivity.this, "js调用了java函数并传递了参数：" + content, Toast.LENGTH_SHORT).show();
        }
    }
}
