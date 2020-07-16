package www.patient.jykj_zxyl.activity.home.twjz;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import entity.ProvideDoctorSetSchedulingInfoGroupDate;
import entity.mySelf.ProvideDoctorSetScheduling;
import entity.patientapp.Photo_Info;
import entity.patientapp.ProvideDoctorSetSchedulingPatient;
import entity.patientapp.UpLoadImgParment;
import entity.service.GetInteractOrderCodeGenerate;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import entity.wdzs.ProvideBasicsImg;
import entity.wdzs.ProvideInteractPatientInterrogation;
import entity.wdzs.ProvideInteractPatientInterrogationParment;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.DoctorPBRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_utils.ParameUtil;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.util.Util;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.WZXXOrderActivity;
import www.patient.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.DoctorPBRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.BitmapUtil;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.Util;

/**
 * 问诊信息
 */
public class WZXXActivity extends AppCompatActivity {

    private Context mContext;
    private WZXXActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;

    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;                 //返回字符串
    private String mAddNetRetStr;                 //返回字符串
    private String mGetImgNetRetStr;                 //获取图片返回字符串
    private TextView mJZLX;                         //就诊类型
    private TextView mYSXM;                         //医生姓名
    private TextView mHZXM;                         //患者姓名
    private TextView mHZSJ;                         //患者手机
    private TextView mXB;                         //性别
    private TextView mNL;                         //年龄
    private TextView mYCRQ;                         //最早发现高血压异常日期
    private TextView mJZHZ;                         //家族内是否有其他高血压患者
    private TextView mSSY;                         //收缩压
    private TextView mSZY;                         //舒张压
    private TextView mXL;                         //心率

    private TextView mCLYQ;                        //测量仪器

    private TextView mCLFF;                          //测量方法
    private TextView mGXYBS;                          //高血压病史
    private TextView mBQZS;                          //病情自述

    private RecyclerView mImageRecycleView;
    private FullyGridLayoutManager mGridLayoutManager;
    private WZZXImageViewRecycleAdapter mAdapter;


    private LinearLayout mYSurplus;                  //我的余额
    private RecyclerView mPBRecycleView;             //排班列表
    private LinearLayoutManager layoutManager;
    private DoctorPBRecycleAdapter mMyPBRecycleAdapter;       //适配器
    private List<ProvideDoctorSetSchedulingPatient> mPbInfos = new ArrayList<>();


    private ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend;                          //专家信息
    private List<ProvideInteractPatientInterrogation> mProvideInteractPatientInterrogations;
    private List<ProvideBasicsImg> mProvideBasicsImg = new ArrayList<>();
    private LinearLayout mBack;

    private String mOperaType;                                 //类型，1：图文就诊
    private String mOrderNum;                                  //订单号
    private ProvideDoctorSetScheduling provideDoctorSetScheduling = new ProvideDoctorSetScheduling();

    private ProvideDoctorSetScheduling mProvideDoctorSetScheduling = new ProvideDoctorSetScheduling();
    private List<ProvideDoctorSetSchedulingInfoGroupDate> mProvideDoctorSetSchedulingInfoGroupDate = new ArrayList<>();

    private ImageViewRecycleAdapter mImageViewRecycleAdapter;
    private List<Photo_Info> mPhotoInfos = new ArrayList<>();

    private File mTempFile;              //声明一个拍照结果的临时文件
    private TextView mCommit;                    //提交

    private ProvideInteractPatientInterrogation mProvideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();               //提交的问诊资料
    private ProvideDoctorSetSchedulingInfoGroupDate provideDoctorSetSchedulingInfoGroupDate;

    private ProvideInteractPatientInterrogationParment mProvideInteractPatientInterrogationParment = new ProvideInteractPatientInterrogationParment();

