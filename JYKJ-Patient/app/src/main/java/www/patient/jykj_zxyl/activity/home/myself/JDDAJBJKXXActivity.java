package www.patient.jykj_zxyl.activity.home.myself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
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
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.HZIfno;
import entity.basicDate.ProvideBasicsRegion;
import entity.grzx.ProvidePatientConditionHealthy;
import entity.mySelf.ProvideViewPatientHealthyAndBasics;
import entity.mySelf.SubPatientBasicBean;
import entity.mySelf.conditions.QueryContactCond;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.patient.jykj_zxyl.activity.myself.AddMedicationActivity;
import www.patient.jykj_zxyl.adapter.ChoiceMedicationDateAdapter;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.*;

/**
 * 就诊总览==》个人总览==>个人状况==>基本健康状况
 */
public class JDDAJBJKXXActivity extends AppCompatActivity {


    private         Context                 mContext;
    private         JDDAJBJKXXActivity        mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;
    private JYZL_GRZLRecycleAdapter mJYZL_GRZLRecycleAdapter;      //适配器
    private         List<HZIfno>            mHZEntyties = new ArrayList<>();            //所有数据
    private         LinearLayout            mJBXX;                                  //基本信息
    private         String                  mPatientCode;                       //患者code

    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串
    private ProvidePatientConditionHealthy mProvidePatientConditionHealthy;


    private         LinearLayout            mBack;

    private         EditText                    et_sfzh;                        //身份证号
    private         EditText                    et_dh;                          //电话

    private         TextView                    tv_xglx;                          //性格类型
    private         TextView                    tv_mz;                          //民族
    private         TextView                    tv_jg;                          //籍贯

    private         EditText                    et_sg;                          //身高
    private         EditText                    et_tz;                          //体重
    private         EditText                    et_yw;                          //腰围

    private         TextView                    tv_sfxy;                          //是否吸烟
    private         TextView                    tv_sfxj;                          //是否酗酒
    private         TextView                    tv_sfay;                          //是否熬夜
    private         TextView                    tv_zzfxycsj;                      //最早发现异常时间
    private         TextView                    tv_qznf;                          //确诊年份
    private         TextView                    tv_zxybs;                          //高血压病史
    private         TextView                    tv_sfyjzs;                          //是否有家族史
    private         EditText                    et_gxybsms;                         //高血压病史描述

    private         LinearLayout                li_xglx;                        //性格类型
    private         LinearLayout                li_mz;                          //民族
    private         LinearLayout                li_jg;                             //籍贯
    private         LinearLayout                li_sfxy;                          //是否吸烟
    private         LinearLayout                li_sfxj;                          //是否酗酒
    private         LinearLayout                li_sfay;                          //是否熬夜
    private         LinearLayout                li_zzfxycsj;                      //最早发现异常时间
    private         LinearLayout                li_qznf;                          //确诊年份
    private         LinearLayout                li_zxybs;                          //高血压病史
    private         LinearLayout                li_sfyjzs;                          //是否有家族史


    private         ProvincePicker              mPicker;


    private                 String                   mSearchProvice;
    private                 String                   mSearchCity;
    private                 String                   mSearchArea;

    public Map<String,ProvideBasicsRegion> mChoiceRegionMap = new HashMap<>();                                  //用户选择的省市区map
    private                 int                           mChoiceRegionLevel;                                       //选择的区域级别
    private                 String                        mChoiceRegionID;                                       //选择的区域ID

    private TimePickerView timePickerView;

    private String time;

    private List<String> mData = new ArrayList<>();
    private ChoiceMedicationDateAdapter mAdapter;

    private TextView subinfo_btn;

