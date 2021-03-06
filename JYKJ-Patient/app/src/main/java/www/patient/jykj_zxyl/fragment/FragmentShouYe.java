package www.patient.jykj_zxyl.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allen.library.interfaces.ILoadingView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.ActivityUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;
import entity.HomeDataBean;
import entity.OperUpdPatientConditionTakingMedicineStateParement;
import entity.ProvideMsgPushReminder;
import entity.ProvidePatientBindingMyDoctorInfo;
import entity.mySelf.UserResultInfo;
import entity.mySelf.conditions.QueryUserCond;
import entity.shouye.OperScanQrCodeInside;
import entity.shouye.ProvidePatientConditionBloodPressureRecord;
import entity.shouye.ProvidePatientConditionTakingRecord;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import rx.functions.Action1;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.*;
import www.patient.jykj_zxyl.activity.home.patient.BloodEntryActivity;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.home.patient.ZJXQ_ZJBDActivity;
import www.patient.jykj_zxyl.activity.hyhd.BindDoctorFriend;
import www.patient.jykj_zxyl.activity.hyhd.LivePlayerActivity;
import www.patient.jykj_zxyl.activity.myself.MedicationRecordActivity;
import www.patient.jykj_zxyl.activity.myself.MedicationSettingsActivity;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.activity.patient_home.KSWYSActivity;
import www.patient.jykj_zxyl.base.base_bean.AllDepartmentBean;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_db.DbManager;
import www.patient.jykj_zxyl.base.base_db.entity.CheckDoctorNumEntity;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_Graphic;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_WDYS;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_YLZX;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_ZJTJ;
import www.patient.jykj_zxyl.myappointment.activity.AllDepartmentsActivity;
import www.patient.jykj_zxyl.myappointment.activity.HealthActivity;
import www.patient.jykj_zxyl.myappointment.activity.MedicalRecordActivity;
import www.patient.jykj_zxyl.myappointment.activity.MyAppointmentActivity;
import www.patient.jykj_zxyl.myappointment.activity.UniversalWebActivity;
import www.patient.jykj_zxyl.myappointment.adapter.HotDepAdapter;
import www.patient.jykj_zxyl.myappointment.dialog.MessageOnlineDialog;
import www.patient.jykj_zxyl.util.StrUtils;
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
import www.patient.jykj_zxyl.util.widget.AuthorityDialog;
import www.patient.jykj_zxyl.util.widget.AuthorityJQQDDialog;
import zxing.android.CaptureActivity;

import static android.app.Activity.RESULT_OK;
import static com.superrtc.ContextUtils.getApplicationContext;


