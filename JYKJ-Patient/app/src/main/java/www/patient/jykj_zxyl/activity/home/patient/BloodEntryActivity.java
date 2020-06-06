package www.patient.jykj_zxyl.activity.home.patient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import entity.eventbus.BloodRecordAddEvent;
import entity.patientInfo.BloodMonitorInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.custom.AllRulerCallback;
import www.patient.jykj_zxyl.custom.VerticalRulerView;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.custom.AllRulerCallback;
import www.patient.jykj_zxyl.custom.VerticalRulerView;

/**
 *血压录入
 */
public class BloodEntryActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private VerticalRulerView mHighBlood;
    private VerticalRulerView mLowBlood;
    private VerticalRulerView mHeartRate;
    private TextView tvHighValue;
    private TextView tvLowValue;
    private TextView tvRateValue;
    private TextView tvDate;
    private TextView tvSave;
    private String highBlood;
    private String lowBlood;
    private String rateNum;
    private String mNetRetStr;
    private Handler mHandler;
    private NetRetEntity netRetEntity;
    private JYKJApplication mApp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_entry);
        mApp = (JYKJApplication) getApplicationContext();
        initView();
        initHandler();
    }

    private void initView(){
        llBack = findViewById(R.id.ll_back);
        mHighBlood = findViewById(R.id.vsv_high_blood);
        mLowBlood = findViewById(R.id.vsv_low_blood);
        mHeartRate = findViewById(R.id.vsv_heart_rate);
        tvHighValue = findViewById(R.id.tv_high_value);
        tvLowValue = findViewById(R.id.tv_low_value);
        tvRateValue = findViewById(R.id.tv_rate_value);
        tvDate = findViewById(R.id.tv_date);
        tvSave = findViewById(R.id.tv_save);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        tvDate.setText(simpleDateFormat.format(date));

        initListener();
        initRuleView();
    }

    private void initListener(){
        llBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);

    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    case 1:
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1)
                        {
                            Toast.makeText(BloodEntryActivity.this, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new BloodRecordAddEvent());
                            finish();
                        } else {
                            Toast.makeText(BloodEntryActivity.this, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                        break;


                }
            }
        };
    }

    private void initRuleView(){

        mHighBlood.setMin(40);   //设置刻度尺最小值
        mHighBlood.setMax(200);   //设置刻度尺最大值
        mHighBlood.setNumber(80);    //设置刻度尺第一次显示的位置
        mHighBlood.setInterval(5);   //设置刻度尺的间距
        mHighBlood.setTextOffset(20); //根据显示的数字自主调节刻度尺数字的左右位置
        mHighBlood.setRuleListener(new AllRulerCallback() {
            @Override
            public void onRulerSelected(int length, int value) {
                highBlood = String.valueOf(value);
                tvHighValue.setText(highBlood);
            }
        });


        mLowBlood.setMin(40);   //设置刻度尺最小值
        mLowBlood.setMax(200);   //设置刻度尺最大值
        mLowBlood.setNumber(120);    //设置刻度尺第一次显示的位置
        mLowBlood.setInterval(5);   //设置刻度尺的间距
        mLowBlood.setTextOffset(20); //根据显示的数字自主调节刻度尺数字的左右位置
        mLowBlood.setRuleListener(new AllRulerCallback() {
            @Override
            public void onRulerSelected(int length, int value) {
                lowBlood = String.valueOf(value);
                tvLowValue.setText(lowBlood);
            }
        });

        mHeartRate.setMin(40);   //设置刻度尺最小值
        mHeartRate.setMax(200);   //设置刻度尺最大值
        mHeartRate.setNumber(120);    //设置刻度尺第一次显示的位置
        mHeartRate.setInterval(5);   //设置刻度尺的间距
        mHeartRate.setTextOffset(20); //根据显示的数字自主调节刻度尺数字的左右位置
        mHeartRate.setRuleListener(new AllRulerCallback() {
            @Override
            public void onRulerSelected(int length, int value) {
                rateNum = String.valueOf(value);
                tvRateValue.setText(rateNum);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_save:
                saveBloodPressureRecord();
                break;
        }
    }

    private void saveBloodPressureRecord(){
        new Thread() {
            public void run() {
                try {
                    BloodMonitorInfo bloodMonitorInfo = new BloodMonitorInfo();
                    bloodMonitorInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
                    bloodMonitorInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    bloodMonitorInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    bloodMonitorInfo.setRequestClientType("1");
                    bloodMonitorInfo.setBloodPressureId("0");
                    bloodMonitorInfo.setRecordType("1");
                    bloodMonitorInfo.setPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    bloodMonitorInfo.setHighPressureNum(highBlood);
                    bloodMonitorInfo.setLowPressureNum(lowBlood);
                    bloodMonitorInfo.setHeartRateNum(rateNum);
                    String jsonString = JSON.toJSONString(bloodMonitorInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "PatientConditionControlle/operUpdPatientConditionBloodPressureRecord");
                    netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("添加失败：" + netRetEntity.getResMsg());
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
}
