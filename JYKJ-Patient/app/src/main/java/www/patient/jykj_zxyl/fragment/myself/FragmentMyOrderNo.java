package www.patient.jykj_zxyl.fragment.myself;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import entity.HZIfno;
import entity.mySelf.ProvideInteractOrderInfo;
import entity.mySelf.conditions.QueryOrder;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.activity.ylzx.YLZXWebActivity;
import www.patient.jykj_zxyl.adapter.RMJXRecycleAdapter;
import www.patient.jykj_zxyl.adapter.myself.MyOrderNoRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.IConstant;
import www.patient.jykj_zxyl.util.INetAddress;
import android.widget.AbsListView.OnScrollListener;

/**
 * 我的订单未完成
 * Created by admin on 2016/6/1.
 */
public class FragmentMyOrderNo extends Fragment{
    private             Context                             mContext;
    private             Handler                             mHandler;
    private MyOrderActivity mActivity;
    private             JYKJApplication                     mApp;

    private             RecyclerView                        mRMJXRecycleView;              //热门精选列表
    private             LinearLayoutManager                 layoutManager;
    private MyOrderNoRecycleAdapter mAdapter;       //适配器
    private             List<ProvideInteractOrderInfo>                        mHZEntyties = new ArrayList<>();            //所有数据
    private             List<ProvideInteractOrderInfo>                        mHZEntytiesClick = new ArrayList<>();            //点击之后的数据
    private int pageno=1;
    private int lastVisibleIndex = 0;
    private LoadDataTask loadDataTask = null;
    private boolean mLoadDate = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myorder_no, container, false);
        mContext = getContext();
        mActivity = (MyOrderActivity) getActivity();
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
        mRMJXRecycleView = (RecyclerView) view.findViewById(R.id.rv_no);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRMJXRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRMJXRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MyOrderNoRecycleAdapter(mHZEntyties,mContext,mActivity);
        mRMJXRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyOrderNoRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,YLZXWebActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        mRMJXRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                            pageno++;
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



            }
        }
    }
    /**
      * 设置数据
     */
    private void setData() {
        /*for (int i = 0; i < 40; i++)
        {
            HZIfno hzIfno = new HZIfno();
            if (i%3 == 0)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(1);
            }
            if (i%3 == 1)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(-1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(2);
            }
            if (i%3 == 2)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(3);
            }
            mHZEntyties.add(hzIfno);
        }*/
        //mAdapter.setDate(mHZEntyties);
        //mAdapter.notifyDataSetChanged();
        //if(null==loadDataTask){
            QueryOrder queorder = new QueryOrder();
            queorder.setPageNum(String.valueOf(pageno));
            queorder.setLoginPatientPosition(mApp.loginDoctorPosition);
            queorder.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
            queorder.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
            queorder.setRequestClientType("1");
            queorder.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
            loadDataTask = new LoadDataTask(queorder);
            loadDataTask.execute();
        //}
    }

    /*@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(null==loadDataTask){
            QueryOrder queorder = new QueryOrder();
            queorder.setPageNum(String.valueOf(pageno));
            queorder.setLoginPatientPosition(mApp.loginDoctorPosition);
            queorder.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
            queorder.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
            queorder.setRequestClientType("1");
            queorder.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
            loadDataTask = new LoadDataTask(queorder);
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

    class LoadDataTask extends AsyncTask<Void,Void,List<ProvideInteractOrderInfo>>{
        private QueryOrder queryOrder;
        public LoadDataTask(QueryOrder queryOrder){
            this.queryOrder = queryOrder;
        }
        @Override
        protected List<ProvideInteractOrderInfo> doInBackground(Void... voids) {
            mLoadDate = false;
            List<ProvideInteractOrderInfo> retlist = new ArrayList();
            queryOrder.setPageNum(String.valueOf(pageno));
            try {
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryOrder), Constant.SERVICEURL+INetAddress.QUERY_UNIMPLEMENT_ORDER_URL);
                NetRetEntity retnetbean = JSON.parseObject(retnetstr, NetRetEntity.class);
                if(1==retnetbean.getResCode() && !TextUtils.isEmpty(retnetbean.getResJsonData()) && retnetbean.getResJsonData().length()>3){
                    retlist = JSON.parseArray(retnetbean.getResJsonData(),ProvideInteractOrderInfo.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return retlist;
        }

        @Override
        protected void onPostExecute(List<ProvideInteractOrderInfo> hzIfnos) {
            if(hzIfnos.size()>0){
                mHZEntyties.addAll(hzIfnos);
                mAdapter.setDate(mHZEntyties);
                mAdapter.notifyDataSetChanged();
            }else{
                if(pageno>1){
                    pageno = pageno - 1;
                }
            }
            mLoadDate = true;
        }
    }
}
