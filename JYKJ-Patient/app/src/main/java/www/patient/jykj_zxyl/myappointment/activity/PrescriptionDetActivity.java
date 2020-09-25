package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.base_bean.PrescriptionDetBean;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.PrescriptionDetContract;
import www.patient.jykj_zxyl.myappointment.Presenter.PrescriptionDetPresenter;
import www.patient.jykj_zxyl.myappointment.adapter.Item_PrescriptionAdapter;
import www.patient.jykj_zxyl.myappointment.adapter.PrescriptionAdapter;
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
    private Item_PrescriptionAdapter item_prescriptionAdapter;
    private LinearLayoutManager layoutManager;
    private static final int DRUG_TYPE_NOMAL = 0;
    private static final int DRUG_TYPE_START = 1;
    private static final int DRUG_TYPE_END = 2;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_prescription_det;
    }

    @Override
    protected void initView() {
        super.initView();
        ActivityUtil.setStatusBarMain(this);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        myRecycleview.setHasFixedSize(true);

        myRecycleview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        Intent intent = getIntent();
        recordCode = intent.getStringExtra("recordCode");
        //    mPresenter.sendPrescriptionDetRequest(mApp.loginDoctorPosition,"1",mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(), mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(),recordCode);
        mPresenter.sendPrescriptionDetRequest("123123^123123213", "1",
                "4dcc513a5dd34fa09a7a229a175e5c11", "Pan", "0101202009181608445105661560");
    }

    @Override
    public void getPrescriptionDetSucess(PrescriptionDetBean prescriptionDetBean) {
        if (prescriptionDetBean != null) {
          List<PrescriptionDetBean.InteractOrderPrescribeListBean> list=  new ArrayList<>();
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


            List<PrescriptionDetBean.InteractOrderPrescribeListBean> interactOrderPrescribeList = prescriptionDetBean.getInteractOrderPrescribeList();
            List<PrescriptionDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean> prescribeInfoBeans = new ArrayList<>();
            for (int i = 0; i < interactOrderPrescribeList.size(); i++) {

                List<PrescriptionDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean> prescribeInfo = interactOrderPrescribeList.get(i).getPrescribeInfo();
                if (prescribeInfo.size() > 1) { //有一组
                    for (int j = 0; j < prescribeInfo.size(); j++) {
                        if (j == 0) { //一组开头
                            prescribeInfo.get(j).setType(DRUG_TYPE_START);
                        } else if (j == (prescribeInfo.size()) - 1) {//一组结尾
                            prescribeInfo.get(j).setType(DRUG_TYPE_END);
                        } else {//中间
                            prescribeInfo.get(j).setType(DRUG_TYPE_NOMAL);
                        }
                    }
                } else { //没有一组
                    prescribeInfo.get(0).setType(DRUG_TYPE_NOMAL);
                }
                prescribeInfoBeans.addAll(prescribeInfo);
            }


            PrescriptionAdapter prescriptionAdapter = new PrescriptionAdapter(R.layout.item_recyclerview, prescribeInfoBeans);
            myRecycleview.setAdapter(prescriptionAdapter);
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