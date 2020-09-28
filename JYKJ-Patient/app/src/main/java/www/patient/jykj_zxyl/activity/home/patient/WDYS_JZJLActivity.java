package www.patient.jykj_zxyl.activity.home.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entity.ProvideInteractClinicRecordWriteState;
import entity.ProvideInteractOrderInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.ZhlyReplyActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_JZJLActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_JZXJActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_KJCFActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_ZDMSActivity;
import www.patient.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

/**
 * 问诊详情
 */
public class WDYS_JZJLActivity extends AppCompatActivity {

    private                 Context                 mContext;
    public                  ProgressDialog              mDialogProgress =null;
    private             String                              mNetRetStr;                 //返回字符串

    private WDYS_JZJLActivity mActivity;
    private                 Handler                 mHandler;
    private                 JYKJApplication         mApp;

    private ProvideViewInteractOrderTreatmentAndPatientInterrogation    mProvideViewInteractOrderTreatmentAndPatientInterrogation;
    private                 LinearLayout            mBack;

    private                 LinearLayout            li_wzzl;                //问诊资料
    private                 LinearLayout            li_zhly;                //诊后留言
    private                 LinearLayout            li_zdms;                //诊断描述
    private                 LinearLayout            li_blxj;                //病历小结
    private                 LinearLayout            li_cfq;                //就诊记录

    private                 TextView                tv_wzzl;                //问诊资料
    private                 TextView                tv_zhly;                //诊后留言
    private                 TextView                tv_zdms;                //诊断描述
    private                 TextView                tv_blxj;                //病例小结
    private                 TextView                tv_cfq;                //处方签

    private                 ProvideInteractClinicRecordWriteState mProvideInteractClinicRecordWriteState;
    private                 ProvideInteractOrderInfo            mProvideInteractOrderInfo;

    private void setLayoutDate() {
        if ( mProvideInteractClinicRecordWriteState.getInterrogationState() == null || mProvideInteractClinicRecordWriteState.getInterrogationState() == 0)
        {
            tv_wzzl.setText("未填写");
            tv_wzzl.setTextColor(getResources().getColor(R.color.colorRed));
        }
        else if (mProvideInteractClinicRecordWriteState.getInterrogationState() == 1)
        {
            tv_wzzl.setText("已填写");
            tv_wzzl.setTextColor(getResources().getColor(R.color.wdys_jzxq_jzzt));
        }

        if ( mProvideInteractClinicRecordWriteState.getMessageState() == null || mProvideInteractClinicRecordWriteState.getMessageState() == 0)
        {
            tv_zhly.setText("未填写");
            tv_zhly.setTextColor(getResources().getColor(R.color.colorRed));
        }
        else if (mProvideInteractClinicRecordWriteState.getMessageState() == 1)
        {
            tv_zhly.setText("已填写");
            tv_zhly.setTextColor(getResources().getColor(R.color.wdys_jzxq_jzzt));
        }

        if (mProvideInteractClinicRecordWriteState.getDiagState() == null || mProvideInteractClinicRecordWriteState.getDiagState() == 0)
        {
            tv_zdms.setText("未填写");
            tv_zdms.setTextColor(getResources().getColor(R.color.colorRed));
        }
        else if (mProvideInteractClinicRecordWriteState.getDiagState() == 1)
        {
            tv_zdms.setText("已填写");
            tv_zdms.setTextColor(getResources().getColor(R.color.wdys_jzxq_jzzt));
        }


        if ( mProvideInteractClinicRecordWriteState.getTreatmentState() == null || mProvideInteractClinicRecordWriteState.getTreatmentState() == 0)
        {
            tv_blxj.setText("未填写");
            tv_blxj.setTextColor(getResources().getColor(R.color.colorRed));
        }
        else if (mProvideInteractClinicRecordWriteState.getTreatmentState() == 1)
        {
            tv_blxj.setText("已填写");
            tv_blxj.setTextColor(getResources().getColor(R.color.wdys_jzxq_jzzt));
        }

        if (mProvideInteractClinicRecordWriteState.getPrescribeState() == null || mProvideInteractClinicRecordWriteState.getPrescribeState() == 0)
        {
            tv_cfq.setText("未填写");
            tv_cfq.setTextColor(getResources().getColor(R.color.colorRed));
        }
        else if (mProvideInteractClinicRecordWriteState.getPrescribeState() == 1)
        {
            tv_cfq.setText("已填写");
            tv_cfq.setTextColor(getResources().getColor(R.color.wdys_jzxq_jzzt));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jzjl);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideInteractOrderInfo = (ProvideInteractOrderInfo) getIntent().getSerializableExtra("provideInteractOrderInfo");
        initLayout();
        initHandler();
        getData();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout)this.findViewById(R.id.iv_back_left);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        mZHLYHF = (LinearLayout)this.findViewById(R.id.zhlyhf);
//        mZHLYHF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(mContext,ZhlyReplyActivity.class).putExtra("wzxx",mProvideViewInteractOrderTreatmentAndPatientInterrogation));
//            }
//        });

        li_wzzl = (LinearLayout)this.findViewById(R.id.li_wzzl);
        li_wzzl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,WDYS_JZJL_WZZLActivity.class).putExtra("order",mProvideInteractOrderInfo.getOrderCode()));
            }
        });

        li_zhly = (LinearLayout)this.findViewById(R.id.li_zhly);
        li_zhly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,WDYS_JZJL_ZHLYActivity.class).putExtra("provideInteractOrderInfo",mProvideInteractOrderInfo));
            }
        });

        li_zdms = (LinearLayout)this.findViewById(R.id.li_zdms);
        li_zdms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,WDYS_JZJL_ZDMSActivity.class).putExtra("provideInteractOrderInfo",mProvideInteractOrderInfo));
            }
        });
        li_blxj = (LinearLayout)this.findViewById(R.id.li_blxj);
        li_blxj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,WDYS_JZJL_BLXJActivity.class).putExtra("provideInteractOrderInfo",mProvideInteractOrderInfo));
            }
        });
        li_cfq = (LinearLayout)this.findViewById(R.id.li_cfq);
        li_cfq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,WDYS_JZJL_CFQActivity.class).putExtra("provideInteractOrderInfo",mProvideInteractOrderInfo));
            }
        });
        tv_wzzl = (TextView)this.findViewById(R.id.tv_wzzl);
        tv_zhly = (TextView)this.findViewById(R.id.tv_zhly);
        tv_zdms = (TextView)this.findViewById(R.id.tv_zdms);
        tv_blxj = (TextView)this.findViewById(R.id.tv_blxj);
        tv_cfq = (TextView)this.findViewById(R.id.tv_cfq);
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            mProvideInteractClinicRecordWriteState = JSON.parseObject(netRetEntity.getResJsonData(),ProvideInteractClinicRecordWriteState.class);
                            if (mProvideInteractClinicRecordWriteState != null)
                                setLayoutDate();
                        }

                        break;
                }
            }
        };
    }



    /**
     * 设置数据
     */
    private void getData() {
        getProgressBar("请稍候","正在获取数据。。。");
        ProvideInteractClinicRecordWriteState provideInteractClinicRecordWriteState = new ProvideInteractClinicRecordWriteState();
        provideInteractClinicRecordWriteState.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideInteractClinicRecordWriteState.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractClinicRecordWriteState.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideInteractClinicRecordWriteState.setRequestClientType("1");
        provideInteractClinicRecordWriteState.setOrderCode(mProvideInteractOrderInfo.getOrderCode());
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideInteractClinicRecordWriteState);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorNotSigningResTreatmentDetail");
                    String string01 = Constant.SERVICEURL+"msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string+string01);
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
