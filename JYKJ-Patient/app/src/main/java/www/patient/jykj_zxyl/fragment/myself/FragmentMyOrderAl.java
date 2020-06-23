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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import entity.HZIfno;
import entity.ProvideInteractOrderInfo;
import entity.mySelf.MyOrderProcess;
import entity.mySelf.conditions.QueryContactCondPage;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.OrderMessage_OrderPayActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_ZHLYActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_JZJLActivity;
import www.patient.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.patient.jykj_zxyl.activity.myself.CommentActivity;
import www.patient.jykj_zxyl.activity.myself.LeaveMessageActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.activity.ylzx.YLZXWebActivity;
import www.patient.jykj_zxyl.adapter.RMJXRecycleAdapter;
import www.patient.jykj_zxyl.adapter.myself.MyOrderAlRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.IConstant;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.StrUtils;


/**
 * 我的订单已完成
 * Created by admin on 2016/6/1.
 */
public class FragmentMyOrderAl extends Fragment {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private MyOrderActivity mActivity;
    private             JYKJApplication                     mApp;

    private             RecyclerView                        mRMJXRecycleView;              //订单列表
    private             LinearLayoutManager                 layoutManager;
    private MyOrderAlRecycleAdapter mAdapter;       //适配器
    private             List<MyOrderProcess>                        mHZEntyties = new ArrayList<>();            //所有数据

    private int mPagenum = 1;
    private LoadDataTask loadDataTask;

    boolean mLoadDate = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myorder_al, container, false);
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
        mRMJXRecycleView = (RecyclerView) view.findViewById(R.id.rv_al);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRMJXRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRMJXRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MyOrderAlRecycleAdapter(mHZEntyties,mContext,mActivity);
        mRMJXRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyOrderAlRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                View parview = getView();
                switch (parview.getId()){
                    case R.id.item_fragmentYLZX_rmjxLayout:
                        MyOrderProcess parbean = (MyOrderProcess)getView().getTag();
                        ProvideViewInteractOrderTreatmentAndPatientInterrogation parorder = new ProvideViewInteractOrderTreatmentAndPatientInterrogation();
                        parorder.setOrderCode(parbean.getOrderCode());
                        startActivity(new Intent(mActivity, TWJZ_JZJLActivity.class).putExtra("wzxx",parorder));
                        break;
                    case  R.id.leave_btn:
                        MyOrderProcess leavebean = (MyOrderProcess)getView().getTag();
                        startActivity(new Intent(mContext, LeaveMessageActivity.class).putExtra("orderInfo",leavebean));
                        break;
                    case  R.id.opinion_btn:
                        MyOrderProcess opinion = (MyOrderProcess)getView().getTag();
                        startActivity(new Intent(mContext, CommentActivity.class).putExtra("orderInfo",opinion));
                        break;
                }
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
                            mPagenum++;
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
        QueryContactCondPage querycond = new QueryContactCondPage();
        querycond.setPageNum(String.valueOf(mPagenum));
        querycond.setLoginPatientPosition(mApp.loginDoctorPosition);
        querycond.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        querycond.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        querycond.setPageNum(String.valueOf(mPagenum));
        querycond.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
        querycond.setRequestClientType("1");
        loadDataTask = new LoadDataTask(querycond);
        loadDataTask.execute();
    }

    class LoadDataTask extends AsyncTask<Void,Void,List<MyOrderProcess>> {
        QueryContactCondPage querycond;
        LoadDataTask(QueryContactCondPage querycond){
            this.querycond = querycond;
        }
        @Override
        protected List<MyOrderProcess> doInBackground(Void... voids) {
            mLoadDate = false;
            List<MyOrderProcess> retlist = new ArrayList();
            try {
                querycond.setPageNum(String.valueOf(mPagenum));
                String netstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(querycond), Constant.SERVICEURL+ INetAddress.QUERY_MYORDER_FINISH_URL);
                NetRetEntity retEntity = JSON.parseObject(netstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),MyOrderProcess.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retlist;
        }

        @Override
        protected void onPostExecute(List<MyOrderProcess> myOrderProcesses) {
            if(myOrderProcesses.size()>0){
                mHZEntyties.addAll(myOrderProcesses);
                mAdapter.setDate(mHZEntyties);
                mAdapter.notifyDataSetChanged();
            }else{
                if(mPagenum>1){
                    mPagenum = mPagenum - 1;
                }
            }
            mLoadDate = true;
        }
    }
}
