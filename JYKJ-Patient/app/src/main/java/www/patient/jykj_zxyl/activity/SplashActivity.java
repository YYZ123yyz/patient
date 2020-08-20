package www.patient.jykj_zxyl.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;

public class SplashActivity extends AppCompatActivity {
    private JYKJApplication mApp;
    public ProgressDialog mDialogProgress = null;
    private String mNetLoginRetStr;                 //登录返回字符串
    private String mNetRegionRetStr;                 //获取返回字符串
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ActivityUtil.setStatusBarMain(this);
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun) {
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            jumpToWelcomeActivity();
        } else {
            jumpToLoginActivity();
        }
        initHandler();

    }

    private void jumpToWelcomeActivity() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }

    private void jumpToLoginActivity() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                autoLogin();
            }
        }, 2000);

    }

    /**
     * 自动登录
     */
    private void autoLogin() {
//        if (mApp.mLoginUserInfo != null && mApp.mLoginUserInfo.getUserPhone() != null &&
//                mApp.mLoginUserInfo.getUserPwd() != null && !"".equals(mApp.mLoginUserInfo.getUserPhone())
//                && !"".equals(mApp.mLoginUserInfo.getUserPhone())) {
//            userLogin();
//        }else{
//            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
        if (mApp.mProvideViewSysUserPatientInfoAndRegion != null) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            mApp.saveUserInfo();
            mApp.loginIM();
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 用户登录
     */
    private void userLogin() {
        ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
        provideViewSysUserPatientInfoAndRegion.setUserPhone(mApp.mLoginUserInfo.getUserPhone());
        provideViewSysUserPatientInfoAndRegion.setUserPwd(mApp.mLoginUserInfo.getUserPwd());
        provideViewSysUserPatientInfoAndRegion.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewSysUserPatientInfoAndRegion.setRequestClientType("1");

        String str = "jsonDataInfo=" + new Gson().toJson(provideViewSysUserPatientInfoAndRegion);
        System.out.println(str);
        getProgressBar("请稍候", "正在登录");
        //连接网络，登录
        new Thread() {
            public void run() {
                try {
                    mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideViewSysUserPatientInfoAndRegion), Constant.SERVICEURL + "patientLoginControlle/loginPatientAppPwdLogin");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetLoginRetStr = new Gson().toJson(retEntity);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        cacerProgress();
                        if (mNetLoginRetStr != null && !mNetLoginRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetLoginRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                UserInfo userInfo = new UserInfo();
                                userInfo.setUserPhone(mApp.mLoginUserInfo.getUserPhone());
                                userInfo.setUserPwd(mApp.mLoginUserInfo.getUserPwd());
                                mApp.mLoginUserInfo = userInfo;
                                mApp.mProvideViewSysUserPatientInfoAndRegion = JSON.parseObject(netRetEntity.getResJsonData(), ProvideViewSysUserPatientInfoAndRegion.class);
//                                mApp.mProvideViewSysUserPatientInfoAndRegion.setDoctorCode("dd0500f4e9684678aad9ee00000001");
//                                mApp.mProvideViewSysUserPatientInfoAndRegion.setQrCode("JY0100HZ200111180041000001");
                                mApp.saveUserInfo();
                                Toast.makeText(SplashActivity.this, "恭喜，登录成功", Toast.LENGTH_SHORT).show();
                                //登录IM
                                mApp.loginIM();
//                                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                                for (int i = 0; i < mApp.gActivityList.size(); i++) {
                                    mApp.gActivityList.get(i).finish();
                                }
                                startActivity(new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));//它可以关掉所要到的界面中间的activity);
//                                finish();
                            } else {
                                Toast.makeText(SplashActivity.this, "登录失败，" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(SplashActivity.this, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        getRegionDate();
                        break;
                }
            }
        };
    }

    /**
     * 获取区域数据
     */
    private void getRegionDate() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在加载数据");
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

}
