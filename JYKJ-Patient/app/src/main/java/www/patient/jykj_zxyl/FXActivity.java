package www.patient.jykj_zxyl;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import entity.mySelf.ProvideViewSysUserDoctorInfoAndHospital;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;

/**
 * 我的识别码 == 》 分享
 */
public class FXActivity extends AppCompatActivity {


    private                 String                      mNetRetStr;                 //返回字符串
    public                  ProgressDialog              mDialogProgress =null;
    private                 Context                     mContext;
    private FXActivity mActivity;
    private                 JYKJApplication             mApp;
    private                 Handler                     mHandler;
    private                 TextView                    mAboutText;
    private                 LinearLayout                mBack;
    private WebView abotuspage;
    WebSettings mWebSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_setting_about);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();
        //getData();
        loadWebPage();
    }

    void loadWebPage(){
        abotuspage.loadUrl("http://jiuyihtn.com/AppAssembly/aboutUs.html");
        abotuspage.setWebViewClient(new WebViewClient() {
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
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        mAboutText.setText(Html.fromHtml(retEntity.getResJsonData()));
                        break;
                }
            }
        };
    }


    /**
     * 初始化布局
     */
    private void initLayout() {
        mAboutText = (TextView)this.findViewById(R.id.tv_about);
        mBack = (LinearLayout)this.findViewById(R.id.back);
        abotuspage = findViewById(R.id.abotuspage);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mWebSettings = abotuspage.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        mWebSettings.setJavaScriptEnabled(true);//是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞
        mWebSettings.setSupportZoom(true);//是否可以缩放，默认true
        mWebSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        mWebSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        mWebSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mWebSettings.setAppCacheEnabled(true);//是否使用缓存
        mWebSettings.setDomStorageEnabled(true);//开启本地DOM存储
        mWebSettings.setLoadsImagesAutomatically(true); // 加载图片
        mWebSettings.setMediaPlaybackRequiresUserGesture(false);//播放音频，多媒体需要用户手动？设置为false为可自动播放
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }

    /**
     * 获取数据
     */
    private void getData() {
        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    ProvideViewSysUserDoctorInfoAndHospital provideViewSysUserDoctorInfoAndHospital = new ProvideViewSysUserDoctorInfoAndHospital();
                    provideViewSysUserDoctorInfoAndHospital.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewSysUserDoctorInfoAndHospital.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideViewSysUserDoctorInfoAndHospital.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    //实体转JSON字符串
                    String str = new Gson().toJson(provideViewSysUserDoctorInfoAndHospital);
                    //获取用户数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"/doctorPersonalSetControlle/getDoctorAboutUsHtmlData");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0)
                    {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && abotuspage.canGoBack()) {
            abotuspage.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (abotuspage != null) {
            abotuspage.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            abotuspage.clearHistory();

            ((ViewGroup) abotuspage.getParent()).removeView(abotuspage);
            abotuspage.destroy();
            abotuspage = null;
        }
        super.onDestroy();
    }
}
