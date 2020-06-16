package www.patient.jykj_zxyl.activity.home.twjz;

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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import entity.ProvideDoctorSetSchedulingInfoGroupDate;
import entity.mySelf.ProvideDoctorSetScheduling;
import entity.patientapp.Photo_Info;
import entity.patientapp.ProvideDoctorSetSchedulingPatient;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import entity.wdzs.ProvideBasicsImg;
import entity.wdzs.ProvideInteractPatientInterrogation;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.DoctorPBRQRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.DoctorPBTimeRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.WZXXOrderActivity;
import www.patient.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.DoctorPBRQRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.DoctorPBTimeRecycleAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;

/**
 * 问诊信息 == > 医生排班
 */
public class WZXX_YSPBActivity extends AppCompatActivity {

    private                 Context                 mContext;
    private WZXX_YSPBActivity mActivity;
    private                 Handler                 mHandler;
    private                 JYKJApplication         mApp;

    public                  ProgressDialog              mDialogProgress =null;
    private             String                              mNetRetStr;                 //返回字符串
    private             String                              mAddNetRetStr;                 //返回字符串


    private         RecyclerView mImageRecycleView;
    private         FullyGridLayoutManager mGridLayoutManager;
    private WZZXImageViewRecycleAdapter mAdapter;



    private                 RecyclerView                mPBRecycleView;             //排班列表
    private                 LinearLayoutManager                 layoutManager;
    private DoctorPBRQRecycleAdapter mMyPBRecycleAdapter;       //日期

    private DoctorPBTimeRecycleAdapter mMyPBTimeRecycleAdapter;       //时间段

    private                 List<ProvideDoctorSetSchedulingPatient>                mPbInfos = new ArrayList<>();



    private ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend;                          //专家信息
    private List<ProvideInteractPatientInterrogation> mProvideInteractPatientInterrogations;
    private List<ProvideBasicsImg> mProvideBasicsImg = new ArrayList<>();
    private LinearLayout mBack;

    private     String                      mOperaType;                                 //类型，1：图文就诊
    private         String                  mOrderNum;                                  //订单号
    private         ProvideDoctorSetScheduling provideDoctorSetScheduling = new ProvideDoctorSetScheduling();

    private                 ProvideDoctorSetScheduling      mProvideDoctorSetScheduling = new ProvideDoctorSetScheduling();
    private                 List<ProvideDoctorSetSchedulingInfoGroupDate> mProvideDoctorSetSchedulingInfoGroupDate = new ArrayList<>();

    private ImageViewRecycleAdapter mImageViewRecycleAdapter;
    private         List<Photo_Info>            mPhotoInfos = new ArrayList<>();

    private             File                    mTempFile;              //声明一个拍照结果的临时文件
    private             TextView                mCommit;                    //提交
    private             int                     mDateIndex = 0;             //选择的时间下标

    private             ProvideInteractPatientInterrogation mProvideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();               //提交的问诊资料

    private             RecyclerView            mTimeRecycleView;
    private             TextView                tv_hy;
    private             TextView                tv_jg;
    private             TextView                tv_qd;
    private void setLayoutDate() {




    }