/**
 * 首页fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe extends Fragment implements View.OnClickListener {
    private Context mContext;
    private MainActivity mActivity;
    private String mNetRetStr;                 //返回字符串

    private String mMessageNetRetStr;                 //未读消息返回字符串
    private static final String HOME_BANNER = "home_banner";

    private String mNetRetFYStr;                 //返回服药
    private Handler mHandler;
    private JYKJApplication mApp;
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
    private TextView mUserNameText;                //用户名
    private TextView mUserTitleText;               //医生职称

    private TextView mNewMessage;                  //新消息提醒
    private LinearLayout mNewMessageLayout;          //新消息提醒
    private LinearLayout llQuickApplication;//快应用
    private MoreFeaturesPopupWindow mPopupWindow;


    private FragmentShouYe mFragment;

    public ProgressDialog mDialogProgress = null;

    private String qrCode;                         //需要绑定的二维码

    private List<ProvideViewDoctorExpertRecommend> provideViewDoctorExpertRecommendList = new ArrayList<>();            //获取到的专家数据
    private ProvidePatientConditionBloodPressureRecord mProvidePatientConditionBloodPressureRecord;     //获取到的血压数据
    private List<ProvidePatientConditionTakingRecord> mProvidePatientConditionTakingRecords;     //获取到的服药数据
    private TextView mXYValueText;                       //血压值
    private TextView mXLValueText;                       //心率值
    private TextView mUpdateDate;                        //更新时间
    private LinearLayout mXLTX;                              //心率
    private LinearLayout mFYTX;                              //服药提醒

    private FragmentShouYe_WDYS mFragmentShouYe_WDYS;               //我的诊所
    private FragmentShouYe_ZJTJ mFragmentShouYe_ZJTJ;               //专家推荐
    private FragmentShouYe_YLZX mFragmentShouYe_YLZX;               //医疗资讯


    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    private int clickState;             //点击的服用状态


    /**
     * 血压录入
     */
    private TextView mBloodEntry;
    /**
     * 血压记录
     */
    private LinearLayout llBloodRecord;

    private LinearLayout mCSHAll;                            //初始化整个布局
    private LinearLayout mCSHXY;                             //初始化血压
    private LinearLayout mCSHYY;                             //初始化用药

    private LinearLayout mXYTX;                              //血压提醒
    private LinearLayout mYYTX;                              //用药提醒

    private LinearLayout live_layout;
    private LinearLayout kswys;
    private LinearLayout diet_layout;
    private LinearLayout port_layout;
    private LinearLayout sweem_layout;
    private LinearLayout feeling_layout;
    private TextView mYPMC1;                             //药品名称1
    private TextView mYPMC2;                             //药品名称2
    private TextView mYPYL1;                             //药品用量1
    private TextView mYPYL2;                             //药品用量2

    private LinearLayout mYPTX2;                             //药品提醒2


    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;

    private View mView;

    private TextView mYFY;                           //已服用
    private TextView mWFY;                           //未服用
    private TextView mYYDate;                        //用药时间

    private LinearLayout mKSWYS;                         //快速问医生

    private LinearLayout li_jqqd;                            //


    private TextView tv_wasfy;                       //未按时服用提示
    private TextView tv_yasfy;                       //已按时服用提示
    private Banner live_banner;
    private Banner home_banner;
    private FragmentShouYe_Graphic fragmentShouYe_graphic;
    private LinearLayout lin_alldepartments;
    private TextView tv_health;
    private TextView my_deoctor;
    private TextView my_reservation;
    private TextView my_medicalrecord;
    private List imgs;
    private RecyclerView hotDepRecycleview;
    private MessageOnlineDialog messageOnlineDialog;


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitymain_shouyefragment, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mFragment = this;
        mApp = (JYKJApplication) getActivity().getApplication();
        initHandler();
        ActivityUtil.setStatusBarMain(getActivity(), R.color.white);
        initView(v);
        mView = v;
        initListener();
        //初始化定位
        initLocation();
        startLocation();
        return v;
    }

    /**
     * 获取消息
     */
    private void getNewMessage() {
        ProvideMsgPushReminder provideMsgPushReminder = new ProvideMsgPushReminder();
        provideMsgPushReminder.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideMsgPushReminder.setRequestClientType("1");
        provideMsgPushReminder.setSearchPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideMsgPushReminder);
                    mMessageNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "/msgDataControlle/searchPatientMsgPushReminderResDataNum");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mMessageNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(100);
            }
        }.start();
    }

   /* @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            LogUtils.e("fragment on hidden ");
            getUserIv();
        }
    }*/

    private void getUserIv() {
        QueryUserCond quecond = new QueryUserCond();
        quecond.setUserCodeList(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        try {
            String mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(quecond), Constant.SERVICEURL + "patientDoctorCommonDataController/getUserInfoList");
            NetRetEntity retEntity = JSON.parseObject(mNetLoginRetStr, NetRetEntity.class);
            if (1 == retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length() > 3) {
                List<UserResultInfo> retlist = JSON.parseArray(retEntity.getResJsonData(), UserResultInfo.class);
                UserResultInfo onebean = retlist.get(0);
                mApp.mProvideViewSysUserPatientInfoAndRegion.setUserLogoUrl(onebean.getUserLogoUrl());
                mApp.mProvideViewSysUserPatientInfoAndRegion.setQrCode(onebean.getQrCode());
                NetRetEntity retentone = new NetRetEntity();
                retentone.setResCode(1);
                retentone.setResJsonData(new Gson().toJson(mApp.mProvideViewSysUserPatientInfoAndRegion));
                mNetLoginRetStr = new Gson().toJson(retentone);
            }
        } catch (Exception e) {
            NetRetEntity retEntity = new NetRetEntity();
            retEntity.setResCode(0);
            retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());

            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getBanner(1);
        getUserIv();
//        getProgressBar("请稍候","正在加载数据。。。");
        //获取最近一次血压数据
        if (null != mApp.mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus() && 1 == mApp.mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus()) {
            //  mUserNameText.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserNameAlias());
        } else {
            //   mUserNameText.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        }

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
        searchPatientStateResBloodPressureNewData();
        getNewMessage();

    }

    private String getParams(int type) {
        String[] ams = {"1", "2"};
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginPatientPosition", "108.93425^34.23053");
        paramMap.put("requestClientType", "1");
        paramMap.put("appTypeCode", "1");
        paramMap.put("positionTypes", "[\"1\",\"2\"]");
        paramMap.put("hospitalCode", "0");
        return RetrofitUtil.encodeParam(paramMap);
    }

    private void getBanner(int type) {

        ApiHelper.getPatientTestApi().getHomeBanner(getParams(type)).compose(
                com.allen.library.interceptor.Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {

                    }

                    @Override
                    public void hideLoadingView() {

                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            LogUtils.e("首页banner数据" + resJsonData);
                            HomeDataBean orderDetialBean = GsonUtils.fromJson(resJsonData, HomeDataBean.class);
                            List<HomeDataBean.ViewBasicsBannerFilesListBean> viewBasicsBannerFilesList = orderDetialBean.getViewBasicsBannerFilesList();
                            List<HomeDataBean.HospitalDepartmentListBean> hospitalDepartmentList = orderDetialBean.getHospitalDepartmentList();
                            if (viewBasicsBannerFilesList.size() > 0) {
                                showBanner(viewBasicsBannerFilesList);
                            } else {
                                showTopEmptyBanner();
                                showMiddleEmptyBanner();
                            }
                            if (hospitalDepartmentList.size() > 0) {
                                showHotDep(hospitalDepartmentList);
                            }

                        }
                    } else {
                        ToastUtils.showShort(baseBean.getResMsg());
                    }
                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                Log.e("xxx", "onError: " + s);
                ToastUtils.showShort("加载失败");
            }

            @Override
            protected String setTag() {
                return HOME_BANNER;
            }
        });
    }

    private void showHotDep(List<HomeDataBean.HospitalDepartmentListBean> hospitalDepartmentList) {
        HotDepAdapter hotDepAdapter = new HotDepAdapter(R.layout.item_hot_dep, hospitalDepartmentList);
        hotDepAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(mContext, TJZJActivity.class)));
        hotDepRecycleview.setAdapter(hotDepAdapter);


    }

    private void showBanner(List<HomeDataBean.ViewBasicsBannerFilesListBean> list) {
        ArrayList<HomeDataBean.ViewBasicsBannerFilesListBean> topBannerList = new ArrayList<>();
        ArrayList<HomeDataBean.ViewBasicsBannerFilesListBean> middleBannerList = new ArrayList<>();
        ArrayList<String> topBannerImage = new ArrayList<>();
        ArrayList<String> middleBannerImage = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPositionType() == 1) {
                topBannerList.add(list.get(i));
            } else {
                middleBannerList.add(list.get(i));
            }
        }
        if (topBannerList.size() > 0) {
            for (int i = 0; i < topBannerList.size(); i++) {
                String filePath = topBannerList.get(i).getViewBannerUrl();
                topBannerImage.add(filePath);
            }
            live_banner.setImages(topBannerImage);
            live_banner.setIndicatorGravity(BannerConfig.CENTER).start();
            live_banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(mContext, UniversalWebActivity.class);
                    intent.putExtra("tittle", topBannerList.get(position).getSubtitle());
                    intent.putExtra("url", topBannerList.get(position).getContentUrl());
                    startActivity(intent);
                }
            });
        } else {
            showTopEmptyBanner();
        }

        if (middleBannerList.size() > 0) {
            for (int i = 0; i < middleBannerList.size(); i++) {
                String filePath = middleBannerList.get(i).getViewBannerUrl();
                middleBannerImage.add(filePath);
            }
            home_banner.setImages(middleBannerImage);
            home_banner.setIndicatorGravity(BannerConfig.CENTER).start();
            home_banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(mContext, UniversalWebActivity.class);
                    intent.putExtra("tittle", middleBannerList.get(position).getSubtitle());
                    intent.putExtra("url", middleBannerList.get(position).getContentUrl());
                    startActivity(intent);
                }
            });
        } else {
            showMiddleEmptyBanner();
        }

    }

    private void showMiddleEmptyBanner() {
        home_banner.setImages(imgs);
        home_banner.setIndicatorGravity(BannerConfig.CENTER).start();
    }

    private void showTopEmptyBanner() {
        live_banner.setImages(imgs);
        live_banner.setIndicatorGravity(BannerConfig.CENTER).start();
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

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(providePatientConditionBloodPressureRecord);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "/PatientConditionControlle/searchPatientStateResBloodPressureNewData");
                    NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 1)
                        mProvidePatientConditionBloodPressureRecord = JSON.parseObject(netRetEntity.getResJsonData(), ProvidePatientConditionBloodPressureRecord.class);
                    //获取最近一次服药数据
                    searchPatientStateResTakingMedicineRecordNewDataUnexecuted();
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

    /**
     * 获取最近一次服药数据
     */
    private void searchPatientStateResTakingMedicineRecordNewDataUnexecuted() {
        ProvidePatientConditionTakingRecord providePatientConditionTakingRecord = new ProvidePatientConditionTakingRecord();
        providePatientConditionTakingRecord.setLoginPatientPosition(mApp.loginDoctorPosition);
        providePatientConditionTakingRecord.setRequestClientType("1");
        providePatientConditionTakingRecord.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        providePatientConditionTakingRecord.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(providePatientConditionTakingRecord);
                    mNetRetFYStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "/PatientConditionControlle/searchPatientStateResTakingMedicineRecordNewDataUnexecuted");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetFYStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:

                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        else {
                            provideViewDoctorExpertRecommendList = JSON.parseArray(netRetEntity.getResJsonData(), ProvideViewDoctorExpertRecommend.class);
                        }

                        break;
                    case 1:

                        break;
                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetFYStr, NetRetEntity.class);

                        if (netRetEntity.getResCode() == 1) {
                            mProvidePatientConditionTakingRecords = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionTakingRecord.class);
                        }
                        showRecord();

                        break;

                    case 5:
                        cacerProgress();
