package www.patient.jykj_zxyl.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.RxPermissions;


import java.util.ArrayList;
import java.util.List;

import entity.OperUpdPatientConditionTakingMedicineStateParement;
import entity.ProvidePatientBindingMyDoctorInfo;
import entity.shouye.OperScanQrCodeInside;
import entity.shouye.ProvidePatientConditionBloodPressureRecord;
import entity.shouye.ProvidePatientConditionTakingRecord;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import rx.functions.Action1;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.BloodMonitorActivity;
import www.patient.jykj_zxyl.activity.home.NewsActivity;
import www.patient.jykj_zxyl.activity.home.QRCodeActivity;
import www.patient.jykj_zxyl.activity.home.patient.BloodEntryActivity;
import www.patient.jykj_zxyl.activity.hyhd.BindDoctorFriend;
import www.patient.jykj_zxyl.activity.myself.MedicationSettingsActivity;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.activity.patient_home.KSWYSActivity;
import www.patient.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_WDYS;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_YLZX;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_ZJTJ;
import www.patient.jykj_zxyl.util.Util;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.BloodMonitorActivity;
import www.patient.jykj_zxyl.activity.home.QRCodeActivity;
import www.patient.jykj_zxyl.activity.home.patient.BloodEntryActivity;
import www.patient.jykj_zxyl.activity.hyhd.BindDoctorFriend;
import www.patient.jykj_zxyl.activity.myself.MedicationSettingsActivity;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.activity.patient_home.KSWYSActivity;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_WDYS;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_YLZX;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_ZJTJ;
import www.patient.jykj_zxyl.util.CircleImageView;
import www.patient.jykj_zxyl.util.Util;
import zxing.android.CaptureActivity;


/**
 * 首页fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe extends Fragment implements View.OnClickListener {
    private             Context                             mContext;
    private MainActivity mActivity;
    private             String                              mNetRetStr;                 //返回字符串
    private             String                              mNetRetFYStr;                 //返回服药
    private             Handler                             mHandler;
    private             JYKJApplication                     mApp;
    private LinearLayout mQrCode;
    private LinearLayout mNews;
    private LinearLayout mDoctorUnion;
    private LinearLayout mYQTH;//邀请同行
    private LinearLayout mMyComments;//我的评价
    private LinearLayout mMyLiveRoom;//我的直播间
//    private LinearLayout mAddPatient;                   //添加患者
    private LinearLayout mScan;      //扫一扫
    private LinearLayout mMyClinic;//我的诊所
    private LinearLayout mMyPatient;
    public static final int REQUEST_CODE_SCAN = 0x123;

    private CircleImageView mHeard;                       //患者头像
    private TextView  mUserNameText;                //用户名
    private TextView  mUserTitleText;               //医生职称

    private TextView  mNewMessage;                  //新消息提醒
    private LinearLayout    mNewMessageLayout;          //新消息提醒
    private LinearLayout llQuickApplication;//快应用
    private MoreFeaturesPopupWindow mPopupWindow;



    private             FragmentShouYe                      mFragment;

    public ProgressDialog mDialogProgress = null;

    private             String                              qrCode;                         //需要绑定的二维码

    private             List<ProvideViewDoctorExpertRecommend> provideViewDoctorExpertRecommendList = new ArrayList<>();            //获取到的专家数据
    private             ProvidePatientConditionBloodPressureRecord  mProvidePatientConditionBloodPressureRecord;     //获取到的血压数据
    private             List<ProvidePatientConditionTakingRecord>   mProvidePatientConditionTakingRecords;     //获取到的服药数据
    private             TextView                            mXYValueText;                       //血压值
    private             TextView                            mXLValueText;                       //心率值
    private             TextView                            mUpdateDate;                        //更新时间
    private             LinearLayout                            mXLTX;                              //心率
    private             LinearLayout                        mFYTX;                              //服药提醒

    private FragmentShouYe_WDYS mFragmentShouYe_WDYS;               //我的诊所
    private FragmentShouYe_ZJTJ mFragmentShouYe_ZJTJ;               //专家推荐
    private FragmentShouYe_YLZX mFragmentShouYe_YLZX;               //医疗资讯



    /**
     * 血压录入
     */
    private TextView    mBloodEntry;
    /**
     * 血压记录
     */
    private LinearLayout llBloodRecord;

    private             LinearLayout                    mCSHAll;                            //初始化整个布局
    private             LinearLayout                    mCSHXY;                             //初始化血压
    private             LinearLayout                    mCSHYY;                             //初始化用药

    private             LinearLayout                    mXYTX;                              //血压提醒
    private             LinearLayout                    mYYTX;                              //用药提醒

    private             TextView                        mYPMC1;                             //药品名称1
    private             TextView                        mYPMC2;                             //药品名称2
    private             TextView                        mYPYL1;                             //药品用量1
    private             TextView                        mYPYL2;                             //药品用量2

    private             LinearLayout                    mYPTX2;                             //药品提醒2


    private                 ViewPager               pager;
    private FragmentAdapter fragmentAdapter;
    private                 List<Fragment>          fragmentList;
    private                 TabLayout               tabLayout;
    private                 List<String>            mTitles;

    private                 View                    mView;

    private                 TextView                mYFY;                           //已服用
    private                 TextView                mWFY;                           //未服用
    private                 TextView                mYYDate;                        //用药时间

    private                 LinearLayout            mKSWYS;                         //快速问医生






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitymain_shouyefragment, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mFragment = this;
        mApp = (JYKJApplication) getActivity().getApplication();
        initHandler();

        initView(v);
        mView = v;
        initListener();
