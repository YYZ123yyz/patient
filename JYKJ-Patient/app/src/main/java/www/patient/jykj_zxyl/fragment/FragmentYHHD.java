package www.patient.jykj_zxyl.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.EaseConstant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import entity.DoctorInfo.InteractDoctorDetailInfo;
import entity.DoctorInfo.InteractPatient;
import entity.HZIfno;
import entity.basicDate.ProvideDoctorPatientUserInfo;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.yhhd.ProvideDoctorGoodFriendGroup;
import entity.yhhd.ProvideGroupConsultationUserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.hyhd.ChatActivity;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.adapter.DorcerFriendExpandableListViewAdapter;
import www.patient.jykj_zxyl.adapter.MessageInfoRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_DDXX;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_HYHD;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_XTTZ;
import www.patient.jykj_zxyl.util.NestedExpandaleListView;
import www.patient.jykj_zxyl.util.Util;


/**
 * 消息中心fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentYHHD extends Fragment {

    public ProgressDialog mDialogProgress =null;

    private Context mContext;
    private MainActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private RecyclerView mMessageRecycleView;

    private LinearLayoutManager layoutManager;
    private MessageInfoRecycleAdapter mMessageInfoRecycleAdapter;       //适配器
    private List<InteractPatient> mInteractPatient = new ArrayList<>();            //所有数据
    private List<ProvideDoctorGoodFriendGroup> mInteractDoctorUnionInfo = new ArrayList<>();            //医生联盟数据
    private List<HZIfno> mHZEntytiesClick = new ArrayList<>();            //点击之后的数据
    private TextView mMessageList;                       //消息列表
    private TextView mAll;                               //全部患者
    private TextView mPay;                               //消息列表
    private TextView mFriend;                            //消息列表

    private NestedExpandaleListView mYSHY;                              //医生好友
    private DorcerFriendExpandableListViewAdapter mDorcerFriendExpandableListViewAdapter;   //医生好友适配器


    private ImageView mJQImage;                           //建群
    private TextView mHYSQText;                          //好友申请
    private String mNetRetStr;                 //返回字符串

    private InteractPatient mClickInteractPatient;              //点击进入聊天的患者

    private List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfo = new ArrayList<>();

    private TextView mHXNetWorkState;                    //环信网络状态监听

    private             int                                 mCurrent = 0;                       //当前下标

    private             int                                 clickIndex;                         //点击列表下标


    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;

    private FragmentShouYe_HYHD mFragmentShouYe_HYHD;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitymain_hyhdfragment, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();

        return v;
    }


    private void getUserIdentification(String code){
        ProvideGroupConsultationUserInfo provideGroupConsultationUserInfo = new ProvideGroupConsultationUserInfo();
        provideGroupConsultationUserInfo.setLoginUserPosition(mApp.loginDoctorPosition);
        provideGroupConsultationUserInfo.setUserCode(code);
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideGroupConsultationUserInfo),Constant.SERVICEURL+"doctorGroupConsultationControlle/searchUserIdentificationByUserCode");
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
     *
     * @param v
     */
    private void initLayout(View v) {
        pager= (ViewPager) v.findViewById(R.id.page);
        tabLayout= (TabLayout) v.findViewById(R.id.tab_layout);

        fragmentList=new ArrayList<>();
        mTitles=new ArrayList<>();
        mTitles.add("患医互动");
        mTitles.add("系统通知");
        mTitles.add("订单消息");
        mFragmentShouYe_HYHD = new FragmentShouYe_HYHD();
        fragmentList.add(mFragmentShouYe_HYHD);
        fragmentList.add(new FragmentShouYe_XTTZ());
        fragmentList.add(new FragmentShouYe_DDXX());

        fragmentAdapter=new FragmentAdapter(getChildFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系

    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mMessageInfoRecycleAdapter != null)
//        {
//            mMessageInfoRecycleAdapter.setDate(mInteractPatient);
//            mMessageInfoRecycleAdapter.notifyDataSetChanged();
//        }
//        if (mCurrent == 0)
//            getMessageList();
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        break;
                    case 1:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            mInteractPatient.clear();
                            if (netRetEntity.getResCode() == 1) {
                                mInteractPatient =  new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<InteractPatient>>(){}.getType());
                                for (int i = 0; i < mInteractPatient.size(); i++)
                                {
                                    mInteractPatient.get(i).setType("user");
                                }
                                mMessageInfoRecycleAdapter.setDate(mInteractPatient);
                                mMessageInfoRecycleAdapter.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(mContext, "登录失败，"+netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 2:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                List<ViewSysUserDoctorInfoAndHospital> viewSysUserDoctorInfoAndHospitals = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ViewSysUserDoctorInfoAndHospital>>(){}.getType());
                                mInteractPatient.removeAll(mInteractPatient);
                                for (int i = 0; i < viewSysUserDoctorInfoAndHospitals.size(); i++)
                                {
                                    InteractPatient interactPatient = new InteractPatient();
                                    interactPatient.setPatientCode(viewSysUserDoctorInfoAndHospitals.get(i).getPatientCode());
                                    interactPatient.setPatientTitleDesc(viewSysUserDoctorInfoAndHospitals.get(i).getDoctorTitleName());
                                    interactPatient.setPatientDiagnosisType(viewSysUserDoctorInfoAndHospitals.get(i).getUserUseType());
                                    interactPatient.setPatientNewLoginDate(viewSysUserDoctorInfoAndHospitals.get(i).getNewLoginDate());
                                    interactPatient.setPatientUserLabelName(viewSysUserDoctorInfoAndHospitals.get(i).getHospitalInfoName());
                                    interactPatient.setPatientUserName(viewSysUserDoctorInfoAndHospitals.get(i).getUserName());
                                    interactPatient.setType("user");
                                    mInteractPatient.add(interactPatient);
                                }
                                mMessageInfoRecycleAdapter.setDate(mInteractPatient);
                                mMessageInfoRecycleAdapter.notifyDataSetChanged();


                            }
                            else
                            {
                                Toast.makeText(mContext, "获取失败，"+netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;

                        case 3:
                            cacerProgress();
                            if (mNetRetStr != null && !mNetRetStr.equals("")) {
                                NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                                if (netRetEntity.getResCode() == 1) {
                                    mInteractDoctorUnionInfo =  JSON.parseArray(netRetEntity.getResJsonData(),ProvideDoctorGoodFriendGroup.class);
                                    mDorcerFriendExpandableListViewAdapter.setDate(mInteractDoctorUnionInfo);
                                    mDorcerFriendExpandableListViewAdapter.notifyDataSetChanged();

                                }
                                else
                                {
                                    Toast.makeText(mContext, "获取失败，"+netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                            }
                            break;

                    case 4:
                        cacerProgress();
//                        mInteractPatient.removeAll(mInteractPatient);
//                        for (int i = 0; i < mProvideDoctorPatientUserInfo.size(); i++)
//                        {
//                            InteractPatient interactPatient = new InteractPatient();
//                            interactPatient.setPatientCode(mProvideDoctorPatientUserInfo.get(i).getUserCode());
//                            interactPatient.setPatientTitleDesc(mProvideDoctorPatientUserInfo.get(i).getDoctorTitleName());
//                            interactPatient.setPatientDiagnosisType(mProvideDoctorPatientUserInfo.get(i).getUserUseType());
//                            interactPatient.setPatientNewLoginDate(mProvideDoctorPatientUserInfo.get(i).getNewLoginDate());
//                            interactPatient.setPatientUserLabelName(mProvideDoctorPatientUserInfo.get(i).getHospitalInfoName());
//                            interactPatient.setPatientUserName(mProvideDoctorPatientUserInfo.get(i).getUserName());
//                            interactPatient.setType("message");
//                            interactPatient.setLastMessage(mProvideDoctorPatientUserInfo.get(i).getLastMessage());
//                            interactPatient.setNoRead(mProvideDoctorPatientUserInfo.get(i).isNoRead());
//                            mInteractPatient.add(interactPatient);
//                        }
//                        mMessageInfoRecycleAdapter.setDate(mInteractPatient);
//                        mMessageInfoRecycleAdapter.notifyDataSetChanged();
//                        mApp.setNewsMessage();
//                        mActivity.setHZTabView();
                        break;
                    case 10:
                        //连接正常
                        if (mApp.gNetConnectionHX)
                        {
                            mHXNetWorkState.setVisibility(View.GONE);
                        }
                        else
                        {
                            mHXNetWorkState.setVisibility(View.VISIBLE);
                        }
                        break;

                    case 6:
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                ProvideGroupConsultationUserInfo provideGroupConsultationUserInfo = JSON.parseObject(netRetEntity.getResJsonData(),ProvideGroupConsultationUserInfo.class);
                                if (provideGroupConsultationUserInfo.getUserUseType() == 6)
                                {
                                    mClickInteractPatient = mInteractPatient.get(clickIndex);
                                    mInteractPatient.get(clickIndex).setNoRead(false);
                                    Intent intent = new Intent();
                                    intent.setClass(mContext,ChatActivity.class);
                                    intent.putExtra(EaseConstant.EXTRA_MESSAGE_NUM,-1);
                                    intent.putExtra("userCode",mClickInteractPatient.getPatientCode());
                                    intent.putExtra("userName",mClickInteractPatient.getPatientUserName());
                                    intent.putExtra("chatType","twjz");
                                    intent.putExtra("chatType","twjz");
                                    String date = Util.dateToStr(provideGroupConsultationUserInfo.getServiceStopDate());
                                    intent.putExtra("date",date);
                                    startActivity(intent);
                                }
                                else
                                {
                                    mClickInteractPatient = mInteractPatient.get(clickIndex);
                                    mInteractPatient.get(clickIndex).setNoRead(false);
                                    Intent intent = new Intent();
                                    intent.setClass(mContext,ChatActivity.class);
                                    intent.putExtra("userCode",mClickInteractPatient.getPatientCode());
                                    intent.putExtra("userName",mClickInteractPatient.getPatientUserName());
                                    intent.putExtra("vedioNum",1000000);
                                    intent.putExtra("voiceNum",1000000);
                                    startActivity(intent);
                                }


                            }
                            else
                            {
                                Toast.makeText(mContext, "用户类型获取失败"+netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
    }

    /**
     * 按钮默认布局
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void defaultLayout(){
        mMessageList.setBackgroundResource(0);
        mMessageList.setTextColor(mActivity.getResources().getColor(R.color.writeColor));
        mAll.setBackgroundResource(0);
        mAll.setTextColor(mActivity.getResources().getColor(R.color.writeColor));
        mPay.setBackgroundResource(0);
        mPay.setTextColor(mActivity.getResources().getColor(R.color.writeColor));
        mFriend.setBackgroundResource(0);
        mFriend.setTextColor(mActivity.getResources().getColor(R.color.writeColor));
    }

    /**
     * 设置环信网络状态
     * @param gNetConnectionHX
     */
    public void setHXNetWorkState(boolean gNetConnectionHX) {
        if (mFragmentShouYe_HYHD != null)
            mFragmentShouYe_HYHD.setHXNetWorkState(gNetConnectionHX);

    }

    /**
     * 获取消息
     */
    public void getYHHDMessage() {
        if (mFragmentShouYe_HYHD != null)
            mFragmentShouYe_HYHD.getMessageList();
    }

    /**
     * 设置fragment下标
     * @param childFragmentIndex
     */
    public void setViewPagerIndex(int childFragmentIndex) {
        pager.setCurrentItem(childFragmentIndex);
    }


    class   ButtonClick implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

//                case R.id.tv_fragmentHYHD_messageList:
//                    mCurrent = 0;
//                    defaultLayout();
//                    mYSHY.setVisibility(View.GONE);
//                    mMessageRecycleView.setVisibility(View.VISIBLE);
//                    mJQImage.setVisibility(View.VISIBLE);
//                    mHYSQText.setVisibility(View.GONE);
//                    mMessageList.setBackgroundResource(R.mipmap.pg_messagetitle);
//                    mMessageList.setTextColor(mActivity.getResources().getColor(R.color.tabColor_nomal));
//                    getMessageList();
//                    break;
//                case R.id.tv_fragmentHYHD_all:
//                    mCurrent = 1;
//                    defaultLayout();
//                    mYSHY.setVisibility(View.GONE);
//                    mHYSQText.setVisibility(View.GONE);
//                    mMessageRecycleView.setVisibility(View.VISIBLE);
//                    mJQImage.setVisibility(View.VISIBLE);
//                    mAll.setBackgroundResource(R.mipmap.pg_messagetitle);
//                    mAll.setTextColor(mActivity.getResources().getColor(R.color.tabColor_nomal));
//                    //获取数据
//                    getHZDate();
//                    break;
//                case R.id.tv_fragmentHYHD_pay:
//                    mCurrent = 2;
//                    defaultLayout();
//                    mYSHY.setVisibility(View.GONE);
//                    mJQImage.setVisibility(View.VISIBLE);
//                    mHYSQText.setVisibility(View.GONE);
//                    mPay.setBackgroundResource(R.mipmap.pg_messagetitle);
//                    mPay.setTextColor(mActivity.getResources().getColor(R.color.tabColor_nomal));
//                    //获取数据
//                    getQYHXDate();
//                    break;
//
//                case R.id.tv_fragmentHYHD_friend:
//                    mCurrent = 3;
//                    defaultLayout();
//                    mYSHY.setVisibility(View.VISIBLE);
//                    mMessageRecycleView.setVisibility(View.GONE);
//                    mJQImage.setVisibility(View.GONE);
//                    mHYSQText.setVisibility(View.VISIBLE);
//                    mFriend.setBackgroundResource(R.mipmap.pg_messagetitle);
//                    mFriend.setTextColor(mActivity.getResources().getColor(R.color.tabColor_nomal));
//
//                    //获取数据
//                    getYSLMDate();
//
//                    break;
//                case R.id.iv_fragmentHYHD_jqImage:
//                    startActivity(new Intent(mContext,NewChatGroupActivity.class));
//                    break;
//                case R.id.iv_fragmentHYHD_hysqImage:
//                    startActivity(new Intent(mContext,HYHD_HYSQActivity.class));
//                    break;
            }
        }
    }

    /**
     * 获取医生好友联盟数据
     */
    private void getYSLMDate() {
        getProgressBar("请稍候。。。","正在获取数据");
        InteractDoctorDetailInfo interactDoctorDetailInfo = new InteractDoctorDetailInfo();
        interactDoctorDetailInfo.setDoctorId(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientId());
        interactDoctorDetailInfo.setDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(interactDoctorDetailInfo),Constant.SERVICEURL+"doctorManagePatient/interactDoctorUnionAllList");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(3);
            }
        }.start();
    }

    /**
     * 获取签约患者数据
     */
    private void getQYHXDate() {
        getProgressBar("请稍候。。。","正在获取数据");
        InteractDoctorDetailInfo interactDoctorDetailInfo = new InteractDoctorDetailInfo();
        interactDoctorDetailInfo.setDoctorId(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientId());
        interactDoctorDetailInfo.setDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(interactDoctorDetailInfo),Constant.SERVICEURL+"doctorManagePatient/interactPatientSigningList");
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

    /**
     * 获取全部患者数据
     */
    private void getHZDate() {
        getProgressBar("请稍候。。。","正在获取数据");
        InteractDoctorDetailInfo interactDoctorDetailInfo = new InteractDoctorDetailInfo();
        interactDoctorDetailInfo.setDoctorId(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientId());
        interactDoctorDetailInfo.setDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(interactDoctorDetailInfo),Constant.SERVICEURL+"doctorManagePatient/interactPatientAllList");
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

    /**
     *   获取进度条
     */

    public void getProgressBar(String title, String progressPrompt){
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(getContext());
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }
}