//                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
//                        Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
//                        cacerProgress();
//                        //获取最近一次血压数据
//                        searchPatientStateResBloodPressureNewData();
                        showFYView();
                        break;

                    case 100:
//                        netRetEntity = JSON.parseObject(mMessageNetRetStr, NetRetEntity.class);
//                        if (netRetEntity.getResCode() == 1) {
//                            ProvideMsgPushReminder provideMsgPushReminder = JSON.parseObject(netRetEntity.getResJsonData(), ProvideMsgPushReminder.class);
//                            if (provideMsgPushReminder.getUnreadMsgNum() == 0)
//                                mNewMessageLayout.setVisibility(View.GONE);
//                            else {
//                                mNewMessageLayout.setVisibility(View.VISIBLE);
//                                mNewMessage.setText("您有" + provideMsgPushReminder.getUnreadMsgNum() + "条未读消息");
//                            }
//
//                        } else {
//                            mNewMessageLayout.setVisibility(View.GONE);
//                        }
                        break;


                }
            }
        };
    }

    /**
     * 服药操作后
     */
    private void showFYView() {
        if (clickState == 3) {
            //已服用
            mYFY.setVisibility(View.GONE);
            mWFY.setVisibility(View.GONE);
            tv_wasfy.setVisibility(View.GONE);
            tv_yasfy.setVisibility(View.VISIBLE);
        }
        if (clickState == 1) {
            //未服用
            mYFY.setVisibility(View.GONE);
            mWFY.setVisibility(View.GONE);
            tv_wasfy.setVisibility(View.VISIBLE);
            tv_yasfy.setVisibility(View.GONE);
        }
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

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(providePatientBindingMyDoctorInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.patient.jykj_zxyl.application.Constant.SERVICEURL + "PatientMyDoctorControlle/searchIndexMyDoctorShow");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
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
        if (mProvidePatientConditionBloodPressureRecord == null || mProvidePatientConditionTakingRecords == null || mProvidePatientConditionTakingRecords.size() == 0) {
            //如果
            mCSHAll.setVisibility(View.VISIBLE);
            if (mProvidePatientConditionBloodPressureRecord == null) {
                mCSHXY.setVisibility(View.VISIBLE);
                mXYTX.setVisibility(View.GONE);
            } else {
                mCSHXY.setVisibility(View.GONE);
                mXYTX.setVisibility(View.VISIBLE);
                mXYValueText.setText(mProvidePatientConditionBloodPressureRecord.getHighPressureNum() + "/" + mProvidePatientConditionBloodPressureRecord.getLowPressureNum());
                mXLValueText.setText(mProvidePatientConditionBloodPressureRecord.getHeartRateNum() + "");
                mUpdateDate.setText(Util.dateToStrTime(mProvidePatientConditionBloodPressureRecord.getRecordTime()));
            }
            if (mProvidePatientConditionTakingRecords == null || mProvidePatientConditionTakingRecords.size() == 0) {
                mCSHYY.setVisibility(View.VISIBLE);
                mYYTX.setVisibility(View.GONE);
            } else {
                mCSHYY.setVisibility(View.GONE);
                mYYTX.setVisibility(View.VISIBLE);
            }

        } else {
            mCSHAll.setVisibility(View.GONE);
        }


        if (mProvidePatientConditionTakingRecords != null && mProvidePatientConditionTakingRecords.size() > 0) {
            mYYTX.setVisibility(View.VISIBLE);
            mYPMC1.setVisibility(View.VISIBLE);
            mYPYL1.setVisibility(View.VISIBLE);
            mYPMC1.setText(mProvidePatientConditionTakingRecords.get(0).getDrugName());
            if (mProvidePatientConditionTakingRecords.get(0).getUseNum() == null)
                mYPYL1.setText(mProvidePatientConditionTakingRecords.get(0).getUseNum() + "/未设置");
            else if (mProvidePatientConditionTakingRecords.get(0).getUseUnit() == null)
                mYPYL1.setText("未设置/" + mProvidePatientConditionTakingRecords.get(0).getUseUnit());
            else
                mYPYL1.setText(mProvidePatientConditionTakingRecords.get(0).getUseNum() + "/" + mProvidePatientConditionTakingRecords.get(0).getUseUnit());
            if (mProvidePatientConditionTakingRecords.get(0).getTakingMedicineTime() != null)
                mYYDate.setText("时间：" + Util.dateToStrTime(mProvidePatientConditionTakingRecords.get(0).getTakingMedicineTime()));
            else
                mYYDate.setText("未设置");
        }
        if (mProvidePatientConditionTakingRecords != null && mProvidePatientConditionTakingRecords.size() > 1) {
            mYYTX.setVisibility(View.VISIBLE);
            mYPMC2.setVisibility(View.VISIBLE);
            mYPYL2.setVisibility(View.VISIBLE);
            mYPMC2.setText(mProvidePatientConditionTakingRecords.get(1).getDrugName());
            mYPYL2.setText(mProvidePatientConditionTakingRecords.get(1).getUseNum() + "/" + mProvidePatientConditionTakingRecords.get(1).getUseUnit());
            if (mProvidePatientConditionTakingRecords.get(1).getTakingMedicineTime() != null)
                mYYDate.setText("时间：" + Util.dateToStrTime(mProvidePatientConditionTakingRecords.get(1).getTakingMedicineTime()));
            else
                mYYDate.setText("未设置");
        } else {
            mYPMC2.setVisibility(View.GONE);
            mYPYL2.setVisibility(View.GONE);
        }

//        mYYTX.setVisibility(View.VISwenIBLE);
    }


    private void initView(View view) {
        //我的预约
        my_reservation = view.findViewById(R.id.my_reservation);
        my_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyAppointmentActivity.class);
                startActivity(intent);
            }
        });
        my_deoctor = view.findViewById(R.id.my_deoctor);
        //健康状况
        tv_health = view.findViewById(R.id.tv_health);
        tv_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HealthActivity.class);
                startActivity(intent);
            }
        });
        //更多科室
        lin_alldepartments = view.findViewById(R.id.lin_alldepartments);
        lin_alldepartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllDepartmentsActivity.class);
                startActivity(intent);

            }
        });

        imgs = new ArrayList<>();
        messageOnlineDialog = new MessageOnlineDialog(mContext);
        imgs.add(getStringFromDrawableRes(getContext(), R.mipmap.live_image));
        imgs.add(getStringFromDrawableRes(getContext(), R.mipmap.tu));
        imgs.add(getStringFromDrawableRes(getContext(), R.mipmap.live_image));


        live_banner = view.findViewById(R.id.live_banner);
        live_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        live_banner.setImageLoader(new MyLoader());
        live_banner.setBannerAnimation(Transformer.Default);
        live_banner.setDelayTime(2000);
        live_banner.isAutoPlay(true);


        home_banner = view.findViewById(R.id.home_banner);
        home_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        home_banner.setImageLoader(new MyLoader());
        home_banner.setBannerAnimation(Transformer.Default);
        home_banner.setDelayTime(2000);
        home_banner.isAutoPlay(true);

        tv_yasfy = (TextView) view.findViewById(R.id.tv_yasfy);
        tv_wasfy = (TextView) view.findViewById(R.id.tv_wasfy);

        hotDepRecycleview = view.findViewById(R.id.hot_dep);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 4, LinearLayoutManager.VERTICAL, false);
        hotDepRecycleview.setLayoutManager(linearLayoutManager);
        mYFY = (TextView) view.findViewById(R.id.yfy_button);
        mWFY = (TextView) view.findViewById(R.id.wfy_button);
        //    mKSWYS = (LinearLayout) view.findViewById(R.id.kswys);
