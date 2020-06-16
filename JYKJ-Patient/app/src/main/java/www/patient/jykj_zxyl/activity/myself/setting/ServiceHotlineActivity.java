package www.patient.jykj_zxyl.activity.myself.setting;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import entity.mySelf.ProvideViewSysUserDoctorInfoAndHospital;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.StrUtils;

/**
 * 设置 == > 客服热线
 */
public class ServiceHotlineActivity extends AppCompatActivity {


    private                 String                      mNetRetStr;                 //返回字符串
    public                  ProgressDialog              mDialogProgress =null;
    private                 Context                     mContext;
    private ServiceHotlineActivity mActivity;
    private                 JYKJApplication             mApp;
    private                 Handler                     mHandler;
    private                 TextView                    mAboutText;
    private                 LinearLayout                mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_setting_serviceholtline);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();
        getData();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        if(StrUtils.defaultStr(retEntity.getResJsonData()).length()>0) {
                            mAboutText.setText(Html.fromHtml(retEntity.getResJsonData()));
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
        mAboutText = (TextView)this.findViewById(R.id.tv_about);
        mBack = (LinearLayout)this.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"/doctorPersonalSetControlle/getDoctorServiceHotline");
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
}
