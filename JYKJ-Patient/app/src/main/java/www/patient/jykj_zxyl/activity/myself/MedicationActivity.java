package www.patient.jykj_zxyl.activity.myself;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entity.ProvidePatientConditionTakingRecordStatistics;
import entity.mySelf.ProvidePatientConditionTakingRecord;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.MedicationListAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.MedicationListAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

public class MedicationActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvStartTime, tvEndTime;
    private TextView tvViewTime;
    private TimePickerView startTime;
    private TimePickerView endTime;
    private OptionsPickerView optionPick;
    private List<String> timeDataList;

    private String startData, endData;
    private TextView tv_1_month, tv_3_month, tv_6_month;
    private LinearLayout tv_1_month_cut, tv_3_month_cut, tv_6_month_cut;
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

    private PieChart pieChart;

    private JYKJApplication mApp;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        mApp = (JYKJApplication) getApplication();
        searchDateType = "1";
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
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        ProvidePatientConditionTakingRecordStatistics providePatientConditionTakingRecordStatistics;
                        if (netRetEntity.getResCode() == 0)
                        {
                            providePatientConditionTakingRecordStatistics = new ProvidePatientConditionTakingRecordStatistics();
                            Toast.makeText(MedicationActivity.this,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            providePatientConditionTakingRecordStatistics = JSON.parseObject(netRetEntity.getResJsonData(),ProvidePatientConditionTakingRecordStatistics.class);
                        }
                        showDate(providePatientConditionTakingRecordStatistics);
//                        mAdapter.setDate(mData);
//                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    /**
     * 展示饼状图
     * @param providePatientConditionTakingRecordStatistics
     */
    private void showDate(ProvidePatientConditionTakingRecordStatistics providePatientConditionTakingRecordStatistics) {
        List<PieEntry> pieEntryList = setPieEntry(providePatientConditionTakingRecordStatistics);//设置PieEntry
        PieData pieData = setPieData(pieEntryList);//设置PieData
        pieChart.setExtraOffsets(0, 0, 0, 0); //设置边距
        setDescription();//设置图表的描述
        setLegend();//设置图例描述
        pieChart.setRotationEnabled(false);//禁止转动
        pieChart.setDrawHoleEnabled(false);//中间不留空洞
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setData(pieData);
        Legend l = pieChart.getLegend();
        l.setEnabled(false);                    //是否启用图列（true：下面属性才有意义
        pieChart.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
        l.setWordWrapEnabled(false);              //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setXEntrySpace(30);
        pieData.setDrawValues(true);
        pieData.setValueTextSize(15);
        pieData.setValueTextColor(R.color.writeColor);
        pieChart.invalidate();
    }

    private void initView() {
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

        pieChart = findViewById(R.id.piechart);



    }

    /**
     * 设置PieEntry
     * @return
     */
    private List<PieEntry> setPieEntry(ProvidePatientConditionTakingRecordStatistics providePatientConditionTakingRecordStatistics){
        PieEntry pie1 = new PieEntry(providePatientConditionTakingRecordStatistics.getFlagTakingMedicine1(),"未按时服用");
        PieEntry pie2 = new PieEntry(providePatientConditionTakingRecordStatistics.getFlagTakingMedicine3(),"已按时服用");
        PieEntry pie3 = new PieEntry(providePatientConditionTakingRecordStatistics.getFlagTakingMedicine2(),"操作已过期");
        PieEntry pie4 = new PieEntry(providePatientConditionTakingRecordStatistics.getFlagTakingMedicine0(),"暂未操作");
//
        List<PieEntry> pieEntryList = new ArrayList<>();
        pieEntryList.add(pie1);pieEntryList.add(pie2);
        pieEntryList.add(pie3);pieEntryList.add(pie4);
        return pieEntryList;
    }

    /**+
     * 设置PieData
     * @param pieEntryList
     * @return
     */
    private PieData setPieData(List<PieEntry> pieEntryList){
        PieDataSet set = new PieDataSet(pieEntryList,"");
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#E34945"));
        colors.add(Color.parseColor("#51B495"));
        colors.add(Color.parseColor("#EB8938"));
        colors.add(Color.parseColor("#3389A2"));

//        set.setSliceSpace(4f);//切割空间
        set.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);//值在图表内显示
        set.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String flag="";
//                if((int) value%4==0){
//                    flag="未按时服用：";
//                }else{
//                    flag="已按时服用：";
//                }
                return value+"%";

            }
        });
        PieData pieData = new PieData(set);
        set.setColors(colors);//添加颜色
        pieData.setValueTextColor(R.color.writeColor);
        pieData.setValueTextSize(15);
        return pieData;
    }

    /**
     * 设置图表的描述
     */
    private void setDescription(){
        Description description = pieChart.getDescription();//获得图表的描述
        description.setText("");
    }

    /**
     * 设置图例描述
     */
    private void setLegend(){
        Legend legend = pieChart.getLegend();//获得图例的描述
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(15f);
        legend.setFormSize(15f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setDrawInside(true);//再里面显示
    }

    private void initListener() {
//        tvStartTime.setOnClickListener(this);
//        tvEndTime.setOnClickListener(this);
//        tvViewTime.setOnClickListener(this);
        tv_1_month.setOnClickListener(this);
        tv_3_month.setOnClickListener(this);
        tv_6_month.setOnClickListener(this);
//        tvMedicationList.setOnClickListener(this);
        llBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
//        tvMedicationRemind.setOnClickListener(this);

//        mRecyclerView = (RecyclerView) findViewById(R.id.rv_medication_list);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(manager);
//        mAdapter = new MedicationListAdapter(mData, this);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    if (mLoadDate) {
//                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
//                        if (lastVisiblePosition >= manager.getItemCount() - 1) {
//                            if (mLoadDate) {
//                                mPageNum++;
//                                getDate();
//                            }
//                        }
//                    }
//                }
//            }
//        });
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
                startTime = new TimePickerBuilder(MedicationActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startData = getDate(date);
                        tvStartTime.setText(startData);
                    }
                }).build();
                startTime.show();
                break;
            case R.id.tv_end_time:
                endTime = new TimePickerBuilder(MedicationActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        endData = getDate(date);
                        tvEndTime.setText(endData);
                    }
                }).build();
                endTime.show();
                break;
            case R.id.tv_view_time:
                optionPick = new OptionsPickerBuilder(MedicationActivity.this, new OnOptionsSelectListener() {
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
                getDate();
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
                getDate();
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
                getDate();
                break;
//            case R.id.tv_medication_list:
//                startActivity(new Intent(MedicationActivity.this, MedicationListActivity.class));
//                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_confirm:
                mData.clear();
                searchDateType = null;
                getDate();
                break;
//            case R.id.tv_medication_remind:
//                startActivity(new Intent(MedicationActivity.this, AddMedicationActivity.class));
//                break;
        }
    }

    private void getDate() {
        new Thread() {
            public void run() {
                try {
                    ProvidePatientConditionTakingRecordStatistics providePatientConditionTakingRecordStatistics = new ProvidePatientConditionTakingRecordStatistics();
                    providePatientConditionTakingRecordStatistics.setLoginPatientPosition(mApp.loginDoctorPosition);
                    providePatientConditionTakingRecordStatistics.setRequestClientType("1");
                    providePatientConditionTakingRecordStatistics.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    providePatientConditionTakingRecordStatistics.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    providePatientConditionTakingRecordStatistics.setSearchTakingMedicine("-1");
                    providePatientConditionTakingRecordStatistics.setSearchDateStart(startData);
                    providePatientConditionTakingRecordStatistics.setSearchDateEnd(endData);
                    providePatientConditionTakingRecordStatistics.setSearchDateType(searchDateType);
                    String jsonString = JSON.toJSONString(providePatientConditionTakingRecordStatistics);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "PatientConditionControlle/searchPatientTakingRecordResChart");
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
