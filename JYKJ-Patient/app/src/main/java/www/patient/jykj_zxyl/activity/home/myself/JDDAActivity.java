package www.patient.jykj_zxyl.activity.home.myself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entity.ProvideInteractClinicRecordWriteState;
import entity.ProvideInteractOrderInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_WZZLActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_ZDMSActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_ZHLYActivity;
import www.patient.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

/**
 * 建档档案
 */
public class JDDAActivity extends AppCompatActivity {

    private                 Context                 mContext;
    public                  ProgressDialog              mDialogProgress =null;
    private             String                              mNetRetStr;                 //返回字符串

    private JDDAActivity mActivity;
    private                 Handler                 mHandler;
    private                 JYKJApplication         mApp;

    private                 LinearLayout            mBack;

    private                 LinearLayout            li_jbjkxx;                //建档档案
    private                 LinearLayout            li_zzxx;                //症状信息
    private                 LinearLayout            li_bqjl;                //标签记录
    private                 LinearLayout            li_jwbs;                //既往病史



    private                 ProvideInteractClinicRecordWriteState mProvideInteractClinicRecordWriteState;
    private                 ProvideInteractOrderInfo            mProvideInteractOrderInfo;

    private void setLayoutDate() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdda);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        initHandler();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout)this.findViewById(R.id.iv_back_left);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        li_jbjkxx = (LinearLayout)this.findViewById(R.id.li_jbjkxx);
        li_jbjkxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,JDDAJBJKXXActivity.class));
            }
        });

        li_zzxx = (LinearLayout)this.findViewById(R.id.li_zzxx);
        li_zzxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,ZZXXActivity.class));
            }
        });

        li_bqjl = (LinearLayout)this.findViewById(R.id.li_bqjl);
        li_bqjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,BQJLActivity.class));
            }
        });

        li_jwbs = (LinearLayout)this.findViewById(R.id.li_jwbs);
        li_jwbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,JWBSActivity.class));
            }
        });
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {

                }
            }
        };
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
