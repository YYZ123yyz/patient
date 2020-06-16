package www.patient.jykj_zxyl.activity.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entity.patientInfo.BloodTrendInfo;
import entity.patientInfo.ProvidePatientConditionBloodPressureGroup;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

public class TrendActivity extends AppCompatActivity implements View.OnClickListener {

    private int mRowNum = 100;                        //分页行数
    private int mPageNum = 1;                       //分页页码
    private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
    private List<ProvidePatientConditionBloodPressureGroup> groupList = new ArrayList<>();
    private LineChart mLineChart;
    private LinearLayout llBloodDay, llBloodWeek, llBloodMonth;
    private LinearLayout viewBloodDay, viewBloodWeek, viewBloodMonth;
    private String searchTimeType = "0";//0全天;1:晨间;3:夜间
    private String searchDateType = "1";//当天(1:当天;2:近3天;3:近7天;4:近30天;5:近3个月;6:近6个月;)
    private TextView tvViewTime;
    private TimePickerView startTime;
    private TimePickerView endTime;
    private OptionsPickerView optionPick;
    private List<String> timeDataList;

    private TextView tvDay1,tvDay2,tvDay3;
    private TextView tvComfirm;
    private RelativeLayout rlStartTime;
    private TextView tvStartTime ;
    private RelativeLayout rlEndTime;
    private TextView tvEndTime;

