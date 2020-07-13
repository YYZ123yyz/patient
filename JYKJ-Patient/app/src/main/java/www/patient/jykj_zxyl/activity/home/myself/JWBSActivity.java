package www.patient.jykj_zxyl.activity.home.myself;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.HZIfno;
import entity.patientInfo.ProvidePatientLabel;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.PatientLaberAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.fragment.myself.jwbs.FragmentJWBS_BRTX;
import www.patient.jykj_zxyl.fragment.myself.jwbs.FragmentJWBS_YSTX;
import www.patient.jykj_zxyl.util.MyViewPager;

/**
 * 个人中心==》建档档案==》==>既往病史
 */
public class JWBSActivity extends AppCompatActivity {


    private Context mContext;
    private JWBSActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private RecyclerView mRecycleView;

    private LinearLayoutManager layoutManager;
    private JYZL_GRZLRecycleAdapter mJYZL_GRZLRecycleAdapter;      //适配器
    private List<HZIfno> mHZEntyties = new ArrayList<>();            //所有数据
    private LinearLayout mJBXX;                                  //基本信息
    private String mPatientCode;                       //患者code

    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;                 //获取返回字符串

    private PatientLaberAdapter mPatientLaberAdapter;
    private List<ProvidePatientLabel> mProvidePatientLabel = new ArrayList<>();

    private LinearLayout li_back;

    private MyViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;

    private FragmentJWBS_BRTX mFragmentJWBS_BRTX;
    private FragmentJWBS_YSTX mFragmentJWBS_YSTX;
    private boolean noScroll = false;
    /**
     * 初始化布局
     */
    private void initLayout() {
        li_back = (LinearLayout) this.findViewById(R.id.li_back);
        li_back.setOnClickListener(new ButtonClick());

        pager = this.findViewById(R.id.page);
        tabLayout = (TabLayout) this.findViewById(R.id.tab_layout);

        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("本人填写");
        mTitles.add("医生填写");

        mFragmentJWBS_BRTX = new FragmentJWBS_BRTX();
        mFragmentJWBS_YSTX = new FragmentJWBS_YSTX();

        fragmentList.add(mFragmentJWBS_BRTX);
        fragmentList.add(mFragmentJWBS_YSTX);

        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragmentList, mTitles);
        pager.setAdapter(fragmentAdapter);
        pager.setScrollble(false);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系

    }


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_back:
                    finish();
                    break;

            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jwbs);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        initHandler();

    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        break;
                    case 1:
                        cacerProgress();
                        showViewDate();
                        break;
                    case 2:
                        break;
                }
            }
        };
    }


    /**
     * 设置显示
     */
    private void showViewDate() {


    }

    private String getDate(Date data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String string = simpleDateFormat.format(data);
        return string;
    }

    /**
     * 获取基本信息
     */
    private void getDate() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
                    provideViewSysUserPatientInfoAndRegion.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewSysUserPatientInfoAndRegion.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideViewSysUserPatientInfoAndRegion.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    provideViewSysUserPatientInfoAndRegion.setSearchPatientCode(mPatientCode);

                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideViewSysUserPatientInfoAndRegion), Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResHealthy");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取区域信息失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
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


}
