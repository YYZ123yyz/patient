package www.patient.jykj_zxyl.fragment.myself.jwbs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import entity.mySelf.conditions.QueryHistCond;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.jyzl.JWBSDetailActivity;
import www.patient.jykj_zxyl.activity.home.myself.JWBSActivity;
import www.patient.jykj_zxyl.activity.home.myself.JWBSBSXQActivity;
import www.patient.jykj_zxyl.adapter.myself.JDDA_JWBS_BRTXAdapter;
import www.patient.jykj_zxyl.adapter.myself.JDDA_JWBS_YSTXAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.IConstant;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.StrUtils;


/**
 * 建档档案 == 》 既往病史 ==》 本人填写
 * Created by admin on 2016/6/1.
 */
public class FragmentJWBS_YSTX extends Fragment implements AbsListView.OnScrollListener {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private JWBSActivity mActivity;
    private             JYKJApplication                     mApp;

    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;

    private         String                  mPatientCode;
    private         int                     mRowNum = 10;                            //每页行数
    private         int                     mPageNum = 1;                           //页数
    private          int                    recordTypeId = 0;                           //搜索的记录类型
    private TextView mMore;

    private         LinearLayout            mQB;                                //选择全部
    private         LinearLayout            mJWBS;                                //选择既往病史
    private         LinearLayout            mBCJL;                                //选择病程记录
    private RelativeLayout mBack;
    private         List<ProvidePatientConditionDiseaseRecord> mProvidePatientConditionDiseaseRecords = new ArrayList<>();

    private JDDA_JWBS_YSTXAdapter mJDDA_JWBS_YSTXAdapter;
    private int pageno = 1;
    private LoadDataTask loadDataTask = null;
    private int lastVisibleIndex = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_jdda_jwbs_ystx, container, false);
        mContext = getContext();
        mActivity = (JWBSActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        setData();
        return v;
    }


    /**
     * 初始化界面
     */
    private void initLayout(View view) {

        mRecycleView = (RecyclerView)view.findViewById(R.id.rv_activityPatientLaber_patientLaber);

        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDA_JWBS_YSTXAdapter = new JDDA_JWBS_YSTXAdapter(mProvidePatientConditionDiseaseRecords,mContext);
        mRecycleView.setAdapter(mJDDA_JWBS_YSTXAdapter);
        mJDDA_JWBS_YSTXAdapter.setOnItemClickListener(new JDDA_JWBS_YSTXAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,JWBSBSXQActivity.class));
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
                switch (msg.what)
                {
                    case 1:
                      break;
                }
            }
        };
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }
    /**
      * 设置数据
     */
    private void setData() {
        if(null==loadDataTask){
            QueryHistCond quebean = new QueryHistCond();
            quebean.setPageNum(String.valueOf(pageno));
            quebean.setDataSourceType("1");
            quebean.setLoginPatientPosition(mApp.loginDoctorPosition);
            quebean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getOperPatientCode());
            quebean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getOperPatientName());
            quebean.setRequestClientType("1");
            quebean.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
            loadDataTask = new LoadDataTask(quebean);
            loadDataTask.execute();
        }else{
            loadDataTask.execute();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(null==loadDataTask){
            QueryHistCond quebean = new QueryHistCond();
            quebean.setPageNum(String.valueOf(pageno));
            quebean.setDataSourceType("1");
            quebean.setLoginPatientPosition(mApp.loginDoctorPosition);
            quebean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getOperPatientCode());
            quebean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getOperPatientName());
            quebean.setRequestClientType("1");
            quebean.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
            loadDataTask = new LoadDataTask(quebean);
            loadDataTask.execute();
        }else{
            pageno = pageno + 1;
            loadDataTask.execute();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
    }


    class LoadDataTask extends AsyncTask<Void,Void,List<ProvidePatientConditionDiseaseRecord>> {
        private QueryHistCond queryCond;
        LoadDataTask(QueryHistCond queryCond){
            this.queryCond = queryCond;
        }
        @Override
        protected List<ProvidePatientConditionDiseaseRecord> doInBackground(Void... voids) {
            List<ProvidePatientConditionDiseaseRecord> retlist = new ArrayList();
            try {
                queryCond.setPageNum(String.valueOf(pageno));
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryCond), Constant.SERVICEURL+ INetAddress.QUERY_PASTHIST_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),ProvidePatientConditionDiseaseRecord.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retlist;
        }

        @Override
        protected void onPostExecute(List<ProvidePatientConditionDiseaseRecord> providePatientConditionDiseaseRecords) {
            if(providePatientConditionDiseaseRecords.size()>0){
                mProvidePatientConditionDiseaseRecords.addAll(providePatientConditionDiseaseRecords);
                mJDDA_JWBS_YSTXAdapter.setDate(mProvidePatientConditionDiseaseRecords);
                mJDDA_JWBS_YSTXAdapter.notifyDataSetChanged();
            }else{
                if(pageno>1) {
                    pageno = pageno - 1;
                }
            }
        }
    }

}
