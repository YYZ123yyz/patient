package www.patient.jykj_zxyl.activity.home.myself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.Result;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.HZIfno;
import entity.basicDate.ProvideBasicsDomain;
import entity.patientInfo.ProvidePatientLabel;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.PatientLaberAdapter;
import www.patient.jykj_zxyl.adapter.myself.JDDAQBZZRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.Util;
import zxing.android.CaptureActivity;
import zxing.decode.DecodeImgCallback;
import zxing.decode.DecodeImgThread;
import zxing.decode.ImageUtil;

/**
 * 个人中心==》建档档案==》==>症状信息
 */
public class ZZXXActivity extends AppCompatActivity {


    private         Context                 mContext;
    private ZZXXActivity mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            mQBZZRecycleView;           //起病症状
    private         RecyclerView            mMQZZRecycleView;          //目前症状
    private         RecyclerView            mBFZRecycleView;          //并发症
    private         RecyclerView            mHBJBRecycleView;          //合并疾病
    private         RecyclerView            mMQZLFARecycleView;          //目前治疗方案


    private         JDDAQBZZRecycleAdapter mJDDAQBZZRecycleAdapter;      //起病症状适配器
    private         JDDAQBZZRecycleAdapter mJDDAMQZZRecycleAdapter;      //目前症状症状适配器
    private         JDDAQBZZRecycleAdapter mJDDABFZRecycleAdapter;      //并发症适配器
    private         JDDAQBZZRecycleAdapter mJDDAHBJBRecycleAdapter;      //合并疾病适配器
    private         JDDAQBZZRecycleAdapter mJDDAMQZLFARecycleAdapter;      //目前治疗方案适配器

    private         List<ProvideBasicsDomain>            mQBZZList = new ArrayList<>();            //起病症状
    private         List<ProvideBasicsDomain>            mMQZZList = new ArrayList<>();            //目前症状
    private         List<ProvideBasicsDomain>            mBFZList = new ArrayList<>();            //并发症
    private         List<ProvideBasicsDomain>            mHBJBList = new ArrayList<>();            //合并疾病
    private         List<ProvideBasicsDomain>            mMQZLFAList = new ArrayList<>();            //目前治疗方案

    private         LinearLayout            mJBXX;                                  //基本信息
    private         String                  mPatientCode;                       //患者code

    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串

    private         LinearLayout            li_qbzz;
    private         LinearLayout            li_mqzz;
    private         LinearLayout            li_bfz;
    private         LinearLayout            li_hbjb;
    private         LinearLayout            li_mqzlfa;
    private         LinearLayout    li_back;


