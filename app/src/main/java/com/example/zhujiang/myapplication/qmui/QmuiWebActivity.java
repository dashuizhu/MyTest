package com.example.zhujiang.myapplication.qmui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.example.zhujiang.myapplication.R;
import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient;
import java.io.IOException;

public class QmuiWebActivity extends AppCompatActivity {

    private final int PROGRESS_PROCESS = 1;
    private final int PROGRESS_GONE = 0;
    private boolean mIsPageFinished = false;

    private QMUIWebView mWebView;
    private ProgressBar mProgressBar;

    private ProgressHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmui_web);
        mWebView = findViewById(R.id.webView);
        mProgressBar = findViewById(R.id.progress_bar);
        mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebViewClient(new QMUIWebViewClient(false, true){

            //自定义协议前缀, 用来识别 是否是直接引用 本地的库文件，
            private static final String CUSTOM_KEY = "pweview-";

            /**
             * assets中的文件 一定要与 h5页面所使用的文件一致
             * @return 本地jquery
             */
            private WebResourceResponse editResponse(String urlName) {
                try {
                    if (urlName.endsWith(".js")) {
                        //替换对应的 js文件
                        return new WebResourceResponse("application/x-javascript", "utf-8",
                                QmuiWebActivity.this.getAssets().open(urlName));
                    } else if (urlName.endsWith(".css")) {
                        //替换对应的css文件
                        return new WebResourceResponse("text/css", "utf-8",
                                QmuiWebActivity.this.getAssets().open(urlName));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //需处理特殊情况
                return null;
            }


            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                    WebResourceRequest request) {
                //js css文件链接
                if (Build.VERSION.SDK_INT >= 21) {
                    String url = request.getUrl().toString();
                    Log.e("test", " url " +url);
                    //获取文件名
                    String urlName = url.substring(url.lastIndexOf("/")+1);
                    //判断是否 指向本地的 文件
                    if (!TextUtils.isEmpty(urlName) && urlName.startsWith(CUSTOM_KEY)) {
                        return editResponse(urlName);
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //页面开始， 先直接加载到30%
                if (mHandler.mDstProgressIndex == 0) {
                    sendProgressMessage(PROGRESS_PROCESS, 30, 100);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("test", " onPageFinished " );
                //直接到100
                sendProgressMessage(PROGRESS_GONE, 100, 0);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //Log.e("test", " nowProgress " + newProgress);
                // 修改进度条
                if (newProgress > mHandler.mDstProgressIndex) {
                    sendProgressMessage(PROGRESS_PROCESS, newProgress, 100);
                }
            }



        });

        mHandler = new ProgressHandler();
    }

    private void sendProgressMessage(int progressType, int newProgress, int duration) {
        Message msg = new Message();
        msg.what = progressType;
        msg.arg1 = newProgress;
        msg.arg2 = duration;
        mHandler.sendMessage(msg);
    }

    private class ProgressHandler extends Handler {

        private int            mDstProgressIndex;
        private int            mDuration;
        private ObjectAnimator mAnimator;


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESS_PROCESS:
                    mIsPageFinished = false;
                    mDstProgressIndex = msg.arg1;
                    mDuration = msg.arg2;
                    mProgressBar.setVisibility(View.VISIBLE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", mDstProgressIndex);
                    mAnimator.setDuration(mDuration);
                    mAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (mProgressBar.getProgress() == 100) {
                                sendEmptyMessageDelayed(PROGRESS_GONE, 500);
                            }
                        }
                    });
                    mAnimator.start();
                    break;
                case PROGRESS_GONE:
                    mDstProgressIndex = 0;
                    mDuration = 0;
                    mProgressBar.setProgress(0);
                    mProgressBar.setVisibility(View.GONE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", 0);
                    mAnimator.setDuration(0);
                    mAnimator.removeAllListeners();
                    mIsPageFinished = true;
                    break;
                default:
                    break;
            }
        }
    }
}
