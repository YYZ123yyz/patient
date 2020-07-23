package www.patient.jykj_zxyl.activity.home.twjz;

import android.annotation.SuppressLint;
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

import java.util.HashMap;

import entity.wdzs.ProvideInteractClinicRecordWriteState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.ZhlyReplyActivity;
import www.patient.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.http.ParameUtil;

/**
 * 问诊详情
 */
public class WDZS_WZXQActivity extends AppCompatActivity {

    private Context mContext;
    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;                 //返回字符串

    private WDZS_WZXQActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;

    private ProvideViewInteractOrderTreatmentAndPatientInterrogation mProvideViewInteractOrderTreatmentAndPatientInterrogation;
    private LinearLayout mBack;

    private LinearLayout mZHLYHF;                //诊后留言
    private LinearLayout mZDMS;                //诊断描述
    private LinearLayout mJZXJ;                //就诊小结
    private LinearLayout mKJCF;                //开具处方
    private LinearLayout mJZJL;                //就诊记录

    private TextView mZHLYHFText;                //诊后留言
    private TextView mZDMSText;                //诊断描述
    private TextView mJZXJText;                //就诊小结
    private TextView mKJCFText;                //开具处方
    private TextView mJZJLText;                //就诊记录

    private ProvideInteractClinicRecordWriteState mProvideInteractClinicRecordWriteState;

    private void setLayoutDate() {
        if (mProvideInteractClinicRecordWriteState.getMessageState() == null || mProvideInteractClinicRecordWriteState.getMessageState() == 0) {
            mZHLYHFText.setText("问诊人未提交");
            mZHLYHFText.setTextColor(getResources().getColor(R.color.colorRed));
        } else if (mProvideInteractClinicRecordWriteState.getMessageState() == 1) {
            mZHLYHFText.setText("问诊人已提交");
            mZHLYHFText.setTextColor(getResources().getColor(R.color.textColor_vo));
        } else if (mProvideInteractClinicRecordWriteState.getMessageState() == 0) {
            mZHLYHFText.setText("已回复");
            mZHLYHFText.setTextColor(getResources().getColor(R.color.groabColor));
        }

        if (mProvideInteractClinicRecordWriteState.getDiagState() == null || mProvideInteractClinicRecordWriteState.getDiagState() == 0) {
            mZDMSText.setText("未填写");
            mZDMSText.setTextColor(getResources().getColor(R.color.colorRed));
        } else if (mProvideInteractClinicRecordWriteState.getDiagState() == 1) {
            mZDMSText.setText("已填写");
            mZDMSText.setTextColor(getResources().getColor(R.color.groabColor));
        }

        if (mProvideInteractClinicRecordWriteState.getTreatmentState() == null || mProvideInteractClinicRecordWriteState.getTreatmentState() == 0) {
            mJZXJText.setText("未填写");
            mJZXJText.setTextColor(getResources().getColor(R.color.colorRed));
        } else if (mProvideInteractClinicRecordWriteState.getTreatmentState() == 1) {
            mJZXJText.setText("已填写");
            mJZXJText.setTextColor(getResources().getColor(R.color.groabColor));
        }


        if (mProvideInteractClinicRecordWriteState.getPrescribeState() == null || mProvideInteractClinicRecordWriteState.getPrescribeState() == 0) {
            mKJCFText.setText("未填写");
            mKJCFText.setTextColor(getResources().getColor(R.color.colorRed));
        } else if (mProvideInteractClinicRecordWriteState.getPrescribeState() == 1) {
            mKJCFText.setText("已填写");
            mKJCFText.setTextColor(getResources().getColor(R.color.groabColor));
        }

        if (mProvideInteractClinicRecordWriteState.getMedicalState() == null || mProvideInteractClinicRecordWriteState.getMedicalState() == 0) {
            mJZJLText.setText("未填写");
            mJZJLText.setTextColor(getResources().getColor(R.color.colorRed));
        } else if (mProvideInteractClinicRecordWriteState.getMedicalState() == 1) {
            mJZJLText.setText("已填写");
            mJZJLText.setTextColor(getResources().getColor(R.color.groabColor));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wzxq);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideViewInteractOrderTreatmentAndPatientInterrogation = (ProvideViewInteractOrderTreatmentAndPatientInterrogation) getIntent().getSerializableExtra("wzxx");
        initLayout();
        initHandler();
        getData();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout) this.findViewById(R.id.iv_back_left);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mZHLYHF = (LinearLayout) this.findViewById(R.id.zhlyhf);
        mZHLYHF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ZhlyReplyActivity.class).putExtra("wzxx", mProvideViewInteractOrderTreatmentAndPatientInterrogation));
            }
        });
        mZDMS = (LinearLayout) this.findViewById(R.id.zdms);
        mZDMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TWJZ_ZDMSActivity.class).putExtra("wzxx", mProvideViewInteractOrderTreatmentAndPatientInterrogation));
            }
        });
        mJZXJ = (LinearLayout) this.findViewById(R.id.jzxj);
        mJZXJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TWJZ_JZXJActivity.class).putExtra("wzxx", mProvideViewInteractOrderTreatmentAndPatientInterrogation));
            }
        });
        mKJCF = (LinearLayout) this.findViewById(R.id.kjcf);
        mKJCF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TWJZ_KJCFActivity.class).putExtra("wzxx", mProvideViewInteractOrderTreatmentAndPatientInterrogation));
            }
        });
        mJZJL = (LinearLayout) this.findViewById(R.id.jzjl);
        mJZJL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TWJZ_JZJLActivity.class).putExtra("wzxx", mProvideViewInteractOrderTreatmentAndPatientInterrogation));
            }
        });

        mZHLYHFText = (TextView) this.findViewById(R.id.tv_zhlyhf);
        mZDMSText = (TextView) this.findViewById(R.id.tv_zdms);
        mJZXJText = (TextView) this.findViewById(R.id.tv_jzxj);
        mKJCFText = (TextView) this.findViewById(R.id.tv_kjcf);
        mJZJLText = (TextView) this.findViewById(R.id.tv_jzjl);
    }


    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {

                            mProvideInteractClinicRecordWriteState = JSON.parseObject(netRetEntity.getResJsonData(), ProvideInteractClinicRecordWriteState.class);
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
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideInteractClinicRecordWriteState provideInteractClinicRecordWriteState = new ProvideInteractClinicRecordWriteState();
        provideInteractClinicRecordWriteState.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideInteractClinicRecordWriteState.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractClinicRecordWriteState.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideInteractClinicRecordWriteState.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
        provideInteractClinicRecordWriteState.setRequestClientType("1");

        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginPatientPosition",mApp.loginDoctorPosition);
        hashMap.put("requestClientType","1");
        hashMap.put("operPatientCode",mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        hashMap.put("operPatientName",mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        hashMap.put("orderCode",mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(hashMap);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "PatientMyDoctorControlle/searchIndexMyDoctorNotSigningResTreatmentDetail");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
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
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

}
