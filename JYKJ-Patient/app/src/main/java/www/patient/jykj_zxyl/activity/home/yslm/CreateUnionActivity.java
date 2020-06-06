package www.patient.jykj_zxyl.activity.home.yslm;

import android.app.AlertDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.basicDate.GetUnionNameParment;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalDepartment;
import entity.basicDate.ProvideHospitalInfo;
import entity.basicDate.ProvideUnionDoctor;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.BitmapUtil;
import www.patient.jykj_zxyl.util.ProvincePicker;

public class CreateUnionActivity extends AppCompatActivity implements View.OnClickListener {

    private                 LinearLayout                ivBack;
    private                 LinearLayout                mChoiceRegionLayout;                          //选择区域
    private                 LinearLayout                mChoiceHospitalLayout;                          //选择医院
    private                 TextView                    mChoiceHospitalTextView;                        //显示选择的医院
    private                 TextView                    mUnionNameText;                                 //联盟名称
    private                 EditText                    mUnionSynopsis;                                             //联盟简介
    private                 EditText                    mUnionReason;                                             //联盟描述
    private                 TextView                    mCommit;                                                //提交

    private                 LinearLayout                mChoiceHospitalDepartmentFLayout;                          //选择一级科室
    private                 TextView                    mChoiceHospitalDepartmentFTextView;                        //显示选择的一级科室

    private                 LinearLayout                mChoiceHospitalDepartmentSLayout;                          //选择二级科室
    private                 TextView                    mChoiceHospitalDepartmentSTextView;                        //显示选择的二级科室

    private                 String                      mNetRetStr;                 //返回字符串
    private                 Handler                     mHandler;

    public                  ProgressDialog              mDialogProgress =null;

    private                 Context                     mContext;                                       //
    private                 CreateUnionActivity         mActivity;
    private                 JYKJApplication             mApp;
    private                 ProvincePicker              mPicker;                                            //省市县三级联动选择框
    public                  Map<String,ProvideBasicsRegion> mChoiceRegionMap = new HashMap<>();                  //用户选择的省市区map
    private                 TextView                    mChoiceRegionText;                                  //地区选择text
    private                 int                           mChoiceRegionLevel;                                       //选择的区域级别
    private                 String                      mChoiceRegionID;                                       //选择的区域ID
    private                 List<ProvideHospitalInfo>   mProvideHospitalInfos = new ArrayList<>();              //获取到的医院列表
    private                 String[]                    mProvideHospitalNameInfos;                              //医院对应的名称列表

    private                 List<ProvideHospitalDepartment>   mProvideHospitalDepartmentFInfos = new ArrayList<>();              //获取到的医院一级科室列表
    private                 String[]                        mProvideHospitalDepartmentFNameInfos;                              //医院一级科室对应的名称列表

    private                 List<ProvideHospitalDepartment>   mProvideHospitalDepartmentSInfos = new ArrayList<>();              //获取到的医院二级科室列表
    private                 String[]                    mProvideHospitalDepartmentSNameInfos;                              //医院二级科室对应的名称列表
    private                 ProvideUnionDoctor              mProvideUnionDoctor;
    private                 GetUnionNameParment             getUnionNameParment;            //获取医生联盟名称参数
    private                 int                             mChoiceHospitalIndex;           //选择的医院下标