    private LoadDataTask loadDataTask = null;
    private SubDataTask subDataTask = null;
    private boolean isupdateope = false;
    private int uphealthyid = 0;
    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout)this.findViewById(R.id.li_back);
        mBack.setOnClickListener(new ButtonClick());

        et_sfzh = (EditText)this.findViewById(R.id.et_sfzh);
        et_dh = (EditText)this.findViewById(R.id.et_dh);
        et_sg = (EditText)this.findViewById(R.id.et_sg);
        et_tz = (EditText)this.findViewById(R.id.et_tz);
        et_yw = (EditText)this.findViewById(R.id.et_yw);

        tv_xglx = (TextView) this.findViewById(R.id.tv_xglx);
        tv_mz = (TextView) this.findViewById(R.id.tv_mz);
        tv_jg = (TextView) this.findViewById(R.id.tv_jg);
        tv_sfxy = (TextView) this.findViewById(R.id.tv_sfxy);
        tv_sfxj = (TextView) this.findViewById(R.id.tv_sfxj);
        tv_sfay = (TextView) this.findViewById(R.id.tv_sfay);
        tv_zzfxycsj = (TextView) this.findViewById(R.id.tv_zzfxycsj);
        tv_qznf = (TextView) this.findViewById(R.id.tv_qznf);
        tv_zxybs = (TextView) this.findViewById(R.id.tv_zxybs);
        tv_sfyjzs = (TextView) this.findViewById(R.id.tv_sfyjzs);
        subinfo_btn = (TextView)findViewById(R.id.subinfo_btn);
        et_gxybsms = findViewById(R.id.et_gxybsms);
        li_xglx = (LinearLayout) this.findViewById(R.id.li_xglx);
        li_xglx.setOnClickListener(new ButtonClick());

        li_mz = (LinearLayout) this.findViewById(R.id.li_mz);
        li_mz.setOnClickListener(new ButtonClick());

        li_jg = (LinearLayout) this.findViewById(R.id.li_jg);
        li_jg.setOnClickListener(new ButtonClick());

        li_sfxy = (LinearLayout) this.findViewById(R.id.li_sfxy);
        li_sfxy.setOnClickListener(new ButtonClick());

        li_sfxj = (LinearLayout) this.findViewById(R.id.li_sfxj);
        li_sfxj.setOnClickListener(new ButtonClick());

        li_sfay = (LinearLayout) this.findViewById(R.id.li_sfay);
        li_sfay.setOnClickListener(new ButtonClick());

        li_zzfxycsj = (LinearLayout) this.findViewById(R.id.li_zzfxycsj);
        li_zzfxycsj.setOnClickListener(new ButtonClick());

        li_qznf = (LinearLayout) this.findViewById(R.id.li_qznf);
        li_qznf.setOnClickListener(new ButtonClick());

        li_zxybs = (LinearLayout) this.findViewById(R.id.li_zxybs);
        li_zxybs.setOnClickListener(new ButtonClick());

        li_sfyjzs = (LinearLayout) this.findViewById(R.id.li_sfyjzs);
        li_sfyjzs.setOnClickListener(new ButtonClick());
        subinfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_back:
                    finish();
                    break;
                case R.id.li_xglx:
                    //性格类型
                    List<String> xglx = new ArrayList<>();
                    xglx.add("内向");
                    xglx.add("外向");
                    OptionsPickerView optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_xglx.setText(xglx.get(options1));
                        }
                    }).setSelectOptions(0).build();
                    optionPickUnit.setPicker(xglx);
                    optionPickUnit.show();

                    break;
                case R.id.li_mz:
                    //民族
                    List<String> mz = getMZList();
                    optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_mz.setText(mz.get(options1));
                        }
                    }).setSelectOptions(0).build();
                    optionPickUnit.setPicker(mz);
                    optionPickUnit.show();
                    break;
                case R.id.li_jg:
                    //籍贯
                    if (mApp.gRegionProvideList == null || mApp.gRegionProvideList.size() == 0)
                    {
                        Toast.makeText(mContext,"区域数据为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //弹出对话框选择省份
                    mPicker = new ProvincePicker(mContext);
                    mPicker.setActivity(mActivity,8);
                    mPicker.show();
                    break;
                case R.id.li_sfxy:
                    //是否吸烟
                    List<String> sfxy = new ArrayList<>();
                    sfxy.add("是");
                    sfxy.add("否");
                    optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_sfxy.setText(sfxy.get(options1));
                        }
                    }).setSelectOptions(0).build();
                    optionPickUnit.setPicker(sfxy);
                    optionPickUnit.show();
                    break;
                case R.id.li_sfxj:
                    //是否酗酒
                    List<String> sfxj = new ArrayList<>();
                    sfxj.add("是");
                    sfxj.add("否");
                    optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_sfxj.setText(sfxj.get(options1));
                        }
                    }).setSelectOptions(0).build();
                    optionPickUnit.setPicker(sfxj);
                    optionPickUnit.show();
                    break;
                case R.id.li_sfay:
                    //是否熬夜
                    List<String> sfay = new ArrayList<>();
                    sfay.add("是");
                    sfay.add("否");
                    optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_sfay.setText(sfay.get(options1));
                        }
                    }).setSelectOptions(0).build();
                    optionPickUnit.setPicker(sfay);
                    optionPickUnit.show();
                    break;
                case R.id.li_zzfxycsj:
                    //最早发现血压异常时间
                    timePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            time = getDate(date);
                            tv_zzfxycsj.setText(time);
