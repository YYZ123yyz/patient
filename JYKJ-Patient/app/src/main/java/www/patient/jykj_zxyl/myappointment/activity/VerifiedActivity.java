package www.patient.jykj_zxyl.myappointment.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.order.RefundApplyContract;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientCommitBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientDoctorInfoBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientListBean;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.ReservationContract;
import www.patient.jykj_zxyl.myappointment.Presenter.ResevationPresenter;
import www.patient.jykj_zxyl.myappointment.bean.IDCardBean;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.ToastUtils;

/*
 * 实名认证
 * */
public class VerifiedActivity extends AbstractMvpBaseActivity<ReservationContract.View,
        ResevationPresenter> implements ReservationContract.View {
    private JYKJApplication mApp;
    private String idNumber;
    private RelativeLayout riBack;
    private EditText verifiedName;
    private EditText verifiedIDNumber;
    private TextView verifiedCommit;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(VerifiedActivity.this);
        riBack = findViewById(R.id.ri_back);
        verifiedName = findViewById(R.id.verified_name);
        verifiedIDNumber = findViewById(R.id.verified_ID_number);
        verifiedCommit = findViewById(R.id.verified_commit);
        addListener();
    }

    private void addListener() {
        riBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        verifiedCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idNumber = verifiedIDNumber.getText().toString();
                String name = verifiedName.getText().toString();
                if (TextUtils.isEmpty(idNumber)) {
                    Toast.makeText(VerifiedActivity.this, "请输入患者身份证号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(VerifiedActivity.this, "请输入患者名字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(idNumber)) {
                    verifyID(idNumber);
                }
                mPresenter.sendReservationIDCardRequest(mApp.loginDoctorPosition, mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(), mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(), idNumber);
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_verified;
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
    public void getReservationDailog() {

    }


    @Override
    public void getReservationClassResult(List<BaseReasonBean> baseReasonBeans) {

    }

    /**]
     * 验证成功
     * @param msg
     */
    @Override
    public void getReservationIDCardResult(String msg) {
        ToastUtils.showToast(msg);
    }

    /**
     * 验证失败
     * @param msg
     */
    @Override
    public void getReservationIDCardResultError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void getReservationCancelResult(boolean isSucess, String msg) {

    }

    @Override
    public void getSearchSignPatientDoctorOrderResult(OrderDetialBean orderDetialBean) {

    }

    @Override
    public void showLoading(int code) {

    }

    /**
     * 验证输入的身份证号是否合法
     *
     * @param str 为用户输入的身份证号码
     * @return
     */
    public boolean verifyID(String str) {
        if (str.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
            return true;
        } else {
            Toast.makeText(VerifiedActivity.this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}