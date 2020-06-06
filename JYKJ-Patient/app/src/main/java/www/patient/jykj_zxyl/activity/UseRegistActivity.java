package www.patient.jykj_zxyl.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.R;

/**
 * 用户注册
 */
public class UseRegistActivity extends AppCompatActivity {
    private                 Context                     mContext;
    private                 UseRegistActivity           mActivity;
    private                 JYKJApplication             mApp;
    private                 EditText                    mPhoneNum;              //手机号
    private                 EditText                    mVCode;                 //验证码
    private                 TextView                    mGetVCode;              //获取验证码
    private                 EditText                    mPassWord;              //密码

    private                 String                      mNetRetStr;                 //返回字符串
    private                 Handler                     mHandler;
    public                  ProgressDialog              mDialogProgress =null;
    private                 Button                      mRegistBt;          //注册

    private                 boolean                     mAgree = true;                     //是否同意协议，默认同意
    private                 ImageView                   mAgreeImg;                  //同意协议图标
    private                 String                      mSmsToken;                  //短信验证码token

    private                 TimerTask                   mTask;
    private                 int                         mInitVCodeTime = 120;
    private                 Timer                       mTimer;

    private RelativeLayout mBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        mActivity = this;
        mContext = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        mApp.gActivityList.add(this);
        initLayout();
        initHandler();
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
                                Toast.makeText(mContext, "注册成功，请立即登录", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(mContext,LoginActivity.class));
                                for (int i = 0; i < mApp.gActivityList.size(); i++)
                                {
                                    mApp.gActivityList.get(i).finish();
                                }
                            }
                            else
                            {
                                Toast.makeText(mContext, "注册失败，"+netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
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
    /**
     * 初始化布局
     */
    private void initLayout() {
        mPhoneNum = (EditText)this.findViewById(R.id.et_activityRegist_phoneNumEdit);
        mVCode = (EditText)this.findViewById(R.id.et_activityRegist_vCodeEdit);
        mPassWord = (EditText)this.findViewById(R.id.et_activityRegist_passwordEdit);

        mAgreeImg = (ImageView) this.findViewById(R.id.iv_activityRegist_agreeImg);
        //设置是否同意协议图标
        setAgreeImg();
        mAgreeImg.setOnClickListener(new ButtonClick());

        mRegistBt = (Button)this.findViewById(R.id.bt_activityRegist_registBt);
        mGetVCode = (TextView)this.findViewById(R.id.tv_activityRetist_getVCodeText);
        mGetVCode.setOnClickListener(new ButtonClick());
        mRegistBt.setOnClickListener(new ButtonClick());

        mBack = (RelativeLayout)this.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                case R.id.tv_activityRetist_getVCodeText:
                    getVCode();
                    break;

                case R.id.bt_activityRegist_registBt:
                    userRegist();
                    break;

                case R.id.iv_activityRegist_agreeImg:
                    //取反
                    mAgree = !mAgree;
                    setAgreeImg();
                    break;

            }
        }
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
        ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
        provideViewSysUserPatientInfoAndRegion.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewSysUserPatientInfoAndRegion.setRequestClientType("1");
        provideViewSysUserPatientInfoAndRegion.setSendUserLinkPhone(mPhoneNum.getText().toString());
//        ParmentEntity parmentEntity = new ParmentEntity();
//        parmentEntity.setJsonDataInfo(new Gson().toJson(provideViewSysUserPatientInfoAndRegion));
        getProgressBar("请稍候","正在获取验证码");
        //连接网络，登录
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideViewSysUserPatientInfoAndRegion),Constant.SERVICEURL+"patientLoginControlle/loginPatientAppRegisterSmsVerify");
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
     * 用户注册
     */
    private void userRegist() {

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
            Toast.makeText(mContext,"请先输入验证码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPassWord.getText().toString() == null || "".equals(mPassWord.getText().toString()))
        {
            Toast.makeText(mContext,"请先输入密码",Toast.LENGTH_SHORT).show();
            return;
        }

        ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
        provideViewSysUserPatientInfoAndRegion.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewSysUserPatientInfoAndRegion.setRequestClientType("1");
        provideViewSysUserPatientInfoAndRegion.setUserPhone(mPhoneNum.getText().toString());
        provideViewSysUserPatientInfoAndRegion.setUserRegisterSmsVerify(mVCode.getText().toString());
        provideViewSysUserPatientInfoAndRegion.setUserPwd(mPassWord.getText().toString());
        provideViewSysUserPatientInfoAndRegion.setTokenSmsVerify(mSmsToken);

        getProgressBar("请稍候","正在注册");
        //连接网络，注册
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideViewSysUserPatientInfoAndRegion),Constant.SERVICEURL+"patientLoginControlle/loginPatientAppRegister");
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