//                            mData.add(time);
//                            mAdapter.setDate(mData);
//                            mAdapter.notifyDataSetChanged();
                        }
                    }).setType(new boolean[]{true, true, true, false, false, false})
                            .setLabel("年", "月", "日", "", "", "").build();
                    timePickerView.show();
                    break;
                case R.id.li_qznf:
                    //确诊年份
                    timePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            time = getDate(date);
                            tv_qznf.setText(time);
//                            mData.add(time);
//                            mAdapter.setDate(mData);
//                            mAdapter.notifyDataSetChanged();
                        }
                    }).setType(new boolean[]{true, true, true, false, false, false})
                            .setLabel("年", "月", "日", "", "", "").build();
                    timePickerView.show();
                    break;
                case R.id.li_zxybs:
                    //高血压病史
                    List<String> gxybs = new ArrayList<>();
                    gxybs.add("是");
                    gxybs.add("否");
                    optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_zxybs.setText(gxybs.get(options1));
                        }
                    }).setSelectOptions(0).build();
                    optionPickUnit.setPicker(gxybs);
                    optionPickUnit.show();
                    break;
                case R.id.li_sfyjzs:
                    //是否有家族史
                    List<String> sfyjzs = new ArrayList<>();
                    sfyjzs.add("是");
                    sfyjzs.add("否");
                    optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_sfyjzs.setText(sfyjzs.get(options1));
                        }
                    }).setSelectOptions(0).build();
                    optionPickUnit.setPicker(sfyjzs);
                    optionPickUnit.show();
                    break;
            }
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdda_jbxx);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        initHandler();
        getDate();
    }



    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        break;
                    case 1:
                        cacerProgress();
                        mProvidePatientConditionHealthy = JSON.parseObject(JSON.parseObject(mNetRetStr,NetRetEntity.class).getResJsonData(),ProvidePatientConditionHealthy.class);
                        showViewDate();
                        break;
                    case 2:
                        break;
                }
            }
        };
    }



    /**
     * 设置显示
     */
    private void showViewDate() {


    }

    private String getDate(Date data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String string = simpleDateFormat.format(data);
        return string;
    }

    /**
     * 获取基本信息
     */
    private void getDate() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在加载数据");
       /* new Thread() {
            public void run() {
                try {
                    ProvidePatientConditionHealthy providePatientConditionHealthy = new ProvidePatientConditionHealthy();
                    providePatientConditionHealthy.setLoginPatientPosition(mApp.loginDoctorPosition);
                    providePatientConditionHealthy.setRequestClientType("1");
                    providePatientConditionHealthy.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    providePatientConditionHealthy.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());

                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(providePatientConditionHealthy), Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResHealthy");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取区域信息失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
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
        }.start();*/
       if(null==loadDataTask){
           QueryContactCond queryCond = new QueryContactCond();
           queryCond.setLoginPatientPosition(mApp.loginDoctorPosition);
           queryCond.setRequestClientType("1");
           queryCond.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
           queryCond.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
           loadDataTask = new LoadDataTask(queryCond);
           loadDataTask.execute();
       }else{
           loadDataTask.execute();
       }
    }

    void saveData(){
        //if(null==subDataTask){
            SubPatientBasicBean subbean = new SubPatientBasicBean();
            subbean.setLoginPatientPosition(mApp.loginDoctorPosition);
            subbean.setRequestClientType("1");
            subbean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
            subbean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
            if(isupdateope){
                subbean.setHealthyId(String.valueOf(uphealthyid));
            }else{
                subbean.setHealthyId("0");
            }
            subbean.setIdNumber(StrUtils.defaultStr(et_sfzh.getText()));
            subbean.setNation(StrUtils.defaultStr(tv_mz.getText()));
            subbean.setNativePlace(StrUtils.defaultStr(tv_jg.getText()));
            if(StrUtils.defaultStr(tv_xglx.getText()).equals("内向")){
                subbean.setCharacterType("1");
            }else if(StrUtils.defaultStr(tv_xglx.getText()).equals("外向")){
                subbean.setCharacterType("2");
            }else if(StrUtils.defaultStr(tv_xglx.getText()).equals("未知")){
                subbean.setCharacterType("0");
            }
            subbean.setHeight(StrUtils.defaultStr(et_sg.getText()));
            subbean.setWaistline(StrUtils.defaultStr(et_yw.getText()));
            subbean.setWeight(StrUtils.defaultStr(et_tz.getText()));
            if("是".equals(StrUtils.defaultStr(tv_sfxy.getText()))){
                subbean.setFlagSmoking("1");
            }else if("否".equals(StrUtils.defaultStr(tv_sfxy.getText()))){
                subbean.setFlagSmoking("0");
            }
            if("是".equals(StrUtils.defaultStr(tv_sfxj.getText()))){
                subbean.setFlagAlcoholism("1");
            }else if("否".equals(StrUtils.defaultStr(tv_sfxj.getText()))){
                subbean.setFlagAlcoholism("0");
            }
            if("是".equals(StrUtils.defaultStr(tv_sfay.getText()))){
                subbean.setFlagStayUpLate("1");
            }else if("否".equals(StrUtils.defaultStr(tv_sfay.getText()))){
                subbean.setFlagStayUpLate("0");
            }
            subbean.setBloodPressureAbnormalDate(StrUtils.defaultStr(tv_zzfxycsj.getText()));
            subbean.setConfirmedYear(StrUtils.defaultStr(tv_qznf.getText()));
            if("是".equals(StrUtils.defaultStr(tv_sfyjzs.getText()))){
                subbean.setFlagFamilyHtn("1");
            }else if("否".equals(StrUtils.defaultStr(tv_sfyjzs.getText()))){
                subbean.setFlagFamilyHtn("0");
            }
            if("是".equals(StrUtils.defaultStr(tv_zxybs.getText()))){
                subbean.setFlagHtnHistory("1");
            }else if("否".equals(StrUtils.defaultStr(tv_zxybs.getText()))){
                subbean.setFlagHtnHistory("0");
            }
            subbean.setHtnHistory(StrUtils.defaultStr(et_gxybsms.getText()));
            subDataTask = new SubDataTask(subbean);
            subDataTask.execute();
        //}
    }







    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

    private List<String> getMZList() {
        String mzString = "汉族、蒙古族、满族、朝鲜族、赫哲族、达斡尔族、鄂温克族、鄂伦春族、回族、东乡族、土族、撒拉族、保安族、裕固族、维吾尔族、哈萨克族、柯尔克孜族、锡伯族、塔吉克族、乌孜别克族、俄罗斯族、塔塔尔族、藏族、门巴族、珞巴族、羌族、彝族、白族、哈尼族、傣族、僳僳族、佤族、拉祜族、纳西族、景颇族、布朗族、阿昌族、普米族、怒族、德昂族、独龙族、基诺族、苗族、布依族、侗族、水族、仡佬族、壮族、瑶族、仫佬族、毛南族、京族、土家族、黎族、畲族、高山族56个民族";
        String[] mzArray = mzString.split("、");
        List<String> mzList =  Arrays.asList(mzArray);
        return mzList;
    }


    /**
     * 设置所在地区显示
     */
    public void setRegionText() {

        if ("sqb".equals(mChoiceRegionMap.get("city").getRegion_id())) {
            tv_jg.setText(mChoiceRegionMap.get("provice").getRegion_name());
            mChoiceRegionLevel = 1;               //市级所有，则是省级
            mChoiceRegionID = mChoiceRegionMap.get("provice").getRegion_id();

            mSearchProvice = mChoiceRegionMap.get("provice").getRegion_id();
            mSearchCity = "";
            mSearchArea = "";

        } else if (mChoiceRegionMap.get("dist") == null || "qqb".equals(mChoiceRegionMap.get("dist").getRegion_id())) {
            tv_jg.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();

            mSearchProvice = mChoiceRegionMap.get("provice").getRegion_id();
            mSearchCity = mChoiceRegionMap.get("city").getRegion_id();
            mSearchArea = "";

//            provideViewDoctorExpertRecommend.setSearchArea("");
        }
        if (!"sqb".equals(mChoiceRegionMap.get("city").getRegion_id())) {
//            provideViewDoctorExpertRecommend.setSearchCity(mChoiceRegionMap.get("city").getRegion_id());
            tv_jg.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();

            mSearchProvice = mChoiceRegionMap.get("provice").getRegion_id();
            mSearchCity = mChoiceRegionMap.get("city").getRegion_id();
            mSearchArea = "";

        }
        if (mChoiceRegionMap.get("dist") != null && !"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id())) {
            tv_jg.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name()+mChoiceRegionMap.get("dist").getRegion_name());
//            provideViewDoctorExpertRecommend.setSearchArea(mChoiceRegionMap.get("dist").getRegion_id());
            mChoiceRegionLevel = 3;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("dist").getRegion_id();

            mSearchProvice = mChoiceRegionMap.get("provice").getRegion_id();
            mSearchCity = mChoiceRegionMap.get("city").getRegion_id();
            mSearchArea =  mChoiceRegionMap.get("dist").getRegion_id();
        }