    /**
     * 初始化布局
     */
    private void initLayout() {
        li_back = (LinearLayout)this.findViewById(R.id.li_back);
        li_back.setOnClickListener(new ButtonClick());
        //起病症状
        mQBZZRecycleView = (RecyclerView) this.findViewById(R.id.rvqbzz);
        //目前症状
        mMQZZRecycleView = (RecyclerView) this.findViewById(R.id.rv_mqzz);
        //并发症
        mBFZRecycleView = (RecyclerView) this.findViewById(R.id.rv_bfz);
        //合并疾病
        mHBJBRecycleView = (RecyclerView) this.findViewById(R.id.rv_hbjb);
        //目前治疗方案
        mMQZLFARecycleView = (RecyclerView) this.findViewById(R.id.rv_mqzlfa);

        //起病症状
        FullyGridLayoutManager mGridLayoutManager = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mQBZZRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mQBZZRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDAQBZZRecycleAdapter = new JDDAQBZZRecycleAdapter(mQBZZList,mContext,mActivity);
        mQBZZRecycleView.setAdapter(mJDDAQBZZRecycleAdapter);

        //目前症状
        FullyGridLayoutManager mGridLayoutManagermqzz = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManagermqzz.setOrientation(LinearLayout.VERTICAL);
        mMQZZRecycleView.setLayoutManager(mGridLayoutManagermqzz);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mMQZZRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDAMQZZRecycleAdapter = new JDDAQBZZRecycleAdapter(mMQZZList,mContext,mActivity);
        mMQZZRecycleView.setAdapter(mJDDAMQZZRecycleAdapter);

        //并发症
        FullyGridLayoutManager mGridLayoutManagerBBZ = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManagerBBZ.setOrientation(LinearLayout.VERTICAL);
        mBFZRecycleView.setLayoutManager(mGridLayoutManagerBBZ);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mBFZRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDABFZRecycleAdapter = new JDDAQBZZRecycleAdapter(mBFZList,mContext,mActivity);
        mBFZRecycleView.setAdapter(mJDDABFZRecycleAdapter);

        //合并疾病
        FullyGridLayoutManager mGridLayoutManagerHBJB = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManagerHBJB.setOrientation(LinearLayout.VERTICAL);
        mHBJBRecycleView.setLayoutManager(mGridLayoutManagerHBJB);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mHBJBRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDAHBJBRecycleAdapter = new JDDAQBZZRecycleAdapter(mHBJBList,mContext,mActivity);
        mHBJBRecycleView.setAdapter(mJDDAHBJBRecycleAdapter);

        //目前治疗方案
        FullyGridLayoutManager mGridLayoutManagerMQZLFA = new FullyGridLayoutManager(mContext,4);
        mGridLayoutManagerMQZLFA.setOrientation(LinearLayout.VERTICAL);
        mMQZLFARecycleView.setLayoutManager(mGridLayoutManagerMQZLFA);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mMQZLFARecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDAMQZLFARecycleAdapter = new JDDAQBZZRecycleAdapter(mMQZLFAList,mContext,mActivity);
        mMQZLFARecycleView.setAdapter(mJDDAMQZLFARecycleAdapter);


        li_qbzz = (LinearLayout)this.findViewById(R.id.li_qbzz);
        li_mqzz = (LinearLayout)this.findViewById(R.id.li_mqzz);
        li_bfz = (LinearLayout)this.findViewById(R.id.li_bfz);
        li_hbjb = (LinearLayout)this.findViewById(R.id.li_hbjb);
        li_mqzlfa = (LinearLayout)this.findViewById(R.id.li_mqzlfa);

        li_qbzz.setOnClickListener(new ButtonClick());
        li_mqzz.setOnClickListener(new ButtonClick());
        li_bfz.setOnClickListener(new ButtonClick());
        li_hbjb.setOnClickListener(new ButtonClick());
        li_mqzlfa.setOnClickListener(new ButtonClick());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            mQBZZList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
            if (mQBZZList != null)
            {
                mJDDAQBZZRecycleAdapter.setDate(mQBZZList);
                mJDDAQBZZRecycleAdapter.notifyDataSetChanged();
            }

        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            if (mMQZZList != null) {
                mMQZZList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
                mJDDAMQZZRecycleAdapter.setDate(mMQZZList);
                mJDDAMQZZRecycleAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            if (mBFZList != null) {
                mBFZList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
                mJDDABFZRecycleAdapter.setDate(mBFZList);
                mJDDABFZRecycleAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 4 && resultCode == RESULT_OK) {
            if (mHBJBList != null) {
                mHBJBList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
                mJDDAHBJBRecycleAdapter.setDate(mHBJBList);
                mJDDAHBJBRecycleAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 5 && resultCode == RESULT_OK) {
            if (mMQZLFAList != null) {
                mMQZLFAList = (ArrayList<ProvideBasicsDomain>) data.getSerializableExtra("zzxx");
                mJDDAMQZLFARecycleAdapter.setDate(mMQZLFAList);
                mJDDAMQZLFARecycleAdapter.notifyDataSetChanged();
            }
        }
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_back:
                    finish();
                    break;
                case R.id.li_qbzz:

                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",1)
                            .putExtra("zzxx",(Serializable) mQBZZList),
                            1);
                    break;
                case R.id.li_mqzz:
                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",2)
                            .putExtra("zzxx",(Serializable) mMQZZList),2);
                    break;
                case R.id.li_bfz:
                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",3)
                            .putExtra("zzxx",(Serializable) mBFZList),3);
                    break;
                case R.id.li_hbjb:
                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",4)
                            .putExtra("zzxx",(Serializable) mHBJBList),4);
                    break;
                case R.id.li_mqzlfa:
                    startActivityForResult(new Intent(mContext,ZZXXChoiceActivity.class)
                            .putExtra("type",5)
                            .putExtra("zzxx",(Serializable) mMQZLFAList),5);
                    break;

            }
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdda_zzxx);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        initHandler();

    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        break;
                    case 1:
                        cacerProgress();
                        showViewDate();
                        break;
                    case 2:
                        break;
                }
            }
        };
    }



    /**
     * 设置显示
     */
    private void showViewDate() {


    }

    private String getDate(Date data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String string = simpleDateFormat.format(data);
        return string;
    }

    /**
     * 获取基本信息
     */
    private void getDate() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
                    provideViewSysUserPatientInfoAndRegion.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewSysUserPatientInfoAndRegion.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideViewSysUserPatientInfoAndRegion.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    provideViewSysUserPatientInfoAndRegion.setSearchPatientCode(mPatientCode);

                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideViewSysUserPatientInfoAndRegion), Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResHealthy");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取区域信息失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
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
