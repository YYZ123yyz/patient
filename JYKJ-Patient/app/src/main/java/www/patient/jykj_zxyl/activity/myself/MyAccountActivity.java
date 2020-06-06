package www.patient.jykj_zxyl.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.ActivityUtil;

public class MyAccountActivity extends AppCompatActivity {

    private                 TextView                    mPhoneLogin;                //手机号登录
    private                 TextView                    mUseRegist;                 //用户注册
    private                 Button                      mLogin;                     //登录
    private                 Context                     mContext;
    private                 MyAccountActivity           mActivity;
    private                 LinearLayout                mYSurplus;                  //我的余额
    private                 LinearLayout                mMyIntegral;                //我的积分
    private                 LinearLayout                mCoupon;                    //我的优惠券
    private                 LinearLayout                mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_myaccount);
        mContext = this;
        mActivity = this;
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mYSurplus = (LinearLayout)this.findViewById(R.id.activity_myAccount_mySurplus);
        mMyIntegral = (LinearLayout)this.findViewById(R.id.activity_myAccount_myIntegral);
        mCoupon = (LinearLayout)this.findViewById(R.id.activity_myAccount_myCoupon);
        mBack = (LinearLayout) findViewById(R.id.ll_back);
        mCoupon.setOnClickListener(new ButtonClick());
        mMyIntegral.setOnClickListener(new ButtonClick());
        mYSurplus.setOnClickListener(new ButtonClick());
        mBack.setOnClickListener(new ButtonClick());
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_myAccount_mySurplus:
                    //我的余额
                    Intent intent = new Intent();
                    intent.setClass(mContext,MySurplusActivity.class);
                    startActivity(intent);
                    break;
                case R.id.activity_myAccount_myIntegral:
                    //我的积分
                    intent = new Intent();
                    intent.setClass(mContext,MyIntegralInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.activity_myAccount_myCoupon:
                    //我的优惠券
                    intent = new Intent();
                    intent.setClass(mContext,MyCouponActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_back:
                        finish();
                        break;
            }
        }
    }



}