//        mKSWYS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AuthorityJQQDDialog mAuthorityJQQDDialog = new AuthorityJQQDDialog(mContext);
////                mAuthorityDialog.setmProvideViewMyDoctorOrderAndTreatment(provideViewMyDoctorOrderAndTreatment);
//                mAuthorityJQQDDialog.show();
//            }
//        });
        //  li_jqqd = (LinearLayout) view.findViewById(R.id.li_jqqd);
//        li_jqqd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AuthorityJQQDDialog mAuthorityJQQDDialog = new AuthorityJQQDDialog(mContext);
////                mAuthorityDialog.setmProvideViewMyDoctorOrderAndTreatment(provideViewMyDoctorOrderAndTreatment);
//                mAuthorityJQQDDialog.show();
//            }
//        });
        mYYDate = (TextView) view.findViewById(R.id.yytxDate);
        mYFY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调接口已服用
                clickState = 3;
                operUpdPatientConditionTakingMedicineState(3);
            }
        });
        mWFY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调接口未服用
                operUpdPatientConditionTakingMedicineState(1);
                clickState = 1;
            }
        });
        view.findViewById(R.id.more_infomation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageOnlineDialog.show();
            }
        });
        view.findViewById(R.id.lin_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageOnlineDialog.show();
            }
        });
        mBloodEntry = view.findViewById(R.id.tv_blood_entry);
        llBloodRecord = view.findViewById(R.id.ll_blood_record);

        mCSHAll = (LinearLayout) view.findViewById(R.id.csh_all);
        mCSHXY = (LinearLayout) view.findViewById(R.id.csh_xy);
        mCSHXY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, BloodEntryActivity.class));
            }
        });
        mCSHYY = (LinearLayout) view.findViewById(R.id.csh_yy);
        mCSHYY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MedicationSettingsActivity.class));
            }
        });
        mXYTX = (LinearLayout) view.findViewById(R.id.xytx);
        mYYTX = (LinearLayout) view.findViewById(R.id.yytx);

        mYPMC1 = (TextView) view.findViewById(R.id.ypmc1);
        mYPMC2 = (TextView) view.findViewById(R.id.ypmc2);
        mYPYL1 = (TextView) view.findViewById(R.id.ypyl1);
        mYPYL2 = (TextView) view.findViewById(R.id.ypyl2);

        mHeard = (CircleImageView) view.findViewById(R.id.iv_userhead);
