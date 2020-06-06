package www.patient.jykj_zxyl.activity.home.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.ProvideInteractOrderInfo;
import entity.ProvideInteractOrderMedical;
import entity.wdzs.ProvideBasicsImg;
import entity.wdzs.ProvideInteractPatientMessage;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

/**
 * 我的医生 ==》就诊记录 =》病历小结
 */
public class WDYS_JZJL_BLXJActivity extends AppCompatActivity {

    public              ProgressDialog mDialogProgress =null;

    private             Context                                 mContext;
    private             Handler                                 mHandler;
    private WDYS_JZJL_BLXJActivity mActivity;
    private              JYKJApplication                            mApp;

    private             String                              mNetRetStr;                 //返回字符串
    private             ProvideViewInteractOrderTreatmentAndPatientInterrogation mProvideViewInteractOrderTreatmentAndPatientInterrogation;
    private             ProvideInteractPatientMessage mProvideInteractPatientMessage;

    private             List<ProvideBasicsImg>                      mProvideBasicsImg = new ArrayList<>();
    private             String                              mGetImgNetRetStr;                 //获取图片返回字符串

    private             TextView                                mMessageReply;                  //留言回复内容
    private             TextView                                mCommit;                        //
    private ProvideInteractOrderMedical mProvideInteractOrderMedical;

    private             TextView                                mTitleName;

    private             TextView                                mZSEdit;                    //主诉
    private             TextView                                mXBSEdit;                    //现病史
    private             TextView                                mJZCTEdit;                    //就诊查体
    private             TextView                                mFZJCEdit;                    //辅助检查
    private             TextView                                mCBZLJHEdit;                    //初步诊疗计划

    private ProvideInteractOrderInfo mProvideInteractOrderInfo;                          //订单信息


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdys_jzjl_blxj);
        mContext =this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideInteractOrderInfo = (ProvideInteractOrderInfo) getIntent().getSerializableExtra("provideInteractOrderInfo");
        initLayout();
        initListener();
        initHandler();
        getData();
    }

    private void initLayout() {
        mTitleName = (TextView)this.findViewById(R.id.tv_userNameTitle);

        mZSEdit = (TextView) this.findViewById(R.id.tv_jzjlZS);
        mXBSEdit = (TextView) this.findViewById(R.id.tv_jzjlxbs);
        mJZCTEdit = (TextView) this.findViewById(R.id.tv_jzjlJZCT);
        mFZJCEdit = (TextView) this.findViewById(R.id.tv_jzjlFZJC);
        mCBZLJHEdit = (TextView) this.findViewById(R.id.tv_jzjlCBZLJH);

//        mCommit = (TextView)this.findViewById(R.id.tv_commit);
//        mCommit.setOnClickListener(new ButtonClick());

    }



    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
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
                            mProvideInteractOrderMedical = JSON.parseObject(netRetEntity.getResJsonData(), ProvideInteractOrderMedical.class);
                            if (mProvideInteractOrderMedical != null)
                            {
                                showLayoutDate();
                            }

                        }
                        break;

                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mGetImgNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {


                        }

                        break;

                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /**
     * 设置数据
     */
    private void getData() {
        getProgressBar("请稍后","正在获取数据。。。");
        ProvideInteractOrderMedical provideInteractOrderMedical = new ProvideInteractOrderMedical();
        provideInteractOrderMedical.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideInteractOrderMedical.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractOrderMedical.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideInteractOrderMedical.setOrderCode(mProvideInteractOrderInfo.getOrderCode());
        provideInteractOrderMedical.setRequestClientType("1");
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideInteractOrderMedical);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorNotSigningResMedical");
                    String string01 = Constant.SERVICEURL+"doctorInteractDataControlle/searchMyClinicDetailResOrderDiag";
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


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_commit:
                    commit();
                    break;

            }
        }
    }
    private void showLayoutDate() {
        mTitleName = (TextView)this.findViewById(R.id.tv_userNameTitle);

        mTitleName.setText("就诊记录");
        if (mProvideInteractOrderMedical.getChiefComplaint() == null || "".equals(mProvideInteractOrderMedical.getChiefComplaint()))
            mZSEdit.setHint("未设置");
        else
            mZSEdit.setText(mProvideInteractOrderMedical.getChiefComplaint());

        if (mProvideInteractOrderMedical.getMedicalExamination() == null || "".equals(mProvideInteractOrderMedical.getMedicalExamination()))
            mXBSEdit.setHint("未设置");
        else
            mXBSEdit.setText(mProvideInteractOrderMedical.getMedicalExamination());

        if (mProvideInteractOrderMedical.getPresentIllness() == null || "".equals(mProvideInteractOrderMedical.getPresentIllness()))
            mJZCTEdit.setHint("未设置");
        else
            mJZCTEdit.setText(mProvideInteractOrderMedical.getPresentIllness());

        if (mProvideInteractOrderMedical.getAuxiliaryCheck() == null || "".equals(mProvideInteractOrderMedical.getAuxiliaryCheck()))
            mFZJCEdit.setHint("未设置");
        else
            mFZJCEdit.setText(mProvideInteractOrderMedical.getAuxiliaryCheck());

        if (mProvideInteractOrderMedical.getTreatmentPlanCode() == null || "".equals(mProvideInteractOrderMedical.getTreatmentPlanCode()))
            mCBZLJHEdit.setHint("未设置");
        else
            mCBZLJHEdit.setText(mProvideInteractOrderMedical.getTreatmentPlanCode());




    }

    /**
     * 提交rang
     */
    private void commit() {


//        mProvideInteractOrderMedical.setLoginDoctorPosition(mApp.loginDoctorPosition);
//        mProvideInteractOrderMedical.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
//        mProvideInteractOrderMedical.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
//        mProvideInteractOrderMedical.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
//        mProvideInteractOrderMedical.setChiefComplaint(mZSEdit.getText().toString());
//        mProvideInteractOrderMedical.setPresentIllness(mXBSEdit.getText().toString());
//        mProvideInteractOrderMedical.setMedicalExamination(mJZCTEdit.getText().toString());
//        mProvideInteractOrderMedical.setAuxiliaryCheck(mFZJCEdit.getText().toString());
//        mProvideInteractOrderMedical.setTreatmentPlanCode(mCBZLJHEdit.getText().toString());
//        if (mProvideInteractOrderMedical.getChiefComplaint() == null || "".equals(mProvideInteractOrderMedical.getChiefComplaint()))
//        {
//            Toast.makeText(mContext,"主诉必填",Toast.LENGTH_SHORT).show();
//            return;
//        }
////        provideInteractOrderMedical.setTreatmentContent(mJZXJContent.getText().toString());
//        getProgressBar("请稍候","正在提交数据。。。");
//        new Thread(){
//            public void run(){
//                try {
//                    String string = new Gson().toJson(mProvideInteractOrderMedical);
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"doctorInteractDataControlle/operUpdMyClinicDetailByMedicalRecord");
//                    String string01 = Constant.SERVICEURL+"doctorInteractDataControlle/operUpdMyClinicDetailByMedicalContent";
//                    System.out.println(string+string01);
//                } catch (Exception e) {
//                    NetRetEntity retEntity = new NetRetEntity();
//                    retEntity.setResCode(0);
//                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
//                    mNetRetStr = new Gson().toJson(retEntity);
//                    e.printStackTrace();
//                }
//                mHandler.sendEmptyMessage(2);
//            }
//        }.start();
    }

    private void initListener() {
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
