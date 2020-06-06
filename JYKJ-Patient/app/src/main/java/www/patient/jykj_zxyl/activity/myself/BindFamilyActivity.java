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

import java.io.File;
import java.io.FileNotFoundException;
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
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.BitmapUtil;
import www.patient.jykj_zxyl.util.ProvincePicker;
import www.patient.jykj_zxyl.util.Util;

/**
 * 个人中心 ==>绑定亲友
 */
public class BindFamilyActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private BindFamilyActivity mActivity;
    public                  ProgressDialog              mDialogProgress =null;

    private                 JYKJApplication             mApp;

    private                 String                      mNetRetStr;                 //返回字符串



    private                 Handler                     mHandler;

    private RelativeLayout          ri_back;



//    private                 ProvideViewSysUserDoctorInfoAndHospital  mProvideViewSysUserDoctorInfoAndHospital = new ProvideViewSysUserDoctorInfoAndHospital();  //医生信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_bindfamily);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();

    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        break;
                    case 1:
                        cacerProgress();
                        setLayoutDate();
                        break;
                    case 3:

                    case 4:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        break;
                }
            }
        };
    }



    /**
     * 设置布局显示
     */
    private void setLayoutDate() {

    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        ri_back = (RelativeLayout)this.findViewById(R.id.ri_back);
        ri_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tv_activityUserCenter_commit:
//                    commit();
                    break;
            }
        }
    }



    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }




}
