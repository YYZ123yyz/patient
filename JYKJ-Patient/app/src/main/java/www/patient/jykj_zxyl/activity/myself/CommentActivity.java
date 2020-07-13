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
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import entity.mySelf.*;
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

public class CommentActivity extends AppCompatActivity {
    private CommentActivity mActivity;
    private Context mContext;
    private JYKJApplication mApp;
    //private CommentInfo mComment;
    private MyOrderProcess myorder;
    private TextView comm_order_code;
    private TextView comm_treat_type;
    private TextView comm_treat_date;
    private EditText comm_content;
    private ToggleButton good_comment;
    private ToggleButton middle_comment;
    private ToggleButton weak_comment;
    /*private RecyclerView mImageRecycleView;
    private FullyGridLayoutManager mGridLayoutManager;
    private ImageViewRecycleAdapter mImageViewRecycleAdapter;
    private List<Photo_Info> mPhotoInfos = new ArrayList<>();*/
    private TextView pub_comment;
    /*private File mTempFile;              //声明一个拍照结果的临时文件
    private int opeimgcount = 0;
    private int hasimgcount = 0;*/
    public ProgressDialog mDialogProgress =null;
    boolean goodcheck = false;
    boolean middlecheck = false;
    boolean weakcheck = false;
    /*private String mImageCode;*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = CommentActivity.this;
        //mComment = (CommentInfo)getIntent().getSerializableExtra("commentinfo");
        myorder = (MyOrderProcess)getIntent().getSerializableExtra("orderinfo");
        mContext = getApplicationContext();
        mApp = (JYKJApplication)getApplication();
        setContentView(R.layout.activity_opinion);

        comm_order_code = findViewById(R.id.comm_order_code);
        comm_treat_type = findViewById(R.id.comm_treat_type);
        comm_treat_date = findViewById(R.id.comm_treat_date);
        comm_content = findViewById(R.id.comm_content);
        good_comment = findViewById(R.id.good_comment);
        middle_comment = findViewById(R.id.middle_comment);
        weak_comment = findViewById(R.id.weak_comment);
        //mImageRecycleView = findViewById(R.id.recycleView);
        pub_comment = findViewById(R.id.pub_comment);
        findViewById(R.id.back).setOnClickListener(new ButtonClick());
        pub_comment.setOnClickListener(new ButtonClick());
//        good_comment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    goodcheck = true;
//                    middlecheck = false;
//                    weakcheck = false;
//                    weak_comment.setChecked(false);
//                    middle_comment.setChecked(false);
//                }else{
//                    goodcheck = false;
//                }
//            }
//        });
//
//        middle_comment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    goodcheck = false;
//                    middlecheck = true;
//                    weakcheck = false;
//                    weak_comment.setChecked(false);
//                    good_comment.setChecked(false);
//                }else{
//                    middlecheck = false;
//                }
//            }
//        });
//
//        weak_comment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    goodcheck = false;
//                    middlecheck = false;
//                    weakcheck = true;
//                    middle_comment.setChecked(false);
//                    good_comment.setChecked(false);
//                }else{
//                    weakcheck = false;
//                }
//            }
//        });
    }



    void subData(){
        String msgcontent = StrUtils.defaultStr(comm_content.getText());
        if(msgcontent.length()==0){
            Toast.makeText(mContext,"请输入评价内容",Toast.LENGTH_SHORT).show();
            return;
        }
        getProgressBar("数据提交","正在提交，请稍后...");
        SubCommentInfo subbean = new SubCommentInfo();
        subbean.setCommentContent(StrUtils.defaultStr(comm_content.getText()));
        subbean.setCommentId("0");
        if(good_comment.isChecked()){
            subbean.setCommentType("1");
        }else  if(middle_comment.isChecked()){
            subbean.setCommentType("2");
        }else  if(weak_comment.isChecked()){
            subbean.setCommentType("3");
        }
        subbean.setDoctorCode(myorder.getOrderCode());
        subbean.setLoginPatientPosition(mApp.loginDoctorPosition);
        subbean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        subbean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getOperPatientName());
        subbean.setOrderCode(myorder.getOrderCode());
        subbean.setRequestClientType("1");
        subbean.setTreatmentType(StrUtils.defaultStr(myorder.getTreatmentType()));
        SubDataTask subDataTask = new SubDataTask(subbean);
        subDataTask.execute();
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

    class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
                case R.id.pub_comment:
                    break;
            }
        }
    }

    class SubDataTask extends AsyncTask<Void,Void,Boolean> {
        SubCommentInfo subinfo;
        String errmsg = "";
        SubDataTask(SubCommentInfo subinfo){
            this.subinfo = subinfo;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(subinfo), Constant.SERVICEURL+ INetAddress.SUB_COMMENT);
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
            cacerProgress();
        }
    }

}
