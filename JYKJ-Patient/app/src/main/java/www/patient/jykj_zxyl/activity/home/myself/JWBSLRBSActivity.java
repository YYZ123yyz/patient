package www.patient.jykj_zxyl.activity.home.myself;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import entity.HZIfno;
import entity.mySelf.PatientRecordImg;
import entity.mySelf.SubPatientHistInfo;
import entity.mySelf.SubPatientImg;
import entity.mySelf.conditions.QueryPatientHist;
import entity.mySelf.conditions.QueryRecordImgCond;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import entity.patientapp.Photo_Info;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.patient.jykj_zxyl.activity.myself.FYSZActivity;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.PatientJWBSAdapter;
import www.patient.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.*;

/**
 * 既往病史 ==》 录入病史
 */
public class JWBSLRBSActivity extends AppCompatActivity {


    private         Context                 mContext;
    private         JWBSLRBSActivity        mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;
    private JYZL_GRZLRecycleAdapter mJYZL_GRZLRecycleAdapter;      //适配器
    private         List<HZIfno>            mHZEntyties = new ArrayList<>();            //所有数据
    private         LinearLayout            mJBXX;                                  //基本信息
    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串
    private         String                  mPatientCode;
    private         int                     mRowNum = 10;                            //每页行数
    private         int                     mPageNum = 1;                           //页数
    private          int                    recordTypeId = 0;                           //搜索的记录类型
    private         TextView                mMore;

    private         LinearLayout            mQB;                                //选择全部
    private         LinearLayout            mJWBS;                                //选择既往病史
    private         LinearLayout            mBCJL;                                //选择病程记录
    private RelativeLayout                  mBack;
    private         ProvidePatientConditionDiseaseRecord mProvidePatientConditionDiseaseRecords = new ProvidePatientConditionDiseaseRecord();

    private PatientJWBSAdapter mPatientJWBSAdapter;

    private PopupWindow mPopuWindow;

    private         TextView                mBCBM;                      //病程别名
    private         TextView                mJZRQ;                      //就诊日期
    private         TextView                mBCFL;                      //病程分类
    private         TextView                mBQZS;                      //病情自述

    private         RecyclerView mImageRecycleView;
    private         FullyGridLayoutManager mGridLayoutManager;
    private ImageViewRecycleAdapter mImageViewRecycleAdapter;
    private         List<Photo_Info>            mPhotoInfos = new ArrayList<>();

    private File mTempFile;              //声明一个拍照结果的临时文件
    private             TextView                mCommit;                    //提交

    private         TextView            tv_activityHZZL_userYW;

    private TimePickerView startTime;
    private String choiceDate;
    private String mRecordId;
    private String mImgcode;



