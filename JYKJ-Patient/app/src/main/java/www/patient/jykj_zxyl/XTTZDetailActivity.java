package www.patient.jykj_zxyl;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import entity.ProvideMsgPushReminder;
import entity.WXUserInfo;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.activity.ForgetPwdActivity;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.PhoneLoginActivity;
import www.patient.jykj_zxyl.activity.UseRegistActivity;
import www.patient.jykj_zxyl.activity.WXBindPhoneActivity;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.HttpUtil;
import www.patient.jykj_zxyl.util.PrefParams;

import static android.widget.Toast.LENGTH_SHORT;

//import com.orhanobut.logger.Logger;
//import util.LinkdoodLoadingDialog;
//
//import www.jykj.com.jykj_zxyl.util.PreferenceUtils;
//import yyz_exploit.Forget_activity;
//import yyz_exploit.Utils.HttpUtil;
//import yyz_exploit.Utils.HttpUtils;
//import yyz_exploit.Utils.PrefParams;
//import yyz_exploit.activity.activity.ResActivity;
//import yyz_exploit.bean.ResBean;

/**
 * 系统通知
 */
public class XTTZDetailActivity extends AppCompatActivity {


    private ProvideMsgPushReminder  mProvideMsgPushReminder;
    private LinearLayout    ll_back;
    private JYKJApplication     mApp;

    private             String                              mNetRetStr;                 //返回字符串

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.messagedetail_webview);
        mApp = (JYKJApplication) getApplication();
        ll_back = (LinearLayout)findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mProvideMsgPushReminder = (ProvideMsgPushReminder) getIntent().getSerializableExtra("message");
        WebView wvTest =  findViewById(R.id.webView); //加载网络网页
        wvTest.getSettings().setJavaScriptEnabled(true);//设置可以响应JS
        // 开启DOM缓存，开启LocalStorage存储（html5的本地存储方式）
        wvTest.getSettings().setDomStorageEnabled(true);
        wvTest.getSettings().setDatabaseEnabled(true);
        wvTest.getSettings().setDatabasePath(this.getApplicationContext().getCacheDir().getAbsolutePath());
        wvTest.setWebViewClient(new WebViewClient());//限制在WebView中打开网页，而不用默认浏览器
        wvTest.setWebChromeClient(new WebChromeClient());
        wvTest.loadUrl(mProvideMsgPushReminder.getMsgLookUrl()); //加载本地网页 wvTest.loadData("file:///android_asset/refresh/refresh.html");
        //设置消息为已读
        operUpdPatientMsgState();
    }

    /**
     * 设置消息为已读
     */
    private void operUpdPatientMsgState() {
        ProvideMsgPushReminder provideMsgPushReminder  = new ProvideMsgPushReminder ();
        provideMsgPushReminder.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideMsgPushReminder.setRequestClientType("1");
        provideMsgPushReminder.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideMsgPushReminder.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideMsgPushReminder.setReminderId(mProvideMsgPushReminder.getReminderId());

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideMsgPushReminder);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"msgDataControlle/operUpdPatientMsgState");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
            }
        }.start();

    }


}
