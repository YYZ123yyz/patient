package www.patient.jykj_zxyl.activity.myself;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.ProvideDrugInfo;

import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.ChoiceMedicationAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.ChoiceMedicationAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

/**
 * 服药设置 == 》选择药品
 */
public class ChoiceMedicationActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llBack;

    private int mRowNum = 10;                    //
    private int mPageNum = 1;
    private boolean mLoadDate = true;
    private RecyclerView mRecyclerView;
    private ChoiceMedicationAdapter mAdapter;
    private List<ProvideDrugInfo> mData = new ArrayList<>();
    private ImageView    mAddMedic;                      //添加药品

    private ProvideDrugInfo mProvideDrugInfo;               //选择的药品信息

    public ProgressDialog mDialogProgress = null;
    private Handler mHandler;
    private String mNetRetStr;                 //获取返回字符串
    private JYKJApplication mApp;
    private EditText    mSearchDuaName;             //搜索的药品名称

    private TextView   mSearch;

    private TextView mNoDun;                    //提示




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_medication);
        mApp = (JYKJApplication) getApplication();
        initView();
        initData();
        initHandler();
        getDate();
    }


    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        cacerProgress();
                        NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1)
                        {
                                List<ProvideDrugInfo> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvideDrugInfo.class);
                                if (list.size() < mRowNum)
                                {
                                    mNoDun.setVisibility(View.VISIBLE);
                                    mLoadDate = false;
                                }
                                else {
                                    mNoDun.setVisibility(View.GONE);
                                }

                                if (mPageNum == 1)
                                    mData = list;
                                else
                                    mData.addAll(list);
                                mAdapter.setDate(mData);
                                mAdapter.notifyDataSetChanged();

                        }
                        else
                        {
                            mNoDun.setVisibility(View.VISIBLE);
                            mLoadDate = false;
                        }

                        break;

                }
            }
        };
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 10000) {
            mProvideDrugInfo = new ProvideDrugInfo();
            mProvideDrugInfo.setDrugName(data.getStringExtra("medicName"));
//            mProvideDrugInfo.setDrugCode("0");
            Intent intent = new Intent();
            intent.putExtra("medicInfo",mProvideDrugInfo);
            setResult(-1,intent);
            finish();
        }
    }


    private void initView(){
        mNoDun = (TextView)this.findViewById(R.id.noDun);
        mSearch = (TextView) this.findViewById(R.id.searchDuName);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageNum = 1;
                getDate();
            }
        });
        mSearchDuaName = (EditText)this.findViewById(R.id.search_duName);
        mAddMedic = (ImageView)this.findViewById(R.id.addMedication);
        mAddMedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ChoiceMedicationActivity.this,ChoiceAddMedicationActivity.class),10000);
            }
        });
        llBack = findViewById(R.id.ll_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRecyclerView = (RecyclerView)this.findViewById(R.id.rv_choiceMedic);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ChoiceMedicationAdapter(mData, this);
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
        mAdapter.setOnItemClickListener(new ChoiceMedicationAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("medicInfo",mData.get(position));
                setResult(-1,intent);
                finish();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }

    /**
     * 获取药品信息
     */
    private void getDate() {
            getProgressBar("请稍候....", "正在加载数据");
            new Thread() {
                public void run() {
                    try {
                        ProvideDrugInfo provideDrugInfo = new ProvideDrugInfo();
                        provideDrugInfo.setRowNum(mRowNum+"");
                        provideDrugInfo.setPageNum(mPageNum+"");
                        provideDrugInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
                        provideDrugInfo.setRequestClientType("1");
                        provideDrugInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                        provideDrugInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                        provideDrugInfo.setSrarchDrugName(mSearchDuaName.getText().toString());
                        mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideDrugInfo), Constant.SERVICEURL + "PatientConditionControlle/searchPatientConditionTakingMedicineResDrugInfoList");
                        NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            NetRetEntity retEntity = new NetRetEntity();
                            retEntity.setResCode(0);
                            retEntity.setResMsg("获取失败：" + retEntity.getResMsg());
                            mNetRetStr = new Gson().toJson(retEntity);
                            mHandler.sendEmptyMessage(1);
                            return;
                        }
                    } catch (Exception e) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());

                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(1);
                }
            }.start();

    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
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
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }
    private void initData(){


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.ll_back:
                finish();
                break;

        }

    }


}
