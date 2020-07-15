package www.patient.jykj_zxyl.activity.home.patient;

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
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import entity.ProvideBasicsImg;
import entity.ProvideInteractOrderInfo;
import entity.ProvideInteractPatientInterrogation;
import entity.mySelf.ProvideDoctorSetScheduling;
import entity.patientapp.Photo_Info;
import entity.patientapp.ProvideDoctorSetSchedulingPatient;


import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.JZJL_WZZLImageViewRecycleAdapter;
import www.patient.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.DoctorPBRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.BitmapUtil;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.Util;

/**
 * 我的医生 == 》 就诊记录 == 》 问诊资料
 */
public class WDYS_JZJL_WZZLActivity extends AppCompatActivity {

    private Context mContext;
    private WDYS_JZJL_WZZLActivity mActivity;
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
    private JZJL_WZZLImageViewRecycleAdapter mAdapter;


    private LinearLayout mYSurplus;                  //我的余额
    private RecyclerView mPBRecycleView;             //排班列表
    private LinearLayoutManager layoutManager;
    private DoctorPBRecycleAdapter mMyPBRecycleAdapter;       //适配器
    private List<ProvideDoctorSetSchedulingPatient> mPbInfos = new ArrayList<>();


    private ProvideInteractOrderInfo mProvideInteractOrderInfo;                          //订单信息
    private ProvideInteractPatientInterrogation mProvideInteractPatientInterrogation;
    private List<ProvideBasicsImg> mProvideBasicsImg = new ArrayList<>();
    private LinearLayout mBack;

    private ProvideDoctorSetScheduling provideDoctorSetScheduling = new ProvideDoctorSetScheduling();

    private ProvideDoctorSetScheduling mProvideDoctorSetScheduling = new ProvideDoctorSetScheduling();


    private JZJL_WZZLImageViewRecycleAdapter mImageViewRecycleAdapter;
    private List<Photo_Info> mPhotoInfos = new ArrayList<>();

    private File mTempFile;              //声明一个拍照结果的临时文件
    private TextView mCommit;                    //提交


    private void setLayoutDate() {

        //设置初始数据
        mJZLX.setText(mProvideInteractPatientInterrogation.getTreatmentTypeName());
//        mYSXM.setText(mProvideInteractPatientInterrogation.getDoctorName());
        mHZXM.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        mHZSJ.setText(mProvideInteractPatientInterrogation.getPatientLinkPhone());
        if (mProvideInteractPatientInterrogation.getGender() == 0)
            mXB.setText("未知");
        if (mProvideInteractPatientInterrogation.getGender() == 1)
            mXB.setText("男");
        if (mProvideInteractPatientInterrogation.getGender() == 2)
            mXB.setText("女");
        mProvideInteractPatientInterrogation.setGender(mProvideInteractPatientInterrogation.getGender());
        mNL.setText(mProvideInteractPatientInterrogation.getBirthday());
        mProvideInteractPatientInterrogation.setInterrogationId(mProvideInteractPatientInterrogation.getInterrogationId());
        if (mProvideInteractPatientInterrogation.getBloodPressureAbnormalDate() == null)
            mYCRQ.setText("请选择最早发现日期");
        else
            mYCRQ.setText(Util.dateToStr(mProvideInteractPatientInterrogation.getBloodPressureAbnormalDate()));
        if (mProvideInteractPatientInterrogation.getFlagFamilyHtn() == 0)
            mJZHZ.setText("否");
        if (mProvideInteractPatientInterrogation.getFlagFamilyHtn() == 1)
            mJZHZ.setText("是");
        mSSY.setText(mProvideInteractPatientInterrogation.getHighPressureNum() + "");
        mSZY.setText(mProvideInteractPatientInterrogation.getLowPressureNum() + "");
        mXL.setText(mProvideInteractPatientInterrogation.getHeartRateNum() + "");
        mCLYQ.setText(mProvideInteractPatientInterrogation.getMeasureInstrumentName());
        mCLFF.setText(mProvideInteractPatientInterrogation.getMeasureModeName());
        mGXYBS.setText(mProvideInteractPatientInterrogation.getHtnHistory());
        mBQZS.setText(mProvideInteractPatientInterrogation.getStateOfIllness());

        if (mProvideInteractPatientInterrogation.getTreatmentType() != null) {
            switch (Integer.valueOf(mProvideInteractPatientInterrogation.getTreatmentType())) {
                case 1:
                    mJZLX.setText("图文就诊");
                    break;
                case 2:
                    mJZLX.setText("电话就诊");
                    break;
                case 3:
                    mJZLX.setText("音频就诊");
                    break;
                case 4:
                    mJZLX.setText("视频就诊");
                    break;
                case 5:
                    mJZLX.setText("签约就诊");
                    break;
            }
        } else {
            mJZLX.setText("未设置");
        }

    }


    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout) this.findViewById(R.id.iv_back_left);
        mCommit = (TextView) this.findViewById(R.id.tv_commit);
