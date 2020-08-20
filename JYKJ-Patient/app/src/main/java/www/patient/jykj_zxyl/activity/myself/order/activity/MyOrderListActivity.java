package www.patient.jykj_zxyl.activity.myself.order.activity;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.patient.jykj_zxyl.activity.myself.order.fragment.OrderCompletedFragment;
import www.patient.jykj_zxyl.activity.myself.order.fragment.OrderOnGoingFragment;
import www.patient.jykj_zxyl.activity.myself.order.fragment.OrderToBeConfirmedFragment;
import www.patient.jykj_zxyl.base.base_activity.BaseActivity;
import www.patient.jykj_zxyl.base.base_view.BaseToolBar;
import www.patient.jykj_zxyl.util.MyViewPager;

/**
 * Description:我的订单列表
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 10:37
 */
public class MyOrderListActivity extends BaseActivity {
    private BaseToolBar mToolBar;//顶部toolBar
    private TabLayout mTabLayout;
    private MyViewPager mVpager;
    private List<Fragment> mFragments;//Fragment集合列表
    private List<String> mTitles;//标题集合列表
    private FragmentAdapter fragmentAdapter;
    private OrderToBeConfirmedFragment orderToBeConfirmedFragment;
    private OrderOnGoingFragment orderOnGoingFragment;
    private OrderCompletedFragment orderCompletedFragment;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        mFragments=new ArrayList<>();
        mTitles=new ArrayList<>();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_order_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mToolBar=findViewById(R.id.toolbar);
        mTabLayout=findViewById(R.id.tab_layout);
        mVpager=findViewById(R.id.vp_pager);

        setToolBar();
        initTabLayout();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        mToolBar.setMainTitle("我的订单");
        //返回键
        mToolBar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout(){
        mTitles.add("待确认");
        mTitles.add("进行中");
        mTitles.add("已完成");
        orderToBeConfirmedFragment = OrderToBeConfirmedFragment.newInstance();
        mFragments.add(orderToBeConfirmedFragment);
        orderOnGoingFragment = OrderOnGoingFragment.newInstance();
        mFragments.add(orderOnGoingFragment);
        orderCompletedFragment = OrderCompletedFragment.newInstance();
        mFragments.add(orderCompletedFragment);
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),
                mFragments, mTitles);
        mVpager.setAdapter(fragmentAdapter);
        mVpager.setOffscreenPageLimit(2);
        mVpager.setScrollble(false);
        mTabLayout.setupWithViewPager(mVpager);//与ViewPage建立关系
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1001){
            orderOnGoingFragment.refreshLaodData();
        }else if(resultCode==1000){
            orderToBeConfirmedFragment.refreshLaodData();
        }
    }
}
