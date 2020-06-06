package www.patient.jykj_zxyl.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.patientInfo.ProvideViewPatientInfo;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.activity.home.MyPatientActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLQTDKActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLTXHZActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLXYActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLYYXXActivity;
import www.patient.jykj_zxyl.adapter.MyPatientRecyclerAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.MyPatientActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLQTDKActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLTXHZActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLXYActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLYYXXActivity;
import www.patient.jykj_zxyl.adapter.MyPatientRecyclerAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

public class MyPatientNotSignetFragment extends Fragment {

    private Context mContext;
    private Handler mHandler;
    private MyPatientActivity mActivity;
    private JYKJApplication mApp;
    private RecyclerView mHZInfoRecycleView;              //患者列表
    private LinearLayoutManager layoutManager;
    private MyPatientRecyclerAdapter mHZGLRecycleAdapter;       //适配器
    private List<ProvideViewPatientLablePunchClockState> mHZEntyties = new ArrayList<>();            //所有数据
    //    private             List<HZIfno>                        mHZEntytiesClick = new ArrayList<>();            //点击之后的数据
    private                 int                         mRowNum = 10;                        //分页行数
    private                 int                         mPageNum = 1;                       //分页页码

    private LinearLayout mQB;            //全部
    private             LinearLayout                        mQBCut;         //全部下划线
    private             LinearLayout                        mYJ;            //预警
    private             LinearLayout                        mYJCut;         //预警下划线
    private             LinearLayout                        mTX;            //提醒
    private             LinearLayout                        mTXCut;         //提醒下划线
    private             LinearLayout                        mZC;            //正常
    private             LinearLayout                        mZCCut;         //正常下划线
    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;
    private int mSearchStateType = 0;//状态类型.0:全部;1:正常;2:提醒;3:预警


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_patient_not_signet, container, false);
        mContext = getContext();
        mActivity = (MyPatientActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        getData(mSearchStateType);
        return v;
    }

    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        mHZInfoRecycleView = (RecyclerView) view.findViewById(R.id.rv_fragmethzgl_hzinfo);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mHZInfoRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mHZInfoRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mHZGLRecycleAdapter = new MyPatientRecyclerAdapter(mHZEntyties,mContext);
        mHZInfoRecycleView.setAdapter(mHZGLRecycleAdapter);

        //患者资料点击事件
        mHZGLRecycleAdapter.setOnItemClickListener(new MyPatientRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("patientInfo",mHZEntyties.get(position));
                intent.setClass(mContext, HZGLHZZLActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //血压点击事件
        mHZGLRecycleAdapter.setOnXYItemClickListener(new MyPatientRecyclerAdapter.OnXYItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,HZGLXYActivity.class);
                intent.putExtra("patientLable",mHZEntyties.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //用药点击事件
        mHZGLRecycleAdapter.setOnYYItemClickListener(new MyPatientRecyclerAdapter.OnYYItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,HZGLYYXXActivity.class);
                intent.putExtra("patientLable",mHZEntyties.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });


        //其他打卡击事件
        mHZGLRecycleAdapter.setOnQTDKItemClickListener(new MyPatientRecyclerAdapter.OnQTDKItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,HZGLQTDKActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //提醒患者点击事件
        mHZGLRecycleAdapter.setOnTXHZItemClickListener(new MyPatientRecyclerAdapter.OnTXHZItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,HZGLTXHZActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        mQB = (LinearLayout)view.findViewById(R.id.li_fragmentHZGL_qb);
        mQBCut = (LinearLayout)view.findViewById(R.id.li_fragmentHZGL_qbCut);
        mYJ = (LinearLayout)view.findViewById(R.id.li_fragmentHZGL_yj);
        mYJCut = (LinearLayout)view.findViewById(R.id.li_fragmentHZGL_yjCut);
        mTX = (LinearLayout)view.findViewById(R.id.li_fragmentHZGL_tx);
        mTXCut = (LinearLayout)view.findViewById(R.id.li_fragmentHZGL_txCut);
        mZC = (LinearLayout)view.findViewById(R.id.li_fragmentHZGL_zc);
        mZCCut = (LinearLayout)view.findViewById(R.id.li_fragmentHZGL_zcCut);


        mQB.setOnClickListener(new ButtonClick());
        mYJ.setOnClickListener(new ButtonClick());
        mTX.setOnClickListener(new ButtonClick());
        mZC.setOnClickListener(new ButtonClick());
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        mHZGLRecycleAdapter.setDate(mHZEntyties);
                        mHZGLRecycleAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        mHZGLRecycleAdapter.setDate(mHZEntyties);
                        mHZGLRecycleAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        mHZGLRecycleAdapter.setDate(mHZEntyties);
                        mHZGLRecycleAdapter.notifyDataSetChanged();
                        break;
                    case 4:
                        mHZGLRecycleAdapter.setDate(mHZEntyties);
                        mHZGLRecycleAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_fragmentHZGL_qb:
                    cutDefault();
                    mQBCut.setVisibility(View.VISIBLE);
                    mSearchStateType = 0;
                    mHZEntyties.clear();
                    getData(mSearchStateType);
                    mHandler.sendEmptyMessage(1);
                    break;
                case R.id.li_fragmentHZGL_yj:
                    cutDefault();
                    mYJCut.setVisibility(View.VISIBLE);
                    mSearchStateType = 3;
                    mHZEntyties.clear();
                    getData(mSearchStateType);
                    mHandler.sendEmptyMessage(2);
                    break;
                case R.id.li_fragmentHZGL_tx:
                    cutDefault();
                    mTXCut.setVisibility(View.VISIBLE);
                    mSearchStateType = 2;
                    mHZEntyties.clear();
                    getData(mSearchStateType);
                    mHandler.sendEmptyMessage(3);
                    break;
                case R.id.li_fragmentHZGL_zc:
                    cutDefault();
                    mZCCut.setVisibility(View.VISIBLE);
                    mSearchStateType = 1;
                    mHZEntyties.clear();
                    getData(mSearchStateType);
                    mHandler.sendEmptyMessage(4);
                    break;

            }
        }
    }

    private void getData(int searchStateType){

//        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    ProvideViewPatientInfo provideViewPatientInfo = new ProvideViewPatientInfo();
                    provideViewPatientInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setSearchDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideViewPatientInfo.setPageNum(mPageNum);
                    provideViewPatientInfo.setRowNum(mRowNum);
                    provideViewPatientInfo.setSearchStateType(searchStateType);
                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+jsonString ,Constant.SERVICEURL+"bindingDoctorPatientControlle/searchDoctorManagePatientDataOtherByParam");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败："+netRetEntity.getResMsg());
//                        mLoadDate = false;
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }
                    List<ProvideViewPatientLablePunchClockState> list = JSON.parseArray(netRetEntity.getResJsonData(),ProvideViewPatientLablePunchClockState.class);
                    mHZEntyties.addAll(list);

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();

    }

    private void cutDefault(){
        mQBCut.setVisibility(View.GONE);
        mYJCut.setVisibility(View.GONE);
        mTXCut.setVisibility(View.GONE);
        mZCCut.setVisibility(View.GONE);
    }



    /**
     *   获取进度条
     *   获取进度条
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(getActivity());
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


}
