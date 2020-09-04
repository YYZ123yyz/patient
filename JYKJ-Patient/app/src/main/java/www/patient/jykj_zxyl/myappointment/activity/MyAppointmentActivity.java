package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.myappointment.fragment.FragmentCompleted;
import www.patient.jykj_zxyl.myappointment.fragment.FragmentVisiting;
import www.patient.jykj_zxyl.util.ActivityUtil;

public class MyAppointmentActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private MyAppointmentActivity mActivity;
    private JYKJApplication mApp;
    private List<Fragment> fragmentList;
    private List<String> mTitles;
    private FragmentAdapter fragmentAdapter;
    private ViewPager page;
    private TabLayout tabLayout;
    private RelativeLayout riBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myappointment_layout);
        mContext = this;
        mApp = (JYKJApplication) getApplication();
        mActivity = MyAppointmentActivity.this;
        ActivityUtil.setStatusBarMain(this);
        initLayout();
    }

    private void initLayout() {
        riBack = findViewById(R.id.ri_back);
        riBack.setOnClickListener(this);
        page = findViewById(R.id.page);
        tabLayout = findViewById(R.id.tab_layout);
        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("就诊中");
        mTitles.add("已完成");
        fragmentList.add(new FragmentVisiting());
        fragmentList.add(new FragmentCompleted());

        fragmentAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager(), fragmentList, mTitles);
        page.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(page);//与ViewPage建立关系
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ri_back:
                finish();
                break;
        }
    }
}
