package www.patient.jykj_zxyl.fragment.wdys;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;

import java.util.ArrayList;
import java.util.List;

import entity.ProvideViewInteractPatientComment;
import entity.ProvideViewMyDoctorSigning;
import entity.ProvideViewMyDoctorSigningRenewal;
import entity.XYEntiy;
import entity.service.GetInteractOrderCodeGenerate;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import entity.wdzs.ProvideInteractPatientInterrogation;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.home.patient.WZXXOrderActivity;
import www.patient.jykj_zxyl.activity.hyhd.ChatActivity;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeWDYSQYYSAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeWDYSQYYSAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 我的医生 == 签约医生
 * Created by admin on 2016/6/1.
 */
public class FragmentWDYS_QYYS extends Fragment {
    private             Context                             mContext;
    private WDYSActivity mActivity;
    private             Handler                             mHandler;
    private             JYKJApplication                     mApp;

    private RecyclerView mRecyclerView;
    private FragmentHomeWDYSQYYSAdapter mAdapter;
    private LinearLayout llBack;

    private             List<ProvideViewMyDoctorSigning>    provideViewMyDoctorSignings = new ArrayList<>();
    private             boolean                             loadDate;

    private                 int                         mNumPage = 1;                  //页数（默认，1）
    private                 int                         mRowNum = 10;                  //每页加载10条
    private         String                      mNetRetStr;                 //返回字符串
    public          ProgressDialog              mDialogProgress =null;

    private                 TextView                tv_zjtj;


    private         String              mSearchName = "";
    private         String              mSearchProvice = "";
    private         String              mSearchCity = "";
    private         String              mSearchArea = "";
    private         String              mSearchJGBJ = "";
    private         String              mSearchYSZC = "";

