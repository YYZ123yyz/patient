package www.patient.jykj_zxyl.activity.myself;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.mySelf.MedicationList;
import entity.mySelf.ProvidePatientConditionTakingRecord;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.MedicationListAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.MedicationListAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

/**
 * 服药设置
 */
public class FYSZActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvStartTime, tvEndTime;
    private TextView tvViewTime;
    private TimePickerView startTime;
    private TimePickerView endTime;
    private OptionsPickerView optionPick;
    private List<String> timeDataList;

    private String startData, endData;
    private TextView tv_1_month, tv_3_month, tv_6_month;
    private TextView tvMedicationList;
    private TextView tvMedicationRemind;
    private LinearLayout llBack;

    private Handler mHandler;

    private String mNetRetStr;                 //返回字符串

    private int mRowNum = 10;                    //
    private int mPageNum = 1;
    private boolean mLoadDate = true;
    private RecyclerView mRecyclerView;
    private MedicationListAdapter mAdapter;
    private List<ProvidePatientConditionTakingRecord> mData = new ArrayList<>();
    private String searchDateType;
    private TextView tvConfirm;
    private JYKJApplication mApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fysz);
        mApp = (JYKJApplication) getApplication();
        initView();

        initHandler();
        initListener();
        initData();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        mAdapter.setDate(mData);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    private void initView() {
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        tvViewTime = findViewById(R.id.tv_view_time);
        tv_1_month = findViewById(R.id.tv_1_month);
        tv_3_month = findViewById(R.id.tv_3_month);
        tv_6_month = findViewById(R.id.tv_6_month);
//        tvMedicationList = findViewById(R.id.tv_medication_list);
//        tvMedicationRemind = findViewById(R.id.tv_medication_remind);
        llBack = findViewById(R.id.ll_back);
        tvConfirm = findViewById(R.id.tv_confirm);
        startData = getTodayData();
        endData = getTodayData();
        tvStartTime.setText(startData);
        tvEndTime.setText(endData);

    }

    private void initListener() {
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        tvViewTime.setOnClickListener(this);
        tv_1_month.setOnClickListener(this);
        tv_3_month.setOnClickListener(this);
        tv_6_month.setOnClickListener(this);
        tvMedicationList.setOnClickListener(this);
        llBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvMedicationRemind.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_medication_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new MedicationListAdapter(mData, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= manager.getItemCount() - 1) {
                            if (mLoadDate) {
                                mPageNum++;
                                getDate();
                            }
                        }
                    }
                }
            }
        });
    }

    private void initData() {
        timeDataList = new ArrayList<>();
        timeDataList.add("当天");
        timeDataList.add("近三天");
        timeDataList.add("近一周");
        timeDataList.add("近三十天");
        timeDataList.add("近三个月");
        timeDataList.add("近六个月");
        getDate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                startTime = new TimePickerBuilder(FYSZActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startData = getDate(date);
                        tvStartTime.setText(startData);
                    }
                }).build();
                startTime.show();
                break;
            case R.id.tv_end_time:
                endTime = new TimePickerBuilder(FYSZActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        endData = getDate(date);
                        tvEndTime.setText(endData);
                    }
                }).build();
                endTime.show();
                break;
            case R.id.tv_view_time:
                optionPick = new OptionsPickerBuilder(FYSZActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        switch (options1) {
                            case 0:
                                searchDateType = "1";
                                mData.clear();
                                getDate();
                                break;
                            case 1:
                                searchDateType = "2";
                                mData.clear();
                                getDate();
                                break;
                            case 2:
                                searchDateType = "3";
                                mData.clear();
                                getDate();
                                break;
                            case 3:
                                searchDateType = "4";
                                mData.clear();
                                getDate();
                                break;
                            case 4:
                                searchDateType = "5";
                                mData.clear();
                                getDate();
                                break;
                            case 5:
                                searchDateType = "6";
                                mData.clear();
                                getDate();
                                break;
                        }
                        tvViewTime.setText(timeDataList.get(options1));
                    }
                }).setSelectOptions(3).build();
                optionPick.setPicker(timeDataList);
                optionPick.show();
                break;
            case R.id.tv_1_month:
                tv_1_month.setBackgroundResource(R.drawable.shape_medication_button_select);
                tv_1_month.setTextColor(getResources().getColor(R.color.writeColor));
                tv_3_month.setBackgroundResource(R.drawable.shape_medication_button_unselect);
                tv_3_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_6_month.setBackgroundResource(R.drawable.shape_medication_button_unselect);
                tv_6_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                searchDateType = "4";
                mData.clear();
                getDate();
                break;
            case R.id.tv_3_month:
                tv_1_month.setBackgroundResource(R.drawable.shape_medication_button_unselect);
                tv_1_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_3_month.setBackgroundResource(R.drawable.shape_medication_button_select);
                tv_3_month.setTextColor(getResources().getColor(R.color.writeColor));
                tv_6_month.setBackgroundResource(R.drawable.shape_medication_button_unselect);
                tv_6_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                searchDateType = "5";
                mData.clear();
                getDate();
                break;
            case R.id.tv_6_month:
                tv_1_month.setBackgroundResource(R.drawable.shape_medication_button_unselect);
                tv_1_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_3_month.setBackgroundResource(R.drawable.shape_medication_button_unselect);
                tv_3_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_6_month.setBackgroundResource(R.drawable.shape_medication_button_select);
                tv_6_month.setTextColor(getResources().getColor(R.color.writeColor));
                searchDateType = "6";
                mData.clear();
                getDate();
                break;
//            case R.id.tv_medication_list:
//                startActivity(new Intent(FYSZActivity.this, MedicationListActivity.class));
//                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_confirm:
                mData.clear();
                getDate();
                break;
//            case R.id.tv_medication_remind:
//                startActivity(new Intent(FYSZActivity.this, AddMedicationActivity.class));
//                break;
        }
    }

    private void getDate() {
        new Thread() {
            public void run() {
                try {
                    MedicationList medicationList = new MedicationList();
                    medicationList.setRowNum(mRowNum + "");
                    medicationList.setPageNum(mPageNum + "");
                    medicationList.setLoginPatientPosition(mApp.loginDoctorPosition);
                    medicationList.setRequestClientType("1");
                    medicationList.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    medicationList.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    medicationList.setSearchTakingMedicine("-1");
                    medicationList.setSearchDateStart(startData);
                    medicationList.setSearchDateEnd(endData);
                    medicationList.setSearchDateType(searchDateType);
                    String jsonString = JSON.toJSONString(medicationList);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "PatientConditionControlle/searchPatientStateResTakingMedicineRecordNoGroup");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        mLoadDate = false;
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    List<ProvidePatientConditionTakingRecord> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionTakingRecord.class);
                    mData.addAll(list);
                } catch (Exception e) {
                    mLoadDate = false;
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(0);
                    e.printStackTrace();
                    return;

                }

                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    private String getDate(Date data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String string = simpleDateFormat.format(data);
        return string;
    }

    private String getTodayData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