    /**
     * 下一步
     */
    private void commit() {
//        mProvideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout)this.findViewById(R.id.iv_back_left);
//        mCommit = (TextView)this.findViewById(R.id.tv_commit);
//        mCommit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(mContext,WZXXOrderActivity.class).putExtra("provideInteractPatientInterrogation",mProvideInteractPatientInterrogation).putExtra("orderID",mOrderNum).putExtra("orderType",mOperaType));
//                commit();
//            }
//        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_hy = (TextView)this.findViewById(R.id.tv_hy);
        tv_jg  = (TextView)this.findViewById(R.id.tv_jg);
        tv_qd  = (TextView)this.findViewById(R.id.tv_qd);
        tv_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否选择了时间段
                boolean choice = false;
                int choiceIndex = 0;
                for (int i = 0; i < mProvideDoctorSetSchedulingInfoGroupDate.size(); i++)
                {
                    if (mProvideDoctorSetSchedulingInfoGroupDate.get(i).isChoice())
                    {
                        for (int j = 0; j < mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList().size(); j++)
                        {
                            if ( mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList().get(j).isChoice())
                            {
                                //时间段选择了，判断号源数
                                if (mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList().get(j).getWorkSourceNum() != -1 &&
                                        mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList().get(j).getWorkSourceNum() > 0)
                                {
                                    choiceIndex = i;
                                    choice = true;
                                }
                            }
                        }
                    }
                }
                if (choice)
                    startActivity(new Intent(mContext,WZXXActivity.class)
                            .putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommend)
                            .putExtra("operaType",mOperaType)
                            .putExtra("provideDoctorSetSchedulingInfoGroupDate",mProvideDoctorSetSchedulingInfoGroupDate.get(choiceIndex)));
                else
                    Toast.makeText(mContext,"请选择剩余号源的时间段",Toast.LENGTH_SHORT).show();

            }
        });
        switch (Integer.valueOf(mOperaType))
        {
            case 2:
                if (provideViewDoctorExpertRecommend.getPhonePriceShow() == null)
                    tv_jg.setText("金额：￥"+0);
                else
                    tv_jg.setText("金额：￥"+provideViewDoctorExpertRecommend.getPhonePriceShow());
                break;
            case 3:
                if (provideViewDoctorExpertRecommend.getVideoPriceShow() == null)
                    tv_jg.setText("金额：￥"+0);
                else
                    tv_jg.setText("金额：￥"+provideViewDoctorExpertRecommend.getVideoPriceShow());
                break;
            case 4:
                if (provideViewDoctorExpertRecommend.getAudioPriceShow() == null)
                    tv_jg.setText("金额：￥"+0);
                else
                    tv_jg.setText("金额：￥"+provideViewDoctorExpertRecommend.getAudioPriceShow());
                break;
        }

        mPBRecycleView = (RecyclerView) this.findViewById(R.id.rv_rq);
        mTimeRecycleView = (RecyclerView)this.findViewById(R.id.rv_sjd);

        //创建默认的线性LayoutManager
        mGridLayoutManager = new FullyGridLayoutManager(mContext,3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mPBRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mPBRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mMyPBRecycleAdapter = new DoctorPBRQRecycleAdapter(mProvideDoctorSetSchedulingInfoGroupDate,mContext);
        mPBRecycleView.setAdapter(mMyPBRecycleAdapter);

        //创建默认的线性LayoutManager
        mGridLayoutManager = new FullyGridLayoutManager(mContext,3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mTimeRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mTimeRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mMyPBTimeRecycleAdapter = new DoctorPBTimeRecycleAdapter(new ArrayList<>(),mContext);
        mTimeRecycleView.setAdapter(mMyPBTimeRecycleAdapter);
        mMyPBTimeRecycleAdapter.setOnItemClickListener(new DoctorPBTimeRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                for (int i = 0; i < mProvideDoctorSetSchedulingInfoGroupDate.get(mDateIndex).getGroupTimeList().size(); i++)
                {
                    mProvideDoctorSetSchedulingInfoGroupDate.get(mDateIndex).getGroupTimeList().get(i).setChoice(false);
                }
                mProvideDoctorSetSchedulingInfoGroupDate.get(mDateIndex).getGroupTimeList().get(position).setChoice(true);

                mMyPBTimeRecycleAdapter.setDate( mProvideDoctorSetSchedulingInfoGroupDate.get(mDateIndex).getGroupTimeList());
                mMyPBTimeRecycleAdapter.notifyDataSetChanged();
                tv_hy.setText(mProvideDoctorSetSchedulingInfoGroupDate.get(mDateIndex).getGroupTimeList().get(position).getWorkSourceNum()+"");
            }
        });
        mMyPBRecycleAdapter.setOnItemClickListener(new DoctorPBRQRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mDateIndex = position;
                for (int i = 0 ; i < mProvideDoctorSetSchedulingInfoGroupDate.size(); i++)
                {
                    mProvideDoctorSetSchedulingInfoGroupDate.get(i).setChoice(false);
                }
                mProvideDoctorSetSchedulingInfoGroupDate.get(position).setChoice(true);
                mMyPBRecycleAdapter.setDate(mProvideDoctorSetSchedulingInfoGroupDate);
                mMyPBRecycleAdapter.notifyDataSetChanged();
                if (mProvideDoctorSetSchedulingInfoGroupDate.get(position).getGroupTimeList().size() > 0)
                {
                    for ( int i = 0 ; i < mProvideDoctorSetSchedulingInfoGroupDate.get(position).getGroupTimeList().size(); i++)
                    {
                        mProvideDoctorSetSchedulingInfoGroupDate.get(position).getGroupTimeList().get(i).setChoice(false);
                    }
                    mProvideDoctorSetSchedulingInfoGroupDate.get(position).getGroupTimeList().get(0).setChoice(true);
                    tv_hy.setText(mProvideDoctorSetSchedulingInfoGroupDate.get(position).getGroupTimeList().get(0).getWorkSourceNum()+"");
                }

                mMyPBTimeRecycleAdapter.setDate(mProvideDoctorSetSchedulingInfoGroupDate.get(position).getGroupTimeList());
                mMyPBTimeRecycleAdapter.notifyDataSetChanged();

            }
        });

    }





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wzxx_yspb);
        mContext = this;
        mActivity = this;

        mApp = (JYKJApplication) getApplication();
        mApp.gPayCloseActivity.add(mActivity);
        provideViewDoctorExpertRecommend = (ProvideViewDoctorExpertRecommend) getIntent().getSerializableExtra("provideViewDoctorExpertRecommend");
        mOperaType = getIntent().getStringExtra("operaType");
        initLayout();
        initHandler();
        getDoctorPBInfo();
    }



    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:


                        break;
                    case 1:
                        break;
                    case 2:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1)
                        {
                            mProvideDoctorSetSchedulingInfoGroupDate = JSON.parseArray(netRetEntity.getResJsonData(),ProvideDoctorSetSchedulingInfoGroupDate.class);


                            //设置数据
                            setPBDate();
                        }
                        break;

                    case 10:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mAddNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        if (netRetEntity.getResCode() == 1)
                            startActivity(new Intent(mContext,WZXXOrderActivity.class).putExtra("provideInteractPatientInterrogation",mProvideInteractPatientInterrogation).putExtra("orderID",mOrderNum).putExtra("orderType",mOperaType));
                        break;
                }
            }
        };
    }

    /**
     * 获取医生排版信息
     */
    private void setPBDate() {

        for (int i = 0 ; i < mProvideDoctorSetSchedulingInfoGroupDate.size();i++)
        {
            if (mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList() == null)
                continue;
            for (int j = 0; j < mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList().size(); j++)
            {
                ProvideDoctorSetSchedulingPatient provideDoctorSetSchedulingPatient = new ProvideDoctorSetSchedulingPatient();
                provideDoctorSetSchedulingPatient.setChoice(false);
//                provideDoctorSetSchedulingPatient.setDate(7);
//                provideDoctorSetSchedulingPatient.setGradation(3);
                provideDoctorSetSchedulingPatient.setSourceNum(mProvideDoctorSetScheduling.getSundayNightSourceNum());
                provideDoctorSetSchedulingPatient.setText(mProvideDoctorSetSchedulingInfoGroupDate.get(i).getWorkDateName()
                        +mProvideDoctorSetSchedulingInfoGroupDate.get(i).getGroupTimeList().get(j).getDayTimeSlot());
                mPbInfos.add(provideDoctorSetSchedulingPatient);
            }
        }

        if (mProvideDoctorSetSchedulingInfoGroupDate != null && mProvideDoctorSetSchedulingInfoGroupDate.size() > 0)
        {
            mProvideDoctorSetSchedulingInfoGroupDate.get(0).setChoice(true);
            mMyPBTimeRecycleAdapter.setDate(mProvideDoctorSetSchedulingInfoGroupDate.get(0).getGroupTimeList());
            if (mProvideDoctorSetSchedulingInfoGroupDate.get(0).getGroupTimeList() != null && mProvideDoctorSetSchedulingInfoGroupDate.get(0).getGroupTimeList().size() > 0)
            {
                mProvideDoctorSetSchedulingInfoGroupDate.get(0).getGroupTimeList().get(0).setChoice(true);
                tv_hy.setText(mProvideDoctorSetSchedulingInfoGroupDate.get(0).getGroupTimeList().get(0).getWorkSourceNum()+"");
            }

            mMyPBRecycleAdapter.setDate(mProvideDoctorSetSchedulingInfoGroupDate);
            mMyPBRecycleAdapter.notifyDataSetChanged();
        }






    }

    /**
     * 获取医生排班信息
     */
    private void getDoctorPBInfo() {
        ProvideDoctorSetScheduling provideDoctorSetScheduling = new ProvideDoctorSetScheduling();
        provideDoctorSetScheduling.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideDoctorSetScheduling.setRequestClientType("1");
        provideDoctorSetScheduling.setSearchDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
        provideDoctorSetScheduling.setSearchDoctorName(provideViewDoctorExpertRecommend.getUserName());

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideDoctorSetScheduling);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"patientInteractDataControlle/getInteractPatientInterrogationResDoctorSchedulingInfo");
                    String string01 = Constant.SERVICEURL+"msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string+string01);
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