    private         String              mOrderNum;
    private         int                 mXYChoiceIndex;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_wdys_yslb, container, false);
        mContext = getContext();
        mActivity = (WDYSActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        provideViewMyDoctorSignings.clear();
        getDate(this.mSearchName,this.mSearchProvice,this.mSearchCity,this.mSearchArea,this.mSearchJGBJ,this.mSearchYSZC);
        return v;
    }

    /**
     * 初始化布局
     */
    private void initLayout(View view) {
        llBack = (LinearLayout) view.findViewById(R.id.ll_back);
//        mChoiceDepartmentFLayout = (LinearLayout) this.findViewById(R.id.li_activityJoinDoctorsUnion_choiceDepartmentFLayout);
//        mChoiceDepartmentFText = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_choiceDepartmentFText);
//        mChoiceDepartmentSLayout = (LinearLayout) this.findViewById(R.id.li_activityJoinDoctorsUnion_choiceDepartmentSLayout);
//        mChoiceDepartmentSText = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_choiceDepartmentSText);
//        mChoiceDepartmentFLayout.setOnClickListener(new ButtonClick());
//        mChoiceDepartmentSLayout.setOnClickListener(new ButtonClick());

        mRecyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new FragmentHomeWDYSQYYSAdapter(provideViewMyDoctorSignings,mContext);
        mRecyclerView.setAdapter(mAdapter);
        //续约
        mAdapter.setOnItemClickXYListener(new FragmentHomeWDYSQYYSAdapter.OnItemClickXYListener() {
            @Override
            public void onClick(int position) {
                mXYChoiceIndex = position;
                    //获取续约数据
                getXYData();

            }

            @Override
            public void onLongClick(int position) {

            }
        });
        //咨询
        mAdapter.setOnItemClickZXListener(new FragmentHomeWDYSQYYSAdapter.OnItemClickZXListener() {
            @Override
            public void onClick(int position) {
                if (provideViewMyDoctorSignings.get(position).getFlagSigningBtn() == 0)
                {
                    Toast.makeText(mContext,"暂不可咨询",Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent();
                intent.setClass(mContext,ChatActivity.class);
                intent.putExtra("userCode",provideViewMyDoctorSignings.get(position).getDoctorCode());
                intent.putExtra("userName",provideViewMyDoctorSignings.get(position).getUserName());
//                intent.putExtra("chatType","twjz");

                intent.putExtra("loginDoctorPosition",mApp.loginDoctorPosition);
                intent.putExtra("operDoctorCode",mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                intent.putExtra("operDoctorName",mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
//                intent.putExtra("orderCode",provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getOrderCode());

                intent.putExtra(EaseConstant.EXTRA_MESSAGE_NUM,provideViewMyDoctorSignings.get(position).getLimitImgText());           //消息数量
                intent.putExtra(EaseConstant.EXTRA_VOICE_NUM,provideViewMyDoctorSignings.get(position).getLimitAudio());           //音频时长（单位：秒）
                intent.putExtra(EaseConstant.EXTRA_VEDIO_NUM,provideViewMyDoctorSignings.get(position).getLimitVideo());           //视频时长（单位：秒）
                startActivity(intent);
//                startActivity(new Intent(mContext,ZJXQActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommendList.get(position)));
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
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= manager.getItemCount() - 1) {
                        if (loadDate)
                        {
                            mNumPage ++;
                            getDate(mSearchName,mSearchProvice,mSearchCity,mSearchArea,mSearchJGBJ,mSearchYSZC);
                        }

                    }
                }
            }
        });

        tv_zjtj = (TextView)view.findViewById(R.id.tv_zjtj);
        tv_zjtj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,TJZJActivity.class));
            }
        });

    }


    /**
     * 获取订单号
     */
    private void getXYData() {
        getProgressBar("请稍候","正在获取数据。。。");
        ProvideViewMyDoctorSigningRenewal provideViewMyDoctorSigningRenewal = new ProvideViewMyDoctorSigningRenewal();
        provideViewMyDoctorSigningRenewal.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewMyDoctorSigningRenewal.setRequestClientType("1");
        provideViewMyDoctorSigningRenewal.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewMyDoctorSigningRenewal.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideViewMyDoctorSigningRenewal.setSigningDoctorCode(provideViewMyDoctorSignings.get(mXYChoiceIndex).getDoctorCode());
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideViewMyDoctorSigningRenewal);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorSigningResInitRenewal");
                    String string01 = Constant.SERVICEURL+"msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string+string01);
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

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 5:
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1 && netRetEntity.getResJsonData() != null && !"".equals(netRetEntity.getResJsonData()))
                        {
                            List<ProvideViewMyDoctorSigning> list = JSON.parseArray(netRetEntity.getResJsonData(),ProvideViewMyDoctorSigning.class);

                            if (list == null || list.size() == 0)
                                loadDate = false;
                            else
                                provideViewMyDoctorSignings.addAll(list);
                            if (list.size() < mRowNum )
                            {
                                loadDate = false;
                            }
                        }
                        mAdapter.setDate(provideViewMyDoctorSignings);
                        mAdapter.notifyDataSetChanged();
                        break;

                    case 7:
                        //获取订单号
                        getOrderNum();
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0 || netRetEntity.getResJsonData() == null)
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            cacerProgress();
                        }
                        else
                        {
                            XYEntiy xyEntiy = JSON.parseObject(netRetEntity.getResJsonData(),XYEntiy.class);
                            Intent intent = new Intent();
                            ProvideInteractPatientInterrogation provideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();
                            provideInteractPatientInterrogation.setDoctorCode(xyEntiy.getDoctorCode());
                            provideInteractPatientInterrogation.setDoctorName(xyEntiy.getUserName());
                            provideInteractPatientInterrogation.setPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                            provideInteractPatientInterrogation.setPatientLinkPhone(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());
                            startActivity(new Intent(mContext,WZXXOrderActivity.class)
                                    .putExtra("provideInteractPatientInterrogation",provideInteractPatientInterrogation)
                                    .putExtra("orderID",mOrderNum).putExtra("orderType","6").putExtra("xyEntiy",xyEntiy));
//
                        }
                        break;
                }
            }
        };
    }

    /**
     * 获取订单号
     */
    private void getOrderNum() {

//        getProgressBar("请稍候","正在获取数据。。。");
//        GetInteractOrderCodeGenerate getInteractOrderCodeGenerate = new GetInteractOrderCodeGenerate();
//        getInteractOrderCodeGenerate.setLoginPatientPosition(mApp.loginDoctorPosition);
//        getInteractOrderCodeGenerate.setRequestClientType("1");
//        getInteractOrderCodeGenerate.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
//        getInteractOrderCodeGenerate.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
//        getInteractOrderCodeGenerate.setUserLinkPhone(mApp.mLoginUserInfo.getUserPhone());
//        getInteractOrderCodeGenerate.setOrderTreatmentType(mOperaType);
//
//        new Thread(){
//            public void run(){
//                try {
//                    String string = new Gson().toJson(getInteractOrderCodeGenerate);
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"patientInteractDataControlle/getInteractOrderCodeGenerate");
//                    String string01 = Constant.SERVICEURL+"msgDataControlle/searchMsgPushReminderAllCount";
//                    System.out.println(string+string01);
//                } catch (Exception e) {
//                    NetRetEntity retEntity = new NetRetEntity();
//                    retEntity.setResCode(0);
//                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
//                    mNetRetStr = new Gson().toJson(retEntity);
//                    e.printStackTrace();
//                }
//                mHandler.sendEmptyMessage(0);
//            }
//        }.start();
    }

    /**
     * 搜索
     */
    public void getDate(String searchName,String searchProvince,String searchCity,String searchArea,String searchHospitalType,String searchDoctorTitle) {
        ProvideViewMyDoctorSigning provideViewMyDoctorSigning = new ProvideViewMyDoctorSigning();
        provideViewMyDoctorSigning.setRowNum(mRowNum+"");
        provideViewMyDoctorSigning.setPageNum(mNumPage+"");
        provideViewMyDoctorSigning.setLoginUserPosition(mApp.loginDoctorPosition);
        provideViewMyDoctorSigning.setRequestClientType("1");
        provideViewMyDoctorSigning.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewMyDoctorSigning.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideViewMyDoctorSigning.setSearchName(searchName);
        provideViewMyDoctorSigning.setSearchProvince(searchProvince);
        provideViewMyDoctorSigning.setSearchCity(searchCity);
        provideViewMyDoctorSigning.setSearchArea(searchArea);
        provideViewMyDoctorSigning.setSearchHospitalType(searchHospitalType);
        provideViewMyDoctorSigning.setSearchDoctorTitle(searchDoctorTitle);
//        provideViewDoctorExpertRecommend.setShowNum("4");

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideViewMyDoctorSigning);
                    String urlStr = Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorSigningShow";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorSigningShow");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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

    /**
     * 设置搜索条件
     * @param mSearchProvice
     * @param mSearchCity
     * @param mSearchArea
     * @param mSearchName
     * @param mSearchJGBJ
     * @param mSearchYSZC
     */
    public void setSearchCondition(String mSearchProvice, String mSearchCity, String mSearchArea, String mSearchName, String mSearchJGBJ, String mSearchYSZC) {
        mRowNum = 10;
        mNumPage = 1;
        this.mSearchName = mSearchName;
        this.mSearchProvice = mSearchProvice;
        this.mSearchCity = mSearchCity;
        this.mSearchArea = mSearchArea;
        this.mSearchJGBJ = mSearchJGBJ;
        this.mSearchYSZC = mSearchYSZC;
        provideViewMyDoctorSignings.clear();
        getDate(this.mSearchName,this.mSearchProvice,this.mSearchCity,this.mSearchArea,this.mSearchJGBJ,this.mSearchYSZC);
    }
}