    private void setLayoutDate() {

        //设置初始数据
        mHZSJ.setText(mProvideInteractPatientInterrogationParment.getPatientLinkPhone());
        if (mProvideInteractPatientInterrogationParment.getGender() == 0)
            mXB.setText("未知");
        if (mProvideInteractPatientInterrogationParment.getGender() == 1)
            mXB.setText("男");
        if (mProvideInteractPatientInterrogationParment.getGender() == 2)
            mXB.setText("女");
        mProvideInteractPatientInterrogationParment.setGender(mProvideInteractPatientInterrogationParment.getGender());
        mNL.setText(mProvideInteractPatientInterrogationParment.getBirthday());
        mProvideInteractPatientInterrogationParment.setInterrogationId(mProvideInteractPatientInterrogationParment.getInterrogationId());
        if (mProvideInteractPatientInterrogationParment.getBloodPressureAbnormalDate() == null)
            mYCRQ.setText("请选择最早发现日期");
        else
            mYCRQ.setText(mProvideInteractPatientInterrogationParment.getBloodPressureAbnormalDate());
        if (mProvideInteractPatientInterrogationParment.getFlagFamilyHtn() == 0)
            mJZHZ.setText("否");
        if (mProvideInteractPatientInterrogationParment.getFlagFamilyHtn() == 1)
            mJZHZ.setText("是");
        mSSY.setText(mProvideInteractPatientInterrogationParment.getHighPressureNum() + "");
        mSZY.setText(mProvideInteractPatientInterrogationParment.getLowPressureNum() + "");
        mXL.setText(mProvideInteractPatientInterrogationParment.getHeartRateNum() + "");
        mCLYQ.setText(mProvideInteractPatientInterrogationParment.getMeasureInstrumentName());
        mCLFF.setText(mProvideInteractPatientInterrogationParment.getMeasureModeName());
        mGXYBS.setText(mProvideInteractPatientInterrogationParment.getHtnHistory());
        mBQZS.setText(mProvideInteractPatientInterrogationParment.getStateOfIllness());
    }