//        mYPTX2 = (LinearLayout)view.findViewById(R.id.yytx2);
        mQrCode = view.findViewById(R.id.ll_qr_code);
//        mNews = view.findViewById(R.id.ll_news);
//        mDoctorUnion = view.findViewById(R.id.ll_doctor_union);
//        mYQTH = view.findViewById(R.id.ll_yqth);
//        mMyComments = view.findViewById(R.id.ll_my_comment);
////        mAddPatient = view.findViewById(R.id.li_fragmentShouYe_addPatient);
        //     mMyLiveRoom = view.findViewById(R.id.ll_my_liveroom);
        mScan = view.findViewById(R.id.ll_sys);
        //我的病历
        my_medicalrecord = view.findViewById(R.id.my_medicalrecord);
        my_medicalrecord.setOnClickListener(this);
//        mMyClinic = view.findViewById(R.id.ll_wdzs);
//
        mUserNameText = (TextView) view.findViewById(R.id.tv_fragmentShouYe_userNameText);

/*
        mMyPatient = view.findViewById(R.id.ll_wdhz);
        mNewMessage = (TextView) view.findViewById(R.id.tv_fragmentShouYe_NewMessage);
        mNewMessageLayout = (LinearLayout) view.findViewById(R.id.li_fragmentShouYe_newMessage);
*/
        llQuickApplication = (LinearLayout) view.findViewById(R.id.ll_quick_application);
