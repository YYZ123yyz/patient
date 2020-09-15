package www.patient.jykj_zxyl.activity.myself;

import android.app.AlertDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.basicDate.ProvideBasicsDomain;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalDepartment;
import entity.basicDate.ProvideHospitalInfo;
import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import entity.mySelf.ProvideViewSysUserDoctorInfoAndHospital;
import entity.mySelf.UpProvideViewSysUserPatientInfoAndRegion;
import entity.mySelf.UserResultInfo;
import entity.mySelf.conditions.QueryUserCond;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.util.*;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.Util;

/**
 * 个人中心
 */
public class UserCenterActivity extends AppCompatActivity {

    private Context mContext;
    private UserCenterActivity mActivity;
    public ProgressDialog mDialogProgress = null;

    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串

    private ProvincePicker mPicker;                                            //省市县三级联动选择框
    public Map<String, ProvideBasicsRegion> mChoiceRegionMap = new HashMap<>();                  //用户选择的省市区map

    private int mChoiceRegionLevel;                                       //选择的区域级别
    private String mChoiceRegionID;                                       //选择的区域ID


    private Handler mHandler;

    private LinearLayout mUserHeadLayout;                //用户头像
    private LinearLayout mChoiceSexLayout;           //选择性别布局
    private LinearLayout mChoiceBirthLayout;             //选择生日布局
    //    private                 TextView                    mUserSexText;                   //用户性别
    private TextView mUserBirthDayText;              //用户生日

    private LinearLayout mChoiceRegionLayout;            //选择区域布局
    private TextView mChoiceRegionText;                 //地区选择text

//    private                 LinearLayout                mChoiceHospitalLayout;              //选择医院布局
//    private                 TextView                    mChoiceHospitalText;                //选择医院text

    private File mTempFile;              //声明一个拍照结果的临时文件
    private ImageView mUserHeadImage;                 //用户头像显示

    private List<ProvideHospitalInfo> mProvideHospitalInfos = new ArrayList<>();              //获取到的医院列表
    private String[] mProvideHospitalNameInfos;                              //医院对应的名称列表

    //    private                 LinearLayout                mChoiceDepartmentLayout;                                  //选择一级科室布局
//    private                 TextView                    mChoiceDepartmentText;                                    //选择二级科室
//
//    private                 LinearLayout                mChoiceDepartmentSecondLayout;                              //选择二级科室布局
//    private                 TextView                    mChocieDepartmentSecondText;                                //选择二级科室
    private int mChoiceHospitalIndex;           //选择的医院下标

    private List<ProvideHospitalDepartment> mProvideHospitalDepartmentFInfos = new ArrayList<>();              //获取到的医院一级科室列表
    private String[] mProvideHospitalDepartmentFNameInfos;                              //医院一级科室对应的名称列表


    private List<ProvideHospitalDepartment> mProvideHospitalDepartmentSInfos = new ArrayList<>();              //获取到的医院二级科室列表
    private String[] mProvideHospitalDepartmentSNameInfos;                              //医院二级科室对应的名称列表

    private EditText mUserNameText;                                                  //用户名

    //    private                 TextView                    mUserTitleName;                                                 //医生职称
//    private                 EditText                    mAddressEdit;                                                   //通讯地址
//    private                 EditText                    mEmalEdit;                                                      //邮箱
    private EditText mSynopsisEdit;                                                      //个人简介
//    private                 EditText                    mGoodAtRealmEdit;                                               //擅长领域

    private List<ProvideBasicsDomain> mDoctorTitleList = new ArrayList<>();              //医生职称数据

    private TextView mCommit;                                                        //提交
    private Bitmap mUserHeadBitmap;
    private String mNetLoginRetStr;                 //登录返回字符串

    private RelativeLayout ri_back;

    private ImageView iv_sex_man;                 //性别、男
    private ImageView iv_sex_woman;                 //性别、女
    private ImageView nickName_open;              //昵称是否开启
    private EditText tv_activityUserCenter_userNikeNameText;     //昵称
    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;