//        mCommit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(mContext,WZXXOrderActivity.class).putExtra("provideInteractPatientInterrogation",mProvideInteractPatientInterrogation).putExtra("orderID",mOrderNum).putExtra("orderType",mOperaType));
////                commit();
//            }
//        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mJZLX = (TextView) this.findViewById(R.id.tv_activityHZZL_userYW);

//        mYSXM = (TextView)this.findViewById(R.id.tv_activityHZZL_userTZ);
//        mYSXM.setText(provideViewDoctorExpertRecommend.getUserName());
//        mProvideInteractPatientInterrogation.setDoctorCode(provideViewDoctorExpertRecommend.getPatientCode());
//        mProvideInteractPatientInterrogation.setDoctorName(provideViewDoctorExpertRecommend.getUserName());
        mHZXM = (TextView) this.findViewById(R.id.tv_activityHZZL_userTZ);
        mHZXM.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());

        mHZSJ = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFXJ);
        mHZSJ.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());

        mXB = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFAY);

        if (mApp.mProvideViewSysUserPatientInfoAndRegion.getGender() == 1)
            mXB.setText("男");
        else if (mApp.mProvideViewSysUserPatientInfoAndRegion.getGender() == 2)
            mXB.setText("女");
        else
            mXB.setText("未设置");
        mNL = (TextView) this.findViewById(R.id.tv_activityHZZL_userAuthState);
//        mNL.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getBirthday());

        mYCRQ = (TextView) this.findViewById(R.id.tv_activityHZZL_MZ);

        mJZHZ = (TextView) this.findViewById(R.id.tv_activityHZZL_userName);

        mSSY = (TextView) this.findViewById(R.id.tv_activityHZZL_sex);
        mSZY = (TextView) this.findViewById(R.id.tv_activityHZZL_szy);
        mXL = (TextView) this.findViewById(R.id.tv_activityHZZL_xl);
        mCLYQ = (TextView) this.findViewById(R.id.tv_activityHZZL_clyq);

        mCLFF = (TextView) this.findViewById(R.id.tv_activityHZZL_clfs);

        mGXYBS = (TextView) this.findViewById(R.id.tv_activityHZZL_BirthDay);
        mBQZS = (TextView) this.findViewById(R.id.tv_activityHZZL_idCardNum);


        mImageRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityPublish_Image);
        //创建默认的线性LayoutManager
        mGridLayoutManager = new FullyGridLayoutManager(mContext, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mImageRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mImageRecycleView.setHasFixedSize(true);
//        //创建并设置Adapter
//        final Photo_Info photo_info = new Photo_Info();
//        photo_info.setPhotoID("ADDPHOTO");
//        mPhotoInfos.add(photo_info);
        mAdapter = new JZJL_WZZLImageViewRecycleAdapter(mProvideBasicsImg, mApp);
        mImageRecycleView.setAdapter(mAdapter);
//        //点击
//        mImageViewRecycleAdapter.setOnItemClickListener(new ImageViewRecycleAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(final int position) {
//                if (mPhotoInfos.size() >= 4)
//                {
//                    Toast.makeText(mContext,"照片不超过三张",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if ("ADDPHOTO".equals(mPhotoInfos.get(position).getPhotoID()))
//                {
//                    String[] items = {"拍照","从相册选择"};
//                    Dialog dialog=new AlertDialog.Builder(mContext)
//                            .setItems(items, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    switch (i)
//                                    {
//                                        case 0:
//                                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//                                            StrictMode.setVmPolicy(builder.build());
//                                            builder.detectFileUriExposure();
//                                            // 添加Action类型：MediaStore.ACTION_IMAGE_CAPTURE
//                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                            // 指定调用相机拍照后照片(结果)的储存路径
//                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
//                                            // 等待返回结果
//                                            startActivityForResult(intent, Constant.SELECT_PIC_BY_TACK_PHOTO);
//                                            break;
//                                        case 1:
//                                            BitmapUtil.selectAlbum(mActivity);//从相册选择
//                                            break;
//                                    }
//                                }
//                            }).show();
//                }
//                else
//                {
//                    Dialog dialog=new AlertDialog.Builder(mContext)
//                            .setMessage("删除该照片")
//                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    mPhotoInfos.remove(position);
//                                    if (mPhotoInfos.size() == 0)
//                                    {
//                                        Photo_Info photo_info1 = new Photo_Info();
//                                        photo_info1.setPhotoID("ADDPHOTO");
//                                        mPhotoInfos.add(photo_info1);
//                                    }
//                                    mImageViewRecycleAdapter.setDate(mPhotoInfos);
//                                    mImageViewRecycleAdapter.notifyDataSetChanged();
//                                }
//                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            }).show();
//                }
//            }
//
//            @Override
//            public void onLongClick(int position) {
//
//            }
//        });

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
        setContentView(R.layout.activity_wdys_wzjl_wzzl);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideInteractOrderInfo = (ProvideInteractOrderInfo) getIntent().getSerializableExtra("provideInteractOrderInfo");

        initDir();
        initLayout();
        initHandler();
        getData();
    }


