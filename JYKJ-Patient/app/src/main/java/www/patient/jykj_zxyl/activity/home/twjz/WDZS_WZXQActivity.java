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

import entity.ProvideInteractOrderInfo;
import entity.wdzs.ProvideInteractClinicRecordWriteState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.ZhlyReplyActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_BLXJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_WZZLActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_ZDMSActivity;
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

    private LinearLayout mLlConsulationInfo;                //问诊资料
    private LinearLayout mLlDiagnosisMsg;                //诊后留言
    private LinearLayout mLlAssumeDesc;                //臆断描述
    private LinearLayout mLlMedicalRecord;                //病历小结
    private LinearLayout mLlPrescriptionNotes;                //处方笺

    private TextView mTvConsulationInfo;                //问诊资料
    private TextView mTvDiagnosisMsg;                //诊后留言
    private TextView mTvAssumeDesc;                //臆断描述
    private TextView mTvMedicalRecord;                //病历小结
    private TextView mTvPrescriptionNotes;                //处方笺

    private ProvideInteractClinicRecordWriteState mProvideInteractClinicRecordWriteState;

    private void setLayoutDate() {
        Integer interrogationState = mProvideInteractClinicRecordWriteState.getInterrogationState();
        if (interrogationState != null) {
            if (interrogationState == 0) {
                mTvConsulationInfo.setText("未填写");
                mTvConsulationInfo.setTextColor(getResources().getColor(R.color.colorRed));

            } else if (interrogationState == 1) {
                mTvConsulationInfo.setText("已填写");
                mTvConsulationInfo.setTextColor(getResources().getColor(R.color.color_799dfe));
            }
        }else{
            mTvConsulationInfo.setText("未填写");
            mTvConsulationInfo.setTextColor(getResources().getColor(R.color.colorRed));
        }
        Integer messageState = mProvideInteractClinicRecordWriteState.getMessageState();
        if (messageState!=null) {
            if (messageState==0) {
                mTvDiagnosisMsg.setText("未填写");
                mTvDiagnosisMsg.setTextColor(getResources().getColor(R.color.colorRed));
            }else if(messageState==1){
                mTvDiagnosisMsg.setText("已填写");
                mTvDiagnosisMsg.setTextColor(getResources().getColor(R.color.color_799dfe));
            }else if(messageState==2){
                mTvDiagnosisMsg.setText("已回复");
                mTvDiagnosisMsg.setTextColor(getResources().getColor(R.color.color_799dfe));
            }

        }else{
            mTvDiagnosisMsg.setText("问诊人未提交");
            mTvDiagnosisMsg.setTextColor(getResources().getColor(R.color.colorRed));
        }

        Integer diagState = mProvideInteractClinicRecordWriteState.getDiagState();
        if (diagState!=null) {
            if (diagState==0) {
                mTvAssumeDesc.setText("未填写");
                mTvAssumeDesc.setTextColor(getResources().getColor(R.color.colorRed));
            }else if(diagState==1){
                mTvAssumeDesc.setText("已填写");
                mTvAssumeDesc.setTextColor(getResources().getColor(R.color.color_799dfe));
            }
        }else{
            mTvAssumeDesc.setText("未填写");
            mTvAssumeDesc.setTextColor(getResources().getColor(R.color.colorRed));
        }

        Integer treatmentState = mProvideInteractClinicRecordWriteState.getTreatmentState();
        if (treatmentState!=null) {
            if (treatmentState==0) {
                mTvMedicalRecord.setText("未填写");
                mTvMedicalRecord.setTextColor(getResources().getColor(R.color.colorRed));
            }else if(treatmentState==1){
                mTvMedicalRecord.setText("已填写");
                mTvMedicalRecord.setTextColor(getResources().getColor(R.color.color_799dfe));
            }
        }else{
            mTvMedicalRecord.setText("未填写");
            mTvMedicalRecord.setTextColor(getResources().getColor(R.color.colorRed));
        }
        Integer prescribeState = mProvideInteractClinicRecordWriteState.getPrescribeState();
        if (prescribeState!=null) {
            if (prescribeState==0) {
                mTvPrescriptionNotes.setText("未填写");
                mTvPrescriptionNotes.setTextColor(getResources().getColor(R.color.colorRed));
            }else if(prescribeState==1){
                mTvPrescriptionNotes.setText("已填写");
                mTvPrescriptionNotes.setTextColor(getResources().getColor(R.color.color_799dfe));
            }

        }else{
            mTvPrescriptionNotes.setText("未填写");
            mTvPrescriptionNotes.setTextColor(getResources().getColor(R.color.colorRed));
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
        mLlConsulationInfo = this.findViewById(R.id.ll_consulation_info);
        mLlConsulationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProvideInteractOrderInfo mProvideInteractOrderInfo = new ProvideInteractOrderInfo();
                mProvideInteractOrderInfo.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
                startActivity(new Intent(mContext, WDYS_JZJL_WZZLActivity.class).putExtra("order", mProvideInteractOrderInfo.getOrderCode()));
            }
        });
        mLlDiagnosisMsg =  this.findViewById(R.id.ll_diagnosis_msg);
        mLlDiagnosisMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ZhlyReplyActivity.class).putExtra("wzxx",
                        mProvideViewInteractOrderTreatmentAndPatientInterrogation));
            }
        });
        mLlAssumeDesc =  this.findViewById(R.id.ll_assume_desc);
        mLlAssumeDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProvideInteractOrderInfo mProvideInteractOrderInfo = new ProvideInteractOrderInfo();
                mProvideInteractOrderInfo.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
                startActivity(new Intent(mContext, WDYS_JZJL_ZDMSActivity.class).putExtra("provideInteractOrderInfo",
                        mProvideInteractOrderInfo));
            }
        });
        mLlMedicalRecord =  this.findViewById(R.id.ll_medical_record);
        mLlMedicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProvideInteractOrderInfo mProvideInteractOrderInfo = new ProvideInteractOrderInfo();
                mProvideInteractOrderInfo.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
                startActivity(new Intent(mContext, WDYS_JZJL_BLXJActivity.class).putExtra("provideInteractOrderInfo",mProvideInteractOrderInfo));
            }
        });
        mLlPrescriptionNotes = (LinearLayout) this.findViewById(R.id.ll_prescription_notes);
        mLlPrescriptionNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TWJZ_KJCFActivity.class).putExtra("wzxx", mProvideViewInteractOrderTreatmentAndPatientInterrogation));
            }
        });

        mTvConsulationInfo = this.findViewById(R.id.tv_consulation_info);
        mTvDiagnosisMsg =  this.findViewById(R.id.tv_diagnosis_msg);
        mTvAssumeDesc = this.findViewById(R.id.tv_assume_desc);
        mTvMedicalRecord =  this.findViewById(R.id.tv_medical_record);
        mTvPrescriptionNotes =  this.findViewById(R.id.tv_prescription_notes);
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
