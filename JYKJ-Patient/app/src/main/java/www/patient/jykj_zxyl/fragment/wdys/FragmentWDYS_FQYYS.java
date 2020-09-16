package www.patient.jykj_zxyl.fragment.wdys;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;

import java.util.ArrayList;
import java.util.List;

import entity.ProvidePatientBindingMyDoctorInfo;
import entity.ProvideViewMyDoctorSigning;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJLActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJLListActivity;
import www.patient.jykj_zxyl.activity.home.patient.ZJXQ_ZJBDActivity;
import www.patient.jykj_zxyl.activity.home.twjz.WDZS_WZXQActivity;
import www.patient.jykj_zxyl.activity.hyhd.ChatActivity;
import www.patient.jykj_zxyl.activity.myself.UserAuthenticationActivity;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeWDYSFQYYSAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeWDYSFQYYSAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.myappointment.activity.ReservationActivity;


/**
 * 我的医生 == 非签约医生
 * Created by admin on 2016/6/1.
 */
public class FragmentWDYS_FQYYS extends Fragment {
    private             Context                             mContext;
    private WDYSActivity mActivity;
    private             Handler                             mHandler;
    private             JYKJApplication                     mApp;

    private RecyclerView mRecyclerView;
    private FragmentHomeWDYSFQYYSAdapter mAdapter;
    private LinearLayout llBack;

    private             List<ProvidePatientBindingMyDoctorInfo>    providePatientBindingMyDoctorInfos = new ArrayList<>();
    private             boolean                             loadDate;

    private                 int                         mNumPage = 1;                  //页数（默认，1）
    private                 int                         mRowNum = 10;                  //每页加载10条
    private         String                      mNetRetStr;                 //返回字符串
    public ProgressDialog mDialogProgress =null;

    private TextView tv_zjtj;

