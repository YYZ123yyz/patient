package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import netService.DownloadService;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.CheckListBean;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.CheckListContract;
import www.patient.jykj_zxyl.myappointment.Presenter.CheckListPresenter;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.CircleImageView;
import www.patient.jykj_zxyl.util.DateUtils;

/**
 * Created by G on 2020/9/11 16:36
 */
public class CheckListActivity extends AbstractMvpBaseActivity<CheckListContract.View,
        CheckListPresenter> implements CheckListContract.View {


    @BindView(R.id.ri_back)
    RelativeLayout riBack;
    @BindView(R.id.userHead)
    CircleImageView userHead;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.usergendder)
    TextView usergendder;
    @BindView(R.id.userage)
    TextView userage;
    @BindView(R.id.userDoctro)
    TextView userDoctro;
    @BindView(R.id.userDepartment)
    TextView userDepartment;
    @BindView(R.id.Department)
    TextView Department;
    @BindView(R.id.chief)
    TextView chief;
    @BindView(R.id.tv_com)
    TextView tvCom;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_look)
    TextView tvLook;
    @BindView(R.id.do_type)
    TextView doType;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.heat_cheak_pro)
    TextView heatCheakPro;
    @BindView(R.id.heat_cheak_part)
    TextView heatCheakPart;
    @BindView(R.id.heat_cheak_total)
    TextView heatCheakTotal;
    @BindView(R.id.request_type)
    TextView requestType;
    @BindView(R.id.request_doctor)
    TextView requestDoctor;
    TextView requestDoctorBlood;
    @BindView(R.id.center_part)
    View centerPart;
    @BindView(R.id.download)
    Button download;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.lin_confirm)
    RelativeLayout linConfirm;
    @BindView(R.id.tv_inspectionname)
    TextView tvInspectionname;
    @BindView(R.id.do_type_value)
    TextView doTypeValue;
    @BindView(R.id.img_one)
    ImageView imgOne;
    @BindView(R.id.project)
    TextView project;
    @BindView(R.id.img_two)
    ImageView imgTwo;
    @BindView(R.id.tv_Location)
    TextView tvLocation;
    @BindView(R.id.img_three)
    ImageView imgThree;
    @BindView(R.id.tv_purpose)
    TextView tvPurpose;
    @BindView(R.id.tv_applicationdepartment)
    TextView tvApplicationdepartment;
    private String recordCode;
    private JYKJApplication mApp;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_checklist;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        recordCode = intent.getStringExtra("recordCode");
        Log.e("TAG", "initView: " + recordCode);
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(CheckListActivity.this);
    }

    @OnClick({R.id.confirm,R.id.download})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.confirm:
                Intent intent2 = new Intent(CheckListActivity.this, DownloadService.class);
                startService(intent2);
                break;
            case R.id.download:

                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        //    mPresenter.sendCheckListRequest(mApp.loginDoctorPosition,"1",mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(), mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(),recordCode);
        mPresenter.sendCheckListRequest("123123^123123213", "1", "4dcc513a5dd34fa09a7a229a175e5c11", "Pan", "0101202009181608445105661560");
    }

    @Override
    public void getCheckListSucess(CheckListBean checkListBean) {
        if (checkListBean != null) {
            //用户头像
            try {
                int avatarResId = Integer.parseInt(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
                Glide.with(this).load(avatarResId).into(userHead);
            } catch (Exception e) {
                //use default avatar
                Glide.with(this).load(checkListBean.getPatientLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(userHead);
            }
            userName.setText(checkListBean.getPatientName());
            //性别
            int patientGender = checkListBean.getPatientGender();
            if (patientGender == 1) {
                usergendder.setText("男");
            } else if (patientGender == 2) {
                usergendder.setText("女");
            }
            //年龄
            userage.setText(checkListBean.getPatientAge() + "岁");
            //门诊号
            userDoctro.setText(checkListBean.getTreatmentCardNum());
            //申请时间
            long createDate = checkListBean.getCreateDate();
            String stringTimeOfYMD = DateUtils.getStringTimeOfYMD(createDate);
            userDepartment.setText(stringTimeOfYMD);
            //临床诊断
            chief.setText(checkListBean.getDiagnosisName());
            //主诉
            tvCom.setText(checkListBean.getChiefComplaint());
            //病史
            tvRecord.setText(checkListBean.getViewMedicalHistory());
            //查看情况
            tvLook.setText(checkListBean.getMedicalExamination());
            //类型
            CheckListBean.InteractOrderInspectionListBean interactOrderInspectionListBean = checkListBean.getInteractOrderInspectionList().get(0);
            int inspectionType = interactOrderInspectionListBean.getInspectionType();
            //title
            if (inspectionType == 10) {
                tvInspectionname.setText(interactOrderInspectionListBean.getInspectionParentName() + "  " + "检查申请单");
                //第二个
                heatCheakPart.setText(interactOrderInspectionListBean.getSampleOrLocationName());
                //第三个
                heatCheakTotal.setText(interactOrderInspectionListBean.getInspectionTarget());
                //第-个
                heatCheakPro.setText(interactOrderInspectionListBean.getInspectionName());

            } else {
                tvInspectionname.setText(interactOrderInspectionListBean.getInspectionParentName() + "  " + "检验申请单");
                project.setText("标本");
                tvLocation.setText("检验项目");
                tvPurpose.setText("检验目的");
                //第二个
                heatCheakPart.setText(interactOrderInspectionListBean.getSampleOrLocationName());
                //第三个
                heatCheakTotal.setText(interactOrderInspectionListBean.getInspectionTarget());
                //第-个
                heatCheakPro.setText(interactOrderInspectionListBean.getInspectionName());

            }
            //执行科室

                doTypeValue.setText(interactOrderInspectionListBean.getApplyDepartmentName());

            //金额
            payMoney.setText(interactOrderInspectionListBean.getMoneys() + "");
          //申请科室
            if(TextUtils.isEmpty(checkListBean.getDepartmentSecondName())){
                tvApplicationdepartment.setText(checkListBean.getDepartmentName());
            }else{
                tvApplicationdepartment.setText(checkListBean.getDepartmentSecondName());
            }
            //申请医生
            requestDoctor.setText(checkListBean.getDoctorName());

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
