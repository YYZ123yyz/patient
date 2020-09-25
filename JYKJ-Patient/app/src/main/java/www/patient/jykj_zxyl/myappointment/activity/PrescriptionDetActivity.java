package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.PrescriptionDetBean;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.PrescriptionDetContract;
import www.patient.jykj_zxyl.myappointment.Presenter.PrescriptionDetPresenter;
import www.patient.jykj_zxyl.myappointment.adapter.Item_PrescriptionAdapter;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.DateUtils;

/**
 * Created by G on 2020/9/11 17:37
 */
public class PrescriptionDetActivity extends AbstractMvpBaseActivity<PrescriptionDetContract.View,
        PrescriptionDetPresenter> implements PrescriptionDetContract.View {


    @BindView(R.id.ri_back)
    RelativeLayout riBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.request_type)
    TextView requestType;
    @BindView(R.id.tv_treatmentnumber)
    TextView tvTreatmentnumber;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_clinicaldiagnosis)
    TextView tvClinicaldiagnosis;
    @BindView(R.id.my_recycleview)
    RecyclerView myRecycleview;
    @BindView(R.id.doctor_sign)
    TextView doctorSign;
    @BindView(R.id.tv_doctrosignature)
    TextView tvDoctrosignature;
    @BindView(R.id.drug_money)
    TextView drugMoney;
    @BindView(R.id.center_part)
    View centerPart;
    @BindView(R.id.download)
    Button download;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.lin_confirm)
    RelativeLayout linConfirm;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_Department)
    TextView tvDepartment;
    private String recordCode;
    private JYKJApplication mApp;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_prescription_det;
    }

    @Override
    protected void initView() {
        super.initView();
        ActivityUtil.setStatusBarMain(this);
        mApp = (JYKJApplication) getApplication();
        Intent intent = getIntent();
        recordCode = intent.getStringExtra("recordCode");
    }

    @Override
    protected void initData() {
        super.initData();
        //    mPresenter.sendPrescriptionDetRequest(mApp.loginDoctorPosition,"1",mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(), mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(),recordCode);
        mPresenter.sendPrescriptionDetRequest("123123^123123213", "1",
                "4dcc513a5dd34fa09a7a229a175e5c11", "Pan", "0101202009181608445105661560");
    }

    @Override
    public void getPrescriptionDetSucess(PrescriptionDetBean prescriptionDetBean) {
        if (prescriptionDetBean != null) {
            //姓名
            tvName.setText(prescriptionDetBean.getPatientName());
            //性别
            int patientGender = prescriptionDetBean.getPatientGender();
            if(patientGender==1){
              tvGender.setText("男");
            }else if(patientGender==2){
                tvGender.setText("女");
            }
            //年龄
            tvAge.setText(prescriptionDetBean.getPatientAge()+"");
            //科室
            String departmentSecondName = prescriptionDetBean.getDepartmentSecondName();
            if(TextUtils.isEmpty(departmentSecondName)){
                tvDepartment.setText(prescriptionDetBean.getDepartmentName());
            }else{
                tvDepartment.setText(departmentSecondName);
            }
            //诊疗号
            tvTreatmentnumber.setText(prescriptionDetBean.getTreatmentCardNum());
            //处方日期
            long createDate = prescriptionDetBean.getCreateDate();
            String stringTimeOfYMD = DateUtils.getStringTimeOfYMD(createDate);
            tvTime.setText(stringTimeOfYMD);
            //临床诊断
            tvClinicaldiagnosis.setText(prescriptionDetBean.getDiagnosisName());
            //医生签章
            tvDoctrosignature.setText(prescriptionDetBean.getDoctorName());
            //金额
            drugMoney.setText(prescriptionDetBean.getPrescribeSumAmount()+"");
         //   new Item_PrescriptionAdapter(prescriptionDetBean.getInteractOrderPrescribeList().get(0).getPrescribeInfo())
        }
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}