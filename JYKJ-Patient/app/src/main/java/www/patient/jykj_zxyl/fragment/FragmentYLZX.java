package www.patient.jykj_zxyl.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentRMJX;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentWDSC;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentYXWX;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentYXXW;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentYXZN;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentRMJX;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentWDSC;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentYXWX;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentYXXW;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentYXZN;


/**
 * 医疗资讯fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentYLZX extends Fragment {
    private             Context                             mContext;
    private MainActivity mActivity;
    private             Handler                             mHandler;
    private             JYKJApplication                     mApp;
    private             ViewPager                           pager;
    private FragmentAdapter fragmentAdapter;
    private             List<Fragment>                      fragmentList;
    private             TabLayout                           tabLayout;
    private                 List<String>                    mTitles;

    private             WebView                             webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ylzx, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        return v;
    }

    /**
     * 初始化布局
     */
    private void initLayout(View view) {
        webView = (WebView)view.findViewById(R.id.webView);
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webView.loadUrl("https://jiuyihtn.com/AppAssembly/patientFakeFriends.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                // super.onReceivedSslError(view, handler, error);

                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
        });
        final DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int point = (int) event.getX();
                        if (point > 0 && point < 50 || point > dm.widthPixels - 50 && point < dm.widthPixels) {
                            webView.requestDisallowInterceptTouchEvent(false);
                        } else {
                            webView.requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                }

                return false;
            }
        });


    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {

                }
            }
        };
    }
}
