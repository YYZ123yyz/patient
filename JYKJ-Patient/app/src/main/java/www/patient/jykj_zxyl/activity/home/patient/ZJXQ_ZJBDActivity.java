package www.patient.jykj_zxyl.activity.home.patient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entity.DoctorBindParment;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

/**
 * 专家详情 == >绑定专家
 */
public class ZJXQ_ZJBDActivity extends AppCompatActivity {
    private LinearLayout llBack;

    public ProgressDialog mDialogProgress = null;

    private Context mContext;                                       //
    private ZJXQ_ZJBDActivity mActivity;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串
    private String mNetRetPLStr;                 //返回字符串
    private Handler mHandler;
    private EditText bdsq;
    private TextView tj;

    private String mUrl;                   //url
    private String mDoctorQRCode;          //医生二维码

    private ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend;

    private void initView() {

        llBack = (LinearLayout) findViewById(R.id.ll_back);

        llBack.setOnClickListener(new ButtonClick());

        bdsq = (EditText) this.findViewById(R.id.bdsq);
        tj = (TextView) this.findViewById(R.id.tj);
        tj.setOnClickListener(new ButtonClick());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zjxq_tjzj);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        provideViewDoctorExpertRecommend = (ProvideViewDoctorExpertRecommend) getIntent().getSerializableExtra("provideViewDoctorExpertRecommend");
        mUrl = getIntent().getStringExtra("url");
        mDoctorQRCode = getIntent().getStringExtra("doctorQRCode");
        initView();
        initHandler();
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        ZJXQ_ZJBDActivity.this.finish();
                        break;

                }
            }
        };
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


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;
                case R.id.tj:
                    if (mUrl == null || "".equals(mUrl))
                        commit();
                    else
                        commitQRCode();
                    break;


            }
        }
    }


    /**
     * 扫码绑定
     */
    private void commitQRCode() {
        DoctorBindParment doctorBindParment = new DoctorBindParment();
        doctorBindParment.setLoginPatientPosition(mApp.loginDoctorPosition);
        doctorBindParment.setRequestClientType("1");
        doctorBindParment.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        doctorBindParment.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        doctorBindParment.setDoctorQrCode(mDoctorQRCode);
        doctorBindParment.setApplyReason(bdsq.getText().toString());
        new Thread() {
            public void run() {
                try {

                    String string = new Gson().toJson(doctorBindParment);
                    String url = Constant.SERVICEURL + mUrl;
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, url);
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
     * 绑定
     */
    private void commit() {
        DoctorBindParment doctorBindParment = new DoctorBindParment();
        doctorBindParment.setLoginPatientPosition(mApp.loginDoctorPosition);
        doctorBindParment.setRequestClientType("1");
        doctorBindParment.setOperPatientCode(mApp.loginDoctorPosition);
        doctorBindParment.setOperPatientName("1");
        doctorBindParment.setOperPatientLinkPhone(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());
        doctorBindParment.setBindingDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
        doctorBindParment.setBindingDoctorName(provideViewDoctorExpertRecommend.getUserName());
        if (bdsq.getText().toString() == null || "".equals(bdsq.getText().toString())) {
            Toast.makeText(mContext, "请填写绑定申请", Toast.LENGTH_SHORT).show();
        }
        doctorBindParment.setBindingApplyInfo(bdsq.getText().toString());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(doctorBindParment);
                    String urlStr = Constant.SERVICEURL + "patientSearchDoctorControlle/operIndexExpertRecommendDoctorBinding";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "patientSearchDoctorControlle/operIndexExpertRecommendDoctorBinding");
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

}