//    @Override
//    protected void onActivityResult(
//            int requestCode,  // 请求码 自定义
//            int resultCode,  // 结果码 成功 -1 == OK
//            Intent data) { // 数据 ? 可以没有
//        try {
//
//            // 如果是直接从相册获取
//            if (requestCode == Constant.SELECT_PIC_FROM_ALBUM
//                    && resultCode == RESULT_OK
//                    && data != null) {
//
//                final Uri uri = data.getData();//返回相册图片的Uri
//                BitmapUtil.startPhotoZoom(mActivity,uri, 450);
//            }
//
//            // 处理拍照返回
//            if (requestCode == Constant.SELECT_PIC_BY_TACK_PHOTO
//                    && resultCode == RESULT_OK) {// 拍照成功 RESULT_OK= -1
//                // 剪裁图片
//                BitmapUtil.startPhotoZoom(mActivity,Uri.fromFile(mTempFile), 450);
//            }
//            // 接收剪裁回来的结果
//            if (requestCode == Constant.REQUEST_PHOTO_CUT
//                    && resultCode == RESULT_OK) {// 剪裁加工成功
//                //让剪裁结果显示到图片框
//                setPicToView(data);
//            }
//        }catch (Exception e)
//        {
//            Log.i("yi","yichahahaha");
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    public void setPicToView(Intent data) {
//        Bitmap photo;
//        try {
//            Uri u = data.getData();
//            if (u != null)
//            {
//                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));//将imageUri对象的图片加载到内存
//            }
//            else
//            {
//                System.out.println("进来了");
//                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg"))));//将imageUri对象的图片加载到内存
//            }
//            System.out.println("图片："+photo);
//            Photo_Info photo_info = new Photo_Info();
//            photo_info.setPhoto(BitmapUtil.bitmaptoString(photo));
//            mPhotoInfos.add(photo_info);
//            mImageViewRecycleAdapter.setDate(mPhotoInfos);
//            mImageViewRecycleAdapter.notifyDataSetChanged();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }


    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            cacerProgress();
                        } else {
                            mProvideInteractPatientInterrogation = JSON.parseObject(netRetEntity.getResJsonData(), ProvideInteractPatientInterrogation.class);
                            if (mProvideInteractPatientInterrogation != null) {
                                //设置数据
                                setLayoutDate();
                                //获取就诊图片
                                getImgs();
                            }

                        }

                        break;
                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            mProvideBasicsImg = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsImg.class);
                            if (mProvideBasicsImg != null) {
                                mAdapter.setDate(mProvideBasicsImg);
                                mAdapter.notifyDataSetChanged();
                            }

                        } else {

                        }
                        break;


                }
            }
        };
    }

    /**
     * 获取就诊图片
     */
    private void getImgs() {
        ProvideBasicsImg provideBasicsImg = new ProvideBasicsImg();
        provideBasicsImg.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideBasicsImg.setRequestClientType("1");
        provideBasicsImg.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideBasicsImg.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideBasicsImg.setOrderCode(mProvideInteractOrderInfo.getOrderCode());
        provideBasicsImg.setImgCode(mProvideInteractPatientInterrogation.getImgCode());
        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideBasicsImg);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "PatientMyDoctorControlle/searchIndexMyDoctorNotSigningResInterrogationImg");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
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
     * 获取
     */
    private void getData() {
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideInteractPatientInterrogation provideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();
        provideInteractPatientInterrogation.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideInteractPatientInterrogation.setRequestClientType("1");
        provideInteractPatientInterrogation.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractPatientInterrogation.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideInteractPatientInterrogation.setOrderCode(mProvideInteractOrderInfo.getOrderCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractPatientInterrogation);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "PatientMyDoctorControlle/searchIndexMyDoctorNotSigningResInterrogationText");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
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