    //    private                 ProvideViewSysUserDoctorInfoAndHospital  mProvideViewSysUserDoctorInfoAndHospital = new ProvideViewSysUserDoctorInfoAndHospital();  //医生信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_usercenter);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();
        getData();
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        break;
                    case 1:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        else {
                            mProvideViewSysUserPatientInfoAndRegion = JSON.parseObject(netRetEntity.getResJsonData(), ProvideViewSysUserPatientInfoAndRegion.class);
                            mApp.mProvideViewSysUserPatientInfoAndRegion = mProvideViewSysUserPatientInfoAndRegion;
                            setLayoutDate();
                        }

                        break;
                    case 3:
                        cacerProgress();
                        if (mNetLoginRetStr != null && !mNetLoginRetStr.equals("")) {
                            netRetEntity = new Gson().fromJson(mNetLoginRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                mApp.mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(netRetEntity.getResJsonData(), ProvideViewSysUserPatientInfoAndRegion.class);
                                mApp.saveUserInfo();
                                Toast.makeText(mContext, "操作成功", Toast.LENGTH_SHORT).show();
                                //登录IM
                                mApp.loginIM();
                                finish();
                            } else {
                                Toast.makeText(mContext, "提交失败" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
    }


    /**
     * 设置布局显示
     */
    private void setLayoutDate() {
        //姓名
        if (mProvideViewSysUserPatientInfoAndRegion.getUserName() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getUserName()))
            mUserNameText.setHint("请填写姓名（必填）");
        else
            mUserNameText.setText(mProvideViewSysUserPatientInfoAndRegion.getUserName());
        //性别
        if (mProvideViewSysUserPatientInfoAndRegion.getGender() == 1)
            iv_sex_man.setBackgroundResource(R.mipmap.sex_choiceno);
        else
            iv_sex_man.setBackgroundResource(R.mipmap.sex_nochoice);
        if (mProvideViewSysUserPatientInfoAndRegion.getGender() == 2)
            iv_sex_woman.setBackgroundResource(R.mipmap.sex_choiceno);
        else
            iv_sex_woman.setBackgroundResource(R.mipmap.sex_nochoice);


        //出生日期
        if (mProvideViewSysUserPatientInfoAndRegion.getBirthday() != null && !"".equals(mProvideViewSysUserPatientInfoAndRegion.getBirthday()))
            mUserBirthDayText.setText(Util.dateToStrNUR(mProvideViewSysUserPatientInfoAndRegion.getBirthday()));
        //区域
        String region = "";
        if (mProvideViewSysUserPatientInfoAndRegion.getProvinceName() != null && !"".equals(mProvideViewSysUserPatientInfoAndRegion.getProvinceName())) {
            region += mProvideViewSysUserPatientInfoAndRegion.getProvinceName();
            if (mProvideViewSysUserPatientInfoAndRegion.getCityName() != null && !"".equals(mProvideViewSysUserPatientInfoAndRegion.getCityName()))
                region += mProvideViewSysUserPatientInfoAndRegion.getProvinceName();
            if (mProvideViewSysUserPatientInfoAndRegion.getAreaName() != null && !"".equals(mProvideViewSysUserPatientInfoAndRegion.getAreaName()))
                region += mProvideViewSysUserPatientInfoAndRegion.getAreaName();
        }
        if (region != null && !"".equals(region))
            mChoiceRegionText.setText(region);
        if (mProvideViewSysUserPatientInfoAndRegion.getAddress() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getAddress()))
            mSynopsisEdit.setText(mProvideViewSysUserPatientInfoAndRegion.getAddress());
        if (mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl() != null && !"".equals(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl())) {
            try {
                int avatarResId = Integer.parseInt(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
                Glide.with(mContext).load(avatarResId).into(mUserHeadImage);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mUserHeadImage);
            }
        }
        if (null != mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus() && 1 == mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus().intValue()) {
            nickName_open.setImageResource(R.mipmap.open);
            nickName_open.setTag(R.mipmap.open);
        } else {
            nickName_open.setImageResource(R.mipmap.close);
            nickName_open.setTag(R.mipmap.close);
        }
        mChoiceRegionID = mProvideViewSysUserPatientInfoAndRegion.getArea();

        mChoiceRegionText.setText(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getAreaName()));
        mSynopsisEdit.setText(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getAddress()));
        tv_activityUserCenter_userNikeNameText.setText(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getUserNameAlias()));
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        ri_back = (RelativeLayout) this.findViewById(R.id.ri_back);
        ri_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mCommit = (TextView) this.findViewById(R.id.tv_activityUserCenter_commit);
        mCommit.setOnClickListener(new ButtonClick());

        mUserNameText = (EditText) this.findViewById(R.id.tv_activityUserCenter_userNameText);
        iv_sex_man = (ImageView) this.findViewById(R.id.iv_sex_man);
        iv_sex_woman = (ImageView) this.findViewById(R.id.iv_sex_woman);
        iv_sex_man.setOnClickListener(new ButtonClick());
        iv_sex_woman.setOnClickListener(new ButtonClick());