    /**
     * 选择生日
     */
    private void showBirthDayChoiceDialog() {
        Calendar nowdate = Calendar.getInstance();
        int mYear = nowdate.get(Calendar.YEAR);
        int mMonth = nowdate.get(Calendar.MONTH);
        int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear + 1;
            int mDay = dayOfMonth;
            String month = "";
            String day = "";
//            TextView date_textview = (TextView) findViewById(R.id.changebirth_textview);
            String days;
            if (mMonth < 10)
                month = "0" + mMonth;
            else
                month = mMonth + "";
            if (mDay < 10)
                day = "0" + mDay;
            else
                day = mDay + "";
            mYCRQ.setText(mYear + "-" + month + "-" + day);
            mProvideInteractPatientInterrogationParment.setBloodPressureAbnormalDate(mYear + "-" + month + "-" + day);
            System.out.println();
        }
    };

    /**
     * 选择测量方式
     */
    private void showCLFSDialog() {
        String[] items = new String[mApp.gBasicDate.get(10007).size()];
        for (int i = 0; i < mApp.gBasicDate.get(10007).size(); i++) {
            items[i] = mApp.gBasicDate.get(10007).get(i).getAttrName();
        }
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                mCLFF.setText(mApp.gBasicDate.get(10007).get(which).getAttrName());
                mProvideInteractPatientInterrogationParment.setMeasureMode(mApp.gBasicDate.get(10007).get(which).getBaseCode());
                mProvideInteractPatientInterrogationParment.setMeasureModeName(mApp.gBasicDate.get(10007).get(which).getAttrName());
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }


    /**
     * 选择测量仪器
     */
    private void showCLYQDialog() {
        String[] items = new String[mApp.gBasicDate.get(10006).size()];
        for (int i = 0; i < mApp.gBasicDate.get(10006).size(); i++) {
            items[i] = mApp.gBasicDate.get(10006).get(i).getAttrName();
        }
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                mCLYQ.setText(mApp.gBasicDate.get(10006).get(which).getAttrName());
                mProvideInteractPatientInterrogationParment.setMeasureInstrument(mApp.gBasicDate.get(10006).get(which).getBaseCode());
                mProvideInteractPatientInterrogationParment.setMeasureInstrumentName(mApp.gBasicDate.get(10006).get(which).getAttrName());
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }


    /**
     * 家族内是否有高血压病史
     */
    private void showDoctorTitleDialog() {
        String[] doctorTitleName = new String[]{"否", "是"};// 家族内是否有高血压病史
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(doctorTitleName, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                mJZHZ.setText(doctorTitleName[which]);
                mProvideInteractPatientInterrogationParment.setFlagFamilyHtn(which);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    /**
     * 家族内是否有高血压病史
     */
    private void showChoiceNLDialog() {
        String[] doctorTitleName = new String[]{"男", "女"};// 家族内是否有高血压病史
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(doctorTitleName, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                mXB.setText(doctorTitleName[which]);
                mProvideInteractPatientInterrogationParment.setGender(which + 1);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }


    /**
     * 下一步
     */
    private void commit() {
//        mProvideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();
        ProvideInteractPatientInterrogation provideInteractPatientInterrogation = mProvideInteractPatientInterrogation;
        mProvideInteractPatientInterrogationParment.setLoginPatientPosition(mApp.loginDoctorPosition);
        mProvideInteractPatientInterrogationParment.setRequestClientType("1");
        mProvideInteractPatientInterrogationParment.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        mProvideInteractPatientInterrogationParment.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        mProvideInteractPatientInterrogationParment.setOrderCode(mOrderNum);
        mProvideInteractPatientInterrogationParment.setFlagHtnHistory("0");
        if (mProvideInteractPatientInterrogationParment.getGender() == null || mProvideInteractPatientInterrogationParment.getGender() == 0) {
            Toast.makeText(mContext, "请选择性别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideInteractPatientInterrogationParment.getOrderCode() == null || "".equals(mProvideInteractPatientInterrogationParment.getOrderCode())) {
            Toast.makeText(mContext, "订单号获取失败，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        mProvideInteractPatientInterrogationParment.setTreatmentType(Integer.valueOf(mOperaType));
//        if (mProvideInteractPatientInterrogationParment.getTreatmentType() == null || "".equals(mProvideInteractPatientInterrogationParment.getTreatmentType()))
//        {
//            Toast.makeText(mContext,"问诊类型获取失败，请稍后重试",Toast.LENGTH_SHORT).show();
//            return;
//        }
        mProvideInteractPatientInterrogationParment.setDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
        mProvideInteractPatientInterrogationParment.setDoctorName(provideViewDoctorExpertRecommend.getUserName());
        mProvideInteractPatientInterrogationParment.setPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        mProvideInteractPatientInterrogationParment.setPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        mProvideInteractPatientInterrogationParment.setPatientLinkPhone(mHZSJ.getText().toString());
//        mProvideInteractPatientInterrogation.setGender(mApp.mProvideViewSysUserPatientInfoAndRegion.getGender());
        mProvideInteractPatientInterrogationParment.setHtnHistory(mGXYBS.getText().toString());
        if (mSZY.getText().toString() == null || "".equals(mSZY.getText().toString())) {
            Toast.makeText(mContext, "请填写舒张压", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mSSY.getText().toString() == null || "".equals(mSSY.getText().toString())) {
            Toast.makeText(mContext, "请填写收缩压", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mXL.getText().toString() == null || "".equals(mXL.getText().toString())) {
            Toast.makeText(mContext, "请填写心率", Toast.LENGTH_SHORT).show();
            return;
        }
        mProvideInteractPatientInterrogationParment.setHighPressureNum(Integer.valueOf(mSZY.getText().toString()));
        if (mProvideInteractPatientInterrogationParment.getHighPressureNum() == null || "".equals(mProvideInteractPatientInterrogationParment.getHighPressureNum())) {
            Toast.makeText(mContext, "请填写舒张压", Toast.LENGTH_SHORT).show();
            return;
        }
        mProvideInteractPatientInterrogationParment.setLowPressureNum(Integer.valueOf(mSSY.getText().toString()));
        if (mProvideInteractPatientInterrogationParment.getLowPressureNum() == null || "".equals(mProvideInteractPatientInterrogationParment.getLowPressureNum())) {
            Toast.makeText(mContext, "请填写收缩压", Toast.LENGTH_SHORT).show();
            return;
        }
        mProvideInteractPatientInterrogationParment.setHeartRateNum(Integer.valueOf(mXL.getText().toString()));
        if (mProvideInteractPatientInterrogationParment.getHeartRateNum() == null || "".equals(mProvideInteractPatientInterrogationParment.getHeartRateNum())) {
            Toast.makeText(mContext, "请填写心率", Toast.LENGTH_SHORT).show();
            return;
        }
        mProvideInteractPatientInterrogationParment.setStateOfIllness(mBQZS.getText().toString());
//        if (mProvideInteractPatientInterrogationParment.getStateOfIllness() == null || "".equals(mProvideInteractPatientInterrogationParment.getStateOfIllness())) {
//            Toast.makeText(mContext, "请填写病情自述", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (mProvideInteractPatientInterrogationParment.getBloodPressureAbnormalDate() == null || "".equals(mProvideInteractPatientInterrogationParment.getBloodPressureAbnormalDate())) {
            Toast.makeText(mContext, "请选择最早发现异常日期", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideInteractPatientInterrogationParment.getFlagFamilyHtn() == null || "".equals(mProvideInteractPatientInterrogationParment.getFlagFamilyHtn())) {
            Toast.makeText(mContext, "请选择家族内是否有高血压患者", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mNL.getText().toString() == null || "".equals(mNL.getText().toString())) {
            Toast.makeText(mContext, "请填写年龄", Toast.LENGTH_SHORT).show();
            return;
        }
        mProvideInteractPatientInterrogationParment.setBirthday(mNL.getText().toString());
        if (mProvideInteractPatientInterrogationParment.getMeasureInstrument() == null || "".equals(mProvideInteractPatientInterrogationParment.getMeasureInstrument())) {
            Toast.makeText(mContext, "请选择测量仪器", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideInteractPatientInterrogationParment.getMeasureMode() == null || "".equals(mProvideInteractPatientInterrogationParment.getMeasureMode())) {
            Toast.makeText(mContext, "请选择方法", Toast.LENGTH_SHORT).show();
            return;
        }

        //生成图片编码
        mProvideInteractPatientInterrogationParment.setImgCode(UUID.randomUUID().toString());
        getProgressBar("请稍候", "正在提交数据");
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    String str = new Gson().toJson(mProvideInteractPatientInterrogationParment);
                    String mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "patientInteractDataControlle/operAddInteractPatientInterrogationData");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("数据提交失败：" + netRetEntity.getResMsg());
                        mAddNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(10);
                        return;
                    } else {
                        //判断是否有图片
                        if (mPhotoInfos.size() > 1) {
                            //上传图片
                            for (int i = 1; i < mPhotoInfos.size(); i++) {
                                UpLoadImgParment upLoadImgParment = new UpLoadImgParment();
                                upLoadImgParment.setLoginPatientPosition(mApp.loginDoctorPosition);
                                upLoadImgParment.setRequestClientType("1");
                                upLoadImgParment.setOrderCode(mOrderNum);
                                upLoadImgParment.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                                upLoadImgParment.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                                upLoadImgParment.setImgBase64Data("data:image/jpg;base64," + mPhotoInfos.get(i).getPhoto());
                                upLoadImgParment.setImgCode(mProvideInteractPatientInterrogationParment.getImgCode());
                                //上传图片
                                str = new Gson().toJson(upLoadImgParment);
                                mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "patientInteractDataControlle/operUpdPatientInterrogationImg");
                                netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                                if (netRetEntity.getResCode() == 0) {
                                    NetRetEntity retEntity = new NetRetEntity();
                                    retEntity.setResCode(0);
                                    retEntity.setResMsg("图片上传失败");
                                    mAddNetRetStr = new Gson().toJson(retEntity);
                                    mHandler.sendEmptyMessage(10);
                                    return;
                                }
                            }
                        }
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(1);
                        retEntity.setResMsg("提交成功：" + netRetEntity.getResMsg());
                        mAddNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(10);
                    }


                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mAddNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(10);
                    return;
                }
            }
        }.start();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout) this.findViewById(R.id.iv_back_left);
        mCommit = (TextView) this.findViewById(R.id.tv_commit);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(mContext,WZXXOrderActivity.class).putExtra("provideInteractPatientInterrogation",mProvideInteractPatientInterrogation).putExtra("orderID",mOrderNum).putExtra("orderType",mOperaType));
                commit();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mJZLX = (TextView) this.findViewById(R.id.tv_activityHZZL_userYW);
        switch (Integer.valueOf(mOperaType)) {
            case 1:
                mJZLX.setText("图文就诊");
                break;
            case 5:
                mJZLX.setText("电话就诊");
                break;
            case 2:
                mJZLX.setText("音频就诊");
                break;
            case 3:
                mJZLX.setText("视频就诊");
                break;
            case 4:
                mJZLX.setText("签约就诊");
                break;
        }
        mYSXM = (TextView) this.findViewById(R.id.tv_activityHZZL_userTZ);
        mYSXM.setText(provideViewDoctorExpertRecommend.getUserName());
        mProvideInteractPatientInterrogation.setDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
        mProvideInteractPatientInterrogation.setDoctorName(provideViewDoctorExpertRecommend.getUserName());

        mProvideInteractPatientInterrogationParment.setDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
        mProvideInteractPatientInterrogationParment.setDoctorName(provideViewDoctorExpertRecommend.getUserName());

//        mHZXM = (TextView)this.findViewById(R.id.tv_activityHZZL_userSFXY);
//        mHZXM.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());

        mHZSJ = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFXJ);
        mHZSJ.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());

        mXB = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFAY);
        mXB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceNLDialog();
            }
        });
        if (mApp.mProvideViewSysUserPatientInfoAndRegion.getGender() == 1)
            mXB.setText("男");
        else if (mApp.mProvideViewSysUserPatientInfoAndRegion.getGender() == 2)
            mXB.setText("女");
        else
            mXB.setText("未设置");
        mNL = (TextView) this.findViewById(R.id.tv_activityHZZL_userAuthState);
