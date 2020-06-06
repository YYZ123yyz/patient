package www.patient.jykj_zxyl.activity.home.patient;

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

import java.util.ArrayList;
import java.util.List;

import entity.ProvideInteractOrderInfo;
import entity.ProvidePatientBindingMyDoctorInfo;
import entity.wdzs.ProvideInteractClinicRecordWriteState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_JZJLActivity;
import www.patient.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.patient.jykj_zxyl.adapter.JYZLecycleAdapter;
import www.patient.jykj_zxyl.adapter.TWDYS_JZLBRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

/**
 * 我的医生 ==》就诊记录列表
 */
public class WDYS_JZJLListActivity extends AppCompatActivity {

    private                 Context                 mContext;
    public                  ProgressDialog              mDialogProgress =null;
    private             String                              mNetRetStr;                 //返回字符串

    private WDYS_JZJLListActivity mActivity;
    private                 Handler                 mHandler;
    private                 JYKJApplication         mApp;

    private                 RecyclerView            mRecycleView;

    private LinearLayoutManager layoutManager;
    private TWDYS_JZLBRecycleAdapter mTWDYS_JZLBRecycleAdapter;       //适配器


    private ProvidePatientBindingMyDoctorInfo    mProvidePatientBindingMyDoctorInfo;
    private                 LinearLayout            mBack;


    private                 ProvideInteractClinicRecordWriteState mProvideInteractClinicRecordWriteState;

    private List<ProvideInteractOrderInfo>          mProvideInteractOrderInfos = new ArrayList<>();

    private                 int                         mNumPage = 1;                  //页数（默认，1）
    private                 int                         mRowNum = 10;                  //每页加载10条

    private             boolean                             loadDate;



    private void setLayoutDate() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jzjllist);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvidePatientBindingMyDoctorInfo = (ProvidePatientBindingMyDoctorInfo) getIntent().getSerializableExtra("providePatientBindingMyDoctorInfo");
        initLayout();
        initHandler();
        getData();
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


        mRecycleView = (RecyclerView) this.findViewById(R.id.rv_jzjcList);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mTWDYS_JZLBRecycleAdapter = new TWDYS_JZLBRecycleAdapter(mProvideInteractOrderInfos,mContext);
        mRecycleView.setAdapter(mTWDYS_JZLBRecycleAdapter);

        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                        if (loadDate)
                        {
                            mNumPage ++;
                            getData();
                        }

                    }
                }
            }
        });

        mTWDYS_JZLBRecycleAdapter.setOnItemClickListener(new TWDYS_JZLBRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,WDYS_JZJLActivity.class).putExtra("provideInteractOrderInfo",mProvideInteractOrderInfos.get(position)));
            }

            @Override
            public void onLongClick(int position) {

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
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            loadDate = false;
                        }

                        else
                        {
                            List<ProvideInteractOrderInfo> list = JSON.parseArray(netRetEntity.getResJsonData(),ProvideInteractOrderInfo.class);
                            if (list == null || list.size() == 0)
                                loadDate = false;
                            else
                            {
                                if (list.size() < mRowNum)
                                    loadDate = false;
                                mProvideInteractOrderInfos.addAll(list);
                            }
                           mTWDYS_JZLBRecycleAdapter.setDate(mProvideInteractOrderInfos);
                            mTWDYS_JZLBRecycleAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        };
    }



    /**
     * 设置数据
     */
    private void getData() {
        getProgressBar("请稍候","正在获取数据。。。");
        ProvideInteractOrderInfo provideInteractOrderInfo = new ProvideInteractOrderInfo();
        provideInteractOrderInfo.setRowNum(mRowNum+"");
        provideInteractOrderInfo.setPageNum(mNumPage+"");
        provideInteractOrderInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideInteractOrderInfo.setRequestClientType("1");
        provideInteractOrderInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractOrderInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideInteractOrderInfo.setSearchDoctorCode(mProvidePatientBindingMyDoctorInfo.getDoctorCode());
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideInteractOrderInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorNotSigningResTreatmentList");
                    String string01 = Constant.SERVICEURL+"msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string+string01);
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