/*
        mNewMessageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApp.gMainActivity.setViewPageIndex(1, 1);
            }
        });
        mQrCode = view.findViewById(R.id.ll_qr_code);
        mNews = view.findViewById(R.id.ll_news);
        mDoctorUnion = view.findViewById(R.id.ll_doctor_union);
        mYQTH = view.findViewById(R.id.ll_yqth);
        mMyComments = view.findViewById(R.id.ll_my_comment);
//        mAddPatient = view.findViewById(R.id.li_fragmentShouYe_addPatient);
*/
        mMyLiveRoom = view.findViewById(R.id.ll_my_liveroom);
/*
        mMyClinic = view.findViewById(R.id.ll_wdzs);

        mUserNameText = (TextView)view.findViewById(R.id.tv_fragmentShouYe_userNameText);
        mUserNameText.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
  mUserTitleText = (TextView) view.findViewById(R.id.tv_fragmentShouYe_userTitleText);
        if (mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName() != null && !"".equals(mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName()))
            mUserTitleText.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName());
        mMyPatient = view.findViewById(R.id.ll_wdhz);
        mNewMessage = (TextView)view.findViewById(R.id.tv_fragmentShouYe_NewMessage);
        mNewMessageLayout = (LinearLayout) view.findViewById(R.id.li_fragmentShouYe_newMessage);
        llQuickApplication = (LinearLayout)view.findViewById(R.id.ll_quick_application);
*/

        mXYValueText = (TextView) view.findViewById(R.id.xy_value);
        mXLValueText = (TextView) view.findViewById(R.id.xl_value);
        mUpdateDate = (TextView) view.findViewById(R.id.updateDate);

        mXLTX = (LinearLayout) view.findViewById(R.id.xvtx);
        mXLTX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mFYTX = (LinearLayout) view.findViewById(R.id.fytx);

        pager = (ViewPager) view.findViewById(R.id.page);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setSelectedTabIndicatorHeight(0);
        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("健康教育");
        mTitles.add("视频");
        mTitles.add("音频");
        mTitles.add("图文");

        mFragmentShouYe_WDYS = new FragmentShouYe_WDYS();
        mFragmentShouYe_ZJTJ = new FragmentShouYe_ZJTJ();
        mFragmentShouYe_YLZX = new FragmentShouYe_YLZX();
        fragmentShouYe_graphic = new FragmentShouYe_Graphic();

        fragmentList.add(mFragmentShouYe_WDYS);
        fragmentList.add(mFragmentShouYe_ZJTJ);
        fragmentList.add(mFragmentShouYe_YLZX);
        fragmentList.add(fragmentShouYe_graphic);

        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), fragmentList, mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
        /*
         live_layout = view.findViewById(R.id.live_layout);
            kswys = view.findViewById(R.id.kswys);
            diet_layout = view.findViewById(R.id.diet_layout);
             port_layout = view.findViewById(R.id.port_layout);
             sweem_layout = view.findViewById(R.id.sweem_layout);
             feeling_layout = view.findViewById(R.id.feeling_layout);
        */


        List<CheckDoctorNumEntity> checkNumEntities1 = DbManager.getInstance().getCheckDocNumEntityService().queryAll();
        SmartRefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);

    }


    private void initListener() {
        mBloodEntry.setOnClickListener(this);
        llBloodRecord.setOnClickListener(this);
        mQrCode.setOnClickListener(this);
/*
        mNews.setOnClickListener(this);
        mDoctorUnion.setOnClickListener(this);
//        mTWJZ.setOnClickListener(this);
        mYQTH.setOnClickListener(this);
        mMyComments.setOnClickListener(this);
//        mAddPatient.setOnClickListener(this);
*/
        mMyLiveRoom.setOnClickListener(this);
        mScan.setOnClickListener(this);
        mXLTX.setOnClickListener(this);
        mFYTX.setOnClickListener(this);
/*
        mMyClinic.setOnClickListener(this);
        mMyPatient.setOnClickListener(this);
        mNewMessageLayout.setOnClickListener(this);
*/
        llQuickApplication.setOnClickListener(this);
        /*
  live_layout.setOnClickListener(this);
        kswys.setOnClickListener(this);
        diet_layout.setOnClickListener(this);
        port_layout.setOnClickListener(this);
        sweem_layout.setOnClickListener(this);
        feeling_layout.setOnClickListener(this);
我的医生
*/
        my_deoctor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_deoctor:
                startActivity(new Intent(getActivity(), WDYSActivity.class));
                break;
            case R.id.tv_blood_entry:
                startActivity(new Intent(getActivity(), BloodEntryActivity.class));
                break;
            case R.id.ll_blood_record:
                startActivity(new Intent(getActivity(), BloodMonitorActivity.class));
                break;
            case R.id.xvtx:
                startActivity(new Intent(getActivity(), BloodMonitorActivity.class));
                break;

//            case R.id.ll_qr_code:
//                startActivity(new Intent(getActivity(), QRCodeActivity.class));
//                break;
            case R.id.fytx:
                startActivity(new Intent(getActivity(), MedicationRecordActivity.class));
                break;
//            case R.id.ll_news:
//                startActivity(new Intent(getActivity(),NewsActivity.class).putExtra("newMessage",mActivity.mProvideMsgPushReminderCount));
//                break;
//            case R.id.li_fragmentShouYe_newMessage:
//                startActivity(new Intent(getActivity(),NewsActivity.class).putExtra("newMessage",mActivity.mProvideMsgPushReminderCount));
//                break;
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
            case R.id.ll_my_liveroom:
                startActivity(new Intent(getActivity(), MyLiveRoomActivity.class));
                break;
            //我的病历
            case R.id.my_medicalrecord:
                startActivity(new Intent(getActivity(), MedicalRecordActivity.class));
                break;
            case R.id.ll_sys:
                //    scan();
                startActivity(new Intent(getActivity(), QRCodeActivity.class));
                break;
//            case R.id.ll_wdzs:
//                startActivity(new Intent(getActivity(),MyClinicActivity.class));
//                break;
//            case R.id.ll_wdhz:
//                startActivity(new Intent(getActivity(),MyPatientActivity.class));
//                break;
            case R.id.ll_qr_code:
                mPopupWindow = new MoreFeaturesPopupWindow(mActivity);
                mPopupWindow.setSouYeFragment(mFragment);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(llQuickApplication, 0, 0);
                }
                break;