//        mNL.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getBirthday());

        mYCRQ = (TextView) this.findViewById(R.id.tv_activityHZZL_MZ);
        mYCRQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBirthDayChoiceDialog();
            }
        });
        mJZHZ = (TextView) this.findViewById(R.id.tv_activityHZZL_userName);
        mJZHZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDoctorTitleDialog();
            }
        });
        mSSY = (TextView) this.findViewById(R.id.tv_activityHZZL_sex);
        mSZY = (TextView) this.findViewById(R.id.tv_activityHZZL_szy);
        mXL = (TextView) this.findViewById(R.id.tv_activityHZZL_xl);
        mCLYQ = (TextView) this.findViewById(R.id.tv_activityHZZL_clyq);
        mCLYQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCLYQDialog();
            }
        });
        mCLFF = (TextView) this.findViewById(R.id.tv_activityHZZL_clfs);
        mCLFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCLFSDialog();
            }
        });
        mGXYBS = (TextView) this.findViewById(R.id.tv_activityHZZL_BirthDay);
        mBQZS = (TextView) this.findViewById(R.id.tv_activityHZZL_idCardNum);

//        mPBRecycleView = (RecyclerView) this.findViewById(R.id.rv_imageView);
//        //创建默认的线性LayoutManager
//        mGridLayoutManager = new FullyGridLayoutManager(mContext,3);
//        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
//        mPBRecycleView.setLayoutManager(mGridLayoutManager);
//        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        mPBRecycleView.setHasFixedSize(true);
//        //创建并设置Adapter
//        mMyPBRecycleAdapter = new DoctorPBRecycleAdapter(mPbInfos,mContext,mActivity);
//        mPBRecycleView.setAdapter(mMyPBRecycleAdapter);
//        mMyPBRecycleAdapter.setOnItemClickListener(new DoctorPBRecycleAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//
//                for (int i = 0; i < mPbInfos.size(); i++)
//                {
//                    mPbInfos.get(i).setChoice(false);
//                }
//                mPbInfos.get(position).setChoice(true);
//                mMyPBRecycleAdapter.setDate(mPbInfos);
//                mMyPBRecycleAdapter.notifyDataSetChanged();
//            }
//        });

        mImageRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityPublish_Image);
        //创建默认的线性LayoutManager
        mGridLayoutManager = new FullyGridLayoutManager(mContext, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mImageRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mImageRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        final Photo_Info photo_info = new Photo_Info();
        photo_info.setPhotoID("ADDPHOTO");
        mPhotoInfos.add(photo_info);
        mImageViewRecycleAdapter = new ImageViewRecycleAdapter(mPhotoInfos, mApp);
        mImageRecycleView.setAdapter(mImageViewRecycleAdapter);
        //点击
        mImageViewRecycleAdapter.setOnItemClickListener(new ImageViewRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(final int position) {
                if (mPhotoInfos.size() >= 4) {
                    Toast.makeText(mContext, "照片不超过三张", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("ADDPHOTO".equals(mPhotoInfos.get(position).getPhotoID())) {
                    String[] items = {"拍照", "从相册选择"};
                    Dialog dialog = new AlertDialog.Builder(mContext)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0:
                                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                            StrictMode.setVmPolicy(builder.build());
                                            builder.detectFileUriExposure();
                                            // 添加Action类型：MediaStore.ACTION_IMAGE_CAPTURE
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            // 指定调用相机拍照后照片(结果)的储存路径
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
                                            // 等待返回结果
                                            startActivityForResult(intent, Constant.SELECT_PIC_BY_TACK_PHOTO);
                                            break;
                                        case 1:
                                            BitmapUtil.selectAlbum(mActivity);//从相册选择
                                            break;
                                    }
                                }
                            }).show();
                } else {
                    Dialog dialog = new AlertDialog.Builder(mContext)
                            .setMessage("删除该照片")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mPhotoInfos.remove(position);
                                    if (mPhotoInfos.size() == 0) {
                                        Photo_Info photo_info1 = new Photo_Info();
                                        photo_info1.setPhotoID("ADDPHOTO");
                                        mPhotoInfos.add(photo_info1);
                                    }
                                    mImageViewRecycleAdapter.setDate(mPhotoInfos);
                                    mImageViewRecycleAdapter.notifyDataSetChanged();
                                }
                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }


    /**
     * 创建临时文件夹 _tempphoto
     */
    private void initDir() {
        // 声明目录
        File tempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/_tempphoto");
        if (!tempDir.exists()) {
            tempDir.mkdirs();// 创建目录
        }
        mTempFile = new File(tempDir, BitmapUtil.getPhotoFileName());// 生成临时文件
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wzxx);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gPayCloseActivity.add(mActivity);
        provideViewDoctorExpertRecommend = (ProvideViewDoctorExpertRecommend) getIntent().getSerializableExtra("provideViewDoctorExpertRecommend");
        provideDoctorSetSchedulingInfoGroupDate = (ProvideDoctorSetSchedulingInfoGroupDate) getIntent().getSerializableExtra("provideDoctorSetSchedulingInfoGroupDate");
        mOperaType = getIntent().getStringExtra("operaType");
        initDir();
        initLayout();
        initHandler();
        getData();
        if (mApp.gBasicDate.get(10006) == null)
            Util.getBasicDate(10006, mApp);
        if (mApp.gBasicDate.get(10007) == null)
            Util.getBasicDate(10007, mApp);
    }


    @Override
    protected void onActivityResult(
            int requestCode,  // 请求码 自定义
            int resultCode,  // 结果码 成功 -1 == OK
            Intent data) { // 数据 ? 可以没有
        try {

            // 如果是直接从相册获取
            if (requestCode == Constant.SELECT_PIC_FROM_ALBUM
                    && resultCode == RESULT_OK
                    && data != null) {

                final Uri uri = data.getData();//返回相册图片的Uri
                BitmapUtil.startPhotoZoom(mActivity, uri, 450);
            }

            // 处理拍照返回
            if (requestCode == Constant.SELECT_PIC_BY_TACK_PHOTO
                    && resultCode == RESULT_OK) {// 拍照成功 RESULT_OK= -1
                // 剪裁图片
                BitmapUtil.startPhotoZoom(mActivity, Uri.fromFile(mTempFile), 450);
            }
            // 接收剪裁回来的结果
            if (requestCode == Constant.REQUEST_PHOTO_CUT
                    && resultCode == RESULT_OK) {// 剪裁加工成功
                //让剪裁结果显示到图片框
                setPicToView(data);
            }
        } catch (Exception e) {
            Log.i("yi", "yichahahaha");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPicToView(Intent data) {
        Bitmap photo;
        try {
            Uri u = data.getData();
            if (u != null) {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));//将imageUri对象的图片加载到内存
            } else {
                System.out.println("进来了");
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg"))));//将imageUri对象的图片加载到内存
            }
            System.out.println("图片：" + photo);
            Photo_Info photo_info = new Photo_Info();
            photo_info.setPhoto(BitmapUtil.bitmaptoString(photo));
            mPhotoInfos.add(photo_info);
            mImageViewRecycleAdapter.setDate(mPhotoInfos);
            mImageViewRecycleAdapter.notifyDataSetChanged();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:

                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            cacerProgress();
                        } else {

                            mOrderNum = netRetEntity.getResJsonData();
                            getInteractPatientInterrogationResNewest();
                        }

                        break;
                    case 1:
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            mProvideInteractPatientInterrogation = JSON.parseObject(netRetEntity.getResJsonData(), ProvideInteractPatientInterrogation.class);
                            mProvideInteractPatientInterrogationParment.setInterrogationId(mProvideInteractPatientInterrogation.getInterrogationId());
                            mProvideInteractPatientInterrogationParment.setOrderCode(mProvideInteractPatientInterrogation.getOrderCode());
                            mProvideInteractPatientInterrogationParment.setTreatmentType(mProvideInteractPatientInterrogation.getTreatmentType());
                            mProvideInteractPatientInterrogationParment.setImgCode(mProvideInteractPatientInterrogation.getImgCode());
                            mProvideInteractPatientInterrogationParment.setDoctorCode(mProvideInteractPatientInterrogation.getDoctorCode());
                            mProvideInteractPatientInterrogationParment.setDoctorName(mProvideInteractPatientInterrogation.getDoctorName());
                            mProvideInteractPatientInterrogationParment.setPatientCode(mProvideInteractPatientInterrogation.getPatientCode());
                            mProvideInteractPatientInterrogationParment.setPatientName(mProvideInteractPatientInterrogation.getPatientName());
                            mProvideInteractPatientInterrogationParment.setPatientLinkPhone(mProvideInteractPatientInterrogation.getPatientLinkPhone());
                            mProvideInteractPatientInterrogationParment.setGender(mProvideInteractPatientInterrogation.getGender());
                            mProvideInteractPatientInterrogationParment.setBirthday(mProvideInteractPatientInterrogation.getBirthday());
                            Date date = mProvideInteractPatientInterrogation.getBloodPressureAbnormalDate();
                            if (mProvideInteractPatientInterrogation.getBloodPressureAbnormalDate() != null)
                                mProvideInteractPatientInterrogationParment.setBloodPressureAbnormalDate(Util.dateToStrDate(mProvideInteractPatientInterrogation.getBloodPressureAbnormalDate()));
                            mProvideInteractPatientInterrogationParment.setHtnHistory(mProvideInteractPatientInterrogation.getHtnHistory());
                            mProvideInteractPatientInterrogationParment.setFlagFamilyHtn(mProvideInteractPatientInterrogation.getFlagFamilyHtn());
                            mProvideInteractPatientInterrogationParment.setHighPressureNum(mProvideInteractPatientInterrogation.getHighPressureNum());
                            mProvideInteractPatientInterrogationParment.setLowPressureNum(mProvideInteractPatientInterrogation.getLowPressureNum());
                            mProvideInteractPatientInterrogationParment.setHeartRateNum(mProvideInteractPatientInterrogation.getHeartRateNum());
                            mProvideInteractPatientInterrogationParment.setMeasureInstrument(mProvideInteractPatientInterrogation.getMeasureInstrument());
                            mProvideInteractPatientInterrogationParment.setMeasureInstrumentName(mProvideInteractPatientInterrogation.getMeasureInstrumentName());
                            mProvideInteractPatientInterrogationParment.setStateOfIllness(mProvideInteractPatientInterrogation.getStateOfIllness());
                            mProvideInteractPatientInterrogationParment.setFlagUseState(mProvideInteractPatientInterrogation.getFlagUseState());
                            mProvideInteractPatientInterrogationParment.setFlagHtnHistory(mProvideInteractPatientInterrogation.getFlagHtnHistory());
                            mProvideInteractPatientInterrogationParment.setTreatmentTypeName(mProvideInteractPatientInterrogation.getTreatmentTypeName());
                            mProvideInteractPatientInterrogationParment.setMeasureMode(mProvideInteractPatientInterrogation.getMeasureMode());
                            mProvideInteractPatientInterrogationParment.setMeasureModeName(mProvideInteractPatientInterrogation.getMeasureModeName());
                            //设置数据
                            setLayoutDate();

                        } else {

                        }
                        cacerProgress();
                        break;

                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            mProvideDoctorSetSchedulingInfoGroupDate = JSON.parseArray(netRetEntity.getResJsonData(), ProvideDoctorSetSchedulingInfoGroupDate.class);
                            //设置数据