//        provideViewDoctorExpertRecommendList.clear();

    }

    class LoadDataTask extends AsyncTask<Void,Void, ProvideViewPatientHealthyAndBasics>{
        private QueryContactCond queryContactCond;

        public LoadDataTask(QueryContactCond queryContactCond) {
            this.queryContactCond = queryContactCond;
        }

        @Override
        protected ProvideViewPatientHealthyAndBasics doInBackground(Void... voids) {
            ProvideViewPatientHealthyAndBasics retbean = null;
            try {
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryContactCond),Constant.SERVICEURL+ INetAddress.QUERY_HEALTHY_BASIC_URL);
                NetRetEntity retnetbean = JSON.parseObject(retnetstr,NetRetEntity.class);
                if(1==retnetbean.getResCode()){
                    if(null!=retnetbean.getResJsonData() && retnetbean.getResJsonData().length()>3){
                        retbean = JSON.parseObject(retnetbean.getResJsonData(),ProvideViewPatientHealthyAndBasics.class);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retbean;
        }

        @Override
        protected void onPostExecute(ProvideViewPatientHealthyAndBasics provideViewPatientHealthyAndBasics) {
            if(null!=provideViewPatientHealthyAndBasics){
                et_sfzh.setText(StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getIdNumber()));
                et_dh.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());
                tv_xglx.setText("未知");
                if(null!=provideViewPatientHealthyAndBasics.getCharacterType()) {
                    if (1 == provideViewPatientHealthyAndBasics.getCharacterType().intValue())
                        tv_xglx.setText("内向");
                    else if (2 == provideViewPatientHealthyAndBasics.getCharacterType().intValue())
                        tv_xglx.setText("外向");
                }
                tv_mz.setText(StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getNation()));
                tv_jg.setText(StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getNativePlace()));
                et_sg.setText(StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getHeight()));
                et_yw.setText(StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getWaistline()));
                et_tz.setText(StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getWeight()));
                if(1==provideViewPatientHealthyAndBasics.getFlagSmoking().intValue())
                    tv_sfxy.setText("是");
                else
                    tv_sfxy.setText("否");
                if(1==provideViewPatientHealthyAndBasics.getFlagAlcoholism().intValue())
                    tv_sfxj.setText("是");
                else
                    tv_sfxj.setText("否");
                if(1==provideViewPatientHealthyAndBasics.getFlagStayUpLate().intValue())
                    tv_sfay.setText("是");
                else
                    tv_sfay.setText("否");
                if(null!=provideViewPatientHealthyAndBasics.getBloodPressureAbnormalDate())
                    tv_zzfxycsj.setText(DateUtils.getStringTimeOfYMD(provideViewPatientHealthyAndBasics.getBloodPressureAbnormalDate().getTime()));
                if(null!=provideViewPatientHealthyAndBasics.getConfirmedYear())
                    tv_qznf.setText(DateUtils.fomrDateSeflFormat(provideViewPatientHealthyAndBasics.getConfirmedYear(),"yyyy"));
                if(1==provideViewPatientHealthyAndBasics.getFlagHtnHistory().intValue())
                    tv_zxybs.setText("是");
                else
                    tv_zxybs.setText("否");
                if(1==provideViewPatientHealthyAndBasics.getFlagFamilyHtn())
                    tv_sfyjzs.setText("是");
                else
                    tv_sfyjzs.setText("否");
                et_gxybsms.setText(StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getHtnHistory()));
                uphealthyid = provideViewPatientHealthyAndBasics.getHealthyId().intValue();
                isupdateope = true;
            }
            cacerProgress();
        }

    }

    class SubDataTask extends AsyncTask<Void,Void,Boolean>{
        SubPatientBasicBean subean;
        String submsg = "";
        SubDataTask(SubPatientBasicBean subean){
            this.subean = subean;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String substr = new Gson().toJson(subean);
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+substr,Constant.SERVICEURL+INetAddress.MAINTAIN_HEALTHY_BASIC_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr,NetRetEntity.class);
                if(1==retEntity.getResCode()){
                    submsg = "保存成功";
                    return true;
                }else{
                    submsg = retEntity.getResMsg();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(mContext,submsg,Toast.LENGTH_SHORT);
            if(aBoolean){
                finish();
            }
        }
    }
}
