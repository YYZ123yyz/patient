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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import www.patient.jykj_zxyl.util.ActivityUtil;


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
        ActivityUtil.setStatusBarMain(mActivity);
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