//                            setPBDate();
                        }
                        break;

                    case 10:
                        mProvideInteractPatientInterrogation.setDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
                        mProvideInteractPatientInterrogation.setDoctorName(provideViewDoctorExpertRecommend.getUserName());

                        mProvideInteractPatientInterrogationParment.setDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
                        mProvideInteractPatientInterrogationParment.setDoctorName(provideViewDoctorExpertRecommend.getUserName());
                        provideViewDoctorExpertRecommend.setLinkPhone(mProvideInteractPatientInterrogationParment.getPatientLinkPhone());
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mAddNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        if (netRetEntity.getResCode() == 1)
                            startActivity(new Intent(mContext, WZXXOrderActivity.class).putExtra("provideInteractPatientInterrogation", mProvideInteractPatientInterrogation)
                                    .putExtra("orderID", mOrderNum)
                                    .putExtra("orderType", mOperaType)
                                    .putExtra("provideViewDoctorExpertRecommend", provideViewDoctorExpertRecommend)
                                    .putExtra("provideDoctorSetSchedulingInfoGroupDate", provideDoctorSetSchedulingInfoGroupDate));
                        break;
                }
            }
        };
    }

    /**
     * 获取医生排版信息
     */
    private void setPBDate() {
//        if (mProvideDoctorSetScheduling.getMondayMorning() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(1);
//            provideDoctorSetSchedulingPatient.setGradation(1);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getMondayMorningSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周一早上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getMondayNoon() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(1);
//            provideDoctorSetSchedulingPatient.setGradation(2);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getMondayNoonSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周一中午");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getMondayNight() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(1);
//            provideDoctorSetSchedulingPatient.setGradation(3);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getMondayNightSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周一晚上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//
//        if (mProvideDoctorSetScheduling.getTuesdayMorning() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(2);
//            provideDoctorSetSchedulingPatient.setGradation(1);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getTuesdayMorningSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周二早上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getTuesdayNoon() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(2);
//            provideDoctorSetSchedulingPatient.setGradation(2);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getTuesdayNoonSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周二中午");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getTuesdayNight() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(2);
//            provideDoctorSetSchedulingPatient.setGradation(3);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getTuesdayNightSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周二晚上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getWednesdayMorning() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(3);
//            provideDoctorSetSchedulingPatient.setGradation(1);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getWednesdayMorningSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周三早上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getWednesdayNoon() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(3);
//            provideDoctorSetSchedulingPatient.setGradation(2);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getWednesdayNoonSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周三中午");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getWednesdayNight() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(3);
//            provideDoctorSetSchedulingPatient.setGradation(3);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getWednesdayNightSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周三晚上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//
//        if (mProvideDoctorSetScheduling.getThursdayMorning() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(4);
//            provideDoctorSetSchedulingPatient.setGradation(1);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getThursdayMorningSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周四早上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getThursdayNoon() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(4);
//            provideDoctorSetSchedulingPatient.setGradation(2);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getTuesdayNoonSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周四中午");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getTuesdayNight() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(4);
//            provideDoctorSetSchedulingPatient.setGradation(3);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getTuesdayNightSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周四晚上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//
//
//        if (mProvideDoctorSetScheduling.getFridayMorning() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(5);
//            provideDoctorSetSchedulingPatient.setGradation(1);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getFridayMorningSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周五早上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getFridayNoon() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(5);
//            provideDoctorSetSchedulingPatient.setGradation(2);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getFridayNoonSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周五中午");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getFridayNight() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(5);
//            provideDoctorSetSchedulingPatient.setGradation(3);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getFridayNightSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周五晚上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//
//
//        if (mProvideDoctorSetScheduling.getSaturdayMorning() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(6);
//            provideDoctorSetSchedulingPatient.setGradation(1);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getSaturdayMorningSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周六早上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getSaturdayNoon() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(6);
//            provideDoctorSetSchedulingPatient.setGradation(2);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getSaturdayNoonSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周六中午");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getSaturdayNight() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(6);
//            provideDoctorSetSchedulingPatient.setGradation(3);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getSaturdayNightSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周六晚上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//
//
//        if (mProvideDoctorSetScheduling.getSundayMorning() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(7);
//            provideDoctorSetSchedulingPatient.setGradation(1);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getSundayMorningSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周日早上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getSundayNoon() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(7);
//            provideDoctorSetSchedulingPatient.setGradation(2);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getSundayNoonSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周日中午");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }
//        if (mProvideDoctorSetScheduling.getSundayNight() == 1)
//        {
//            ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
//            provideDoctorSetSchedulingPatient.setChoice(false);
//            provideDoctorSetSchedulingPatient.setDate(7);
//            provideDoctorSetSchedulingPatient.setGradation(3);
//            provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getSundayNightSourceNum());
//            provideDoctorSetSchedulingPatient.setText("周日晚上");
//            mPbInfos.add(provideDoctorSetSchedulingPatient);
//        }

        for (int i = 0; i < mProvideDoctorSetSchedulingInfoGroupDate.size(); i++) {
            if (mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList() == null)
                continue;
            for (int j = 0; j < mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList().size(); j++) {
                ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
                provideDoctorSetSchedulingPatient.setChoice(false);
//                provideDoctorSetSchedulingPatient.setDate(7);
//                provideDoctorSetSchedulingPatient.setGradation(3);
                provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getSundayNightSourceNum());
                provideDoctorSetSchedulingPatient.setText(mProvideDoctorSetSchedulingInfoGroupDate.get(i).getWorkDateName()
                        + mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList().get(j).getDayTimeSlot());
                mPbInfos.add(provideDoctorSetSchedulingPatient);
            }
        }
        mMyPBRecycleAdapter.setDate(mPbInfos);
        mMyPBRecycleAdapter.notifyDataSetChanged();
    }

    /**
     * 获取医生排班信息
     */
    private void getDoctorPBInfo() {
        ProvideDoctorSetScheduling provideDoctorSetScheduling = new ProvideDoctorSetScheduling();
        provideDoctorSetScheduling.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideDoctorSetScheduling.setRequestClientType("1");
        provideDoctorSetScheduling.setSearchDoctorCode(provideViewDoctorExpertRecommend.getPatientCode());
        provideDoctorSetScheduling.setSearchDoctorName(provideViewDoctorExpertRecommend.getUserName());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideDoctorSetScheduling);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "patientInteractDataControlle/getInteractPatientInterrogationResDoctorSchedulingInfo");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }


    /**
     * 获取
     */
    private void getData() {
        getProgressBar("请稍候", "正在获取数据。。。");
        GetInteractOrderCodeGenerate getInteractOrderCodeGenerate = new GetInteractOrderCodeGenerate();
        getInteractOrderCodeGenerate.setLoginPatientPosition(mApp.loginDoctorPosition);
        getInteractOrderCodeGenerate.setRequestClientType("1");
        getInteractOrderCodeGenerate.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        Log.e("tag", "getData: "+mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode() );
        getInteractOrderCodeGenerate.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        Log.e("tag", "getData: "+mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName() );
        getInteractOrderCodeGenerate.setUserLinkPhone(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());
        Log.e("tag", "getData: "+mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone() );

        getInteractOrderCodeGenerate.setOrderTreatmentType(mOperaType);
        Log.e("tag", "getData: "+mOperaType.toString());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(getInteractOrderCodeGenerate);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "patientInteractDataControlle/getInteractOrderCodeGenerate");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                    Log.e("tag", "专家详情 "+mNetRetStr );
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
     * 获取问诊资料
     */
    private void getInteractPatientInterrogationResNewest() {
        ProvideInteractPatientInterrogation provideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();
        provideInteractPatientInterrogation.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideInteractPatientInterrogation.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractPatientInterrogation.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
//        provideBasicsImg.setOrderCode(getInteractOrderCodeGenerate.getOrderCode());
        provideInteractPatientInterrogation.setRequestClientType(mOperaType);

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractPatientInterrogation);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "patientInteractDataControlle/getInteractPatientInterrogationResNewest");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mGetImgNetRetStr = new Gson().toJson(retEntity);
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

}