    private                 ImageView                       mChoiceImage;                   //选择图片
    private                 File                            mTempFile;              //声明一个拍照结果的临时文件
    private                 Bitmap                          mUnionIcon;             //联盟图标



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_union);
        mContext = this;
        mActivity = this;
        mProvideUnionDoctor = new ProvideUnionDoctor();
        getUnionNameParment = new GetUnionNameParment();
        mApp = (JYKJApplication) getApplication();
        initView();
        initListener();
        initHandler();
        initDir();
    }

    /**
     * 创建临时文件夹 _tempphoto
     */
    private void initDir() {
        // 声明目录
        File tempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/_tempphoto");
        if(!tempDir.exists()){
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
                BitmapUtil.startPhotoZoom(mActivity,uri, 450);
            }

            // 处理拍照返回
            if (requestCode == Constant.SELECT_PIC_BY_TACK_PHOTO
                    && resultCode == RESULT_OK) {// 拍照成功 RESULT_OK= -1
                // 剪裁图片
                BitmapUtil.startPhotoZoom(mActivity,Uri.fromFile(mTempFile), 450);
            }
            // 接收剪裁回来的结果
            if (requestCode == Constant.REQUEST_PHOTO_CUT
                    && resultCode == RESULT_OK) {// 剪裁加工成功
                //让剪裁结果显示到图片框
                setPicToView(data);
            }
        }catch (Exception e)
        {
            Log.i("yi","yichahahaha");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPicToView(Intent data) {
        Bitmap photo;
        try {
            Uri u = data.getData();
            if (u != null)
            {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));//将imageUri对象的图片加载到内存
            }
            else
            {
                System.out.println("进来了");
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg"))));//将imageUri对象的图片加载到内存
            }
            System.out.println("图片："+photo);
            //显示图片
            mChoiceImage.setImageBitmap(photo);
            mUnionIcon = photo;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        if (retEntity.getResCode() == 0)
                            Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        //获取联盟名称成功
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        //展示联盟名称
                        mUnionNameText.setText(retEntity.getResMsg());
                        break;
                    case 3:
                        //创建成功
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        if (retEntity.getResCode() == 1)
                            finish();
                        break;
                }
            }
        };
    }



    private void initView(){
        ivBack = (LinearLayout) findViewById(R.id.ll_back);
        mChoiceRegionLayout = (LinearLayout)findViewById(R.id.li_activityCreateUnion_choiceRegion);
        mChoiceHospitalLayout = (LinearLayout)findViewById(R.id.li_activityCreateUnion_choiceHospitalLayout);
        mChoiceHospitalDepartmentFLayout = (LinearLayout)findViewById(R.id.li_activityCreateUnion_choiceHospitalDepartmentF);
        mChoiceHospitalDepartmentSLayout = (LinearLayout)findViewById(R.id.li_activityCreateUnion_choiceHospitalDepartmentS);


        mChoiceRegionText = (TextView)this.findViewById(R.id.tv_activityCreateUnion_choiceRegionText);
        mChoiceHospitalTextView = (TextView)this.findViewById(R.id.tv_activityCreateUnion_choiceHostpitalText);
        mChoiceHospitalDepartmentFTextView = (TextView)this.findViewById(R.id.tv_activityCreateUnion_choiceHospitalDepartmentF);
        mChoiceHospitalDepartmentSTextView = (TextView)this.findViewById(R.id.tv_activityCreateUnion_choiceHospitalDepartmentS);
        mUnionSynopsis = (EditText)this.findViewById(R.id.et_activityCreateUnion_unionNameSynopsis);
        mUnionReason = (EditText)this.findViewById(R.id.et_activityCreateUnion_unionNameReason);
        mCommit = (TextView)this.findViewById(R.id.et_activityCreateUnion_commit);
        mUnionNameText = (TextView)this.findViewById(R.id.tv_activityCreateUnion_uniionNameText);

        mChoiceImage = (ImageView)this.findViewById(R.id.iv_uploadImg);

    }

    private void initListener(){
        ivBack.setOnClickListener(this);
        mChoiceRegionLayout.setOnClickListener(this);
        mChoiceHospitalLayout.setOnClickListener(this);
        mChoiceHospitalDepartmentFLayout.setOnClickListener(this);
        mChoiceHospitalDepartmentSLayout.setOnClickListener(this);
        mCommit.setOnClickListener(this);
        mChoiceImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
            case R.id.li_activityCreateUnion_choiceRegion:
                if (mApp.gRegionProvideList == null || mApp.gRegionProvideList.size() == 0)
                {
                    Toast.makeText(mContext,"区域数据为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                //弹出对话框选择省份
                mPicker = new ProvincePicker(mContext);
                mPicker.setActivity(mActivity,1);
                mPicker.show();
                break;
            case R.id.li_activityCreateUnion_choiceHospitalLayout:
                if (mProvideUnionDoctor.getProvince() == null || "".equals(mProvideUnionDoctor.getProvince()))
                {
                    Toast.makeText(mContext,"请选择地区",Toast.LENGTH_SHORT).show();
                    return;
                }
                showChoiceHospitalView();
                break;
            case R.id.li_activityCreateUnion_choiceHospitalDepartmentF:
                if (mProvideUnionDoctor.getProvince() == null || "".equals(mProvideUnionDoctor.getProvince()))
                {
                    Toast.makeText(mContext,"请选择地区",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mProvideUnionDoctor.getHospitalId() == null || "".equals(mProvideUnionDoctor.getHospitalId()))
                {
                    Toast.makeText(mContext,"请选择医院",Toast.LENGTH_SHORT).show();
                    return;
                }
                showChoiceHospitalDepartmentFView();
                break;
            case R.id.li_activityCreateUnion_choiceHospitalDepartmentS:
                if (mProvideUnionDoctor.getProvince() == null || "".equals(mProvideUnionDoctor.getProvince()))
                {
                    Toast.makeText(mContext,"请选择地区",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mProvideUnionDoctor.getHospitalId() == null || "".equals(mProvideUnionDoctor.getHospitalId()))
                {
                    Toast.makeText(mContext,"请选择医院",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mProvideUnionDoctor.getDepartmentId() == null || "".equals(mProvideUnionDoctor.getDepartmentId()))
                {
                    Toast.makeText(mContext,"请选择一级科室",Toast.LENGTH_SHORT).show();
                    return;
                }
                showChoiceHospitalDepartmentSView();
                break;
            case R.id.et_activityCreateUnion_commit:
                commit();
                break;

            case R.id.iv_uploadImg:
                String[] items = {"拍照","从相册选择"};
                Dialog dialog=new android.support.v7.app.AlertDialog.Builder(mContext)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i)
                                {
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
        }
    }

    /**
     * 提交申请
     */
    private void commit() {
        mProvideUnionDoctor.setUnionName(mUnionNameText.getText().toString());
        mProvideUnionDoctor.setUnionSynopsis(mUnionSynopsis.getText().toString());
        mProvideUnionDoctor.setApplyReason(mUnionReason.getText().toString());
        mProvideUnionDoctor.setCountry("中国");

        mProvideUnionDoctor.setApplyDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        mProvideUnionDoctor.setApplyDoctorTitle(mApp.mProvideViewSysUserPatientInfoAndRegion.getAddress()+"");
        mProvideUnionDoctor.setAddress("");
        if (mProvideUnionDoctor.getProvince() == null || "".equals(mProvideUnionDoctor.getProvince()))
        {
            Toast.makeText(mContext,"请选择地区",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideUnionDoctor.getHospitalId() == null || "".equals(mProvideUnionDoctor.getHospitalId()))
        {
            Toast.makeText(mContext,"请选择医院",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideUnionDoctor.getDepartmentId() == null || "".equals(mProvideUnionDoctor.getDepartmentId()))
        {
            Toast.makeText(mContext,"请选择科室",Toast.LENGTH_SHORT).show();
            return;
        }

        if (mProvideUnionDoctor.getDepartmentSecondId() == null || "".equals(mProvideUnionDoctor.getDepartmentSecondId()))
        {
            Toast.makeText(mContext,"请选择二级科室",Toast.LENGTH_SHORT).show();
            return;
        }

        if (mProvideUnionDoctor.getApplyReason() == null || "".equals(mProvideUnionDoctor.getApplyReason()))
        {
            Toast.makeText(mContext,"请填写申请创建联盟描述",Toast.LENGTH_SHORT).show();
            return;
        }

        if (mProvideUnionDoctor.getUnionSynopsis() == null || "".equals(mProvideUnionDoctor.getUnionSynopsis()))
        {
            Toast.makeText(mContext,"请填写联盟简介",Toast.LENGTH_SHORT).show();
            return;
        }

        if (mUnionIcon != null)
            mProvideUnionDoctor.setBase64ImgData((URLEncoder.encode("data:image/jpg;base64,"+BitmapUtil.bitmaptoString(mUnionIcon))));


        //提交数据
        getProgressBar("请稍候。。。。","正在提交");
        new Thread(){
            public void run(){
                try {
                    String str = new Gson().toJson(mProvideUnionDoctor);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(mProvideUnionDoctor),Constant.SERVICEURL+"unionDoctorController/operCreateDoctorUnion");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("提交失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(1);
                    return;
                }
                //判断是否有图片
                mHandler.sendEmptyMessage(3);
            }
        }.start();
    }

    /**
     * 上传联盟图标
     */
    private void upLoadUnionIcon() {
        //获取区域内医院
        getProgressBar("请稍候。。。。","正在上传图片");
        new Thread(){
            public void run(){
                try {
                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
                    provideBasicsRegion.setRegion_level(mChoiceRegionLevel);
                    provideBasicsRegion.setRegion_id(mChoiceRegionID);
                    String str = new Gson().toJson(provideBasicsRegion);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideBasicsRegion),Constant.SERVICEURL+"unionDoctorController/operUploadDoctorUnionLogoImg");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取医院信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(3);
            }
        }.start();
    }

    /**
     * 设置所在地区显示
     */
    public void setRegionText() {
        if (mProvideUnionDoctor.getProvince() != null)
        {
            //将选择的医院，科室，名称清空
            if(!mProvideUnionDoctor.getProvince().equals(mChoiceRegionMap.get("provice").getRegion_id())
                    || !mProvideUnionDoctor.getProvince().equals(mChoiceRegionMap.get("city").getRegion_id())
                    || !mProvideUnionDoctor.getProvince().equals(mChoiceRegionMap.get("dist").getRegion_id()))
            {
                mProvideUnionDoctor.setHospitalId("");
                mProvideUnionDoctor.setDepartmentId("");
                mProvideUnionDoctor.setDepartmentSecondId("");
                mProvideUnionDoctor.setUnionName("");
                mChoiceHospitalTextView.setText("请选择医院");
                mChoiceHospitalDepartmentFTextView.setText("请选择一级科室");
                mChoiceHospitalDepartmentSTextView.setText("请选择二级科室");
                mUnionNameText.setText("联盟名称");
            }
        }



        mProvideUnionDoctor.setProvince(mChoiceRegionMap.get("provice").getRegion_id());
        getUnionNameParment.setProvince(mChoiceRegionMap.get("provice").getRegion_name());
        if ("sqb".equals(mChoiceRegionMap.get("city").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name());
            mChoiceRegionLevel = 1;               //市级所有，则是省级
            mChoiceRegionID = mChoiceRegionMap.get("provice").getRegion_id();
            mProvideUnionDoctor.setCity("");
            getUnionNameParment.setCity("");
            mProvideUnionDoctor.setArea("");
            getUnionNameParment.setArea("");
        }
        else if (mChoiceRegionMap.get("dist") == null ||"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
            mProvideUnionDoctor.setArea("");
            getUnionNameParment.setArea("");
        }
        if (!"sqb".equals(mChoiceRegionMap.get("city").getRegion_id()))
        {
            mProvideUnionDoctor.setCity(mChoiceRegionMap.get("city").getRegion_id());
            getUnionNameParment.setCity(mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
        }
        if (mChoiceRegionMap.get("dist") != null && !"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name()+mChoiceRegionMap.get("dist").getRegion_name());
            mProvideUnionDoctor.setArea(mChoiceRegionMap.get("dist").getRegion_id());
            getUnionNameParment.setArea(mChoiceRegionMap.get("dist").getRegion_name());
            mChoiceRegionLevel = 3;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("dist").getRegion_id();
        }

        //获取区域内医院
        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
                    provideBasicsRegion.setRegion_level(mChoiceRegionLevel);
                    provideBasicsRegion.setRegion_id(mChoiceRegionID);
                    String str = new Gson().toJson(provideBasicsRegion);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideBasicsRegion),Constant.SERVICEURL+"hospitalDataController/getHospitalInfo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取医院信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //医院数据获取成功
                    mProvideHospitalInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalInfo>>(){}.getType());

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
     * 选择二级科室
     */
    private void showChoiceHospitalDepartmentSView() {
        if (mProvideHospitalDepartmentSInfos != null)
        {
            mProvideHospitalDepartmentSNameInfos = new String[mProvideHospitalDepartmentSInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentSInfos.size(); i++)
        {
            mProvideHospitalDepartmentSNameInfos[i] = mProvideHospitalDepartmentSInfos.get(i).getDepartmentName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择二级科室");
        listDialog.setItems(mProvideHospitalDepartmentSNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalDepartmentSText(which);
            }
        });
        listDialog.show();
    }

    /**
     * 显示科室名称以及获取数据
     * @param index
     */
    private void showChoiceHospitalDepartmentSText(int index) {
        mChoiceHospitalDepartmentSTextView.setText(mProvideHospitalDepartmentSInfos.get(index).getDepartmentName());
        getUnionNameParment.setDepartmentSecondName(mProvideHospitalDepartmentSInfos.get(index).getDepartmentName());
        mProvideUnionDoctor.setDepartmentSecondId(mProvideHospitalDepartmentSInfos.get(index).getHospitalDepartmentId()+"");

        //获取联盟名称
        getProgressBar("请稍候。。。。","正在获取联盟名称");
        new Thread(){
            public void run(){
                try {
                    getUnionNameParment.setDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    //获取联盟名称
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(getUnionNameParment),Constant.SERVICEURL+"unionDoctorController/getUnionName");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("联盟名称获取失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
//                    mProvideHospitalDepartmentSInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(2);
            }
        }.start();

    }

    /**
     * 选择一级科室
     */
    private void showChoiceHospitalDepartmentFView() {
        if (mProvideHospitalDepartmentFInfos != null)
        {
            mProvideHospitalDepartmentFNameInfos = new String[mProvideHospitalDepartmentFInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentFInfos.size(); i++)
        {
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
     * @param index
     */
    private void showChoiceHospitalDepartmentText(int index) {

        mProvideUnionDoctor.setDepartmentSecondId("");
        mProvideUnionDoctor.setUnionName("");
        mChoiceHospitalDepartmentSTextView.setText("请选择二级科室");
        mUnionNameText.setText("联盟名称");

        mChoiceHospitalDepartmentFTextView.setText(mProvideHospitalDepartmentFInfos.get(index).getDepartmentName());
        getUnionNameParment.setDepartmentName(mProvideHospitalDepartmentFInfos.get(index).getDepartmentName());
        mProvideUnionDoctor.setDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId()+"");
        //获取科室信息
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    //获取二级科室
                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(mChoiceHospitalIndex).getHospitalInfoCode());
                    provideHospitalDepartment.setHospitalDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId());
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取二级科室信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //二级科室信息获取成功
                    mProvideHospitalDepartmentSInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
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
     * 选择医院对话框
     *
     */
    private void showChoiceHospitalView() {
        if (mProvideHospitalInfos != null)
        {
           mProvideHospitalNameInfos = new String[mProvideHospitalInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalInfos.size(); i++)
        {
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
        mProvideUnionDoctor.setDepartmentId("");
        mProvideUnionDoctor.setDepartmentSecondId("");
        mProvideUnionDoctor.setUnionName("");
        mChoiceHospitalDepartmentFTextView.setText("请选择一级科室");
        mChoiceHospitalDepartmentSTextView.setText("请选择二级科室");
        mUnionNameText.setText("联盟名称");
        mChoiceHospitalTextView.setText(mProvideHospitalInfos.get(index).getHospitalName());
        mProvideUnionDoctor.setHospitalId(mProvideHospitalInfos.get(index).getHospitalInfoCode());
        getUnionNameParment.setHospitalName(mProvideHospitalInfos.get(index).getHospitalName());
        mChoiceHospitalIndex = index;
        //获取科室信息
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                   //获取一级科室
                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
                    provideHospitalDepartment.setHospitalDepartmentId(0);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取一级科室信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //一级科室信息获取成功
                    mProvideHospitalDepartmentFInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
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
}
