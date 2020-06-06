package www.patient.jykj_zxyl.fragment.shouye;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import entity.ProvidePatientBindingMyDoctorInfo;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.OrderMessage_OrderPayActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.myself.UserAuthenticationActivity;
import www.patient.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.patient.jykj_zxyl.adapter.BloodRecordAdapter;
import www.patient.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.SettingMemberUnionOrgDateAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.Fragment_Shouye_OrderMessageRecycleAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 首页==》通知订单消息
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe_DDXX extends Fragment {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private MainActivity mActivity;
    private             JYKJApplication                     mApp;
    private             RecyclerView                        mHZInfoRecycleView;              //患者列表
    private             LinearLayoutManager                 layoutManager;
    private Fragment_Shouye_OrderMessageRecycleAdapter mFragment_Shouye_OrderMessageRecycleAdapter;       //适配器
    private             RecyclerView                        mRecycleView;
    public ProgressDialog mDialogProgress = null;

    private                 List<ProvideInteractOrderInfo> mList = new ArrayList<>();

    private             String                              mNetRetStr;                 //返回字符串

    private             int                                 mRowNum = 10;
    private             int                                 mPageNum = 1;
    private             boolean                             loadDate = true;
    private             int                                 mDeleteIndex;                   //点击删除的下标


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shouye_ddxx, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        getOrderMessageInfo();
        return v;
    }


    /**
     * 展示我的医生数据
     * @param
     */
    private void showMyDoctorInfo() {}

    /**
     * 获取我的医生数据
     */
    private void getOrderMessageInfo() {
        ProvideInteractOrderInfo provideInteractOrderInfo = new ProvideInteractOrderInfo();
        provideInteractOrderInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideInteractOrderInfo.setRequestClientType("1");
        provideInteractOrderInfo.setSearchPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractOrderInfo.setSearchPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideInteractOrderInfo.setPageNum(mPageNum+"");
        provideInteractOrderInfo.setRowNum(mRowNum+"");

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideInteractOrderInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"msgDataControlle/searchPatientMsgInteractOrderInfo");
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
        mRecycleView = (RecyclerView)view.findViewById(R.id.rv_ddxx);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mFragment_Shouye_OrderMessageRecycleAdapter = new Fragment_Shouye_OrderMessageRecycleAdapter(mList,mContext,this);
        mRecycleView.setAdapter(mFragment_Shouye_OrderMessageRecycleAdapter);

    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 6:
                        if (mNetRetStr == null || "".equals(mNetRetStr))
                        {
                            cacerProgress();
                            Toast.makeText(mContext,"网络故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            loadDate = false;
//                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            List<ProvideInteractOrderInfo> list = JSON.parseArray(netRetEntity.getResJsonData(),ProvideInteractOrderInfo.class);
                            if (list != null)
                                mList.addAll(list);
                            if (list == null)
                                Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            if (list == null || list.size() < mRowNum)
                                loadDate = false;
                        }

                        mFragment_Shouye_OrderMessageRecycleAdapter.setDate(mList);
                        mFragment_Shouye_OrderMessageRecycleAdapter.notifyDataSetChanged();
                        break;
                    case 7:
                        if (mNetRetStr == null || "".equals(mNetRetStr))
                        {
                            cacerProgress();
                            Toast.makeText(mContext,"网络故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mList.remove(mDeleteIndex);
                            mFragment_Shouye_OrderMessageRecycleAdapter.setDate(mList);
                            mFragment_Shouye_OrderMessageRecycleAdapter.notifyDataSetChanged();
                        }
                        break;
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

    /**
     * 删除订单
     * @param position
     */
    public void deleteOrderMessage(int position) {
        // 下面的参数上下文 对话框里面一般都用this
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("温馨提示");
        builder.setMessage("确定删除该订单");
        // 设置取消按钮
        builder.setNegativeButton("不", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // 设置确定按钮
        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDeleteIndex = position;
                //删除订单
                deleteOrder(mList.get(position));
            }
        });
        builder.setCancelable(false);
        // 和Toast一样 最后一定要show 出来
        builder.show();
    }

    /**
     * 删除订单
     * @param provideInteractOrderInfo
     */
    private void deleteOrder(ProvideInteractOrderInfo provideInteractOrderInfo) {

        new Thread(){
            public void run(){
                try {
                    ProvideInteractOrderInfo provideInteractOrderInfo1 = new ProvideInteractOrderInfo();
                    provideInteractOrderInfo1.setLoginPatientPosition(mApp.loginDoctorPosition);
                    provideInteractOrderInfo1.setRequestClientType("1");
                    provideInteractOrderInfo1.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    provideInteractOrderInfo1.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideInteractOrderInfo1.setOrderCode(provideInteractOrderInfo.getOrderCode());
                    provideInteractOrderInfo1.setFlagOrderState(provideInteractOrderInfo.getFlagOrderState());
                    String string = new Gson().toJson(provideInteractOrderInfo1);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"msgDataControlle/operDelPatientMsgInteractOrderInfo");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(7);
            }
        }.start();
    }

    /**
     * 支付
     * @param position
     */
    public void zhifuOrderMessage(int position) {
       startActivity(new Intent(mActivity,OrderMessage_OrderPayActivity.class).putExtra("provideInteractOrderInfo",mList.get(position)));
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {


            }
        }
    }

    private void cutDefault(){

    }

    /**
     * 设置数据
     */
    private void setData() {

    }
}
