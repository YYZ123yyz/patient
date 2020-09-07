package www.patient.jykj_zxyl.fragment.wdys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allin.commlibrary.CollectionUtils;
import com.allin.commlibrary.StringUtils;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entity.ProvidePatientBindingMyDoctorInfo;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.home.patient.ZJXQActivity;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeTJZJAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeWDYSFQYYSAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentWDYS_GZYSdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_view.LoadingLayoutManager;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentRMJX;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentWDSC;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentYXWX;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentYXXW;
import www.patient.jykj_zxyl.fragment.ylzx.FragmentYXZN;


/**
 * 我的医生 == 关注医生
 * Created by admin on 2016/6/1.
 */
public class FragmentWDYS_GZYS extends Fragment {
    private Context mContext;
    private WDYSActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;

    private RecyclerView mRecyclerView;
    private FragmentWDYS_GZYSdapter mAdapter;
    private LinearLayout llBack;

    private List<ProvideViewDoctorExpertRecommend> provideViewDoctorExpertRecommends = new ArrayList<>();
    private boolean loadDate;

    private int mNumPage = 1;                  //页数（默认，1）
    private int mRowNum = 10;                  //每页加载10条
    private String mNetRetStr;                 //返回字符串
    public ProgressDialog mDialogProgress = null;

    private TextView tv_zjtj;

    private String mSearchName = "";
    private String mSearchProvice = "";
    private String mSearchCity = "";
    private String mSearchArea = "";
    private String mSearchJGBJ = "";
    private String mSearchYSZC = "";