//        mUserTitleName = (TextView) this.findViewById(R.id.tv_activityUserCenter_userTitleEdit);
//        mUserTitleName.setOnClickListener(new ButtonClick());
//        mAddressEdit = (EditText) this.findViewById(R.id.et_activityUserCenter_addressEdit);
//        mEmalEdit = (EditText)this.findViewById(R.id.et_activityUserCenter_emalEdit);
        mSynopsisEdit = (EditText) this.findViewById(R.id.et_activityUserCenter_synopsisEdit);
        mUserHeadLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_userHeadLayout);
        mUserHeadLayout.setOnClickListener(new ButtonClick());
        mChoiceSexLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_userSexLayout);
        mChoiceSexLayout.setOnClickListener(new ButtonClick());
        mUserHeadImage = (ImageView) this.findViewById(R.id.iv_activityUserCenter_userImage);

        mChoiceBirthLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_userBirthLayout);
        mChoiceBirthLayout.setOnClickListener(new ButtonClick());
//        mUserSexText = (TextView)this.findViewById(R.id.tv_activityUserCenter_userSexText);

        mUserBirthDayText = (TextView) this.findViewById(R.id.tv_activityUserCenter_userBirthDayText);

        mChoiceRegionLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_userRegionLayout);
        mChoiceRegionLayout.setOnClickListener(new ButtonClick());

        mChoiceRegionText = (TextView) this.findViewById(R.id.tv_activityUserCenter_userRegionText);
        nickName_open = (ImageView) this.findViewById(R.id.nickName_open);
        nickName_open.setOnClickListener(new ButtonClick());
        tv_activityUserCenter_userNikeNameText = (EditText) this.findViewById(R.id.tv_activityUserCenter_userNikeNameText);


    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_activityUserCenter_userHeadLayout:

                    String[] items = {"拍照", "从相册选择"};
                    Dialog dialog = new android.support.v7.app.AlertDialog.Builder(mContext)
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
                    break;
