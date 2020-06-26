package www.patient.jykj_zxyl.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.PhoneFormatCheckUtils;

/**
 * 手机号登录Activity
 */
public class PhoneLoginActivity extends AppCompatActivity {


    public                  ProgressDialog              mDialogProgress =null;

    private                 TextView                    mPhoneLogin;                //手机号登录
    private                 Context                     mContext;
    private                 PhoneLoginActivity          mActivity;
    private                 JYKJApplication             mApp;
    private                 EditText                    mPhoneNum;              //手机号
    private                 EditText                    mVCode;                 //验证码
    private                 TextView                    mGetVCode;              //获取验证码

    private                 String                      mNetRetStr;                 //返回字符串
    private                 Handler                     mHandler;
    private                 TextView                    mUserRegist;            //用户注册
    private                 Button                      mLogin;                 //登录

    private                 boolean                     mAgree = true;                     //是否同意协议，默认同意
    private                 ImageView                   mAgreeImg;                  //同意协议图标

    private                 TimerTask                   mTask;
    private                 int                         mInitVCodeTime = 120;
    private                 Timer                       mTimer;
    private                 String                      mSmsToken;                  //短信验证码token

    private                 RelativeLayout              mBack;

    private TextView tv_yhxy;           //用户协议
    private TextView tv_yszc;           //隐私政策
    private String  inputPhoneNum = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();
    }

    /**
     * 启动定时器
     */
    private void startTask(){
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (mInitVCodeTime > 0)
                {
                    mHandler.sendEmptyMessage(10);

                }
                else
                {
                    mHandler.sendEmptyMessage(11);
                }
            }
        };
        mTimer.schedule(mTask, 0, 1000);
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals(""))
                        {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            if (netRetEntity.getResCode() == 1)
                            {
                                startTask();
                                mSmsToken = netRetEntity.getResTokenData();
                                Toast.makeText(mContext,"获取成功，请注意接收短信",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(mContext,"获取失败，"+netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(mContext,"网络连接异常，请联系管理员",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);

                            if (netRetEntity.getResCode() == 1) {
                                UserInfo userInfo = new UserInfo();

                                mApp.mProvideViewSysUserPatientInfoAndRegion = JSON.parseObject(netRetEntity.getResJsonData(), ProvideViewSysUserPatientInfoAndRegion.class);
                                userInfo.setUserPhone(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());
                                userInfo.setUserPwd(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());
                                mApp.mLoginUserInfo = userInfo;
                                mApp.saveUserInfo();
                                Toast.makeText(mContext, "恭喜，登录成功", Toast.LENGTH_SHORT).show();
                                //登录IM
                                mApp.loginIM();
                                startActivity(new Intent(mContext,MainActivity.class));
                                for (int i = 0; i < mApp.gActivityList.size(); i++)
                                {
                                    mApp.gActivityList.get(i).finish();
                                }
                            }
                            else
                            {
                                Toast.makeText(mContext, "登录失败，"+netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 10:
                        mInitVCodeTime--;
                        mGetVCode.setText(mInitVCodeTime+"");
                        mGetVCode.setClickable(false);
                        break;
                    case 11:
                        mGetVCode.setText("重新获取");
                        mGetVCode.setClickable(true);
                        mInitVCodeTime = 120;
                        mTimer.cancel();
                        mTask.cancel();
                        break;

                }
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (RelativeLayout)this.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mPhoneNum = (EditText)this.findViewById(R.id.et_activityPhoneLogin_phoneNumEdit);

        mVCode = (EditText)this.findViewById(R.id.et_activityPhoneLogin_vCodeEdit);
        mUserRegist = (TextView)this.findViewById(R.id.textView_activityPhoneLogin_userRegistTextView);
        mLogin = (Button)this.findViewById(R.id.bt_activityPhoneLogin_loginBt);
        mGetVCode = (TextView)this.findViewById(R.id.textView_activityPhoneLogin_getVcodeTextView);


        mAgreeImg = (ImageView) this.findViewById(R.id.iv_activityPhoneLogin_agreeImg);
        //设置是否同意协议图标
        setAgreeImg();
        mAgreeImg.setOnClickListener(new ButtonClick());

        mGetVCode.setOnClickListener(new ButtonClick());
        mUserRegist.setOnClickListener(new ButtonClick());
        mLogin.setOnClickListener(new ButtonClick());

        tv_yhxy = (TextView)this.findViewById(R.id.tv_yhxy);
        tv_yszc = (TextView)this.findViewById(R.id.tv_yszc);
        tv_yhxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhoneLoginActivity.this,YHXYWebActivity.class));
            }
        });

        tv_yszc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhoneLoginActivity.this,YSZCWebActivity.class));
            }
        });

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
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.textView_activityPhoneLogin_getVcodeTextView:
                    getVCode();
                    break;
                case R.id.textView_activityPhoneLogin_userRegistTextView:
                    startActivity(new Intent(mContext,UseRegistActivity.class));
                    break;
                case R.id.bt_activityPhoneLogin_loginBt:


                    userLogin();
                    break;
                case R.id.iv_activityPhoneLogin_agreeImg:
                    //取反
                    mAgree = !mAgree;
                    setAgreeImg();
                    break;
            }
        }
    }

    /**
     * 登录
     */
    private void userLogin() {
        //登录
        if (!mAgree)
        {
            Toast.makeText(mContext,"请先同意用户服务协议",Toast.LENGTH_SHORT).show();
            return;
        }

        if (mPhoneNum.getText().toString() == null || "".equals(mPhoneNum.getText().toString()))
        {
            Toast.makeText(mContext,"请先输入手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mVCode.getText().toString() == null || "".equals(mVCode.getText().toString()))
        {
            Toast.makeText(mContext,"请输入验证码",Toast.LENGTH_SHORT).show();
            return;
        }
        //判断手机号格式
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(mPhoneNum.getText().toString()))
        {
            Toast.makeText(mContext,"手机号格式不正确，请输入正确手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
        provideViewSysUserPatientInfoAndRegion.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewSysUserPatientInfoAndRegion.setRequestClientType("1");
        provideViewSysUserPatientInfoAndRegion.setUserPhone(mPhoneNum.getText().toString());
        provideViewSysUserPatientInfoAndRegion.setTokenSmsVerify(mSmsToken);
        provideViewSysUserPatientInfoAndRegion.setUserLoginSmsVerify(mVCode.getText().toString());
        getProgressBar("请稍候","正在登录");
        //连接网络，登录
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideViewSysUserPatientInfoAndRegion),Constant.SERVICEURL+"patientLoginControlle/loginPatientAppVerifyLogin");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    /**
     * 获取验证码
     */
    private void getVCode(){
        //获取验证码
        if (mPhoneNum.getText().toString() == null || "".equals(mPhoneNum.getText().toString()))
        {
            Toast.makeText(mContext,"请先输入手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        //判断手机号格式
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(mPhoneNum.getText().toString()))
        {
            Toast.makeText(mContext,"手机号格式不正确，请输入正确手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
        provideViewSysUserPatientInfoAndRegion.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewSysUserPatientInfoAndRegion.setRequestClientType("1");
        provideViewSysUserPatientInfoAndRegion.setSendUserLinkPhone(mPhoneNum.getText().toString());

        getProgressBar("请稍候","正在获取验证码");
        //连接网络，登录
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideViewSysUserPatientInfoAndRegion),Constant.SERVICEURL+"patientLoginControlle/loginPatientAppVerifyLoginSmsVerify");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }
}
