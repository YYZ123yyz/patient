package www.patient.jykj_zxyl.activity.myself;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.mySelf.MedicationList;
import entity.mySelf.ProvidePatientConditionTakingRecord;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.MedicationListAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.MedicationListAdapter;
import www.patient.jykj_zxyl.application.Constant;

public class MedicationListActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler mHandler;

    private String mNetRetStr;                 //返回字符串

    private int mRowNum = 10;                    //
    private int mPageNum = 1;
    private boolean mLoadDate = true;
    private LinearLayout llBack;
    private RecyclerView mRecyclerView;
    private MedicationListAdapter mAdapter;
    private List<ProvidePatientConditionTakingRecord> mData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_list);
        initView();
        initHandler();
        initListener();
        getDate();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        mAdapter.setDate(mData);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    private void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_medication_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new MedicationListAdapter(mData, this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= manager.getItemCount() - 1) {
                            if (mLoadDate) {
                                mPageNum++;
                                getDate();
                            }
                        }
                    }
                }
            }
        });

    }

    private void initListener() {
        llBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }


    private void getDate() {
        new Thread() {
            public void run() {
                try {
                    MedicationList medicationList = new MedicationList();
                    medicationList.setRowNum(mRowNum + "");
                    medicationList.setPageNum(mPageNum + "");
                    medicationList.setLoginPatientPosition("108.93425^34.23053");
                    medicationList.setRequestClientType("1");
                    medicationList.setOperPatientCode("dd0500f4e9684678aad9ee13123456");
                    medicationList.setOperPatientName("刘能");
                    medicationList.setSearchTakingMedicine("-1");
                    String jsonString = JSON.toJSONString(medicationList);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "PatientConditionControlle/searchPatientStateResTakingMedicineRecordNoGroup");
                    Log.e("mNetRetStr",mNetRetStr);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        mLoadDate = false;
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    List<ProvidePatientConditionTakingRecord> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionTakingRecord.class);
                    mData.addAll(list);
                } catch (Exception e) {
                    mLoadDate = false;
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(0);
                    e.printStackTrace();
                    return;

                }

                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

}
