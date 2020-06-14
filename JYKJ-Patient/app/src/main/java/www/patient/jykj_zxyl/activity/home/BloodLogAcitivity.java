package www.patient.jykj_zxyl.activity.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.patientInfo.BloodLogRequest;
import entity.patientInfo.ProvidePatientConditionBloodPressureGroup;
import entity.patientInfo.ProvidePatientConditionBloodPressureRecord;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.BloodLogListAdapter;
import www.patient.jykj_zxyl.adapter.BloodLogListDetailAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 血压记录
 */
public class BloodLogAcitivity extends AppCompatActivity {
    private RecyclerView mRcyclerview;
    private BloodLogListAdapter mAdapter;
    private ProvideViewPatientLablePunchClockState mData;
    private List<ProvidePatientConditionBloodPressureGroup> pressureGroupsList;
    private int mRowNum = 10;                        //分页行数
    private int mPageNum = 1;                       //分页页码
    private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
//    private DialogUtil dialogUtil;
    private boolean mLoadDate = true;                    //是否可以加载数据
    private boolean isShow;
    List<ProvidePatientConditionBloodPressureRecord> recordList = new ArrayList<>();
    private BloodLogListDetailAdapter bloodLogListDetailAdapter;
    private LinearLayout llBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_log);
        mApp = (JYKJApplication) getApplication();
        initView();
        initHandler();
        initData();
    }


    private void initView() {
        mRcyclerview = findViewById(R.id.log_list);
        llBack = findViewById(R.id.ll_back);
    }

    private void initData() {
        pressureGroupsList = new ArrayList<>();
        setData();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcyclerview.setLayoutManager(manager);
        mAdapter = new BloodLogListAdapter(pressureGroupsList, this);
        mRcyclerview.setAdapter(mAdapter);

        mRcyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= manager.getItemCount() - 1) {
                            mPageNum++;
                            setData();
                        }
                    }
                }
            }
        });

        mAdapter.setOnItemClickListener(new BloodLogListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, BloodLogListAdapter.ViewHolder holder) {
                ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);
                LinearLayout llDetails = holder.itemView.findViewById(R.id.ll_details);
                RecyclerView rv = holder.itemView.findViewById(R.id.rv_log_list);
                LinearLayoutManager manager = new LinearLayoutManager(BloodLogAcitivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(manager);
                bloodLogListDetailAdapter = new BloodLogListDetailAdapter(recordList, BloodLogAcitivity.this);
                rv.setAdapter(bloodLogListDetailAdapter);
                ProvidePatientConditionBloodPressureGroup data = pressureGroupsList.get(position);
                if (isShow) {
                    isShow = false;
                    ivArrow.setImageResource(R.mipmap.by_1);
                    llDetails.setVisibility(View.VISIBLE);
                    getLogDetail(data);
                } else {
                    isShow = true;
                    ivArrow.setImageResource(R.mipmap.by_2);
                    llDetails.setVisibility(View.GONE);
                }
            }
        });

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setData() {
        getLogTitle();
    }

    private void getLogTitle() {
//        if (dialogUtil == null) {
//            dialogUtil = new DialogUtil(this);
//        } else {
//            dialogUtil.show();
//        }
        new Thread() {
            public void run() {
                try {
                    BloodLogRequest bloodLogRequest = new BloodLogRequest();
                    bloodLogRequest.setPageNum(mPageNum);
                    bloodLogRequest.setRowNum(mRowNum);
                    bloodLogRequest.setLoginPatientPosition(mApp.loginDoctorPosition);
                    bloodLogRequest.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    bloodLogRequest.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    bloodLogRequest.setRequestClientType("1");
                    String jsonString = JSON.toJSONString(bloodLogRequest);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "PatientConditionControlle/searchPatientStateResBloodPressureLogTitle");

                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
//                        if (dialogUtil.isShow()) {
//                            dialogUtil.dismiss();
//                        }
                        mLoadDate = false;
                        return;
                    }
                    List<ProvidePatientConditionBloodPressureGroup> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionBloodPressureGroup.class);
                    if (list == null || list.size() == 0)
                    {
                        mLoadDate = false;
                    }
                    if (list != null && list.size() < mRowNum)
                    {
                        mLoadDate = false;
                    }
                    pressureGroupsList.addAll(list);
//                    if (dialogUtil.isShow()) {
//                        dialogUtil.dismiss();
//                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
//                    if (dialogUtil.isShow()) {
//                        dialogUtil.dismiss();
//                    }
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();


    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mAdapter.setDate(pressureGroupsList);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        bloodLogListDetailAdapter.setDate(recordList);
                        bloodLogListDetailAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    private void getLogDetail(ProvidePatientConditionBloodPressureGroup data) {
        recordList.clear();
        new Thread() {
            public void run() {
                try {
                    BloodLogRequest bloodLogRequest = new BloodLogRequest();
                    bloodLogRequest.setPageNum(1);
                    bloodLogRequest.setRowNum(100);
                    bloodLogRequest.setLoginPatientPosition(mApp.loginDoctorPosition);
                    bloodLogRequest.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    bloodLogRequest.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    bloodLogRequest.setRequestClientType("1");
                    bloodLogRequest.setSearchRecordDate(data.getGroupRecordDate());
                    String jsonString = JSON.toJSONString(bloodLogRequest);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "PatientConditionControlle/searchPatientStateResBloodPressureLogList");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(2);
                        return;
                    }

                    List<ProvidePatientConditionBloodPressureRecord> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionBloodPressureRecord.class);
                    recordList.addAll(list);

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();

    }
}
