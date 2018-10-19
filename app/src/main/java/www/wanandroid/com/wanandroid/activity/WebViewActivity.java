package www.wanandroid.com.wanandroid.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.constant.Constant;

@Route(path = Constant.ACTIVITY_URL_WEBVIEW)
public class WebViewActivity extends BaseActivity {


    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.webview)
    WebView webview;
    @Autowired(name = Constant.KEY_WEBVIEW)
    String htmlUrl;


    @Override
    protected void init() {
        initToolbar();
        webview.getSettings().setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webview.getSettings().setUseWideViewPort(true);//将图片调整到合适webview的大小
        webview.getSettings().setLoadWithOverviewMode(true);//缩放至屏幕的大小
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         webview.loadUrl(url);
                                         return true;
                                     }

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         super.onPageFinished(view, url);
                                         String title = view.getTitle();
                                         if (!TextUtils.isEmpty(title)) {
                                             getSupportActionBar().setTitle(title);
                                         }
                                     }

                                     @Override
                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                         super.onPageStarted(view, url, favicon);
                                         getSupportActionBar().setTitle("正在加载.....");
                                     }
                                 }

        );
        webview.setWebChromeClient(new WebChromeClient() {
                                       @Override
                                       public void onProgressChanged(WebView view, int newProgress) {
                                           if (newProgress == 100) {
                                               pb.setVisibility(View.GONE);
                                           } else {
                                               pb.setVisibility(View.VISIBLE);
                                               pb.setProgress(newProgress);
                                           }
                                       }

                                       @Override
                                       public void onReceivedTitle(WebView view, String title) {
                                           super.onReceivedTitle(view, title);

                                       }
                                   }

        );
        webview.loadUrl(htmlUrl);
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {
                webview.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}
