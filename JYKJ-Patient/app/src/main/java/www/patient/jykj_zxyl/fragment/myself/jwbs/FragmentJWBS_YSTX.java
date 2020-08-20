package www.patient.jykj_zxyl.fragment.myself.jwbs;

import android.annotation.SuppressLint;
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
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import entity.mySelf.JwbsYstxInfo;
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
public class FragmentJWBS_YSTX extends Fragment {
    private Context mContext;
    private Handler mHandler;
    private JWBSActivity mActivity;
    private JYKJApplication mApp;

    private RecyclerView mRecycleView;

    private LinearLayoutManager layoutManager;

    private String mPatientCode;
    private int mRowNum = 10;                            //每页行数
    private int mPageNum = 1;                           //页数
    private int recordTypeId = 0;                           //搜索的记录类型
    private TextView mMore;

    private LinearLayout mQB;                                //选择全部
    private LinearLayout mJWBS;                                //选择既往病史
    private LinearLayout mBCJL;                                //选择病程记录
    private RelativeLayout mBack;
    private List<JwbsYstxInfo> mProvidePatientConditionDiseaseRecords = new ArrayList<>();

    private JDDA_JWBS_YSTXAdapter mJDDA_JWBS_YSTXAdapter;
    private int pageno = 1;
    private LoadDataTask loadDataTask = null;
    private int lastVisibleIndex = 0;
    private boolean mLoadDate = true;
    private RefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_jdda_jwbs_ystx, container, false);
        mContext = getContext();
        mActivity = (JWBSActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        //setData();
        return v;
    }


    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        refreshLayout =view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(this.getContext())));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this.getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                pageno=1;
                setData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                if(mProvidePatientConditionDiseaseRecords.size()<mRowNum){
                    refreshlayout.finishLoadMore();
                }else{
                    pageno++;
                    setData();
                }
            }
        });

        mRecycleView = (RecyclerView) view.findViewById(R.id.rv_activityPatientLaber_patientLaber);

        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDA_JWBS_YSTXAdapter = new JDDA_JWBS_YSTXAdapter(mProvidePatientConditionDiseaseRecords, mContext);
        mRecycleView.setAdapter(mJDDA_JWBS_YSTXAdapter);
        mJDDA_JWBS_YSTXAdapter.setOnItemClickListener(new JDDA_JWBS_YSTXAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext, JWBSBSXQActivity.class).putExtra("jwbsYstxInfo", mProvidePatientConditionDiseaseRecords.get(position)));
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
//                    if (mLoadDate) {
//                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
//                        if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
//                            pageno++;
//                            setData();
//                        }
//                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mProvidePatientConditionDiseaseRecords = new ArrayList();
        mPageNum = 1;
        pageno = 1;
        setData();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        break;
                }
            }
        };
    }


    class ButtonClick implements View.OnClickListener {
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
        //if(null==loadDataTask){
        QueryHistCond quebean = new QueryHistCond();
        quebean.setPageNum(String.valueOf(pageno));
        quebean.setDataSourceType("2");
        quebean.setLoginPatientPosition(mApp.loginDoctorPosition);
        quebean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        quebean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        quebean.setRequestClientType("1");
        quebean.setRowNum(String.valueOf(mRowNum));
        loadDataTask = new LoadDataTask(quebean);
        loadDataTask.execute();
        /*}else{
            loadDataTask.execute();
        }*/
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


    class LoadDataTask extends AsyncTask<Void, Void, List<JwbsYstxInfo>> {
        private QueryHistCond queryCond;

        LoadDataTask(QueryHistCond queryCond) {
            this.queryCond = queryCond;
        }

        @Override
        protected List<JwbsYstxInfo> doInBackground(Void... voids) {
            mLoadDate = false;
            List<JwbsYstxInfo> retlist = new ArrayList();
            try {
                queryCond.setPageNum(String.valueOf(pageno));
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(queryCond), Constant.SERVICEURL + INetAddress.QUERY_PASTHIST_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr, NetRetEntity.class);
                if (1 == retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length() > 3) {
                    retlist = JSON.parseArray(retEntity.getResJsonData(), JwbsYstxInfo.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retlist;
        }

        @Override
        protected void onPostExecute(List<JwbsYstxInfo> providePatientConditionDiseaseRecords) {
            if (providePatientConditionDiseaseRecords.size() > 0) {
                if (pageno==1) {
                    mProvidePatientConditionDiseaseRecords.clear();
                    refreshLayout.finishRefresh();
                }else{
                    refreshLayout.finishLoadMore();
                }

                mProvidePatientConditionDiseaseRecords.addAll(providePatientConditionDiseaseRecords);
                mJDDA_JWBS_YSTXAdapter.setDate(mProvidePatientConditionDiseaseRecords);
                mJDDA_JWBS_YSTXAdapter.notifyDataSetChanged();

            } else {
                if (pageno > 1) {
                    pageno = pageno - 1;
                }
            }
            mLoadDate = true;
        }
    }

}