//                case R.id.li_activityUserCenter_userSexLayout:
//                    showSexChoiceDialog();
//                    break;
                case R.id.li_activityUserCenter_userBirthLayout:
                    showBirthDayChoiceDialog();
                    break;
                case R.id.li_activityUserCenter_userRegionLayout:
                    //选择区域
                    if (mApp.gRegionProvideList == null || mApp.gRegionProvideList.size() == 0) {
                        Toast.makeText(mContext, "区域数据为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //弹出对话框选择省份
                    mPicker = new ProvincePicker(mContext);
                    mPicker.setActivity(mActivity, 5);
                    mPicker.show();
                    break;
                case R.id.nickName_open:
                    if (R.mipmap.open == ((Integer) nickName_open.getTag()).intValue()) {
                        nickName_open.setImageResource(R.mipmap.close);
                        nickName_open.setTag(R.mipmap.close);
                    } else {
                        nickName_open.setImageResource(R.mipmap.open);
                        nickName_open.setTag(R.mipmap.open);
                    }
                    break;
                case R.id.tv_activityUserCenter_commit:
                    commit();
                    break;
                case R.id.iv_sex_man:
                    mProvideViewSysUserPatientInfoAndRegion.setGender(1);
                    iv_sex_man.setBackgroundResource(R.mipmap.sex_choiceno);
                    iv_sex_woman.setBackgroundResource(R.mipmap.sex_nochoice);
                    break;
                case R.id.iv_sex_woman:
                    mProvideViewSysUserPatientInfoAndRegion.setGender(2);
                    iv_sex_man.setBackgroundResource(R.mipmap.sex_nochoice);
                    iv_sex_woman.setBackgroundResource(R.mipmap.sex_choiceno);
                    break;
            }
        }
    }

    /**
     * 提交
     */
    private void commit() {
        mProvideViewSysUserPatientInfoAndRegion.setUserName(mUserNameText.getText().toString());
        mProvideViewSysUserPatientInfoAndRegion.setUserNameAlias(tv_activityUserCenter_userNikeNameText.getText().toString());
        mProvideViewSysUserPatientInfoAndRegion.setAddress(mSynopsisEdit.getText().toString());
        if (R.mipmap.open == ((Integer) nickName_open.getTag()).intValue()) {
            mProvideViewSysUserPatientInfoAndRegion.setFlagPatientStatus(1);
        } else {
            mProvideViewSysUserPatientInfoAndRegion.setFlagPatientStatus(0);
        }

        getProgressBar("请稍候。。。。", "正在提交");
        new Thread() {
            public void run() {
                boolean isupsuccess = false;
                try {
                    UpProvideViewSysUserPatientInfoAndRegion upbean = new UpProvideViewSysUserPatientInfoAndRegion();
                    upbean.setLoginPatientPosition(mApp.loginDoctorPosition);
                    upbean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    upbean.setRequestClientType("1");
                    upbean.setPatientId(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getPatientId()));
                    upbean.setUserName(mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    upbean.setUserNameAlias(mProvideViewSysUserPatientInfoAndRegion.getUserNameAlias());
                    upbean.setFlagUserNameAliasStatus(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus()));
                    upbean.setGender(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getGender()));
                    if (null != mProvideViewSysUserPatientInfoAndRegion.getBirthday()) {
                        upbean.setBirthdayStr(DateUtils.getStringTimeOfYMD(mProvideViewSysUserPatientInfoAndRegion.getBirthday().getTime()));
                    }
                    upbean.setCity(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getCity()));
                    upbean.setProvince(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getProvince()));
                    upbean.setAddress(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getAddress()));
                    upbean.setArea(StrUtils.defaultStr(mChoiceRegionID));
                    if (mUserHeadBitmap != null)
                        upbean.setBase64ImgData((URLEncoder.encode("data:image/jpg;base64," + BitmapUtil.bitmaptoString(mUserHeadBitmap))));
                    String str = new Gson().toJson(upbean);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "patientPersonalSetControlle/operUserPatientInfo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 1) {
                        isupsuccess = true;
                        mApp.mProvideViewSysUserPatientInfoAndRegion.setUserName(mProvideViewSysUserPatientInfoAndRegion.getUserName());
                        mApp.mProvideViewSysUserPatientInfoAndRegion.setUserNameAlias(mProvideViewSysUserPatientInfoAndRegion.getUserNameAlias());
                        upbean.setGender(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getGender()));
                        if (null != mProvideViewSysUserPatientInfoAndRegion.getBirthday()) {
                            mApp.mProvideViewSysUserPatientInfoAndRegion.setBirthday(mProvideViewSysUserPatientInfoAndRegion.getBirthday());
                        }
                        mApp.mProvideViewSysUserPatientInfoAndRegion.setCity(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getCity()));
                        mApp.mProvideViewSysUserPatientInfoAndRegion.setProvince(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getProvince()));
                        mApp.mProvideViewSysUserPatientInfoAndRegion.setAddress(StrUtils.defaultStr(mProvideViewSysUserPatientInfoAndRegion.getAddress()));
                        mApp.mProvideViewSysUserPatientInfoAndRegion.setArea(StrUtils.defaultStr(mChoiceRegionID));
                       /* NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取提交失败："+netRetEntity.getResMsg());*/
                        mNetRetStr = new Gson().toJson(netRetEntity);
                        //mHandler.sendEmptyMessage(3);
                        //return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(3);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                //重新登录，刷新用户信息
                if (isupsuccess) {
                    //String[] userids = new String[]{StrUtils.defaultStr(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientId())};
                    QueryUserCond quecond = new QueryUserCond();
                    quecond.setUserCodeList(mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    /*UserInfo userInfo = new UserInfo();
                    userInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
                    userInfo.setRequestClientType("1");
                    userInfo.setUserPhone(mApp.mLoginUserInfo.getUserPhone());
                    userInfo.setUserPwd(mApp.mLoginUserInfo.getUserPwd());*/
                    try {
                        mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(quecond), Constant.SERVICEURL + "patientDoctorCommonDataController/getUserInfoList");
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
                        mNetLoginRetStr = new Gson().toJson(retEntity);
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(3);

            }
        }.start();
    }

    /**
     * 选择二级科室
     */
    private void showChoiceHospitalDepartmentSView() {
        if (mProvideHospitalDepartmentSInfos != null) {
            mProvideHospitalDepartmentSNameInfos = new String[mProvideHospitalDepartmentSInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentSInfos.size(); i++) {
            mProvideHospitalDepartmentSNameInfos[i] = mProvideHospitalDepartmentSInfos.get(i).getDepartmentName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择二级科室");
        listDialog.setItems(mProvideHospitalDepartmentSNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                showChoiceHospitalDepartmentSText(which);
            }
        });
        listDialog.show();
    }


