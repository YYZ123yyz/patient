package www.patient.jykj_zxyl.fragment.shouye;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.ProvideInteractOrderInfo;
import entity.ProvideMsgPushReminder;
import entity.ProvidePatientBindingMyDoctorInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.XTTZDetailActivity;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.news.UnionNewsDetailActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.patient.jykj_zxyl.adapter.UnionNewsAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 首页==》系统通知fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe_XTTZ extends Fragment {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private MainActivity mActivity;
    private             JYKJApplication                     mApp;

    public ProgressDialog mDialogProgress = null;

    private             String                              mNetRetStr;                 //返回字符串
    private                 List<ProvidePatientBindingMyDoctorInfo> providePatientBindingMyDoctorInfos = new ArrayList<>();         //获取到的我的医生数据

    private             int                                 mRowNum = 10;
    private             int                                 mPageNum = 1;
    private             boolean                             loadDate = true;

    private RecyclerView mRecyclerView;
    private UnionNewsAdapter mAdapter;


    private             List<ProvideMsgPushReminder> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shouye_xtxx, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        getMessageInfo();
        return v;
    }

    /**
     * 获取系统消息
     */
    private void getMessageInfo() {
        ProvideMsgPushReminder provideMsgPushReminder  = new ProvideMsgPushReminder ();
        provideMsgPushReminder.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideMsgPushReminder.setRequestClientType("1");
        provideMsgPushReminder.setSearchPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideMsgPushReminder.setPageNum(mPageNum+"");
        provideMsgPushReminder.setRowNum(mRowNum+"");

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideMsgPushReminder);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"msgDataControlle/searchPatientMsgPushReminderListResAllData");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(6);
            }
        }.start();
    }

    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_xtxx);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new UnionNewsAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new UnionNewsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,XTTZDetailActivity.class).putExtra("message",mList.get(position)));
//                mMsgPushReminders.get(position).setFlagMsgRead(1);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    if (loadDate){
                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
                        if(lastVisiblePosition >= manager.getItemCount() - 1) {
                            if (loadDate)
                            {
                                mPageNum++;
                                getMessageInfo();
                            }
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
                    case 6:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            loadDate = false;
//                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            List<ProvideMsgPushReminder> list = JSON.parseArray(netRetEntity.getResJsonData(),ProvideMsgPushReminder.class);
                                if (list.get(0).getReminderId() == null)
                                {
                                    list.remove(0);
                                }
                            if (list != null)
                                mList.addAll(list);
                            if (list == null)
                                Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            if (list == null || list.size() < mRowNum)
                                loadDate = false;
                        }

                        mAdapter.setDate(mList);
                        mAdapter.notifyDataSetChanged();
//                        break;
                }
            }
        };
    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
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
