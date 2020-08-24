package www.patient.jykj_zxyl.myappointment.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.myappointment.dialog.CancelContractDialog;
import www.patient.jykj_zxyl.util.ActivityUtil;

public class AncelAppointmentActivity extends Activity  implements View.OnClickListener{
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.lin_Detect)
    LinearLayout linDetect;
    @BindView(R.id.ed_termination)
    EditText edTermination;
    @BindView(R.id.btn_send)
    Button btnSend;
    private MyAppointmentActivity mActivity;
    private JYKJApplication mApp;
    private Context mContext;
    private Handler mHandler;
    private String mNetRetStr;
    public ProgressDialog mDialogProgress = null;
    private CancelContractDialog cancelContractDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ancelappointment_layout);
        ButterKnife.bind(this);
        mContext = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        cancelContractDialog = new CancelContractDialog(this);
        initLayout();
        initHandler();
    }

    private void initHandler() {
   mHandler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case 1:

                   break;
               case R.id.btn_send:
                   commit();
                   break;
               case R.id.lin_Detect:
                   cancelContractDialog.show();
                   break;
           }
       }
   };
    }

    private void commit() {
        getProgressBar("请稍候...", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("loginDoctorPosition", "108.93425^34.23053");
                    map.put("mainDoctorCode", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserCode());
                    map.put("mainDoctorName", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
//                    map.put("signCode", orderId);
//                    map.put("signNo", singNO);
//                    map.put("mainPatientCode", patientCode);
//                    map.put("mainUserName", nickName);
//                    // 	解约原因分类编码
//                    map.put("refuseReasonClassCode", mCancelContractBean.getAttrCode() + "");
//                    // 	解约原因分类名称
//                    map.put("refuseReasonClassName", mCancelContractBean.getAttrName());
//                    map.put("refuseRemark", ed_termination.getText().toString());
//                    map.put("confimresult", "0");
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map),
                            Constant.SERVICEURL + "doctorSignControlle/operTerminationConfim");
                    Log.e("tag", "run: 取消预约" + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);

                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    private void initLayout() {
        //提交
        btnSend.setOnClickListener(this);
        llBack.setOnClickListener(this);
        linDetect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }

    /**
     * 获取进度条
     * 获取进度条
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