//    /**
//     * 显示科室名称以及获取数据
//     * @param index
//     */
//    private void showChoiceHospitalDepartmentSText(int index) {
//        mChocieDepartmentSecondText.setText(mProvideHospitalDepartmentSInfos.get(index).getDepartmentName());
//        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentSecondId(mProvideHospitalDepartmentSInfos.get(index).getHospitalDepartmentId()+"");
//    }


    /**
     * 选择一级科室
     */
    private void showChoiceHospitalDepartmentFView() {
        if (mProvideHospitalDepartmentFInfos != null) {
            mProvideHospitalDepartmentFNameInfos = new String[mProvideHospitalDepartmentFInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentFInfos.size(); i++) {
            mProvideHospitalDepartmentFNameInfos[i] = mProvideHospitalDepartmentFInfos.get(i).getDepartmentName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择一级科室");
        listDialog.setItems(mProvideHospitalDepartmentFNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalDepartmentText(which);
            }
        });
        listDialog.show();
    }

    /**
     * 显示科室名称以及获取数据
     *
     * @param index
     */
    private void showChoiceHospitalDepartmentText(int index) {

//        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentSecondId("");
//        mChocieDepartmentSecondText.setText("");
//        mChocieDepartmentSecondText.setHint("请选择二级科室");
//        mChoiceDepartmentText.setText(mProvideHospitalDepartmentFInfos.get(index).getDepartmentName());
//        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId()+"");
//        //获取科室信息
//        getProgressBar("请稍候。。。。","正在获取数据");
//        new Thread(){
//            public void run(){
//                try {
//                    //获取二级科室
//                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
//                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(mChoiceHospitalIndex).getHospitalInfoCode());
//                    provideHospitalDepartment.setHospitalDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId());
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
//                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
//                    if (netRetEntity.getResCode() == 0) {
//                        NetRetEntity retEntity = new NetRetEntity();
//                        retEntity.setResCode(0);
//                        retEntity.setResMsg("获取二级科室信息失败："+netRetEntity.getResMsg());
//                        mNetRetStr = new Gson().toJson(retEntity);
//                        mHandler.sendEmptyMessage(0);
//                        return;
//                    }
//                    //二级科室信息获取成功
//                    mProvideHospitalDepartmentSInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
//                } catch (Exception e) {
//                    NetRetEntity retEntity = new NetRetEntity();
//                    retEntity.setResCode(0);
//                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
//                    mNetRetStr = new Gson().toJson(retEntity);
//                    e.printStackTrace();
//                }
//                mHandler.sendEmptyMessage(0);
//            }
//        }.start();
    }


    /**
     * 选择医院对话框
     */
    private void showChoiceHospitalView() {
        if (mProvideHospitalInfos != null) {
            mProvideHospitalNameInfos = new String[mProvideHospitalInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalInfos.size(); i++) {
            mProvideHospitalNameInfos[i] = mProvideHospitalInfos.get(i).getHospitalName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择医院");
        listDialog.setItems(mProvideHospitalNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalText(which);
            }
        });
        listDialog.show();
    }


    /**
     * 显示医院并获取一级科室
     */
    private void showChoiceHospitalText(int index) {
//        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentId("");
//        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentSecondId("");
//        mChoiceDepartmentText.setText("");
//        mChoiceDepartmentText.setHint("请选择一级科室");
//        mChocieDepartmentSecondText.setText("");
//        mChocieDepartmentSecondText.setHint("请选择二级科室");
//        mChoiceHospitalText.setText(mProvideHospitalInfos.get(index).getHospitalName());
//        mProvideViewSysUserDoctorInfoAndHospital.setHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
//        mChoiceHospitalIndex = index;
//        //获取科室信息
//        getProgressBar("请稍候。。。。","正在获取数据");
//        new Thread(){
//            public void run(){
//                try {
//                    //获取一级科室
//                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
//                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
//                    provideHospitalDepartment.setHospitalDepartmentId(0);
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
//                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
//                    if (netRetEntity.getResCode() == 0) {
//                        NetRetEntity retEntity = new NetRetEntity();
//                        retEntity.setResCode(0);
//                        retEntity.setResMsg("获取一级科室信息失败："+netRetEntity.getResMsg());
//                        mNetRetStr = new Gson().toJson(retEntity);
//                        mHandler.sendEmptyMessage(0);
//                        return;
//                    }
//                    //一级科室信息获取成功
//                    mProvideHospitalDepartmentFInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
//                } catch (Exception e) {
//                    NetRetEntity retEntity = new NetRetEntity();
//                    retEntity.setResCode(0);
//                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
//                    mNetRetStr = new Gson().toJson(retEntity);
//                    e.printStackTrace();
//                }
//                mHandler.sendEmptyMessage(0);
//            }
//        }.start();
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
            mUserBirthDayText.setText(mYear + "-" + month + "-" + day);
            mProvideViewSysUserPatientInfoAndRegion.setBirthday(Util.strToDateLongV2(mYear + "-" + month + "-" + day));
            System.out.println();
        }
    };

    /**
     * 选择性别
     */
    private void showDoctorTitleDialog() {
        String[] doctorTitleName = new String[mDoctorTitleList.size()];// 医生职称
        for (int i = 0; i < mDoctorTitleList.size(); i++)
            doctorTitleName[i] = mDoctorTitleList.get(i).getAttrName();
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(doctorTitleName, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
//                mUserTitleName.setText(doctorTitleName[which]);
//                mProvideViewSysUserDoctorInfoAndHospital.setDoctorTitle(mDoctorTitleList.get(which).getAttrCode());
//                mProvideViewSysUserDoctorInfoAndHospital.setDoctorTitleName(mDoctorTitleList.get(which).getAttrName());
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }


    /**
     * 选择性别
     */
    private void showSexChoiceDialog() {
        String[] sexArry = new String[]{"女", "男"};// 性别选择
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
//                mUserSexText.setText(sexArry[which]);
                switch (which) {
                    case 0:
//                        mProvideViewSysUserDoctorInfoAndHospital.setGender(2);
                        break;
                    case 1:
//                        mProvideViewSysUserDoctorInfoAndHospital.setGender(1);
                        break;
                }
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
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
            mUserHeadBitmap = photo;

            //显示图片
//            mUserHeadImage.setImageBitmap(photo);
            Glide.with(this).load(photo).into(mUserHeadImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据
     */
    private void initDate() {
//        new Thread(){
//            public void run(){
//                try {
        //获取医院数据,判断区域级别
//                    if (mProvideViewSysUserDoctorInfoAndHospital.getArea() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getArea()))
//                    {
//                        mChoiceRegionLevel = 3;
//                        mChoiceRegionID = mProvideViewSysUserDoctorInfoAndHospital.getArea();
//                    }
//                    else if (mProvideViewSysUserDoctorInfoAndHospital.getCity() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getCity()))
//                    {
//                        mChoiceRegionLevel = 2;
//                        mChoiceRegionID = mProvideViewSysUserDoctorInfoAndHospital.getCity();
//                    }
//                    else if (mProvideViewSysUserDoctorInfoAndHospital.getProvince() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getProvince()))
//                    {
//                        mChoiceRegionLevel = 1;
//                        mChoiceRegionID = mProvideViewSysUserDoctorInfoAndHospital.getProvince();
//                    }
//                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
//                    provideBasicsRegion.setRegion_level(mChoiceRegionLevel);
//                    provideBasicsRegion.setRegion_id(mChoiceRegionID);
//                    String str = new Gson().toJson(provideBasicsRegion);
//                    //获取医院数据
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideBasicsRegion),Constant.SERVICEURL+"hospitalDataController/getHospitalInfo");
//                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
//                    if (netRetEntity.getResCode() == 0) {
//                        NetRetEntity retEntity = new NetRetEntity();
//                        retEntity.setResCode(0);
//                        retEntity.setResMsg("获取医院信息失败："+netRetEntity.getResMsg());
//                        mNetRetStr = new Gson().toJson(retEntity);
//                        mHandler.sendEmptyMessage(4);
//                        return;
//                    }
//                    //医院数据获取成功
//                    mProvideHospitalInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalInfo>>(){}.getType());
//
//                    //获取一级科室
//                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
//                    provideHospitalDepartment.setHospitalInfoCode(mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode());
//                    provideHospitalDepartment.setHospitalDepartmentId(0);
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
//                    netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
//                    if (netRetEntity.getResCode() == 0) {
//                        NetRetEntity retEntity = new NetRetEntity();
//                        retEntity.setResCode(0);
//                        retEntity.setResMsg("获取一级科室信息失败："+netRetEntity.getResMsg());
//                        mNetRetStr = new Gson().toJson(retEntity);
//                        mHandler.sendEmptyMessage(4);
//                        return;
//                    }
//                    //一级科室信息获取成功
//                    mProvideHospitalDepartmentFInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
//
//                    //获取二级科室
//                    provideHospitalDepartment = new ProvideHospitalDepartment();
//                    provideHospitalDepartment.setHospitalInfoCode(mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode());
//                    if (mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId()))
//                    {
//                        provideHospitalDepartment.setHospitalDepartmentId(Integer.parseInt(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId()));
//                        mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
//                        netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
//                        if (netRetEntity.getResCode() == 0) {
//                            NetRetEntity retEntity = new NetRetEntity();
//                            retEntity.setResCode(0);
//                            retEntity.setResMsg("获取二级科室信息失败："+netRetEntity.getResMsg());
//                            mNetRetStr = new Gson().toJson(retEntity);
//                            mHandler.sendEmptyMessage(4);
//                            return;
//                        }
//                        //二级科室信息获取成功
//                        mProvideHospitalDepartmentSInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
//                        System.out.println();
//                    }
//                } catch (Exception e) {
//                    NetRetEntity retEntity = new NetRetEntity();
//                    retEntity.setResCode(0);
//                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
//                    mNetRetStr = new Gson().toJson(retEntity);
//                    mHandler.sendEmptyMessage(4);
//                    e.printStackTrace();
//                }
//
//                mHandler.sendEmptyMessage(4);
//            }
//        }.start();

    }

    /**
     * 获取数据
     */
    private void getData() {
        getProgressBar("请稍候。。。。", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
                    provideViewSysUserPatientInfoAndRegion.setLoginPatientPosition(mApp.loginDoctorPosition);
                    provideViewSysUserPatientInfoAndRegion.setRequestClientType("1");
                    provideViewSysUserPatientInfoAndRegion.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideViewSysUserPatientInfoAndRegion.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    //实体转JSON字符串
                    String str = new Gson().toJson(provideViewSysUserPatientInfoAndRegion);
                    //获取用户数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "/patientPersonalSetControlle/getUserPatientInfo");
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


    //获取医生职称
    public void getBasicDate() {
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    provideBasicsDomain.setBaseCode(50003);

                    String str = new Gson().toJson(provideBasicsDomain);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    mDoctorTitleList = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsDomain.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(2);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(2);
                    return;
                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    /**
     * 设置所在地区显示
     */
    public void setRegionText() {
        if ("sqb".equals(mChoiceRegionMap.get("city").getRegion_id())) {
            mProvideViewSysUserPatientInfoAndRegion.setProvince(mChoiceRegionMap.get("provice").getRegion_id());
            mProvideViewSysUserPatientInfoAndRegion.setCity("");
            mProvideViewSysUserPatientInfoAndRegion.setArea("");
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name());
            mChoiceRegionLevel = 1;               //市级所有，则是省级
            mChoiceRegionID = mChoiceRegionMap.get("provice").getRegion_id();
        } else if (mChoiceRegionMap.get("dist") == null || "qqb".equals(mChoiceRegionMap.get("dist").getRegion_id())) {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name() + mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
            mProvideViewSysUserPatientInfoAndRegion.setProvince(mChoiceRegionMap.get("provice").getRegion_id());
            mProvideViewSysUserPatientInfoAndRegion.setCity(mChoiceRegionMap.get("city").getRegion_id());
            mProvideViewSysUserPatientInfoAndRegion.setArea("");
        }
        if (!"sqb".equals(mChoiceRegionMap.get("city").getRegion_id())) {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name() + mChoiceRegionMap.get("city").getRegion_name());
            mProvideViewSysUserPatientInfoAndRegion.setProvince(mChoiceRegionMap.get("provice").getRegion_id());
            mProvideViewSysUserPatientInfoAndRegion.setCity(mChoiceRegionMap.get("city").getRegion_id());
            mProvideViewSysUserPatientInfoAndRegion.setArea("");
            mChoiceRegionLevel = 2;               //市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
        }
        if (mChoiceRegionMap.get("dist") != null && !"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id())) {
            mProvideViewSysUserPatientInfoAndRegion.setProvince(mChoiceRegionMap.get("provice").getRegion_id());
            mProvideViewSysUserPatientInfoAndRegion.setCity(mChoiceRegionMap.get("city").getRegion_id());
            mProvideViewSysUserPatientInfoAndRegion.setArea(mChoiceRegionMap.get("dist").getRegion_id());
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name() + mChoiceRegionMap.get("city").getRegion_name() + mChoiceRegionMap.get("dist").getRegion_name());
            mChoiceRegionLevel = 3;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("dist").getRegion_id();
        }
    }


}
