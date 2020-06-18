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
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import entity.HZIfno;
import entity.mySelf.SubPatientHistInfo;
import entity.mySelf.conditions.QueryHistCond;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.jyzl.GRXX_GRZK_JWBSActivity;
import www.patient.jykj_zxyl.activity.home.jyzl.JWBSDetailActivity;
import www.patient.jykj_zxyl.activity.home.myself.JWBSActivity;
import www.patient.jykj_zxyl.activity.home.myself.JWBSLRBSActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.activity.ylzx.YLZXWebActivity;
import www.patient.jykj_zxyl.adapter.PatientJWBSAdapter;
import www.patient.jykj_zxyl.adapter.myself.JDDA_JWBS_BRTXAdapter;
import www.patient.jykj_zxyl.adapter.myself.MyOrderAlRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.IConstant;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.StrUtils;


/**
 * 建档档案 == 》 既往病史 ==》 本人填写
 * Created by admin on 2016/6/1.
 */
public class FragmentJWBS_BRTX extends Fragment{
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

    private JDDA_JWBS_BRTXAdapter mJDDA_JWBS_BRTXAdapter;

    private TextView    tv_xzbs;
    private int pageno = 1;
    private LoadDataTask loadDataTask = null;
    private int lastVisibleIndex = 0;
    private boolean mLoadDate = true;                    //是否可以加载数据

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_jdda_jwbs_brtx, container, false);
        mContext = getContext();
        mActivity = (JWBSActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        setData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mProvidePatientConditionDiseaseRecords = new ArrayList();
        mPageNum = 1;
        pageno = 1;
        setData();
    }

    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        /*LinearLayout mBack = (LinearLayout)view.findViewById(R.id.iv_back_left);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });*/
        tv_xzbs = (TextView)view.findViewById(R.id.tv_szbs);
        tv_xzbs.setOnClickListener(new ButtonClick());
        mRecycleView = (RecyclerView)view.findViewById(R.id.rv_activityPatientLaber_patientLaber);

        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDA_JWBS_BRTXAdapter = new JDDA_JWBS_BRTXAdapter(mProvidePatientConditionDiseaseRecords,mContext);
        mRecycleView.setAdapter(mJDDA_JWBS_BRTXAdapter);
        mJDDA_JWBS_BRTXAdapter.setOnItemClickListener(new JDDA_JWBS_BRTXAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,JWBSDetailActivity.class).putExtra("patientInfo",mProvidePatientConditionDiseaseRecords.get(position)));
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
                            mPageNum++;
                            setData();
                        }
                    }
                }
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

                case R.id.tv_szbs:
                    startActivity(new Intent(mContext,JWBSLRBSActivity.class));
                    break;

            }
        }
    }

   /* @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(null==loadDataTask){
            QueryHistCond quebean = new QueryHistCond();
            quebean.setPageNum(String.valueOf(pageno));
            quebean.setDataSourceType("1");
            quebean.setLoginPatientPosition(mApp.loginDoctorPosition);
            quebean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
            quebean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
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
    }*/
    /**
     * 设置数据
     */
    private void setData() {
        QueryHistCond quebean = new QueryHistCond();
        quebean.setPageNum(String.valueOf(mPageNum));
        quebean.setDataSourceType("1");
        quebean.setLoginPatientPosition(mApp.loginDoctorPosition);
        quebean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        quebean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        quebean.setRequestClientType("1");
        quebean.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
        loadDataTask = new LoadDataTask(quebean);
        loadDataTask.execute();
    }

    class LoadDataTask extends AsyncTask<Void,Void,List<ProvidePatientConditionDiseaseRecord>>{
        private QueryHistCond queryCond;
        LoadDataTask(QueryHistCond queryCond){
            this.queryCond = queryCond;
        }
        @Override
        protected List<ProvidePatientConditionDiseaseRecord> doInBackground(Void... voids) {
            mLoadDate = false;
            List<ProvidePatientConditionDiseaseRecord> retlist = new ArrayList();
            try {
                queryCond.setPageNum(String.valueOf(mPageNum));
                String querypara = new Gson().toJson(queryCond);
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+querypara, Constant.SERVICEURL+ INetAddress.QUERY_PASTHIST_URL);
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
                mJDDA_JWBS_BRTXAdapter.setDate(mProvidePatientConditionDiseaseRecords);
                mJDDA_JWBS_BRTXAdapter.notifyDataSetChanged();
            }else{
                if(mPageNum>1) {
                    mPageNum = mPageNum - 1;
                }
            }
            mLoadDate = true;
        }
    }
}
