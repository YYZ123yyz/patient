package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import netService.DownloadService;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.MedicalRecordContract;
import www.patient.jykj_zxyl.myappointment.adapter.MedicalRecordDrugAdapter;
import www.patient.jykj_zxyl.presenter.MedicalRecordPresenter;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.CircleImageView;
import www.patient.jykj_zxyl.util.DateUtils;


/*
 * 病历
 *
 * Drug_Rv_Adapter
 *
 * */
public class MedicalRecordActivity extends AbstractMvpBaseActivity<MedicalRecordContract.View, MedicalRecordPresenter>
        implements MedicalRecordContract.View {
    @BindView(R.id.ri_back)
    RelativeLayout riBack;
    @BindView(R.id.lin_chief)
    LinearLayout linChief;
    @BindView(R.id.lin_history)
    LinearLayout linHistory;
    @BindView(R.id.lin_past)
    LinearLayout linPast;
    @BindView(R.id.lin_examination)
    LinearLayout linExamination;
    @BindView(R.id.lin_look)
    LinearLayout linLook;
    @BindView(R.id.lin_suggest)
    LinearLayout linSuggest;
    @BindView(R.id.treatment)
    TextView treatment;
    @BindView(R.id.lin_suggest_1)
    LinearLayout linSuggest1;
    @BindView(R.id.lin_checklist)
    LinearLayout linChecklist;
    @BindView(R.id.dispaly_fullpayment)
    ImageView dispalyFullpayment;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.share)
    Button share;
    @BindView(R.id.download)
    Button download;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.lin_prescriptionnote)
    LinearLayout linPrescriptionnote;
    private String gennder;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout diagFlow; //临床诊断
    @BindView(R.id.flowlayout_check)
    TagFlowLayout checkFlow;//检查检验
    @BindView(R.id.flowlayout_prescr)
    TagFlowLayout prescrFlow;//处方笺
    @BindView(R.id.lin_chief_msg)
    LinearLayout chiefMsg;
    @BindView(R.id.dispaly_Chief)
    ImageView ivChief;
    @BindView(R.id.userHead)
    CircleImageView userHead;//头像
    @BindView(R.id.userName)
    TextView patientName;//患者名
    @BindView(R.id.usergendder)
    TextView userGennder;//性别
    @BindView(R.id.userage)
    TextView userAger;//年龄
    @BindView(R.id.userDoctro)
    TextView userDoc;//医生名
    @BindView(R.id.userdepartment)
    TextView userdepartMent;//诊疗号
    @BindView(R.id.department)
    TextView departMent;//诊室
    @BindView(R.id.time)
    TextView time;//时间
    @BindView(R.id.chief)
    TextView chiefTv;//主诉
    @BindView(R.id.medicalhistory)
    TextView medicalHistoryTv;//现病史
    @BindView(R.id.past)
    TextView pastTv;//既往病史
    @BindView(R.id.examination)
    TextView examinationTv;//过敏史
    @BindView(R.id.physical)
    TextView lookTv;//查体
    @BindView(R.id.lin_medicalhistoryf_msg)
    LinearLayout linMedicalhistoryfMsg;
    @BindView(R.id.dispaly_medicalhistory)
    ImageView displayMedicalhistory;
    @BindView(R.id.lin_past_msg)
    LinearLayout linPastMsg;
    @BindView(R.id.dispaly_past)
    ImageView displayPast;
    @BindView(R.id.lin_examination_msg)
    LinearLayout linExaminationMsg;
    @BindView(R.id.dispaly_examination)
    ImageView displayExamination;
    @BindView(R.id.lin_look_msg)
    LinearLayout linLokMsg;
    @BindView(R.id.dispaly_physical)
    ImageView displayLook;
    @BindView(R.id.lin_suggest_msg)
    LinearLayout linSuggestMsg;
    @BindView(R.id.dispaly_treatment)
    ImageView displaySuggest;
    @BindView(R.id.prescr_part)
    LinearLayout linPrescr;
    @BindView(R.id.drug_recycle)
    RecyclerView drugRecycleview;
    @BindView(R.id.into_check)
    ImageView intoCheck;
    @BindView(R.id.lin_confirm)
    LinearLayout linConfig;
    @BindView(R.id.lin_fullpayment)
    LinearLayout linOnyShare;
    private LayoutInflater mInflater;
    private static final int DRUG_TYPE_NOMAL = 0;
    private static final int DRUG_TYPE_START = 1;
    private static final int DRUG_TYPE_END = 2;
    private String recordCode;
    private JYKJApplication mApp;
    private String reserveCode;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_medical_record;
    }


    public void showAnimation(View view) {

        float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, centerX,
                centerY);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public void endAnimation(View view) {

        float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;
        RotateAnimation rotateAnimation = new RotateAnimation(180, 360, centerX,
                centerY);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    protected void initData() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginPatientPosition", "108.93425^34.23053");
        paramMap.put("requestClientType", "1");
        paramMap.put("operPatientCode", mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        paramMap.put("operPatientName", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        paramMap.put("orderCode", reserveCode);
        String s = RetrofitUtil.encodeParam(paramMap);
        mPresenter.getRecordDet(s);

        mInflater = LayoutInflater.from(this);

    }

    @Override
    public void showLoading(int code) {

    }


    @Override
    public void getMedicalRecordSucess(MedicalRecordBean bean) {
        Glide.with(MedicalRecordActivity.this).load(bean.getPatientLogoUrl()).into(userHead);

        patientName.setText(bean.getPatientName());
        userGennder.setText(bean.getPatientGender() == 0 ? "未知" : (bean.getPatientGender() == 1 ? "男" : "女"));
        switch (bean.getPatientGender()) {
            case 0:
                gennder = "未知";
                break;
            case 1:
                gennder = "男";
                break;
            case 2:
                gennder = "女";
                break;
        }
        recordCode = bean.getRecordCode();
        userGennder.setText(gennder);
        userAger.setText(String.valueOf(bean.getPatientAge()));
        userDoc.setText(bean.getDoctorName());
        userdepartMent.setText(bean.getTreatmentCardNum());
        departMent.setText(bean.getDepartmentSecondName());
        time.setText(DateUtils.getStringTimeOfYMD(bean.getCreateDate()));

        chiefTv.setText(bean.getChiefComplaint());
        medicalHistoryTv.setText(bean.getHistoryNew());
        pastTv.setText(bean.getHistoryPast());
        examinationTv.setText(bean.getFlagHistoryAllergy() == 0 ? "无" : bean.getHistoryAllergy());
        lookTv.setText(bean.getMedicalExamination());
        diagFlow.setAdapter(getTagAdapter(bean.getDiagnosisName(), diagFlow));
        checkFlow.setAdapter(getTagAdapter(bean.getInspectionName(), checkFlow));


        if (bean.getFlagSendMedicalRecord() == 0) { //未确认
            prescrFlow.setVisibility(View.VISIBLE);
            linPrescr.setVisibility(View.GONE);
            prescrFlow.setAdapter(getTagAdapter(bean.getDrugName(), prescrFlow));
            intoCheck.setVisibility(View.GONE);
            linConfig.setVisibility(View.VISIBLE);
            linOnyShare.setVisibility(View.GONE);
        } else { //已确认
            prescrFlow.setVisibility(View.GONE);
            linPrescr.setVisibility(View.VISIBLE);
            intoCheck.setVisibility(View.VISIBLE);
            linConfig.setVisibility(View.GONE);
            linOnyShare.setVisibility(View.VISIBLE);
            List<MedicalRecordBean.InteractOrderPrescribeListBean> interactOrderPrescribeList = bean.getInteractOrderPrescribeList();
            List<MedicalRecordBean.InteractOrderPrescribeListBean.PrescribeInfoBean> prescribeInfoBeans = new ArrayList<>();
            for (int i = 0; i < interactOrderPrescribeList.size(); i++) {

                List<MedicalRecordBean.InteractOrderPrescribeListBean.PrescribeInfoBean> prescribeInfo = interactOrderPrescribeList.get(i).getPrescribeInfo();
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
            MedicalRecordDrugAdapter medicalRecordDrugAdapter = new MedicalRecordDrugAdapter(R.layout.item_record_drug, prescribeInfoBeans);
            drugRecycleview.setAdapter(medicalRecordDrugAdapter);


        }


    }

    private TagAdapter<String> getTagAdapter(String msg, TagFlowLayout checkFlow) {
        return new TagAdapter<String>(dealData(msg)) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                        checkFlow, false);
                tv.setText(s);
                return tv;
            }
        };
    }

    private List<String> dealData(String msg) {
        ArrayList<String> strings = new ArrayList<>();
        if (StringUtils.isNotEmpty(msg)) {
            if (msg.contains(",")) {
                String[] split = msg.split(",");
                strings.addAll(Arrays.asList(split));
            } else {
                strings.add(msg);
            }
        }
        return strings;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent.hasExtra("reserveCode")) {
            reserveCode = intent.getStringExtra("reserveCode");
        }
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(MedicalRecordActivity.this);
        drugRecycleview.setLayoutManager(new LinearLayoutManager(this));
    }


    @OnClick({R.id.confirm, R.id.download, R.id.lin_chief, R.id.lin_history, R.id.lin_prescriptionnote,
            R.id.lin_past, R.id.lin_examination, R.id.lin_look, R.id.lin_suggest, R.id.lin_checklist,R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            //处方笺
            case R.id.lin_prescriptionnote:
                Intent intent1 = new Intent(MedicalRecordActivity.this, PrescriptionDetActivity.class);
                intent1.putExtra("recordCode", reserveCode);
                startActivity(intent1);
                break;
            //检查检验
            case R.id.lin_checklist:
                //检查检验单
                Intent intent = new Intent(MedicalRecordActivity.this, CheckListActivity.class);
                intent.putExtra("recordCode", reserveCode);
                startActivity(intent);
                break;
            //确认病历
            case R.id.confirm:

                break;
            case R.id.download:
//                startActivity(new Intent(MedicalRecordActivity.this, PrescriptionDetActivity.class));
                break;
            case R.id.lin_chief:
                clickAndSome(chiefMsg, ivChief);
                break;
            case R.id.lin_history:
                clickAndSome(linMedicalhistoryfMsg, displayMedicalhistory);
                break;
            case R.id.lin_past:
                clickAndSome(linPastMsg, displayPast);
                break;
            case R.id.lin_examination:
                clickAndSome(linExaminationMsg, displayExamination);
                break;
            case R.id.lin_look:
                clickAndSome(linLokMsg, displayLook);
                break;
            case R.id.lin_suggest:
                clickAndSome(linSuggestMsg, displaySuggest);
                break;
            case R.id.share:
                Intent intent2 = new Intent(MedicalRecordActivity.this, DownloadService.class);
                startService(intent2);
                break;
        }
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showShort(msg);
    }

    private void clickAndSome(LinearLayout vis, ImageView ani) {
        vis.setVisibility(vis.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        if (vis.getVisibility() == View.VISIBLE) {
            showAnimation(ani);
        } else {
            endAnimation(ani);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}