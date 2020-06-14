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
import com.google.gson.Gson;
import com.google.zxing.Result;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.HZIfno;
import entity.basicDate.ProvideBasicsDomain;
import entity.mySelf.ProvideViewPatientHealthyAndBasics;
import entity.mySelf.SubSymptomInfo;
import entity.mySelf.conditions.QueryContactCond;
import entity.patientInfo.ProvidePatientLabel;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.PatientLaberAdapter;
import www.patient.jykj_zxyl.adapter.myself.JDDAQBZZRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.StrUtils;
import www.patient.jykj_zxyl.util.Util;
import zxing.android.CaptureActivity;
import zxing.decode.DecodeImgCallback;
import zxing.decode.DecodeImgThread;
import zxing.decode.ImageUtil;

/**
 * 个人中心==》建档档案==》==>症状信息
 */
public class ZZXXActivity extends AppCompatActivity {


    private         Context                 mContext;
    private ZZXXActivity mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            mQBZZRecycleView;           //起病症状
    private         RecyclerView            mMQZZRecycleView;          //目前症状
    private         RecyclerView            mBFZRecycleView;          //并发症
    private         RecyclerView            mHBJBRecycleView;          //合并疾病
    private         RecyclerView            mMQZLFARecycleView;          //目前治疗方案


    private         JDDAQBZZRecycleAdapter mJDDAQBZZRecycleAdapter;      //起病症状适配器
    private         JDDAQBZZRecycleAdapter mJDDAMQZZRecycleAdapter;      //目前症状症状适配器
    private         JDDAQBZZRecycleAdapter mJDDABFZRecycleAdapter;      //并发症适配器
    private         JDDAQBZZRecycleAdapter mJDDAHBJBRecycleAdapter;      //合并疾病适配器
    private         JDDAQBZZRecycleAdapter mJDDAMQZLFARecycleAdapter;      //目前治疗方案适配器

    private         List<ProvideBasicsDomain>            mQBZZList = new ArrayList<>();            //起病症状
    private         List<ProvideBasicsDomain>            mMQZZList = new ArrayList<>();            //目前症状
    private         List<ProvideBasicsDomain>            mBFZList = new ArrayList<>();            //并发症
    private         List<ProvideBasicsDomain>            mHBJBList = new ArrayList<>();            //合并疾病
    private         List<ProvideBasicsDomain>            mMQZLFAList = new ArrayList<>();            //目前治疗方案

    private         LinearLayout            mJBXX;                                  //基本信息
    private         String                  mPatientCode;                       //患者code

    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串