//        getNewMessage();

        return v;
    }

    /**
     * 获取消息
     */
    private void getNewMessage() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getProgressBar("请稍候","正在加载数据。。。");
        //获取最近一次血压数据
        searchPatientStateResBloodPressureNewData();
    }

    /**
     * 获取最近一次血压数据
     */
    private void searchPatientStateResBloodPressureNewData() {
        ProvidePatientConditionBloodPressureRecord providePatientConditionBloodPressureRecord = new ProvidePatientConditionBloodPressureRecord();
        providePatientConditionBloodPressureRecord.setLoginPatientPosition(mApp.loginDoctorPosition);
        providePatientConditionBloodPressureRecord.setRequestClientType("1");
        providePatientConditionBloodPressureRecord.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        providePatientConditionBloodPressureRecord.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(providePatientConditionBloodPressureRecord);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"/PatientConditionControlle/searchPatientStateResBloodPressureNewData");
                    NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                    if (netRetEntity.getResCode() == 1)
                        mProvidePatientConditionBloodPressureRecord = JSON.parseObject(netRetEntity.getResJsonData(),ProvidePatientConditionBloodPressureRecord.class);
                    //获取最近一次服药数据
                    searchPatientStateResTakingMedicineRecordNewDataUnexecuted();
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     * 获取最近一次服药数据
     */
    private void searchPatientStateResTakingMedicineRecordNewDataUnexecuted() {
        ProvidePatientConditionTakingRecord providePatientConditionTakingRecord = new ProvidePatientConditionTakingRecord();
        providePatientConditionTakingRecord.setLoginPatientPosition(mApp.loginDoctorPosition);
        providePatientConditionTakingRecord.setRequestClientType("1");
        providePatientConditionTakingRecord.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        providePatientConditionTakingRecord.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(providePatientConditionTakingRecord);
                    mNetRetFYStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"/PatientConditionControlle/searchPatientStateResTakingMedicineRecordNewDataUnexecuted");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetFYStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:

                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        else
                        {
                            provideViewDoctorExpertRecommendList = JSON.parseArray(netRetEntity.getResJsonData(),ProvideViewDoctorExpertRecommend.class);
                        }

                        break;
                    case 1:

                        break;
                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetFYStr,NetRetEntity.class);

                        if (netRetEntity.getResCode() == 1)
                        {
                            mProvidePatientConditionTakingRecords = JSON.parseArray(netRetEntity.getResJsonData(),ProvidePatientConditionTakingRecord.class);
                        }
                        showRecord();

                        break;

                    case 5:
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        cacerProgress();
                        //获取最近一次血压数据
                        searchPatientStateResBloodPressureNewData();
                        break;
                }
            }
        };
    }

    /**
     * 获取我的医生数据
     */
    private void searchIndexMyDoctorShow() {
        ProvidePatientBindingMyDoctorInfo providePatientBindingMyDoctorInfo = new ProvidePatientBindingMyDoctorInfo();
        providePatientBindingMyDoctorInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        providePatientBindingMyDoctorInfo.setRequestClientType("1");
        providePatientBindingMyDoctorInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        providePatientBindingMyDoctorInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        providePatientBindingMyDoctorInfo.setShowNum("4");

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(providePatientBindingMyDoctorInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorShow");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(6);
            }
        }.start();
    }


    /**
     * 展示数据
     */
    private void showRecord() {
        //判断血压和服药数据
        if (mProvidePatientConditionBloodPressureRecord == null || mProvidePatientConditionTakingRecords == null || mProvidePatientConditionTakingRecords.size() == 0)
        {
            //如果
            mCSHAll.setVisibility(View.VISIBLE);
            if (mProvidePatientConditionBloodPressureRecord == null)
            {
                mCSHXY.setVisibility(View.VISIBLE);
                mXYTX.setVisibility(View.GONE);
            }

            else
            {
                mCSHXY.setVisibility(View.GONE);
                mXYTX.setVisibility(View.VISIBLE);
                mXYValueText.setText(mProvidePatientConditionBloodPressureRecord.getHighPressureNum()+"/"+mProvidePatientConditionBloodPressureRecord.getLowPressureNum());
                mXLValueText.setText(mProvidePatientConditionBloodPressureRecord.getHeartRateNum()+"");
                mUpdateDate.setText(Util.dateToStrTime(mProvidePatientConditionBloodPressureRecord.getRecordTime()));
            }
            if (mProvidePatientConditionTakingRecords == null || mProvidePatientConditionTakingRecords.size() == 0)
            {
                mCSHYY.setVisibility(View.VISIBLE);
                mYYTX.setVisibility(View.GONE);
            }
            else
            {
                mCSHYY.setVisibility(View.GONE);
                mYYTX.setVisibility(View.VISIBLE);
            }

        }
        else
        {
            mCSHAll.setVisibility(View.GONE);
        }


        if (mProvidePatientConditionTakingRecords != null && mProvidePatientConditionTakingRecords.size() > 0)
        {
            mYYTX.setVisibility(View.VISIBLE);
            mYPMC1.setVisibility(View.VISIBLE);
            mYPYL1.setVisibility(View.VISIBLE);
            mYPMC1.setText(mProvidePatientConditionTakingRecords.get(0).getDrugName());
            if (mProvidePatientConditionTakingRecords.get(0).getUseNum() == null)
                mYPYL1.setText(mProvidePatientConditionTakingRecords.get(0).getUseNum()+"/未设置");
           else if (mProvidePatientConditionTakingRecords.get(0).getUseUnit() == null)
                mYPYL1.setText("未设置/"+mProvidePatientConditionTakingRecords.get(0).getUseUnit());
            else
                mYPYL1.setText(mProvidePatientConditionTakingRecords.get(0).getUseNum()+"/"+mProvidePatientConditionTakingRecords.get(0).getUseUnit());
            if (mProvidePatientConditionTakingRecords.get(0).getTakingMedicineTime() != null)
                mYYDate.setText("时间："+Util.dateToStrTime(mProvidePatientConditionTakingRecords.get(0).getTakingMedicineTime()));
            else
                mYYDate.setText("未设置");
        }
        if (mProvidePatientConditionTakingRecords != null && mProvidePatientConditionTakingRecords.size() > 1)
        {
            mYYTX.setVisibility(View.VISIBLE);
            mYPMC2.setVisibility(View.VISIBLE);
            mYPYL2.setVisibility(View.VISIBLE);
            mYPMC2.setText(mProvidePatientConditionTakingRecords.get(1).getDrugName());
            mYPYL2.setText(mProvidePatientConditionTakingRecords.get(1).getUseNum()+"/"+mProvidePatientConditionTakingRecords.get(1).getUseUnit());
            if (mProvidePatientConditionTakingRecords.get(1).getTakingMedicineTime() != null)
                mYYDate.setText("时间："+Util.dateToStrTime(mProvidePatientConditionTakingRecords.get(1).getTakingMedicineTime()));
            else
                mYYDate.setText("未设置");
        }
        else
        {
            mYPMC2.setVisibility(View.GONE);
            mYPYL2.setVisibility(View.GONE);
        }

//        mYYTX.setVisibility(View.VISwenIBLE);
    }




    private void initView(View view){
        mYFY = (TextView)view.findViewById(R.id.yfy_button);
        mWFY = (TextView)view.findViewById(R.id.wfy_button);
        mKSWYS = (LinearLayout)view.findViewById(R.id.kswys);
        mKSWYS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,KSWYSActivity.class));
            }
        });
        mYYDate = (TextView)view.findViewById(R.id.yytxDate);
        mYFY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调接口已服用
                operUpdPatientConditionTakingMedicineState(3);
            }
        });
        mWFY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调接口未服用
                operUpdPatientConditionTakingMedicineState(1);
            }
        });

        mBloodEntry = view.findViewById(R.id.tv_blood_entry);
        llBloodRecord = view.findViewById(R.id.ll_blood_record);

        mCSHAll = (LinearLayout)view.findViewById(R.id.csh_all);
        mCSHXY = (LinearLayout)view.findViewById(R.id.csh_xy);
        mCSHXY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,BloodEntryActivity.class));
            }
        });
        mCSHYY = (LinearLayout)view.findViewById(R.id.csh_yy);
        mCSHYY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MedicationSettingsActivity.class));
            }
        });
        mXYTX = (LinearLayout)view.findViewById(R.id.xytx);
        mYYTX = (LinearLayout)view.findViewById(R.id.yytx);

        mYPMC1 = (TextView)view.findViewById(R.id.ypmc1);
        mYPMC2 = (TextView)view.findViewById(R.id.ypmc2);
        mYPYL1 = (TextView)view.findViewById(R.id.ypyl1);
        mYPYL2 = (TextView)view.findViewById(R.id.ypyl2);

        mHeard = (CircleImageView)view.findViewById(R.id.iv_userhead);
        try {
            int avatarResId = Integer.parseInt(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            Glide.with(mContext).load(avatarResId).into(mHeard);
        } catch (Exception e) {
            //use default avatar
            Glide.with(mContext).load(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl())
                    .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(mHeard);
        }
//        mYPTX2 = (LinearLayout)view.findViewById(R.id.yytx2);
        mQrCode = view.findViewById(R.id.ll_qr_code);
//        mNews = view.findViewById(R.id.ll_news);
//        mDoctorUnion = view.findViewById(R.id.ll_doctor_union);
//        mYQTH = view.findViewById(R.id.ll_yqth);
//        mMyComments = view.findViewById(R.id.ll_my_comment);
////        mAddPatient = view.findViewById(R.id.li_fragmentShouYe_addPatient);
//        mMyLiveRoom = view.findViewById(R.id.ll_my_liveroom);
        mScan = view.findViewById(R.id.ll_sys);
//        mMyClinic = view.findViewById(R.id.ll_wdzs);
//
        mUserNameText = (TextView)view.findViewById(R.id.tv_fragmentShouYe_userNameText);
        mUserNameText.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());

