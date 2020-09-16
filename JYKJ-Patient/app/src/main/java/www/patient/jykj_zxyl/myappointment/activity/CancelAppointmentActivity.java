package www.patient.jykj_zxyl.myappointment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.utils.ToastUtils;
import com.hyphenate.easeui.ui.ChatActivity;

import java.util.List;

import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientCommitBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientDoctorInfoBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientListBean;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.ReservationContract;
import www.patient.jykj_zxyl.myappointment.Presenter.ResevationPresenter;
import www.patient.jykj_zxyl.myappointment.dialog.CancelContractDialog;
import www.patient.jykj_zxyl.util.ActivityUtil;

/*
* 取消预约
* */
public class CancelAppointmentActivity extends AbstractMvpBaseActivity<ReservationContract.View,
        ResevationPresenter> implements ReservationContract.View{

    private CancelAppointmentActivity mActivity;
    private JYKJApplication mApp;
    private Context mContext;
    public ProgressDialog mDialogProgress = null;
    private CancelContractDialog cancelContractDialog;
    private String ed;
    private String reserveCode;
    private BaseReasonBean mCancelContractBean;
    private LinearLayout llBack;
    private RelativeLayout rl;
    private TextView tvName;
    private LinearLayout linDetect;
    private Button btnSend;
    private EditText edTermination;
    private String reserveCode1;
    private String doctorName;
    private String doctorCode;
    private OrderDetialBean orderDetialBean;
    private String doctorUrl;
    private String signCode;
    private String appointment;
    private String endTime;
    private String aClass;
    private String type;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        ButterKnife.bind(this);
        mApp = (JYKJApplication) getApplication();
        mActivity= CancelAppointmentActivity.this;
        cancelContractDialog = new CancelContractDialog(this);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.ancelappointment_layout;
    }
    @Override
    protected void initView() {
        super.initView();
        ActivityUtil.setStatusBarMain(mActivity);
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            reserveCode = extras.getString("reserveCode");
            doctorName = extras.getString("doctorName");
            doctorCode = extras.getString("doctorCode");
            doctorUrl = extras.getString("doctorUrl");
            signCode = extras.getString("SignCode");
//        //预约时间
            appointment = extras.getString("Appointment");
//        //结束时间
            endTime = extras.getString("endTime");
            //预约项目
            aClass = extras.getString("class");
            //预约类型
            type = extras.getString("type");
        }

        llBack = findViewById(R.id.ll_back);
        rl = findViewById(R.id.rl);
        tvName = findViewById(R.id.tv_name);
        linDetect = findViewById(R.id.lin_Detect);
        edTermination = findViewById(R.id.ed_termination);
        btnSend = findViewById(R.id.btn_send);
        addListener();
    }
    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchSignPatientDoctorOrderRequest(reserveCode,doctorCode,doctorName);
    }



    private void addListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelContractDialog.show();

            }
        });
        cancelContractDialog.setOnClickItemListener(new CancelContractDialog.OnClickItemListener() {
            @Override
            public void onClickItem(BaseReasonBean cancelContractBean) {
                tvName.setText(cancelContractBean.getAttrName());
                Log.e("", "onClickItem: "+cancelContractBean.getAttrName() );
                mCancelContractBean = cancelContractBean;
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed = edTermination.getText().toString();
                if(TextUtils.isEmpty(ed)){
                    ToastUtils.showToast("解约描述不能为空");
                    return;
                }if(TextUtils.isEmpty(mCancelContractBean.getAttrName())){
                    ToastUtils.showToast("解约原因不能为空");
                    return;
                }
                        mPresenter.sendReservationCancelRequest(reserveCode,
                                mCancelContractBean.getAttrCode()+"",mCancelContractBean.getAttrName(),ed,CancelAppointmentActivity.this);
            }
        });

    }

    @Override
    public void getReservationTiteResult(List<ReservePatientDoctorInfoBean> reservePatientDoctorInfoBean) {

    }

    @Override
    public void getReservationListResult(List<ReservePatientListBean> reservePatientListBeans) {

    }

    @Override
    public void getReservationListResultError(String msg) {

    }

    @Override
    public void getReservationCommitConfimResult(String confim, String msg) {

    }

    @Override
    public void getReservationCommitIdCardCheckResult(String msg) {

    }

    @Override
    public void getReservationCommitResult(ReservePatientCommitBean reservePatientCommitBeans) {

    }

    @Override
    public void getReservationCommitResultError(String msg) {

    }

    @Override
    public void getReservationunpaidResultError(ReservePatientCommitBean reservePatientCommitBeans) {

    }

    @Override
    public void getReservationDailog() {

    }


    @Override
    public void getReservationClassResult(List<BaseReasonBean> baseReasonBeans) {

    }

    @Override
    public void getReservationIDCardResult(String msg) {

    }

    @Override
    public void getReservationIDCardResultError(String msg) {

    }

    /**
     *  取消预约成功
     */
    @Override
    public void getReservationCancelResult(boolean isSucess, String msg) {
        if (isSucess) {
            if (ActivityStackManager.getInstance().exists(ChatActivity.class)) {
                ActivityStackManager.getInstance().finish(ChatActivity.class);
            }
            this.finish();
            Intent intent = new Intent();
            intent.setClass(this, ChatActivity.class);
            intent.putExtra("userCode", doctorName);
            intent.putExtra("userName", doctorCode);
            intent.putExtra("doctorUrl",doctorUrl );
            intent.putExtra("patientUrl", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            intent.putExtra("operDoctorName", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
            Bundle bundle=new Bundle();
            OrderMessage appointment = getOrderMessage("appointment", "2");
            appointment.setImageUrl( mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            bundle.putSerializable("orderMsg", appointment);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "提交失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取订单信息
     * @param messageType 消息类型
     * @param orderType 操作类型
     * @return orderMessage
     */
    private OrderMessage getOrderMessage(String messageType, String orderType) {

        OrderMessage orderMessage=  new OrderMessage(signCode,messageType,orderType,appointment,endTime,aClass,type);

        return orderMessage;

    }


    /**
     *
     * @param orderDetialBean 订单信息
     */
    @Override
    public void getSearchSignPatientDoctorOrderResult(OrderDetialBean orderDetialBean) {
        this.orderDetialBean=orderDetialBean;
    //    handleOrderListResult(orderDetialBean);

    }

    @Override
    public void showLoading(int code) {

    }

}
