package www.patient.jykj_zxyl.fragment.shouye;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLQTDKActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLTXHZActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLXYActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLYYXXActivity;
import www.patient.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.patient.jykj_zxyl.activity.ylzx.YLZX01Activity;
import www.patient.jykj_zxyl.activity.ylzx.YLZXWebActivity;
import www.patient.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLQTDKActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLTXHZActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLXYActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLYYXXActivity;
import www.patient.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.patient.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 首页==》医疗资讯fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe_YLZX extends Fragment {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private MainActivity mActivity;
    private             JYKJApplication                     mApp;

    private             WebView                             mWebView;

    private             TextView                            more;
    private             LinearLayout                        ylzxWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shouye_ylzx, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
//        mWebView = (WebView)v.findViewById(R.id.webView);
        ylzxWebView = (LinearLayout)v.findViewById(R.id.ylzxWebView);
        ylzxWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,YLZX01Activity.class));
            }
        });
        more = (TextView)v.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,YLZXWebActivity.class));
            }
        });
//        loadWebPage();
//        initLayout(v);
//        initHandler();
//        setData();
        return v;
    }

    void loadWebPage(){

        mWebView.loadUrl("http://jiuyihtn.com/AppAssembly/medicalLink/medicalLink1.html");
        mWebView.setWebViewClient(new WebViewClient() {
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


}
