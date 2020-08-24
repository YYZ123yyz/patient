package www.patient.jykj_zxyl.activity.myself;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.couponFragment.AlreadyUserFragment;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.activity.myself.couponFragment.WithoutUserFragment;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.fragment.myself.FragmentMyOrderAl;
import www.patient.jykj_zxyl.fragment.myself.FragmentMyOrderNo;
import www.patient.jykj_zxyl.fragment.myself.FragmentMyOrderOn;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.MyViewPager;

/**
 * 个人中心 ==> 我的订单
 */
public class MyOrderActivity extends AppCompatActivity {

    private Context mContext;
    private MyOrderActivity mActivity;
    public ProgressDialog mDialogProgress = null;

    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串


    private Handler mHandler;

    private RelativeLayout ri_back;

    private MyViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;


    //    private                 ProvideViewSysUserDoctorInfoAndHospital  mProvideViewSysUserDoctorInfoAndHospital = new ProvideViewSysUserDoctorInfoAndHospital();  //医生信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_myorder);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();

    }


    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        break;
                    case 1:
                        cacerProgress();
                        setLayoutDate();
                        break;
                    case 3:

                    case 4:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        break;
                }
            }
        };
    }


    /**
     * 设置布局显示
     */
    private void setLayoutDate() {

    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        ri_back = (RelativeLayout) this.findViewById(R.id.ri_back);
        ri_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pager = (MyViewPager) this.findViewById(R.id.page);
        tabLayout = (TabLayout) this.findViewById(R.id.tab_layout);

        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("未完成");
        mTitles.add("进行中");
        mTitles.add("已完成");
        fragmentList.add(new FragmentMyOrderNo());
        fragmentList.add(new FragmentMyOrderOn());
        fragmentList.add(new FragmentMyOrderAl());
        fragmentAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager(), fragmentList, mTitles);
        pager.setAdapter(fragmentAdapter);
        pager.setScrollble(false);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tv_activityUserCenter_commit:
//                    commit();
                    break;
            }
        }
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
