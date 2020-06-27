package www.patient.jykj_zxyl.activity.myself;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.tencent.cos.common.Const;
import entity.mySelf.MyOrderProcess;
import entity.mySelf.SubPatientImg;
import entity.mySelf.SubZhlyImgInfo;
import entity.mySelf.SubZwlyAllInfo;
import entity.patientapp.Photo_Info;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class LeaveMessageActivity extends AppCompatActivity {
    private LeaveMessageActivity mActivity;
    private Context mContext;
    private JYKJApplication mApp;
    MyOrderProcess parabean;
    private RecyclerView mImageRecycleView;
    private FullyGridLayoutManager mGridLayoutManager;
    private ImageViewRecycleAdapter mImageViewRecycleAdapter;
    private List<Photo_Info> mPhotoInfos = new ArrayList<>();
    private TextView sub_leavemsg;
    private EditText tv_lemsg_content;
    private File mTempFile;              //声明一个拍照结果的临时文件
    private SubDataTask subDataTask;
    private SubImgTask subImgTask;
    private String mImageCode;
    private int opeimgcount = 0;
    private int hasimgcount = 0;
    public                  ProgressDialog              mDialogProgress =null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = LeaveMessageActivity.this;
        mContext = getApplicationContext();
        mApp = (JYKJApplication)getApplication();
        initDir();
        parabean = (MyOrderProcess)getIntent().getSerializableExtra("orderInfo");
        setContentView(R.layout.activity_leavmsg_after_threat);
        sub_leavemsg = findViewById(R.id.sub_leavemsg);
        tv_lemsg_content = findViewById(R.id.tv_lemsg_content);
        mImageRecycleView = findViewById(R.id.rv_img);
        findViewById(R.id.back).setOnClickListener(new ButtonClick());
        sub_leavemsg.setOnClickListener(new ButtonClick());
        mGridLayoutManager = new FullyGridLayoutManager(mContext,3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mImageRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mImageRecycleView.setHasFixedSize(true);
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

    class   ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
                case R.id.sub_leavemsg:
                    subData();
                    break;
            }
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

    void subData(){
        String msgcontent = StrUtils.defaultStr(tv_lemsg_content.getText());
        if(msgcontent.length()==0){
            Toast.makeText(mContext,"请输入留言内容",Toast.LENGTH_SHORT).show();
            return;
        }
        getProgressBar("数据提交","正在提交，请稍后...");
        mImageCode = MyId.createUUID();
        SubZwlyAllInfo subbean = new SubZwlyAllInfo();
        subbean.setImgCode(mImageCode);
        subbean.setLoginPatientPosition(mApp.loginDoctorPosition);
        subbean.setMessageContent(msgcontent);
        subbean.setMessageId("0");
        subbean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        subbean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        subbean.setOrderCode(parabean.getOrderCode());
        subbean.setPatientLinkPhone(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserPhone());
        subbean.setRequestClientType("1");
        subbean.setTreatmentType(StrUtils.defaultStr(parabean.getTreatmentType()));
        subDataTask = new SubDataTask(subbean);
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
                SubZhlyImgInfo subimg = new SubZhlyImgInfo();
                subimg.setLoginPatientPosition(mApp.loginDoctorPosition);
                subimg.setImgBase64Data(URLEncoder.encode("data:image/jpg;base64,"+parphoto.getPhoto()));
                subimg.setRequestClientType("1");
                subimg.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                subimg.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                subimg.setImgCode(mImageCode);
                subimg.setOrderCode(parabean.getOrderCode());
                subImgTask = new SubImgTask(subimg);
                subImgTask.execute();
            }
        }
    }

    class SubDataTask extends AsyncTask<Void,Void,Boolean>{
        SubZwlyAllInfo subinfo;
        String errmsg = "";
        SubDataTask(SubZwlyAllInfo subinfo){
            this.subinfo = subinfo;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(subinfo), Constant.SERVICEURL+ INetAddress.SUB_ZHLY_CHARACTER);
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode()){
                    return true;
                }else {
                    errmsg = retEntity.getResMsg();
                }
            } catch (Exception e) {
                e.printStackTrace();
                errmsg = "提交异常";
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(!aBoolean){
                Toast.makeText(mContext,errmsg,Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SubImgTask extends AsyncTask<Void,Void,Boolean>{
        SubZhlyImgInfo imgInfo;
        String errmsg = "";
        SubImgTask(SubZhlyImgInfo imgInfo){
            this.imgInfo = imgInfo;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(imgInfo),Constant.SERVICEURL+INetAddress.SUB_ZHLY_IMG);
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode()){
                    return true;
                }else{
                    errmsg = retEntity.getResMsg();
                }
            }catch (Exception ex){
                ex.printStackTrace();
                errmsg = "提交异常";
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            opeimgcount = opeimgcount+1;
            if(opeimgcount>=hasimgcount){
                if(!aBoolean){
                    Toast.makeText(mContext,errmsg,Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }
}