    private EditText tv_activityHZZL_userSG;
    private EditText tv_activityHZZL_region;
    private LoadDataTask loadDataTask = null;
    private LoadImgsTask loadImgsTask = null;
    private SubDataTask subDataTask = null;
    private SubImgTask subImgTask = null;
    private TextView tv_szbs;
    private int opeimgcount = 0;
    private int hasimgcount = 0;

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
            Photo_Info photo_info = new Photo_Info();
            photo_info.setPhoto(BitmapUtil.bitmaptoString(photo));
            mPhotoInfos.add(photo_info);
            mImageViewRecycleAdapter.setDate(mPhotoInfos);
            mImageViewRecycleAdapter.notifyDataSetChanged();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jwbs_lrbs);
        mProvidePatientConditionDiseaseRecords = (ProvidePatientConditionDiseaseRecord) getIntent().getSerializableExtra("patientInfo");
        mActivity = this;
        mContext = this;
        mApp = (JYKJApplication) getApplication();
        initDir();
        initLayout();
        initData();
    }

    void initData(){
        Intent parintent = getIntent();
        mRecordId =  StrUtils.defaultStr(parintent.getStringExtra("recordId"));
        mImgcode = StrUtils.defaultStr(parintent.getStringExtra("imgCode"));
        if(mImgcode.length()==0){
            mImgcode = MyId.createUUID();
        }
        QueryPatientHist quehistcon = new QueryPatientHist();
        quehistcon.setLoginPatientPosition(mApp.loginDoctorPosition);
        quehistcon.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        quehistcon.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        quehistcon.setRecordId(mRecordId);
        quehistcon.setRequestClientType("1");
        QueryRecordImgCond queimgcond = new QueryRecordImgCond();
        queimgcond.setImgCode(mImgcode);
        queimgcond.setLoginPatientPosition(mApp.loginDoctorPosition);
        queimgcond.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        queimgcond.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        queimgcond.setRequestClientType("1");
        loadDataTask = new LoadDataTask(quehistcon);
        loadDataTask.execute();
        loadImgsTask = new LoadImgsTask(queimgcond);
        loadImgsTask.execute();
    }

    private void initLayout()  {
        mBack = (RelativeLayout)this.findViewById(R.id.back);
        mBack.setOnClickListener(new ButtonClick());

        tv_activityHZZL_userYW = (TextView)this.findViewById(R.id.tv_activityHZZL_userYW);
        tv_activityHZZL_userYW.setOnClickListener(new ButtonClick());

        mImageRecycleView = (RecyclerView)this.findViewById(R.id.rv_img);
        //创建默认的线性LayoutManager
        mGridLayoutManager = new FullyGridLayoutManager(mContext,3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mImageRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mImageRecycleView.setHasFixedSize(true);
        tv_activityHZZL_userSG = findViewById(R.id.tv_activityHZZL_userSG);
        tv_activityHZZL_region = findViewById(R.id.tv_activityHZZL_region);
        tv_szbs = findViewById(R.id.tv_szbs);
        tv_szbs.setOnClickListener(new ButtonClick());
        //创建并设置Adapter
        final Photo_Info photo_info = new Photo_Info();
        photo_info.setPhotoID("ADDPHOTO");
        mPhotoInfos.add(photo_info);
        mImageViewRecycleAdapter = new ImageViewRecycleAdapter(mPhotoInfos,mApp);
        mImageRecycleView.setAdapter(mImageViewRecycleAdapter);
        //点击
        mImageViewRecycleAdapter.setOnItemClickListener(new ImageViewRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(final int position) {
                if (mPhotoInfos.size() >= 6)
                {
                    Toast.makeText(mContext,"照片不超过五张",Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("ADDPHOTO".equals(mPhotoInfos.get(position).getPhotoID()))
                {
                    String[] items = {"拍照","从相册选择"};
                    Dialog dialog=new AlertDialog.Builder(mContext)
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
                }
                else
                {
                    Dialog dialog=new AlertDialog.Builder(mContext)
                            .setMessage("删除该照片")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mPhotoInfos.remove(position);
                                    if (mPhotoInfos.size() == 0)
                                    {
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

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.tv_activityHZZL_userYW:
                    startTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            choiceDate = getDate(date);
                            tv_activityHZZL_userYW.setText(choiceDate);
                        }
                    }).build();
                    startTime.show();
                    break;
                case R.id.tv_szbs:
                    savedata();
                    break;
            }
        }
    }

    void savedata(){
        SubPatientHistInfo subinfo = new SubPatientHistInfo();
        subinfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        subinfo.setRequestClientType("1");
        subinfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        subinfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        if(null!=mRecordId && mRecordId.length()>0){
            subinfo.setRecordId(mRecordId);
        }else{
            subinfo.setRecordId("0");
        }
        if(mPhotoInfos.size()>0){
            subinfo.setImgCode(mImgcode);
        }else{
            subinfo.setImgCode("");
        }
        subinfo.setRecordName(StrUtils.defaultStr(tv_activityHZZL_userSG.getText()));
        subinfo.setTreatmentDate(StrUtils.defaultStr(tv_activityHZZL_userYW.getText()));
        subinfo.setRecordContent(StrUtils.defaultStr(tv_activityHZZL_region.getText()));
        subDataTask = new SubDataTask(subinfo);
        subDataTask.execute();
        for(int j=0;j<mPhotoInfos.size();j++){
            Photo_Info parphoto = mPhotoInfos.get(j);
            if(null!=parphoto.getPhoto()){
                hasimgcount =  hasimgcount + 1;
            }
        }
        for(int j=0;j<mPhotoInfos.size();j++){
            Photo_Info parphoto = mPhotoInfos.get(j);
            if(null!=parphoto.getPhoto()){
                SubPatientImg subimg = new SubPatientImg();
                subimg.setLoginPatientPosition(mApp.loginDoctorPosition);
                subimg.setImgBase64Data(URLEncoder.encode("data:image/jpg;base64,"+parphoto.getPhoto()));
                subimg.setRequestClientType("1");
                subimg.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                subimg.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                subimg.setImgCode(mImgcode);
                subImgTask = new SubImgTask(subimg);
                subImgTask.execute();
            }
        }
    }

    private String getDate(Date data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String string = simpleDateFormat.format(data);
        return string;
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

    class SubImgTask extends AsyncTask<Void,Void,Boolean>{
        SubPatientImg subimg;
        String repmsg = "";
        SubImgTask(SubPatientImg subimg){
            this.subimg = subimg;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String subjson = new Gson().toJson(subimg);
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+subjson,Constant.SERVICEURL+INetAddress.SUB_PATIENTHISTIMG_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr,NetRetEntity.class);
                if(1==retEntity.getResCode()){
                    repmsg = "上传成功";
                    return true;
                }else{
                    repmsg = retEntity.getResMsg();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                repmsg = "上传失败";
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){

            }else{
                Toast.makeText(mContext,repmsg,Toast.LENGTH_SHORT).show();
            }
            opeimgcount = opeimgcount+1;
            if(opeimgcount>=hasimgcount){
                finish();
            }
        }
    }

    class LoadDataTask extends AsyncTask<Void,Void,ProvidePatientConditionDiseaseRecord>{
        QueryPatientHist queryCond;
        LoadDataTask(QueryPatientHist queryCond){
            this.queryCond = queryCond;
        }
        @Override
        protected ProvidePatientConditionDiseaseRecord doInBackground(Void... voids) {
            ProvidePatientConditionDiseaseRecord retbean = null;
            try{
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryCond),Constant.SERVICEURL+ INetAddress.QUERY_PERSON_PASTHIST_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retbean = JSON.parseObject(retEntity.getResJsonData(),ProvidePatientConditionDiseaseRecord.class);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return retbean;
        }

        @Override
        protected void onPostExecute(ProvidePatientConditionDiseaseRecord providePatientConditionDiseaseRecord) {
            if(null!=providePatientConditionDiseaseRecord){
                tv_activityHZZL_userSG.setText(providePatientConditionDiseaseRecord.getRecordName());
                tv_activityHZZL_userYW.setText(DateUtils.fomrDateSeflFormat(providePatientConditionDiseaseRecord.getTreatmentDate(),"yyyy-MM-dd"));
                tv_activityHZZL_region.setText(providePatientConditionDiseaseRecord.getRecordContent());
            }
        }
    }

    class LoadImgsTask extends AsyncTask<Void,Void, List<PatientRecordImg>>{
        QueryRecordImgCond queryCond;
        LoadImgsTask(QueryRecordImgCond queryCond){
            this.queryCond = queryCond;
        }
        @Override
        protected List<PatientRecordImg> doInBackground(Void... voids) {
            List<PatientRecordImg> retlist = new ArrayList();
            try {
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryCond),Constant.SERVICEURL+INetAddress.QUERY_PATIENTRECIMG_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),PatientRecordImg.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return retlist;
        }

        @Override
        protected void onPostExecute(List<PatientRecordImg> patientRecordImgs) {
            //mPhotoInfos.clear();
            for(int i=0;i<patientRecordImgs.size();i++){
                PatientRecordImg theimg = patientRecordImgs.get(i);
                Photo_Info parphont =  new Photo_Info();
                parphont.setItemID(theimg.getBasicsImgId());
                parphont.setPhotoUrl(theimg.getImgUrl());
                mPhotoInfos.add(parphont);
            }
            if(patientRecordImgs.size()>0){
                mImageViewRecycleAdapter.setDate(mPhotoInfos);
                mImageViewRecycleAdapter.notifyDataSetChanged();
            }

        }
    }


    class SubDataTask extends AsyncTask<Void,Void,Boolean> {
        String submsg = "";
        SubPatientHistInfo subinfo;
        SubDataTask(SubPatientHistInfo subinfo){
            this.subinfo = subinfo;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(subinfo),Constant.SERVICEURL+ INetAddress.MAINTAIN_PATIENTHIST_URL);
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
                submsg = "保存失败";
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(mContext,submsg,Toast.LENGTH_SHORT).show();
            if(aBoolean){
                finish();
            }
        }
    }
}