/*
            case R.id.kswys:
                alertWillpub();
                break;
            case R.id.diet_layout:
                alertWillpub();
                break;
            case R.id.port_layout:
                alertWillpub();
                break;
            case R.id.sweem_layout:
                alertWillpub();
                break;
            case R.id.feeling_layout:
                alertWillpub();
                break;
            case R.id.live_layout:
                /*Intent goliveintent = new Intent(mContext, LivePlayerActivity.class);
                goliveintent.putExtra("TITLE","医生直播间");
                goliveintent.putExtra("TYPE", LivePlayerActivity.ACTIVITY_TYPE_LIVE_PLAY);
                startActivity(goliveintent);* /
                Intent goliveintent = new Intent(mContext, MyLiveRoomActivity.class);
                startActivity(goliveintent);

                break;
*/
        }
    }

    void alertWillpub() {
        AuthorityJQQDDialog mAuthorityJQQDDialog = new AuthorityJQQDDialog(mContext);
//                mAuthorityDialog.setmProvideViewMyDoctorOrderAndTreatment(provideViewMyDoctorOrderAndTreatment);
        mAuthorityJQQDDialog.show();
    }

    /**
     * 扫一扫
     */
    private void scan() {
        RxPermissions.getInstance(getActivity())
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {//允许权限，6.0以下默认true
                            Intent intent = new Intent(getActivity(), CaptureActivity.class);
                            startActivityForResult(intent, REQUEST_CODE_SCAN);
                        } else {
                            Toast.makeText(getActivity(), "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            // 扫描二维码/条码回传
            ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend = new ProvideViewDoctorExpertRecommend();
            provideViewDoctorExpertRecommend.setDoctorCode(data.getStringExtra("codedContent"));
            provideViewDoctorExpertRecommend.setUserName(data.getStringExtra("codedContent"));
            startActivity(new Intent(mContext, ZJXQ_ZJBDActivity.class).putExtra("provideViewDoctorExpertRecommend", provideViewDoctorExpertRecommend));
        }

//        Intent intent.putExtra(zxing.common.Constant.CODED_CONTENT, rawResult.getText());
//        startActivity();
    }

    /**
     * 服药打卡
     *
     * @param i
     */
    private void operUpdPatientConditionTakingMedicineState(int i) {
        getProgressBar("请稍候", "正在处理");
        OperUpdPatientConditionTakingMedicineStateParement operUpdPatientConditionTakingMedicineStateParement = new OperUpdPatientConditionTakingMedicineStateParement();
        operUpdPatientConditionTakingMedicineStateParement.setLoginPatientPosition(mApp.loginDoctorPosition);
        operUpdPatientConditionTakingMedicineStateParement.setRequestClientType("1");
        operUpdPatientConditionTakingMedicineStateParement.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operUpdPatientConditionTakingMedicineStateParement.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        operUpdPatientConditionTakingMedicineStateParement.setTakingRecordId(mProvidePatientConditionTakingRecords.get(0).getTakingRecordId() + "");
        operUpdPatientConditionTakingMedicineStateParement.setPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operUpdPatientConditionTakingMedicineStateParement.setFlagTakingMedicine(i + "");

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(operUpdPatientConditionTakingMedicineStateParement);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.patient.jykj_zxyl.application.Constant.SERVICEURL + "PatientConditionControlle/operUpdPatientConditionTakingMedicineState");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    private void operScanQrCodeInside(String content) {
        getProgressBar("请稍候", "正在处理");
        OperScanQrCodeInside operScanQrCodeInside = new OperScanQrCodeInside();
        operScanQrCodeInside.setLoginDoctorPosition(mApp.loginDoctorPosition);
        operScanQrCodeInside.setUserUseType("5");
        operScanQrCodeInside.setOperUserCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operScanQrCodeInside.setOperUserName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        operScanQrCodeInside.setScanQrCode(content);

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(operScanQrCodeInside);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.patient.jykj_zxyl.application.Constant.SERVICEURL + "doctorDataControlle/operScanQrCodeInside");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
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
    private void bindDoctorFriend(String url, String reason, String qrCode) {
        getProgressBar("请稍候", "正在处理");
        BindDoctorFriend bindDoctorFriend = new BindDoctorFriend();
        bindDoctorFriend.setLoginDoctorPosition(mApp.loginDoctorPosition);
        bindDoctorFriend.setBindingDoctorQrCode(qrCode);
        bindDoctorFriend.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        bindDoctorFriend.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        bindDoctorFriend.setApplyReason(reason);

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(bindDoctorFriend);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.patient.jykj_zxyl.application.Constant.SERVICEURL + "/" + url);

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
     *
     * @param string
     */
    public void setNewMessageView(String string) {
/*
        if ("".equals(string))
            mNewMessageLayout.setVisibility(View.GONE);
        else
        {
            mNewMessageLayout.setVisibility(View.VISIBLE);
            mNewMessage.setText(string);
        }
*/
    }


    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(mApp.gContext);
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");
                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    String cityCode = location.getCityCode();
                    String areaCode = location.getAdCode();
                    Log.e("TAG", "onLocationChanged: " + areaCode);
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    mApp.gProviceName = location.getProvince();
                    mApp.gGDLocation = areaCode;
                    mApp.gGDLocationName = location.getDistrict();
                    //       mUserTitleText.setText(location.getProvince() + location.getCity());
                } else {
                    //定位失败
                    mUserTitleText.setText("定位失败");
/*
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
*/
                }