    private         LinearLayout            li_qbzz;
    private         LinearLayout            li_mqzz;
    private         LinearLayout            li_bfz;
    private         LinearLayout            li_hbjb;
    private         LinearLayout            li_mqzlfa;
    private         LinearLayout    li_back;
    private TextView subinfo_btn;
    private EditText self_descrip;
    private LoadDataTask loadDataTask = null;
    private SubDataTask subDataTask = null;
    private boolean isupstate = false;
    private int upid = 0;
    /**
     * 初始化布局
     */
    private void initLayout() {
        li_back = (LinearLayout)this.findViewById(R.id.li_back);
        li_back.setOnClickListener(new ButtonClick());
        //起病症状
        mQBZZRecycleView = (RecyclerView) this.findViewById(R.id.rvqbzz);
        //目前症状
        mMQZZRecycleView = (RecyclerView) this.findViewById(R.id.rv_mqzz);
        //并发症
        mBFZRecycleView = (RecyclerView) this.findViewById(R.id.rv_bfz);
        //合并疾病
        mHBJBRecycleView = (RecyclerView) this.findViewById(R.id.rv_hbjb);
        //目前治疗方案
        mMQZLFARecycleView = (RecyclerView) this.findViewById(R.id.rv_mqzlfa);

        //起病症状
        FullyGridLayoutManager mGridLayoutManager = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mQBZZRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mQBZZRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDAQBZZRecycleAdapter = new JDDAQBZZRecycleAdapter(mQBZZList,mContext,mActivity);
        mQBZZRecycleView.setAdapter(mJDDAQBZZRecycleAdapter);

        //目前症状
        FullyGridLayoutManager mGridLayoutManagermqzz = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManagermqzz.setOrientation(LinearLayout.VERTICAL);
        mMQZZRecycleView.setLayoutManager(mGridLayoutManagermqzz);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mMQZZRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDAMQZZRecycleAdapter = new JDDAQBZZRecycleAdapter(mMQZZList,mContext,mActivity);
        mMQZZRecycleView.setAdapter(mJDDAMQZZRecycleAdapter);

        //并发症
        FullyGridLayoutManager mGridLayoutManagerBBZ = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManagerBBZ.setOrientation(LinearLayout.VERTICAL);
        mBFZRecycleView.setLayoutManager(mGridLayoutManagerBBZ);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mBFZRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDABFZRecycleAdapter = new JDDAQBZZRecycleAdapter(mBFZList,mContext,mActivity);
        mBFZRecycleView.setAdapter(mJDDABFZRecycleAdapter);

        //合并疾病
        FullyGridLayoutManager mGridLayoutManagerHBJB = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManagerHBJB.setOrientation(LinearLayout.VERTICAL);
        mHBJBRecycleView.setLayoutManager(mGridLayoutManagerHBJB);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mHBJBRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDAHBJBRecycleAdapter = new JDDAQBZZRecycleAdapter(mHBJBList,mContext,mActivity);
        mHBJBRecycleView.setAdapter(mJDDAHBJBRecycleAdapter);

        //目前治疗方案
        FullyGridLayoutManager mGridLayoutManagerMQZLFA = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManagerMQZLFA.setOrientation(LinearLayout.VERTICAL);
        mMQZLFARecycleView.setLayoutManager(mGridLayoutManagerMQZLFA);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mMQZLFARecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDAMQZLFARecycleAdapter = new JDDAQBZZRecycleAdapter(mMQZLFAList,mContext,mActivity);
        mMQZLFARecycleView.setAdapter(mJDDAMQZLFARecycleAdapter);


        li_qbzz = (LinearLayout)this.findViewById(R.id.li_qbzz);
        li_mqzz = (LinearLayout)this.findViewById(R.id.li_mqzz);
        li_bfz = (LinearLayout)this.findViewById(R.id.li_bfz);
        li_hbjb = (LinearLayout)this.findViewById(R.id.li_hbjb);
        li_mqzlfa = (LinearLayout)this.findViewById(R.id.li_mqzlfa);
        subinfo_btn = (TextView)this.findViewById(R.id.subinfo_btn);
        self_descrip = (EditText)this.findViewById(R.id.self_descrip);
        ButtonClick parbtnclick = new ButtonClick();
        li_qbzz.setOnClickListener(parbtnclick);
        li_mqzz.setOnClickListener(parbtnclick);
        li_bfz.setOnClickListener(parbtnclick);
        li_hbjb.setOnClickListener(parbtnclick);
        li_mqzlfa.setOnClickListener(parbtnclick);
        subinfo_btn.setOnClickListener(parbtnclick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            mQBZZList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
            if (mQBZZList != null)
            {
                mJDDAQBZZRecycleAdapter.setDate(mQBZZList);
                mJDDAQBZZRecycleAdapter.notifyDataSetChanged();
            }

        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            if (mMQZZList != null) {
                mMQZZList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
                mJDDAMQZZRecycleAdapter.setDate(mMQZZList);
                mJDDAMQZZRecycleAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            if (mBFZList != null) {
                mBFZList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
                mJDDABFZRecycleAdapter.setDate(mBFZList);
                mJDDABFZRecycleAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 4 && resultCode == RESULT_OK) {
            if (mHBJBList != null) {
                mHBJBList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
                mJDDAHBJBRecycleAdapter.setDate(mHBJBList);
                mJDDAHBJBRecycleAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 5 && resultCode == RESULT_OK) {
            if (mMQZLFAList != null) {
                mMQZLFAList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
                mJDDAMQZLFARecycleAdapter.setDate(mMQZLFAList);
                mJDDAMQZLFARecycleAdapter.notifyDataSetChanged();
            }
        }
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_back:
                    finish();
                    break;
                case R.id.li_qbzz:

                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",1)
                            .putExtra("zzxx",(Serializable) mQBZZList),
                            1);
                    break;
                case R.id.li_mqzz:
                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",2)
                            .putExtra("zzxx",(Serializable) mMQZZList),2);
                    break;
                case R.id.li_bfz:
                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",3)
                            .putExtra("zzxx",(Serializable) mBFZList),3);
                    break;
                case R.id.li_hbjb:
                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",4)
                            .putExtra("zzxx",(Serializable) mHBJBList),4);
                    break;
                case R.id.li_mqzlfa:
                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",5)
                            .putExtra("zzxx",(Serializable) mMQZLFAList),5);
                    break;
                case R.id.subinfo_btn:
                    savedata();
                    break;
            }
        }
    }

    void savedata(){
        SubSymptomInfo subinfo = new SubSymptomInfo();
        subinfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        subinfo.setRequestClientType("1");
        subinfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        subinfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        if(isupstate){
            subinfo.setHealthyId(String.valueOf(upid));
        }else{
            subinfo.setHealthyId("0");
        }
        StringBuffer codesb = new StringBuffer();
        for(int i=0;i<mQBZZList.size();i++){
            ProvideBasicsDomain parbean = mQBZZList.get(i);
            if(codesb.toString().length()>0){
                codesb.append("^");
                codesb.append(parbean.getAttrCode());
            }
        }
        if(codesb.toString().length()>0){
            subinfo.setOnsetSymptoms(codesb.toString());
        }else{
            subinfo.setOnsetSymptoms("");
        }
        codesb = new StringBuffer();
        for(int i=0;i<mMQZZList.size();i++){
            ProvideBasicsDomain parbean = mMQZZList.get(i);
            if(codesb.toString().length()>0){
                codesb.append("^");
                codesb.append(parbean.getAttrCode());
            }
        }
        if(codesb.toString().length()>0){
            subinfo.setCurrentSymptoms(codesb.toString());
        }else{
            subinfo.setCurrentSymptoms("");
        }
        codesb = new StringBuffer();
        for(int i=0;i<mBFZList.size();i++){
            ProvideBasicsDomain parbean = mBFZList.get(i);
            if(codesb.toString().length()>0){
                codesb.append("^");
                codesb.append(parbean.getAttrCode());
            }
        }
        if(codesb.toString().length()>0){
            subinfo.setComplication(codesb.toString());
        }else{
            subinfo.setComplication("");
        }
        codesb = new StringBuffer();
        for(int i=0;i<mHBJBList.size();i++){
            ProvideBasicsDomain parbean = mHBJBList.get(i);
            if(codesb.toString().length()>0){
                codesb.append("^");
                codesb.append(parbean.getAttrCode());
            }
        }
        if(codesb.toString().length()>0){
            subinfo.setCombinedDisease(codesb.toString());
        }else{
            subinfo.setCombinedDisease("");
        }
        codesb = new StringBuffer();
        for(int i=0;i<mMQZLFAList.size();i++){
            ProvideBasicsDomain parbean = mMQZLFAList.get(i);
            if(codesb.toString().length()>0){
                codesb.append("^");
                codesb.append(parbean.getAttrCode());
            }
        }
        if(codesb.toString().length()>0){
            subinfo.setCurrentTreatmentPlan(codesb.toString());
        }else{
            subinfo.setCurrentTreatmentPlan("");
        }
        subinfo.setStateOfIllness(StrUtils.defaultStr(self_descrip.getText()));
        subDataTask = new SubDataTask(subinfo);
        subDataTask.execute();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdda_zzxx);
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
                    ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
                    provideViewSysUserPatientInfoAndRegion.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewSysUserPatientInfoAndRegion.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideViewSysUserPatientInfoAndRegion.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    provideViewSysUserPatientInfoAndRegion.setSearchPatientCode(mPatientCode);

                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideViewSysUserPatientInfoAndRegion), Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResHealthy");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取区域信息失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
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
       if(null==loadDataTask) {
           QueryContactCond querycond = new QueryContactCond();
           querycond.setLoginPatientPosition(mApp.loginDoctorPosition);
           querycond.setRequestClientType("1");
           querycond.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
           querycond.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
           loadDataTask = new LoadDataTask(querycond);
           loadDataTask.execute();
       }else{
           loadDataTask.execute();
       }

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

    class SubDataTask extends AsyncTask<Void,Void,Boolean>{
        SubSymptomInfo subean;
        String savemsg = "";
        SubDataTask(SubSymptomInfo subean){
            this.subean = subean;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(subean),Constant.SERVICEURL+INetAddress.QUERY_HEALTHY_SYMPTOM_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr,NetRetEntity.class);
                if(1==retEntity.getResCode()){
                    savemsg = "保存成功";
                    return true;
                }else{
                    savemsg = retEntity.getResMsg();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                savemsg = "保存失败";
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean) {
                Toast.makeText(mContext, savemsg, Toast.LENGTH_SHORT);
                finish();
            }
            else
                Toast.makeText(mContext,savemsg,Toast.LENGTH_SHORT);
        }
    }

    class LoadDataTask extends AsyncTask<Void,Void, ProvideViewPatientHealthyAndBasics>{
        QueryContactCond queryCond;
        LoadDataTask(QueryContactCond queryCond){
            this.queryCond = queryCond;
        }
        @Override
        protected ProvideViewPatientHealthyAndBasics doInBackground(Void... voids) {
            ProvideViewPatientHealthyAndBasics retbean = null;
            try {
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryCond),Constant.SERVICEURL+ INetAddress.QUERY_HEALTHY_SYMPTOM_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retbean = JSON.parseObject(retEntity.getResJsonData(),ProvideViewPatientHealthyAndBasics.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retbean;
        }

        @Override
        protected void onPostExecute(ProvideViewPatientHealthyAndBasics provideViewPatientHealthyAndBasics) {
            if(null!=provideViewPatientHealthyAndBasics){
                String parattrcodes = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getOnsetSymptoms());
                String parattrnames = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getOnsetSymptomsName());
                if(parattrcodes.length()>0){
                    String[] paracodearr = parattrcodes.split("^");
                    String[] paranamearr = parattrnames.split("^");
                    mQBZZList = new ArrayList<>();
                    for(int i=0;i<paracodearr.length;i++){
                        ProvideBasicsDomain attrbean = new ProvideBasicsDomain();
                        attrbean.setAttrCode(Integer.parseInt(paracodearr[i]));
                        attrbean.setAttrName(paranamearr[i]);
                        mQBZZList.add(attrbean);
                    }
                    mJDDAQBZZRecycleAdapter.setDate(mQBZZList);
                    mJDDAQBZZRecycleAdapter.notifyDataSetChanged();
                }

                parattrcodes = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getCurrentSymptoms());
                parattrnames = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getCurrentSymptomsName());
                if(parattrcodes.length()>0){
                    String[] paracodearr = parattrcodes.split("^");
                    String[] paranamearr = parattrnames.split("^");
                    mMQZZList = new ArrayList<>();
                    for(int i=0;i<paracodearr.length;i++){
                        ProvideBasicsDomain attrbean = new ProvideBasicsDomain();
                        attrbean.setAttrCode(Integer.parseInt(paracodearr[i]));
                        attrbean.setAttrName(paranamearr[i]);
                        mMQZZList.add(attrbean);
                    }
                    mJDDAMQZZRecycleAdapter.setDate(mMQZZList);
                    mJDDAMQZZRecycleAdapter.notifyDataSetChanged();
                }

                parattrcodes = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getComplication());
                parattrnames = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getComplicationName());
                if(parattrcodes.length()>0){
                    String[] paracodearr = parattrcodes.split("^");
                    String[] paranamearr = parattrnames.split("^");
                    mBFZList = new ArrayList<>();
                    for(int i=0;i<paracodearr.length;i++){
                        ProvideBasicsDomain attrbean = new ProvideBasicsDomain();
                        attrbean.setAttrCode(Integer.parseInt(paracodearr[i]));
                        attrbean.setAttrName(paranamearr[i]);
                        mBFZList.add(attrbean);
                    }
                    mJDDABFZRecycleAdapter.setDate(mBFZList);
                    mJDDABFZRecycleAdapter.notifyDataSetChanged();
                }

                parattrcodes = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getCombinedDisease());
                parattrnames = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getCombinedDiseaseName());
                if(parattrcodes.length()>0){
                    String[] paracodearr = parattrcodes.split("^");
                    String[] paranamearr = parattrnames.split("^");
                    mHBJBList = new ArrayList<>();
                    for(int i=0;i<paracodearr.length;i++){
                        ProvideBasicsDomain attrbean = new ProvideBasicsDomain();
                        attrbean.setAttrCode(Integer.parseInt(paracodearr[i]));
                        attrbean.setAttrName(paranamearr[i]);
                        mHBJBList.add(attrbean);
                    }
                    mJDDAHBJBRecycleAdapter.setDate(mHBJBList);
                    mJDDAHBJBRecycleAdapter.notifyDataSetChanged();
                }

                parattrcodes = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getCurrentTreatmentPlan());
                parattrnames = StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getCurrentTreatmentPlanName());
                if(parattrcodes.length()>0){
                    String[] paracodearr = parattrcodes.split("^");
                    String[] paranamearr = parattrnames.split("^");
                    mMQZLFAList = new ArrayList<>();
                    for(int i=0;i<paracodearr.length;i++){
                        ProvideBasicsDomain attrbean = new ProvideBasicsDomain();
                        attrbean.setAttrCode(Integer.parseInt(paracodearr[i]));
                        attrbean.setAttrName(paranamearr[i]);
                        mMQZLFAList.add(attrbean);
                    }
                    mJDDAMQZLFARecycleAdapter.setDate(mMQZLFAList);
                    mJDDAMQZLFARecycleAdapter.notifyDataSetChanged();
                }

                self_descrip.setText(StrUtils.defaultStr(provideViewPatientHealthyAndBasics.getStateOfIllness()));
                isupstate = true;
                upid = provideViewPatientHealthyAndBasics.getHealthyId().intValue();
            }
            cacerProgress();
        }
    }
}
