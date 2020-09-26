package www.patient.jykj_zxyl.activity.home.myself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.HZIfno;
import entity.basicDate.ProvideBasicsRegion;
import entity.mySelf.conditions.QueryContactCondPage;
import entity.patientInfo.ProvidePatientConditionHealthy;
import entity.mySelf.ProvidePatientLabel;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.ylzx.YLZXWebActivity;
import www.patient.jykj_zxyl.adapter.ChoiceMedicationDateAdapter;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.NewPatientLaberAdapter;
import www.patient.jykj_zxyl.adapter.myself.MyOrderAlRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.*;

/**
 * 个人中心==》建档档案==》==>标签记录
 */
public class BQJLActivity extends AppCompatActivity{


    private         Context                 mContext;
    private BQJLActivity mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;
    private JYZL_GRZLRecycleAdapter mJYZL_GRZLRecycleAdapter;      //适配器
    private         List<HZIfno>            mHZEntyties = new ArrayList<>();            //所有数据
    private         LinearLayout            mJBXX;                                  //基本信息
    private         String                  mPatientCode;                       //患者code

    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串

    private NewPatientLaberAdapter mPatientLaberAdapter;
    private List<ProvidePatientLabel> mProvidePatientLabel = new ArrayList<>();

    private             LinearLayout        li_back;

    private LoadDataTask loadDataTask;
    private int pageno = 1;
    private int lastVisibleIndex = 0;
    private boolean mLoadDate = true;

    /**
     * 初始化布局
     */
    private void initLayout() {
       /* ProvidePatientLabel providePatientLabel = new ProvidePatientLabel();
        providePatientLabel.setUserLabelSecondName("轻度高血压");
        providePatientLabel.setCreateDate(Util.strToDateLongV("2020-03-04 12:22:11"));
        mProvidePatientLabel.add(providePatientLabel);

        ProvidePatientLabel providePatientLabel1 = new ProvidePatientLabel();
        providePatientLabel1.setUserLabelSecondName("中度高血压");
        providePatientLabel1.setCreateDate(Util.strToDateLongV("1111-03-04 12:22:11"));
        mProvidePatientLabel.add(providePatientLabel1);*/


        li_back = (LinearLayout)this.findViewById(R.id.li_back);
        li_back.setOnClickListener(new ButtonClick());
        mRecycleView = (RecyclerView) this.findViewById(R.id.rv);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter

        mPatientLaberAdapter = new NewPatientLaberAdapter(mProvidePatientLabel,mContext);
        mRecycleView.setAdapter(mPatientLaberAdapter);
        mPatientLaberAdapter.setOnItemClickListener(new NewPatientLaberAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onLongClick(int position) {

            }
        });
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                            pageno++;
                            getDate();
                        }
                    }
                }
            }
        });
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_back:
                    finish();
                    break;

            }
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdda_bqjl);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        initHandler();
        getDate();
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

   /* @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(null==loadDataTask){
            QueryContactCondPage querybean = new QueryContactCondPage();
            querybean.setPageNum(String.valueOf(pageno));
            querybean.setLoginPatientPosition(mApp.loginDoctorPosition);
            querybean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
            querybean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
            querybean.setRequestClientType("1");
            querybean.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
            loadDataTask = new LoadDataTask(querybean);
            loadDataTask.execute();
        }else{
            pageno = pageno + 1;
            loadDataTask.execute();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
    }*/


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
        //getProgressBar("请稍候。。。。", "正在加载数据");
        /*new Thread() {
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
        }.start();*/
        //if(null==loadDataTask){
        QueryContactCondPage quebean = new QueryContactCondPage();
        quebean.setPageNum(String.valueOf(pageno));
        quebean.setLoginPatientPosition(mApp.loginDoctorPosition);
        quebean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        quebean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        quebean.setRequestClientType("1");
        quebean.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
        loadDataTask = new LoadDataTask(quebean);
        loadDataTask.execute();
        //}
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


    class LoadDataTask extends AsyncTask<Void,Void,List<ProvidePatientLabel>>{
        QueryContactCondPage querybean;
        LoadDataTask(QueryContactCondPage querybean){
            this.querybean = querybean;
        }
        @Override
        protected List<ProvidePatientLabel> doInBackground(Void... voids) {
            mLoadDate = false;
            List<ProvidePatientLabel> retlist = new ArrayList();
            try {
                querybean.setPageNum(String.valueOf(pageno));
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(querybean),Constant.SERVICEURL+ INetAddress.QUERY_PATIENTLABEL_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr,NetRetEntity.class);
                Log.e("TAG", "标签信息: "+retnetstr );
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),ProvidePatientLabel.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return retlist;
        }

        @Override
        protected void onPostExecute(List<ProvidePatientLabel> providePatientLabels) {
            if(providePatientLabels.size()>0){
                mProvidePatientLabel.addAll(providePatientLabels);
                mPatientLaberAdapter.setDate(mProvidePatientLabel);
                mPatientLaberAdapter.notifyDataSetChanged();
            }else{
                if(pageno>1){
                    pageno = pageno - 1;
                }
            }
            mLoadDate = true;
            //cacerProgress();
        }
    }

}