/*
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("****************").append("\n");
                //定位之后的回调时间

                //解析定位结果，
                String result = sb.toString();
*/
            } else {
                mUserTitleText.setText("定位失败");
            }
        }
    };


    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        // 设置是否单次定位
        locationOption.setOnceLocation(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(true);
        //设置是否使用传感器
        locationOption.setSensorEnable(false);
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        int strInterval = 1000;
        if (!TextUtils.isEmpty(strInterval + "")) {
            try {
                // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
                locationOption.setInterval(Long.valueOf(strInterval));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

/*
        String strTimeout = etHttpTimeout.getText().toString();
        if(!TextUtils.isEmpty(strTimeout)){
            try{
                // 设置网络请求超时时间
                locationOption.setHttpTimeOut(Long.valueOf(strTimeout));
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
*/
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

//自定义的图片加载器

    private class MyLoader extends ImageLoader {

        @Override

        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load((String) path).into(imageView);
           /* imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("imgv",String.valueOf(v.getId()));
                }
            });*/
        }

    }

    public static String getStringFromDrawableRes(Context context, int id) {

        Resources resources = context.getResources();

        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"

                + resources.getResourcePackageName(id) + "/"

                + resources.getResourceTypeName(id) + "/"

                + resources.getResourceEntryName(id);

        return path;

    }

}