    private SmartRefreshLayout mRefreshLayout;//刷新列表
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_wdys_yslb, container, false);
        mContext = getContext();
        mActivity = (WDYSActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        getDate(this.mSearchName, this.mSearchProvice, this.mSearchCity, this.mSearchArea, this.mSearchJGBJ, this.mSearchYSZC);
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
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new FragmentWDYS_GZYSdapter(provideViewDoctorExpertRecommends, mContext);
        mRecyclerView.setAdapter(mAdapter);

      /*  mAdapter.setOnItemClickListener(new FragmentWDYS_GZYSdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext, ZJXQActivity.class).putExtra("provideViewDoctorExpertRecommend"
                        , provideViewDoctorExpertRecommends.get(position)));
            }

            @Override
            public void onLongClick(int position) {

            }
        });*/

        mAdapter.setOnItemClickQXGZListener(new FragmentWDYS_GZYSdapter.OnItemClickQXGZListener() {
            @Override
            public void onClick(int position) {
                // 下面的参数上下文 对话框里面一般都用this
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("温馨提示");
                builder.setMessage("确定不再关注？");
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
                        operIndexMyDoctorBindingCancel(position);
                    }
                });
                builder.setCancelable(false);
                // 和Toast一样 最后一定要show 出来
                builder.show();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

//        //解绑
////        mAdapter.setOnItemClickXYListener(new FragmentHomeWDYSFQYYSAdapter.OnItemClickXYListener() {
////            @Override
////            public void onClick(int position) {
////                Toast.makeText(mContext, "解绑", Toast.LENGTH_SHORT).show();
//////                startActivity(new Intent(mContext,ZJXQActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommendList.get(position)));
////            }
////
////            @Override
////            public void onLongClick(int position) {
////
////            }
////        });


//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
//                    if (lastVisiblePosition >= manager.getItemCount() - 1) {
//                        if (loadDate) {
//                            mNumPage++;
//                            getDate(mSearchName, mSearchProvice, mSearchCity, mSearchArea, mSearchJGBJ, mSearchYSZC);
//                        }
//
//                    }
//                }
//            }
//        });

        tv_zjtj = (TextView) view.findViewById(R.id.tv_zjtj);
        tv_zjtj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TJZJActivity.class));
            }
        });
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(this.getContext())));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this.getContext()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mNumPage=1;
                getDate(mSearchName,
                        mSearchProvice,
                        mSearchCity,
                        mSearchArea,
                        mSearchJGBJ, mSearchYSZC);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mNumPage++;
                getDate(mSearchName, mSearchProvice, mSearchCity, mSearchArea, mSearchJGBJ, mSearchYSZC);

            }
        });
        initLoadingAndRetryManager();
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
            mNumPage = 1;
            getDate(mSearchName, mSearchProvice, mSearchCity, mSearchArea,
                    mSearchJGBJ,
                    mSearchYSZC);
        });
        mLoadingLayout.showLoading();
    }


    /**
     * 取消关注
     *
     * @param position
     */
    private void operIndexMyDoctorBindingCancel(int position) {
        ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend = new ProvideViewDoctorExpertRecommend();
        provideViewDoctorExpertRecommend.setLoginUserPosition(mApp.loginDoctorPosition);
        provideViewDoctorExpertRecommend.setRequestClientType("1");
        provideViewDoctorExpertRecommend.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewDoctorExpertRecommend.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideViewDoctorExpertRecommend.setPcBindingId(provideViewDoctorExpertRecommends.get(position).getPcBindingId());
        provideViewDoctorExpertRecommend.setCollectDoctorCode(provideViewDoctorExpertRecommends.get(position).getDoctorCode());
        provideViewDoctorExpertRecommend.setCollectDoctorName(provideViewDoctorExpertRecommends.get(position).getUserName());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideViewDoctorExpertRecommend);
                    String urlStr = Constant.SERVICEURL + "PatientMyDoctorControlle/operIndexMyDoctorCollectCancel";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "PatientMyDoctorControlle/operIndexMyDoctorCollectCancel");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(6);
            }
        }.start();
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 5:
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1 ) {
                            String resJsonData = netRetEntity.getResJsonData();
                            if (StringUtils.isNotEmpty(resJsonData)) {
                                List<ProvideViewDoctorExpertRecommend> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvideViewDoctorExpertRecommend.class);
                                if (mNumPage == 1){
                                    provideViewDoctorExpertRecommends.clear();
                                }
                                if (list == null || list.size() == 0)
                                    loadDate = false;
                                else
                                    provideViewDoctorExpertRecommends.addAll(list);
                                if (list.size() < mRowNum) {
                                    loadDate = false;
                                }
                                mLoadingLayout.showContent();
                            }else{
                                if(mNumPage==1){
                                    mLoadingLayout.showEmpty();
                                }else{
                                    mRefreshLayout.finishLoadMore();
                                }
                            }

                        }else{
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.finishLoadMore();
                            mLoadingLayout.showError();
                        }
                        mAdapter.setDate(provideViewDoctorExpertRecommends);
                        mAdapter.notifyDataSetChanged();
                        if(mNumPage==1){
                            mRefreshLayout.finishRefresh();
                        }else{
                            mRefreshLayout.finishLoadMore();
                        }
                        break;
                    case 6:
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            mNumPage = 1;
                            mRowNum = 10;
                            provideViewDoctorExpertRecommends.clear();
                            getDate(mSearchName, mSearchProvice, mSearchCity, mSearchArea, mSearchJGBJ, mSearchYSZC);
                        }
                        break;
                }
            }
        };
    }

    /**
     * 搜索
     */
    public void getDate(String searchName, String searchProvince, String searchCity, String searchArea, String searchHospitalType, String searchDoctorTitle) {
        ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend = new ProvideViewDoctorExpertRecommend();
        provideViewDoctorExpertRecommend.setRowNum(mRowNum + "");
        provideViewDoctorExpertRecommend.setPageNum(mNumPage + "");
        provideViewDoctorExpertRecommend.setLoginUserPosition(mApp.loginDoctorPosition);
        provideViewDoctorExpertRecommend.setRequestClientType("1");
        provideViewDoctorExpertRecommend.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewDoctorExpertRecommend.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideViewDoctorExpertRecommend.setSearchName(searchName);
        provideViewDoctorExpertRecommend.setSearchProvince(searchProvince);
        provideViewDoctorExpertRecommend.setSearchCity(searchCity);
        provideViewDoctorExpertRecommend.setSearchArea(searchArea);
        provideViewDoctorExpertRecommend.setSearchHospitalType(searchHospitalType);
        provideViewDoctorExpertRecommend.setSearchDoctorTitle(searchDoctorTitle);
//        provideViewDoctorExpertRecommend.setShowNum("4");

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideViewDoctorExpertRecommend);
                    String urlStr = Constant.SERVICEURL + "PatientMyDoctorControlle/searchIndexMyDoctorCollectShow";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "PatientMyDoctorControlle/searchIndexMyDoctorCollectShow");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
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
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }


    /**
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
        provideViewDoctorExpertRecommends.clear();
        getDate(this.mSearchName, this.mSearchProvice, this.mSearchCity, this.mSearchArea, this.mSearchJGBJ, this.mSearchYSZC);
    }
}
