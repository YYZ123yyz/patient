package www.patient.jykj_zxyl.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import entity.eventbus.BloodRecordAddEvent;
import entity.patientInfo.BloodMonitorInfo;
import entity.patientInfo.BloodRecodeData;
import entity.patientInfo.BloodRecodeListData;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.activity.home.patient.BloodEntryActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.adapter.BloodRecordAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.BloodEntryActivity;
import www.patient.jykj_zxyl.adapter.BloodRecordAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.DateUtils;

/**
 * 血压监控
 */
public class BloodMonitorActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecycleView;

    private LinearLayoutManager layoutManager;
    private BloodRecordAdapter bloodRecordAdapter;       //适配器
    private LinearLayout llBack;
    private LinearLayout llLuru,llQushi,llJilu;
    private String mNetRetStr;
    private String mNetRetStr1;
    private Handler mHandler;
    private BloodRecodeData patientData;
    private TextView tvUpdateTime;
    private TextView highPressureNum;
    private TextView lowPressureNum;
    private TextView rateNum;
    private TextView typeName;
    private List<BloodRecodeListData> mData = new ArrayList<>();
    private ImageView ivBloodType;
    private JYKJApplication mApp;

    private TextView    tv_advisory_doctor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_monitor);
        EventBus.getDefault().register(this);
        mApp = (JYKJApplication) getApplicationContext();
        initView();
        initHandler();
        initListener();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(BloodRecordAddEvent event) {
        mData.clear();
        initData();
    }

    private void initView(){
        ActivityUtil.setStatusBar(this);
        tv_advisory_doctor = (TextView)this.findViewById(R.id.tv_advisory_doctor);
        tv_advisory_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BloodMonitorActivity.this,WDYSActivity.class));
            }
        });
        mRecycleView = (RecyclerView) this.findViewById(R.id.recycler_view);
        llBack = findViewById(R.id.ll_back);
        llLuru = findViewById(R.id.ll_luru);
        llQushi = findViewById(R.id.ll_qushi);
        llJilu = findViewById(R.id.ll_jilu);
        tvUpdateTime = findViewById(R.id.tv_update_time);
        highPressureNum = findViewById(R.id.tv_high_num);
        lowPressureNum =findViewById(R.id.tv_low_num);
        rateNum =findViewById(R.id.tv_rate_num);
        typeName = findViewById(R.id.tv_type_name);
        ivBloodType = findViewById(R.id.iv_blood_type);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        bloodRecordAdapter = new BloodRecordAdapter(mData,this);
        mRecycleView.setAdapter(bloodRecordAdapter);

    }

    private void initData(){
        getConditionData();
        getBloodRecordList();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        tvUpdateTime.setText("数据更新："+ DateUtils.stampToDate(patientData.getRecordDate()));
                        highPressureNum.setText(patientData.getHighPressureNum()+"");
                        lowPressureNum.setText(patientData.getLowPressureNum()+"");
                        rateNum.setText(patientData.getHeartRateNum()+"");
                        typeName.setText(patientData.getBloodTypeSecondName());
                        int secondType = patientData.getBloodTypeSecond();
                        switch (secondType){
                            case 3000106:
                                ivBloodType.setImageResource(R.mipmap.icon_blood_recode2);
                                break;
                            case 3000104:
                                ivBloodType.setImageResource(R.mipmap.icon_blood_recode1);
                                break;
                            case 3000105:
                                ivBloodType.setImageResource(R.mipmap.icon_blood_recode1);
                                break;
                                default:
                                    ivBloodType.setImageResource(R.mipmap.icon_blood_recode);
                                    break;
                        }
                        break;
                    case 2:
                        bloodRecordAdapter.setDate(mData);
                        bloodRecordAdapter.notifyDataSetChanged();
                        break;

                }
            }
        };
    }

    private void getConditionData(){
        new Thread() {
            public void run() {
                try {
                    BloodMonitorInfo bloodMonitorInfo = new BloodMonitorInfo();
                    bloodMonitorInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
                    bloodMonitorInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    bloodMonitorInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    bloodMonitorInfo.setRequestClientType("1");
                    String jsonString = JSON.toJSONString(bloodMonitorInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "PatientConditionControlle/searchPatientStateResBloodPressureNewData");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    patientData = new Gson().fromJson(netRetEntity.getResJsonData(), BloodRecodeData.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }
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

    private void getBloodRecordList(){
        new Thread() {
            public void run() {
                try {
                    BloodMonitorInfo bloodMonitorInfo1 = new BloodMonitorInfo();
                    bloodMonitorInfo1.setLoginPatientPosition(mApp.loginDoctorPosition);
                    bloodMonitorInfo1.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    bloodMonitorInfo1.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    bloodMonitorInfo1.setRequestClientType("1");
                    bloodMonitorInfo1.setSearchNum("7");
                    String jsonString = JSON.toJSONString(bloodMonitorInfo1);
                    mNetRetStr1 = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "PatientConditionControlle/searchPatientStateResBloodPressureNewDataNum");
                    NetRetEntity netRetEntity1 = new Gson().fromJson(mNetRetStr1, NetRetEntity.class);
                    List<BloodRecodeListData> list = JSON.parseArray(netRetEntity1.getResJsonData(), BloodRecodeListData.class);
                    mData.addAll(list);
                    if (netRetEntity1.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity1.getResMsg());
                        mNetRetStr1 = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(2);
                        return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr1 = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();

    }

    private void initListener(){
        llBack.setOnClickListener(this);
        llLuru.setOnClickListener(this);
        llQushi.setOnClickListener(this);
        llJilu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_luru:
                startActivity(new Intent(this, BloodEntryActivity.class));
                break;
            case R.id.ll_qushi:
                startActivity(new Intent(BloodMonitorActivity.this,TrendActivity.class));
                break;
            case R.id.ll_jilu:
                startActivity(new Intent(BloodMonitorActivity.this,BloodLogAcitivity.class));
               break;
        }
    }
}