    private String startData, endData;
    private TextView tv_1_month, tv_3_month, tv_6_month;
    private LinearLayout tv_1_month_cut, tv_3_month_cut, tv_6_month_cut;
    private LinearLayout llBack;
    private TextView tvConfirm;
    private LinearLayout ll_blood_day,ll_blood_week,ll_blood_month;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);
        mApp = (JYKJApplication) getApplication();
        initData();
        initListener();
        initHandler();
    }

    private void initData() {

        timeDataList = new ArrayList<>();
        timeDataList.add("当天");
        timeDataList.add("近三天");
        timeDataList.add("近一周");
        timeDataList.add("近三十天");
        timeDataList.add("近三个月");
        timeDataList.add("近六个月");

        mLineChart = (LineChart)findViewById(R.id.line_chart);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvStartTime.setOnClickListener(this);
        tvEndTime = findViewById(R.id.tv_end_time);
        tvEndTime.setOnClickListener(this);
//        tvViewTime = findViewById(R.id.tv_view_time);
        tv_1_month = findViewById(R.id.tv_1_month);
        tv_3_month = findViewById(R.id.tv_3_month);
        tv_6_month = findViewById(R.id.tv_6_month);

        tv_1_month_cut = findViewById(R.id.tv_1_month_cut);
        tv_3_month_cut = findViewById(R.id.tv_3_month_cut);
        tv_6_month_cut = findViewById(R.id.tv_6_month_cut);
//        tvMedicationList = findViewById(R.id.tv_medication_list);
//        tvMedicationRemind = findViewById(R.id.tv_medication_remind);
        llBack = findViewById(R.id.ll_back);
        tvConfirm = findViewById(R.id.tv_confirm);
        startData = getTodayData();
        endData = getTodayData();
        tvStartTime.setText(startData);
        tvEndTime.setText(endData);
//        ll_blood_day = (LinearLayout)this.findViewById(R.id.ll_blood_day);
//        ll_blood_week = (LinearLayout)this.findViewById(R.id.ll_blood_week);
//        ll_blood_month = (LinearLayout)this.findViewById(R.id.ll_blood_month);
//        ll_blood_day.setOnClickListener(this);
//        ll_blood_week.setOnClickListener(this);
//        ll_blood_month.setOnClickListener(this);
        viewBloodDay = this.findViewById(R.id.view_blood_day);
        viewBloodWeek  = this.findViewById(R.id.view_blood_week);
        viewBloodMonth  = this.findViewById(R.id.view_blood_month);
        //默认当天
        searchTimeType = "1";
        getNetData();

    }

    private void initListener() {

        tv_1_month.setOnClickListener(this);
        tv_3_month.setOnClickListener(this);
        tv_6_month.setOnClickListener(this);
        llBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

    }


    private void getNetData() {

        new Thread() {
            public void run() {
                try {
                    BloodTrendInfo provideViewPatientInfo = new BloodTrendInfo();
                    provideViewPatientInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setRequestClientType("1");
                    provideViewPatientInfo.setPageNum(mPageNum);
                    provideViewPatientInfo.setRowNum(mRowNum);
                    provideViewPatientInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideViewPatientInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    provideViewPatientInfo.setSearchDateType(searchDateType);
//                    provideViewPatientInfo.setSearchTimeType(searchTimeType);
                    provideViewPatientInfo.setSearchDateStart(startData);
                    provideViewPatientInfo.setSearchDateEnd(endData);

                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "PatientConditionControlle/searchPatientStateResBloodPressureCycleData");
//                    Log.e("mNetRetStr",mNetRetStr);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }
                    groupList = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionBloodPressureGroup.class);
//                    groupList.addAll(list);
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

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        setData();
                        break;

                }
            }
        };
    }

    private void setData() {
        initLineChart();
        setLineChart();
    }
    LineDataSet dataSet1,dataSet2,dataSet3;
    private void setLineChart() {

        List<Entry> entries1 = new ArrayList<>();
        entries1.clear();
        for (int i = 0; i < groupList.size(); i++) {
            entries1.add(new Entry(i, groupList.get(i).getDayAvgHeartRateNum()));
        }
        dataSet1 = new LineDataSet(entries1, "心率");
        dataSet1.setColor(Color.parseColor("#3ED045"));
        dataSet1.setCircleColor(Color.parseColor("#3ED045"));
        dataSet1.setLineWidth(1f);
        dataSet1.setDrawFilled(false);
        dataSet1.setCircleRadius(3f);
        dataSet1.setDrawCircleHole(true);
        dataSet1.setCircleHoleColor(Color.parseColor("#3ED045"));
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);


        List<Entry> entries2 = new ArrayList<>();
        entries2.clear();
        for (int i = 0; i < groupList.size(); i++) {
            entries2.add(new Entry(i, groupList.get(i).getDayAvgHighPressureNum()));
        }
        dataSet2 = new LineDataSet(entries2, "收缩压");
        dataSet2.setColor(Color.parseColor("#176DE6"));
        dataSet2.setCircleColor(Color.parseColor("#176DE6"));
        dataSet2.setCircleHoleColor(Color.parseColor("#176DE6"));
        dataSet2.setLineWidth(1f);
        dataSet2.setDrawFilled(false);
        dataSet2.setCircleRadius(3f);
        dataSet2.setDrawCircleHole(true);


        List<Entry> entries3 = new ArrayList<>();
        entries3.clear();
        for (int i = 0; i < groupList.size(); i++) {
            entries3.add(new Entry(i, groupList.get(i).getDayAvgLowPressureNum()));
        }
        dataSet3 = new LineDataSet(entries3, "舒张压");
        dataSet3.setColor(Color.parseColor("#FAB834"));
        dataSet3.setCircleColor(Color.parseColor("#FAB834"));
        dataSet3.setCircleHoleColor(Color.parseColor("#FAB834"));
        dataSet3.setLineWidth(1f);
        dataSet3.setDrawFilled(false);
        dataSet3.setCircleRadius(3f);
        dataSet3.setDrawCircleHole(true);

        LineData lineData = new LineData(dataSet1, dataSet2, dataSet3);
        mLineChart.setData(lineData);
    }

    private void initLineChart(){
        XAxis xAxis = mLineChart.getXAxis();
        //设置X轴的位置（默认在上方)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum((float) list.size());
        //不显示网格线
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(6);
        //设置最小间隔，防止当放大时出现重复标签
        xAxis.setGranularity(2f);
        //设置为true当一个页面显示条目过多，X轴值隔一个显示一个
        xAxis.setGranularityEnabled(true);
        // 标签倾斜
        //设置X轴值为字符串
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if(groupList.size()>0) {
                    int index = (int) value;
                    if (index < 0 || index >= groupList.size()) {
                        return "";
                    } else {
                        return groupList.get((int) value).getGroupRecordDate();
                    }
                }
                return "";
            }
        });

        //得到Y轴
        YAxis yAxis = mLineChart.getAxisLeft();
        YAxis rightYAxis = mLineChart.getAxisRight();
        //设置Y轴是否显示
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        //设置y轴坐标之间的最小间隔
        //不显示网格线
        yAxis.setDrawGridLines(false);
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(1);

        //设置从Y轴值
        yAxis.setAxisMinimum(0f);
        mLineChart.setDragEnabled(true);
        mLineChart.setPinchZoom(true);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                startTime = new TimePickerBuilder(TrendActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startData = getDate(date);
                        tvStartTime.setText(startData);
                    }
                }).build();
                startTime.show();
                break;
            case R.id.tv_end_time:
                endTime = new TimePickerBuilder(TrendActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        endData = getDate(date);
                        tvEndTime.setText(endData);
                    }
                }).build();
                endTime.show();
                break;
            case R.id.tv_view_time:
                optionPick = new OptionsPickerBuilder(TrendActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        switch (options1) {
                            case 0:
                                searchDateType = "1";
//                                mData.clear();
//                                getDate();
                                break;
                            case 1:
                                searchDateType = "2";
//                                mData.clear();
//                                getDate();
                                break;
                            case 2:
                                searchDateType = "3";
//                                mData.clear();
//                                getDate();
                                break;
                            case 3:
                                searchDateType = "4";
//                                mData.clear();
//                                getDate();
                                break;
                            case 4:
                                searchDateType = "5";
//                                mData.clear();
//                                getDate();
                                break;
                            case 5:
                                searchDateType = "6";
//                                mData.clear();
//                                getDate();
                                break;
                        }
                        tvViewTime.setText(timeDataList.get(options1));
                    }
                }).setSelectOptions(3).build();
                optionPick.setPicker(timeDataList);
                optionPick.show();
                break;
            case R.id.tv_1_month:
                tv_1_month.setTextColor(getResources().getColor(R.color.groabColor));
                tv_1_month_cut.setVisibility(View.VISIBLE);
                tv_3_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_3_month_cut.setVisibility(View.GONE);
                tv_6_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_6_month_cut.setVisibility(View.GONE);
                searchDateType = "1";
                startData = getTodayData();
                endData = getTodayData();
                tvStartTime.setText(startData);
                tvEndTime.setText(endData);
                getNetData();
                break;
            case R.id.tv_3_month:
                tv_1_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_1_month_cut.setVisibility(View.GONE);
                tv_3_month.setTextColor(getResources().getColor(R.color.groabColor));
                tv_3_month_cut.setVisibility(View.VISIBLE);
                tv_6_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_6_month_cut.setVisibility(View.GONE);
                searchDateType = "3";
                startData = getQTCurrentMonday();
                endData = getTodayData();
                tvStartTime.setText(startData);
                tvEndTime.setText(endData);
                getNetData();
                break;
            case R.id.tv_6_month:
                tv_1_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_1_month_cut.setVisibility(View.GONE);
                tv_3_month.setTextColor(getResources().getColor(R.color.textColor_vo));
                tv_3_month_cut.setVisibility(View.GONE);
                tv_6_month.setTextColor(getResources().getColor(R.color.groabColor));
                tv_6_month_cut.setVisibility(View.VISIBLE);
                searchDateType = "4";
                startData = getPreviousSundayY();
                endData = getTodayData();
                tvStartTime.setText(startData);
                tvEndTime.setText(endData);
                getNetData();
                break;
