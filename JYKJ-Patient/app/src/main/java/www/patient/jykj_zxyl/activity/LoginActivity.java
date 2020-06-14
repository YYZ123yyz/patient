package www.patient.jykj_zxyl.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
//import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import entity.ResBean;
import entity.WXUserInfo;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.HttpUtil;
import www.patient.jykj_zxyl.util.HttpUtils;
import www.patient.jykj_zxyl.util.PrefParams;


//import util.LinkdoodLoadingDialog;
//
//import www.jykj.com.jykj_zxyl.util.PreferenceUtils;
//import yyz_exploit.Forget_activity;
//import yyz_exploit.Utils.HttpUtil;
//import yyz_exploit.Utils.HttpUtils;
//import yyz_exploit.Utils.PrefParams;
//import yyz_exploit.activity.activity.ResActivity;
//import yyz_exploit.bean.ResBean;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {


    public ProgressDialog mDialogProgress = null;
    private Handler mHandler;
    private String mNetRegionRetStr;                 //获取返回字符串
    private TextView mPhoneLogin;                //手机号登录
    private TextView mUseRegist;                 //用户注册
    private Button mLogin;                     //登录
    private EditText mAccountEdit;               //输入帐号
    private EditText mPassWordEdit;              //输入密码
    private Context mContext;
    private LoginActivity mActivity;
    private JYKJApplication mApp;                       //
    private String mNetLoginRetStr;                 //登录返回字符串


    private boolean mAgree = true;                     //是否同意协议，默认同意
    private ImageView mAgreeImg;                  //同意协议图标

    protected ProgressBar mLoadingDialog;
    private ImageView login_weChat;

    // 微信登录
    private static IWXAPI WXapi;
    private String WX_APP_ID = "wx4ccb2ac1c5491336";
    private String WX_APPSecret = "3a56a462937397a324b57b807583108a";
    private RelativeLayout login_forget;