//        mMyPatient = view.findViewById(R.id.ll_wdhz);
//        mNewMessage = (TextView)view.findViewById(R.id.tv_fragmentShouYe_NewMessage);
        mNewMessageLayout = (LinearLayout) view.findViewById(R.id.li_fragmentShouYe_newMessage);
        llQuickApplication = (LinearLayout)view.findViewById(R.id.ll_quick_application);

//        mQrCode = view.findViewById(R.id.ll_qr_code);
//        mNews = view.findViewById(R.id.ll_news);
//        mDoctorUnion = view.findViewById(R.id.ll_doctor_union);
//        mYQTH = view.findViewById(R.id.ll_yqth);
//        mMyComments = view.findViewById(R.id.ll_my_comment);
////        mAddPatient = view.findViewById(R.id.li_fragmentShouYe_addPatient);
//        mMyLiveRoom = view.findViewById(R.id.ll_my_liveroom);
//        mMyClinic = view.findViewById(R.id.ll_wdzs);
//
//        mUserNameText = (TextView)view.findViewById(R.id.tv_fragmentShouYe_userNameText);
//        mUserNameText.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        mUserTitleText = (TextView)view.findViewById(R.id.tv_fragmentShouYe_userTitleText);
        if (mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName() != null && !"".equals(mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName()))
            mUserTitleText.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName());