//            case R.id.tv_medication_list:
//                startActivity(new Intent(MedicationActivity.this, MedicationListActivity.class));
//                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_confirm:
//                mData.clear();
                searchDateType = null;
                getNetData();
                break;
//            case R.id.tv_medication_remind:
//                startActivity(new Intent(MedicationActivity.this, AddMedicationActivity.class));
//                break;
            case R.id.ll_blood_day:
                if(viewBloodDay.getVisibility()!=View.VISIBLE) {
                    viewBloodDay.setVisibility(View.VISIBLE);
                    viewBloodWeek.setVisibility(View.GONE);
                    viewBloodMonth.setVisibility(View.GONE);
                }
                groupList.clear();
                searchTimeType = "0";
                getNetData();
                break;
            case R.id.ll_blood_week:
                if(viewBloodWeek.getVisibility()!=View.VISIBLE) {
                    viewBloodDay.setVisibility(View.GONE);
                    viewBloodWeek.setVisibility(View.VISIBLE);
                    viewBloodMonth.setVisibility(View.GONE);
                }
                groupList.clear();
                searchTimeType = "1";
                getNetData();
                break;
            case R.id.ll_blood_month:
                if(viewBloodMonth.getVisibility()!=View.VISIBLE) {
                    viewBloodDay.setVisibility(View.GONE);
                    viewBloodWeek.setVisibility(View.GONE);
                    viewBloodMonth.setVisibility(View.VISIBLE);
                }
                groupList.clear();
                searchTimeType = "3";
                getNetData();
                break;
        }
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ll_blood_day:
//                if(viewBloodDay.getVisibility()!=View.VISIBLE) {
//                    viewBloodDay.setVisibility(View.VISIBLE);
//                    viewBloodWeek.setVisibility(View.GONE);
//                    viewBloodMonth.setVisibility(View.GONE);
//                }
//                groupList.clear();
//                searchTimeType = "0";
//                getData(searchTimeType,searchDateType);
//                break;
//            case R.id.ll_blood_week:
//                if(viewBloodWeek.getVisibility()!=View.VISIBLE) {
//                    viewBloodDay.setVisibility(View.GONE);
//                    viewBloodWeek.setVisibility(View.VISIBLE);
//                    viewBloodMonth.setVisibility(View.GONE);
//                }
//                groupList.clear();
//                searchTimeType = "1";
//                getData(searchTimeType,searchDateType);
//                break;
//            case R.id.ll_blood_month:
//                if(viewBloodMonth.getVisibility()!=View.VISIBLE) {
//                    viewBloodDay.setVisibility(View.GONE);
//                    viewBloodWeek.setVisibility(View.GONE);
//                    viewBloodMonth.setVisibility(View.VISIBLE);
//                }
//                groupList.clear();
//                searchTimeType = "3";
//                getData(searchTimeType,searchDateType);
//                break;
//            case R.id.tv_view_time:
//                optionPick = new OptionsPickerBuilder(TrendActivity.this, new OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                        switch (options1) {
//                            case 0:
//                                searchDateType = "1";
//                                groupList.clear();
//                                getData(searchTimeType,searchDateType);
//                                break;
//                            case 1:
//                                searchDateType = "2";
//                                groupList.clear();
//                                getData(searchTimeType,searchDateType);
//                                break;
//                            case 2:
//                                searchDateType = "3";
//                                groupList.clear();
//                                getData(searchTimeType,searchDateType);
//                                break;
//                            case 3:
//                                searchDateType = "4";
//                                groupList.clear();
//                                getData(searchTimeType,searchDateType);
//                                break;
//                            case 4:
//                                searchDateType = "5";
//                                groupList.clear();
//                                getData(searchTimeType,searchDateType);
//                                break;
//                            case 5:
//                                searchDateType = "6";
//                                groupList.clear();
//                                getData(searchTimeType,searchDateType);
//                                break;
//                        }
//                        tvViewTime.setText(timeDataList.get(options1));
//                    }
//                }).setSelectOptions(3).build();
//                optionPick.setPicker(timeDataList);
//                optionPick.show();
//                break;
//            case R.id.ll_back:
//                finish();
//                break;
//            case R.id.tv_30_day:
//                tvDay1.setBackgroundResource(R.drawable.shape_corner_button1);
//                tvDay2.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay3.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay1.setTextColor(getResources().getColor(R.color.writeColor));
//                tvDay2.setTextColor(getResources().getColor(R.color.blackColor));
//                tvDay3.setTextColor(getResources().getColor(R.color.blackColor));
//                searchDateType = "4";
//                groupList.clear();
//                getData(searchTimeType,searchDateType);
//                break;
//            case R.id.tv_3_month:
//                tvDay1.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay2.setBackgroundResource(R.drawable.shape_corner_button1);
//                tvDay3.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay1.setTextColor(getResources().getColor(R.color.blackColor));
//                tvDay2.setTextColor(getResources().getColor(R.color.writeColor));
//                tvDay3.setTextColor(getResources().getColor(R.color.blackColor));
//                searchDateType = "5";
//                groupList.clear();
//                getData(searchTimeType,searchDateType);
//                break;
//                case R.id.tv_6_month:
//                    tvDay1.setBackgroundResource(R.drawable.shape_button_button2);
//                    tvDay2.setBackgroundResource(R.drawable.shape_button_button2);
//                    tvDay3.setBackgroundResource(R.drawable.shape_corner_button1);
//                    tvDay1.setTextColor(getResources().getColor(R.color.blackColor));
//                    tvDay2.setTextColor(getResources().getColor(R.color.blackColor));
//                    tvDay3.setTextColor(getResources().getColor(R.color.writeColor));
//                    searchDateType = "6";
//                    groupList.clear();
//                    getData(searchTimeType,searchDateType);
//                   break;
//            case R.id.rl_start_time:
//                startTime = new TimePickerBuilder(TrendActivity.this, new OnTimeSelectListener() {
//                    @Override
//                    public void onTimeSelect(Date date, View v) {
//                        startData = getDate(date);
//                        tvStartTime.setText(startData);
//                    }
//                }).build();
//                startTime.show();
//                break;
//            case R.id.rl_end_time:
//                endTime = new TimePickerBuilder(TrendActivity.this, new OnTimeSelectListener() {
//                    @Override
//                    public void onTimeSelect(Date date, View v) {
//                        endData = getDate(date);
//                        tvEndTime.setText(endData);
//                    }
//                }).build();
//                endTime.show();
//                break;
//            case R.id.tv_comfirm:
//                groupList.clear();
//                getData(searchTimeType,searchDateType);
//                break;
//
//        }
//    }

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

    // 获得当前日期与本周一相差的天数
    private int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    // 获得当前周--周一的日期
    private String getQTCurrentMonday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        String day = format.format(d);
        return day;


    }

    // 获得当前周--周日的日期
    private String getPreviousSundayY() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        System.out.println("过去一个月："+mon);
        return mon;
    }

}