//    private ReceiveBroadCast receiveBroadCast;

    private String mOpenID;                 //用户微信ID

    private LinearLayout mLoginWeChat;

    private IWXAPI api;
    private SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();

        initHandler();
        //自动登录
        // autoLogin();
    }

    /**
     * 自动登录
     */
    private void autoLogin() {
        System.out.println(mApp.mProvideViewSysUserPatientInfoAndRegion);
        if (mApp.mLoginUserInfo != null && mApp.mLoginUserInfo.getUserPhone() != null &&
                mApp.mLoginUserInfo.getUserPwd() != null && !"".equals(mApp.mLoginUserInfo.getUserPhone())
                && !"".equals(mApp.mLoginUserInfo.getUserPhone())) {
            mAccountEdit.setText(mApp.mLoginUserInfo.getUserPhone());
            mPassWordEdit.setText(mApp.mLoginUserInfo.getUserPwd());
            userLogin();
        }
    }


    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        cacerProgress();
                        if (mNetLoginRetStr != null && !mNetLoginRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetLoginRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                UserInfo userInfo = new UserInfo();
                                userInfo.setUserPhone(mAccountEdit.getText().toString());
                                userInfo.setUserPwd(mPassWordEdit.getText().toString());
                                mApp.mLoginUserInfo = userInfo;
                                mApp.mProvideViewSysUserPatientInfoAndRegion =  JSON.parseObject(netRetEntity.getResJsonData(),ProvideViewSysUserPatientInfoAndRegion.class);
//                                mApp.mViewSysUserDoctorInfoAndHospital.setDoctorCode("dd0500f4e9684678aad9ee00000001");
//                                mApp.mViewSysUserDoctorInfoAndHospital.setQrCode("JY0100HZ200111180041000001");
                                mApp.saveUserInfo();


                                Toast.makeText(mContext, "恭喜，登录成功", LENGTH_SHORT).show();
//                                mApp.mProvideViewSysUserPatientInfoAndRegion.getUserRoleId();

                                //登录IM
                                mApp.loginIM();
                                cacerProgress();
                                startActivity(new Intent(mContext, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));//它可以关掉所要到的界面中间的activity);
                                for (int i = 0; i < mApp.gActivityList.size(); i++) {
                                    mApp.gActivityList.get(i).finish();
                                }
                            } else {
                                Toast.makeText(mContext, "登录失败，" + netRetEntity.getResMsg(), LENGTH_SHORT).show();
                                cacerProgress();
                            }
                        } else {
                            Toast.makeText(mContext, "网络异常，请联系管理员", LENGTH_SHORT).show();
                            cacerProgress();
                        }
                        break;
                    case 2:
                        getRegionDate();
                        break;
                    case 3:
                        NetRetEntity netRetEntity = JSON.parseObject(mNetLoginRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        if (netRetEntity.getResCode() == 1 && "1".equals(netRetEntity.getResData()))
                        {
                            Toast.makeText(mContext,"手机绑定，请先绑定手机",Toast.LENGTH_SHORT).show();
                            //绑定手机
                            startActivity(new Intent(mContext,WXBindPhoneActivity.class).putExtra("openID",mOpenID));
                        }
                        else
                        {
                            //登录成功
                            getRegionDate();
                        }
                        break;
                }
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mPhoneLogin = (TextView) this.findViewById(R.id.textView_activityLogin_phoneLoginTextView);
        mUseRegist = (TextView) this.findViewById(R.id.textView_activityLogin_userRegistTextView);

        mAccountEdit = (EditText) this.findViewById(R.id.tv_activityLogin_accountEdit);
        mPassWordEdit = (EditText) this.findViewById(R.id.tv_activityLogin_passWordEdit);

        mAgreeImg = (ImageView) this.findViewById(R.id.iv_activityLogin_agreeImg);
        //设置是否同意协议图标
        setAgreeImg();
        mAgreeImg.setOnClickListener(new ButtonClick());

        mLogin = (Button) this.findViewById(R.id.button_activityLogin_LoginButton);
        mLogin.setOnClickListener(new ButtonClick());
        mPhoneLogin.setOnClickListener(new ButtonClick());
        mUseRegist.setOnClickListener(new ButtonClick());



        //微信登录
        login_weChat = findViewById(R.id.iv_wxLogin);
        login_weChat.setOnClickListener(new ButtonClick());

        //忘记密码
        login_forget = findViewById(R.id.rv_forgetPwd);
        login_forget.setOnClickListener(new ButtonClick());


    }

    /**
     * 设置是否同意协议图标
     */
    private void setAgreeImg() {
        if (mAgree)
            mAgreeImg.setImageResource(R.mipmap.choice);
        else
            mAgreeImg.setImageResource(R.mipmap.nochoice);
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.textView_activityLogin_phoneLoginTextView:
                    //手机号登录
                    Intent intent = new Intent();
                    intent.setClass(mContext, PhoneLoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.textView_activityLogin_userRegistTextView:
                    //用户注册
                    intent = new Intent();
                    intent.setClass(mContext, UseRegistActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_activityLogin_LoginButton:
                    userLogin();
                    break;
                case R.id.iv_activityLogin_agreeImg:
                    //取反
                    mAgree = !mAgree;
                    setAgreeImg();
                    break;
                //微信登录
                case R.id.iv_wxLogin:
                    weChatLogin();
                    break;
                //忘记密码
                case R.id.rv_forgetPwd:
                    Intent intent1 = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }


    //微信登录
    private void weChatLogin() {
        if (isWeixinAvilible(mContext)) {
            //创建微信api并注册到微信
            WXapi = WXAPIFactory.createWXAPI(mContext, WX_APP_ID);
            WXapi.registerApp(WX_APP_ID);
            //发起登录请求
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            WXapi.sendReq(req);
//            PreferenceUtils.putString(mContext, "loginUserInfo", "LOGIN");
        } else {
            Toast.makeText(mContext, "您还没有安装微信，请先安装微信客户端", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 用户登录
     */
    private void userLogin() {
        //登录
        if (!mAgree) {
            Toast.makeText(mContext, "请先同意用户服务协议", LENGTH_SHORT).show();
            return;
        }
        if (mAccountEdit.getText().toString() == null || "".equals(mAccountEdit.getText().toString())) {
            Toast.makeText(mContext, "帐号不能为空", LENGTH_SHORT).show();
            return;
        }
        if (mPassWordEdit.getText().toString() == null || "".equals(mPassWordEdit.getText().toString())) {
            Toast.makeText(mContext, "密码不能为空", LENGTH_SHORT).show();
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserPhone(mAccountEdit.getText().toString());
        userInfo.setUserPwd(mPassWordEdit.getText().toString());
        userInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        userInfo.setRequestClientType("1");

        //   showLoadingDialog("正在登录");
        //连接网络，登录
        new Thread() {
            public void run() {
                try {
                    mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(userInfo), Constant.SERVICEURL + "patientLoginControlle/loginPatientAppPwdLogin");
//                    cacerProgress();
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetLoginRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                    cacerProgress();
                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }


    /**
     * 获取区域数据
     */
    private void getRegionDate() {
        //连接网络，登录
        //    showLoadingDialog("请稍候...");
        getProgressBar("请稍候....", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
                    provideBasicsRegion.setRegion_parent_id("0");

                    mNetRegionRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideBasicsRegion), Constant.SERVICEURL + "basicDataController/getBasicsRegion");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRegionRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取区域信息失败：" + retEntity.getResMsg());
                        mNetRegionRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //区域数据获取成功
                    mApp.gRegionList = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideBasicsRegion>>() {
                    }.getType());

                    for (int i = 0; i < mApp.gRegionList.size(); i++) {
                        if (mApp.gRegionList.get(i).getRegion_level() == 1) {
                            mApp.gRegionProvideList.add(mApp.gRegionList.get(i));
                        }
                        if (mApp.gRegionList.get(i).getRegion_level() == 2) {
                            mApp.gRegionCityList.add(mApp.gRegionList.get(i));
                        }
                        if (mApp.gRegionList.get(i).getRegion_level() == 3) {
                            mApp.gRegionDistList.add(mApp.gRegionList.get(i));
                        }
                    }
                    cacerProgress();
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRegionRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();

    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

//    /**
//     * 自定义加载loading弹出层(来自String)  *
//     */
//    public void showLoadingDialog(String text) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (text != null) {
//                    mLoadingDialog.setText(text);
//                    if (!mLoadingDialog.isShowing()) {
//                        mLoadingDialog.show();
//                    }
//
//                }
//            }
//        });
//    }


    @Override
    public void onResume() {
        super.onResume();
//        receiveBroadCast = new LoginActivity.ReceiveBroadCast();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("authlogin");
//        getBaseContext().registerReceiver(receiveBroadCast, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        getApplication().unregisterReceiver(receiveBroadCast);
    }


    private void weChatAuth() {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(LoginActivity.this, WX_APP_ID, true);
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_login_duzun";
        api.sendReq(req);
    }


    public void getAccessToken() {
        SharedPreferences WxSp = getApplicationContext()
                .getSharedPreferences(PrefParams.spName, Context.MODE_PRIVATE);
        String code = WxSp.getString(PrefParams.CODE, "");
        final SharedPreferences.Editor WxSpEditor = WxSp.edit();
        Log.d("tag", "-----获取到的code----" + code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + WX_APP_ID
                + "&secret="
                + WX_APPSecret
                + "&code="
                + code
                + "&grant_type=authorization_code";
        Log.d("tag", "--------即将获取到的access_token的地址--------");
        HttpUtil.sendHttpRequest(url, new HttpUtil.HttpCallBackListener() {
            @Override
            public void onFinish(String response) {

                //解析以及存储获取到的信息
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("tag", "-----获取到的json数据1-----" + jsonObject.toString());
                    String access_token = jsonObject.getString("access_token");
                    Log.d("tag", "--------获取到的access_token的地址--------" + access_token);
                    String openid = jsonObject.getString("openid");
                    String refresh_token = jsonObject.getString("refresh_token");

                    if (!access_token.equals("")) {
                        WxSpEditor.putString(PrefParams.ACCESS_TOKEN, access_token);
                        WxSpEditor.apply();
                    }
                    if (!refresh_token.equals("")) {
                        WxSpEditor.putString(PrefParams.REFRESH_TOKEN, refresh_token);
                        WxSpEditor.apply();
                    }
                    if (!openid.equals("")) {
                        WxSpEditor.putString(PrefParams.WXOPENID, openid);
                        WxSpEditor.apply();
                        getPersonMessage(access_token, openid);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override


            public void onError(Exception e) {
                Toast.makeText(getApplication(), "通过code获取数据没有成功", Toast.LENGTH_SHORT).show();
            }

        });
    }

    //获取用户信息
    private void getPersonMessage(String access_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        HttpUtil.sendHttpRequest(url, new HttpUtil.HttpCallBackListener() {

            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("tag", "------获取到的个人信息------" + jsonObject.toString());

                    String openid1 = jsonObject.getString("openid");
                    mOpenID = jsonObject.getString("openid");

                    String nickname = jsonObject.getString("nickname");
                    String sex = jsonObject.getString("sex");
                    String language = jsonObject.getString("language");
                    String city = jsonObject.getString("city");
                    String province = jsonObject.getString("province");
                    String country = jsonObject.getString("country");
                    String headimgurl = jsonObject.getString("headimgurl");
                    String unionid = jsonObject.getString("unionid");

                    sp = getSharedPreferences("loginUser", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("nickname",nickname);
                    edit.putString("headimgurl",headimgurl);
                    edit.commit();
                    //调用微信登录接口登录
                    WXUserInfo wxUserInfo = new WXUserInfo();
                    wxUserInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
                    wxUserInfo.setRequestClientType("1");
                    wxUserInfo.setOpenId(jsonObject.getString("openid"));
                    wxUserInfo.setUnionId(jsonObject.getString("unionid"));
                    wxUserInfo.setNickName(jsonObject.getString("nickname"));
                    wxUserInfo.setGender(jsonObject.getString("sex"));
                    wxUserInfo.setAvatarUrl(jsonObject.getString("headimgurl"));
                    wxUserInfo.setProvince(jsonObject.getString("province"));
                    wxUserInfo.setCity(jsonObject.getString("city"));
                    wxUserInfo.setCountry(jsonObject.getString("country"));
//                    wxUserInfo.setPrivilege(mApp.loginDoctorPosition);
                    mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(wxUserInfo), Constant.SERVICEURL + "patientLoginControlle/loginPatientAppWechatGrant");
                    mHandler.sendEmptyMessage(3);
                } catch (Exception e) {
                    e.printStackTrace();
                    NetRetEntity netRetEntity = new NetRetEntity();
                    netRetEntity.setResCode(0);
                    netRetEntity.setResMsg("授权失败，请重试");
                    mNetLoginRetStr = JSON.toJSONString(netRetEntity);
                }
                mHandler.sendEmptyMessage(3);
            }


            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplication(), "通过openid获取数据没有成功", Toast.LENGTH_SHORT).show();
            }

        });
    }


//    class ReceiveBroadCast extends BroadcastReceiver {
//
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            getAccessToken();
////            Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
////            mApp.saveUserInfo();
////            startActivity(intent1);
//        }
//    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }



}