//        mMyPatient = view.findViewById(R.id.ll_wdhz);
//        mNewMessage = (TextView)view.findViewById(R.id.tv_fragmentShouYe_NewMessage);
//        mNewMessageLayout = (LinearLayout) view.findViewById(R.id.li_fragmentShouYe_newMessage);
//        llQuickApplication = (LinearLayout)view.findViewById(R.id.ll_quick_application);

        mXYValueText = (TextView)view.findViewById(R.id.xy_value);
        mXLValueText = (TextView)view.findViewById(R.id.xl_value);
        mUpdateDate = (TextView)view.findViewById(R.id.updateDate);

        mXLTX = (LinearLayout)view.findViewById(R.id.xvtx);
        mXLTX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mFYTX = (LinearLayout)view.findViewById(R.id.fytx);

        pager= (ViewPager) view.findViewById(R.id.page);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout);

        fragmentList=new ArrayList<>();
        mTitles=new ArrayList<>();
        mTitles.add("我的医生");
        mTitles.add("专家推荐");
        mTitles.add("医疗资讯");

        mFragmentShouYe_WDYS = new FragmentShouYe_WDYS();
        mFragmentShouYe_ZJTJ = new FragmentShouYe_ZJTJ();
        mFragmentShouYe_YLZX = new FragmentShouYe_YLZX();

        fragmentList.add(mFragmentShouYe_WDYS);
        fragmentList.add(mFragmentShouYe_ZJTJ);
        fragmentList.add(mFragmentShouYe_YLZX);

        fragmentAdapter=new FragmentAdapter(getChildFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
    }




    private void initListener(){
        mBloodEntry.setOnClickListener(this);
        llBloodRecord.setOnClickListener(this);
        mQrCode.setOnClickListener(this);
//        mNews.setOnClickListener(this);
//        mDoctorUnion.setOnClickListener(this);
////        mTWJZ.setOnClickListener(this);
//        mYQTH.setOnClickListener(this);
//        mMyComments.setOnClickListener(this);
////        mAddPatient.setOnClickListener(this);
//        mMyLiveRoom.setOnClickListener(this);
        mScan.setOnClickListener(this);
        mXLTX.setOnClickListener(this);
        mFYTX.setOnClickListener(this);
//        mMyClinic.setOnClickListener(this);
//        mMyPatient.setOnClickListener(this);
        mNewMessageLayout.setOnClickListener(this);
        llQuickApplication.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_blood_entry:
                startActivity(new Intent(getActivity(),BloodEntryActivity.class));
                break;
            case R.id.ll_blood_record:
                startActivity(new Intent(getActivity(), BloodMonitorActivity.class));
                break;
            case R.id.xvtx:
                startActivity(new Intent(getActivity(), BloodMonitorActivity.class));
                break;

            case R.id.ll_qr_code:
                startActivity(new Intent(getActivity(),QRCodeActivity.class));
                break;
            case R.id.fytx:
                startActivity(new Intent(getActivity(),MedicationSettingsActivity.class));
                break;
//            case R.id.ll_news:
//                startActivity(new Intent(getActivity(),NewsActivity.class).putExtra("newMessage",mActivity.mProvideMsgPushReminderCount));
//                break;
            case R.id.li_fragmentShouYe_newMessage:
                startActivity(new Intent(getActivity(),NewsActivity.class).putExtra("newMessage",mActivity.mProvideMsgPushReminderCount));
                break;
//            case R.id.ll_doctor_union:
//                startActivity(new Intent(getActivity(),DoctorsUnionActivity.class));
//                break;
//
//            case R.id.ll_yqth:
////                startActivity(new Intent(getActivity(),InvitepeersActivity.class));
//                break;
//            case R.id.ll_my_comment:
////                startActivity(new Intent(getActivity(),MyCommentActivity.class));
//                break;
//
////            case R.id.li_fragmentShouYe_addPatient:
//////                startActivity(new Intent(getActivity(),AddPatientActivity.class));
////                break;
//            case R.id.ll_my_liveroom:
//                startActivity(new Intent(getActivity(),MyLiveRoomActivity.class));
//                break;
            case R.id.ll_sys:
                scan();
                break;
//            case R.id.ll_wdzs:
//                startActivity(new Intent(getActivity(),MyClinicActivity.class));
//                break;
//            case R.id.ll_wdhz:
//                startActivity(new Intent(getActivity(),MyPatientActivity.class));
//                break;
            case R.id.ll_quick_application:
                mPopupWindow = new MoreFeaturesPopupWindow(mActivity);
                mPopupWindow.setSouYeFragment(mFragment);
                if(mPopupWindow!=null&&!mPopupWindow.isShowing()){
                    mPopupWindow.showAsDropDown(llQuickApplication,0,0);
                }
                break;
        }
    }

    /**
     * 扫一扫
     */
    private void scan(){
        RxPermissions.getInstance(getActivity())
                .request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean){//允许权限，6.0以下默认true
                            Intent intent = new Intent(getActivity(), CaptureActivity.class);
                            startActivityForResult(intent, REQUEST_CODE_SCAN);
                        }else{
                            Toast.makeText(getActivity(),"获取权限失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传

    }

    /**
     * 服药打卡
     * @param i
     */
    private void operUpdPatientConditionTakingMedicineState(int i) {
        getProgressBar("请稍候","正在处理");
        OperUpdPatientConditionTakingMedicineStateParement operUpdPatientConditionTakingMedicineStateParement = new OperUpdPatientConditionTakingMedicineStateParement();
        operUpdPatientConditionTakingMedicineStateParement.setLoginPatientPosition(mApp.loginDoctorPosition);
        operUpdPatientConditionTakingMedicineStateParement.setRequestClientType("1");
        operUpdPatientConditionTakingMedicineStateParement.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operUpdPatientConditionTakingMedicineStateParement.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        operUpdPatientConditionTakingMedicineStateParement.setTakingRecordId(mProvidePatientConditionTakingRecords.get(0).getTakingRecordId()+"");
        operUpdPatientConditionTakingMedicineStateParement.setPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operUpdPatientConditionTakingMedicineStateParement.setFlagTakingMedicine(i+"");

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(operUpdPatientConditionTakingMedicineStateParement);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"PatientConditionControlle/operUpdPatientConditionTakingMedicineState");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    private void operScanQrCodeInside(String content) {
        getProgressBar("请稍候","正在处理");
        OperScanQrCodeInside operScanQrCodeInside = new OperScanQrCodeInside();
        operScanQrCodeInside.setLoginDoctorPosition(mApp.loginDoctorPosition);
        operScanQrCodeInside.setUserUseType("5");
        operScanQrCodeInside.setOperUserCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operScanQrCodeInside.setOperUserName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        operScanQrCodeInside.setScanQrCode(content);

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(operScanQrCodeInside);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"doctorDataControlle/operScanQrCodeInside");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 医生好友绑定
     */
    private void bindDoctorFriend(String url,String reason,String qrCode) {
        getProgressBar("请稍候","正在处理");
        BindDoctorFriend bindDoctorFriend = new BindDoctorFriend();
        bindDoctorFriend.setLoginDoctorPosition(mApp.loginDoctorPosition);
        bindDoctorFriend.setBindingDoctorQrCode(qrCode);
        bindDoctorFriend.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        bindDoctorFriend.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        bindDoctorFriend.setApplyReason(reason);

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(bindDoctorFriend);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"/"+url);

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }



    /**
     * 设置新消息提醒
     * @param string
     */
    public void setNewMessageView(String string) {
//        if ("".equals(string))
//            mNewMessageLayout.setVisibility(View.GONE);
//        else
//        {
//            mNewMessageLayout.setVisibility(View.VISIBLE);
//            mNewMessage.setText(string);
//        }
    }
}