    private         String              mSearchName = "";
    private         String              mSearchProvice = "";
    private         String              mSearchCity = "";
    private         String              mSearchArea = "";
    private         String              mSearchJGBJ = "";
    private         String              mSearchYSZC = "";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_wdys_yslb, container, false);
        mContext = getContext();
        mActivity = (WDYSActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        providePatientBindingMyDoctorInfos.clear();
        getDate(this.mSearchName,this.mSearchProvice,this.mSearchCity,this.mSearchArea,this.mSearchJGBJ,this.mSearchYSZC);
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
        mAdapter = new FragmentHomeWDYSFQYYSAdapter(providePatientBindingMyDoctorInfos,mContext);
        mRecyclerView.setAdapter(mAdapter);
        //解绑
      /*  mAdapter.setOnItemClickXYListener(new FragmentHomeWDYSFQYYSAdapter.OnItemClickXYListener() {
            @Override
            public void onClick(int position) {
                // 下面的参数上下文 对话框里面一般都用this
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("温馨提示");
                builder.setMessage("确定解除绑定关系");
                // 设置取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // 设置确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //接触绑定
                        unBindMyDoctor(position);
                    }
                });
                builder.setCancelable(false);
                // 和Toast一样 最后一定要show 出来
                builder.show();
//                startActivity(new Intent(mContext,ZJXQActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommendList.get(position)));
            }

            @Override
            public void onLongClick(int position) {

            }
        });*/

        //
               mAdapter.setOnItemClickSQBDListener(new FragmentHomeWDYSFQYYSAdapter.OnItemClickSQBDListener() {

            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext, ReservationActivity.class));

//                Toast.makeText(mContext,"申请绑定",Toast.LENGTH_SHORT).show();
//                ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend = new ProvideViewDoctorExpertRecommend();
//                provideViewDoctorExpertRecommend.setDoctorCode(providePatientBindingMyDoctorInfos.get(position).getDoctorCode());
//                provideViewDoctorExpertRecommend.setUserName(providePatientBindingMyDoctorInfos.get(position).getUserName());
//                startActivity(new Intent(mContext,ZJXQ_ZJBDActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommend));
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //咨询
        mAdapter.setOnItemClickZXListener(new FragmentHomeWDYSFQYYSAdapter.OnItemClickZXListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(mContext,ChatActivity.class);
              //  intent.setClass();
                intent.putExtra("userCode",providePatientBindingMyDoctorInfos.get(position).getDoctorCode());
                intent.putExtra("userName",providePatientBindingMyDoctorInfos.get(position).getUserName());
//                intent.putExtra("chatType","twjz");
                intent.putExtra("doctorUrl",providePatientBindingMyDoctorInfos.get(position).getUserLogoUrl());

                intent.putExtra("loginDoctorPosition",mApp.loginDoctorPosition);
                intent.putExtra("operDoctorCode",mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                intent.putExtra("operDoctorName",mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());

                intent.putExtra("doctorUrl",providePatientBindingMyDoctorInfos.get(position).getUserLogoUrl());
                intent.putExtra("patientUrl",mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());

//                intent.putExtra("orderCode",provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getOrderCode());

//                intent.putExtra(EaseConstant.EXTRA_MESSAGE_NUM,providePatientBindingMyDoctorInfos.get(position).getLimitImgText());           //消息数量
//                intent.putExtra(EaseConstant.EXTRA_VOICE_NUM,providePatientBindingMyDoctorInfos.get(position).getLimitAudio());           //音频时长（单位：秒）
//                intent.putExtra(EaseConstant.EXTRA_VEDIO_NUM,providePatientBindingMyDoctorInfos.get(position).getLimitVideo());           //视频时长（单位：秒）
                startActivity(intent);
//                startActivity(new Intent(mContext,ZJXQActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommendList.get(position)));
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        //就诊记录
        mAdapter.setOnItemClickJZJLListener(new FragmentHomeWDYSFQYYSAdapter.OnItemClickJZJLListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,WDYS_JZJLListActivity.class).putExtra("providePatientBindingMyDoctorInfo",providePatientBindingMyDoctorInfos.get(position)));
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
     * 解除绑定
     * @param position
     */
    private void unBindMyDoctor(int position) {
        ProvidePatientBindingMyDoctorInfo providePatientBindingMyDoctorInfo = new ProvidePatientBindingMyDoctorInfo();
        providePatientBindingMyDoctorInfo.setLoginUserPosition(mApp.loginDoctorPosition);
        providePatientBindingMyDoctorInfo.setRequestClientType("1");
        providePatientBindingMyDoctorInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        providePatientBindingMyDoctorInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        providePatientBindingMyDoctorInfo.setDpBindingId(providePatientBindingMyDoctorInfos.get(position).getDpBindingId());
        providePatientBindingMyDoctorInfo.setBindingDoctorCode(providePatientBindingMyDoctorInfos.get(position).getDoctorCode());
        providePatientBindingMyDoctorInfo.setBindingDoctorName(providePatientBindingMyDoctorInfos.get(position).getUserName());

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(providePatientBindingMyDoctorInfo);
                    String urlStr = Constant.SERVICEURL+"PatientMyDoctorControlle/operIndexMyDoctorBindingCancel";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"PatientMyDoctorControlle/operIndexMyDoctorBindingCancel");
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
                            List<ProvidePatientBindingMyDoctorInfo> list = JSON.parseArray(netRetEntity.getResJsonData(),ProvidePatientBindingMyDoctorInfo.class);

                            if (list == null || list.size() == 0)
                                loadDate = false;
                            else
                                providePatientBindingMyDoctorInfos.addAll(list);
                            if (list.size() < mRowNum )
                            {
                                loadDate = false;
                            }
                        }
                        mAdapter.setDate(providePatientBindingMyDoctorInfos);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 6:
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        else
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            mNumPage = 1;
                            mRowNum = 10;
                            providePatientBindingMyDoctorInfos.clear();
                            getDate(mSearchName,mSearchProvice,mSearchCity,mSearchArea,mSearchJGBJ,mSearchYSZC);
                        }
                        break;
                }
            }
        };
    }

    /**
     * 搜索
     */
    public void getDate(String searchName,String searchProvince,String searchCity,String searchArea,String searchHospitalType,String searchDoctorTitle) {
        if (mNumPage == 1)
            providePatientBindingMyDoctorInfos.clear();
        ProvidePatientBindingMyDoctorInfo providePatientBindingMyDoctorInfo = new ProvidePatientBindingMyDoctorInfo();
        providePatientBindingMyDoctorInfo.setRowNum(mRowNum+"");
        providePatientBindingMyDoctorInfo.setPageNum(mNumPage+"");
        providePatientBindingMyDoctorInfo.setLoginUserPosition(mApp.loginDoctorPosition);
        providePatientBindingMyDoctorInfo.setRequestClientType("1");
        providePatientBindingMyDoctorInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        providePatientBindingMyDoctorInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        providePatientBindingMyDoctorInfo.setSearchName(searchName);
        providePatientBindingMyDoctorInfo.setSearchProvince(searchProvince);
        providePatientBindingMyDoctorInfo.setSearchCity(searchCity);
        providePatientBindingMyDoctorInfo.setSearchArea(searchArea);
        providePatientBindingMyDoctorInfo.setSearchHospitalType(searchHospitalType);
        providePatientBindingMyDoctorInfo.setSearchDoctorTitle(searchDoctorTitle);

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(providePatientBindingMyDoctorInfo);
                    String urlStr = Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorNotSigningShow";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorNotSigningShow");
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
     * 按条件搜索
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
        providePatientBindingMyDoctorInfos.clear();
        getDate(this.mSearchName,this.mSearchProvice,this.mSearchCity,this.mSearchArea,this.mSearchJGBJ,this.mSearchYSZC);
    }
}

