package www.patient.jykj_zxyl.activity.myself.order.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_activity.BaseActivity;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.OrderAbstractDetialBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.enum_type.OrderStatusEnum;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.DateTimeUtils;
import www.patient.jykj_zxyl.util.ToastUtils;

/**
 * Description:退款详情
 *
 * @author: qiuxinhai
 * @date: 2020-08-03 18:34
 */
public class RefundResultActivity extends BaseActivity {
    private ImageView ivBackLeft;
    private TextView tvTitle;
    private TextView tvTitleTip;
    private TextView tvTitleTipDate;
    private LinearLayout llStartRefundRoot;
    private RelativeLayout rlRefundRoot;
    private RelativeLayout rlRefundMoneyRoot;
    private TextView tvRefundMoney;
    private TextView tvRefundPath;
    private JYKJApplication mApp;
    private TextView tvRevokeBtn;
    private String mainDoctorCode;
    private String mainDoctorName;
    private int  orderState;
    private String orderId;
    private String dateTime;
    private String signNo;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            mainDoctorCode = extras.getString("mainDoctorCode");
            mainDoctorName = extras.getString("mainDoctorName");
            orderState = extras.getInt("orderState");
            orderId = extras.getString("orderId");
            dateTime = extras.getString("dateTime");
            signNo=extras.getString("signNo");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_refund_result;
    }

    @Override
    protected void initView() {
        super.initView();

        ActivityUtil.setStatusBarMain(this,R.color.color_48C2F4);
        mApp = (JYKJApplication)this.getApplication();
        ivBackLeft= findViewById(R.id.iv_back_left);
        tvTitle=findViewById(R.id.tv_title);
        tvTitleTip=findViewById(R.id.tv_title_tip);
        tvTitleTipDate=findViewById(R.id.tv_title_tip_date);
        llStartRefundRoot=findViewById(R.id.ll_start_refund_root);
        rlRefundRoot=findViewById(R.id.rl_refund_root);
        rlRefundMoneyRoot=findViewById(R.id.rl_refund_money_root);
        tvRefundMoney=findViewById(R.id.tv_refund_money);
        tvRefundPath=findViewById(R.id.tv_refund_path);
        tvRevokeBtn=findViewById(R.id.tv_revoke_btn);
        String state = Integer.toString(orderState);
        if (state.equals(OrderStatusEnum.orderRefundingCode)) {
            llStartRefundRoot.setVisibility(View.VISIBLE);
            rlRefundRoot.setVisibility(View.VISIBLE);
            rlRefundMoneyRoot.setVisibility(View.GONE);
        }else if(state.equals(OrderStatusEnum.orderAdvenceCancelContractCode)){
            llStartRefundRoot.setVisibility(View.GONE);
            rlRefundRoot.setVisibility(View.VISIBLE);
            rlRefundMoneyRoot.setVisibility(View.VISIBLE);
            tvTitleTip.setText("退款成功");
            tvTitleTipDate.setText(dateTime);
        }
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        tvTitle.setText("退款详情");
        if (orderState==120) {
            getOrderMsgByCode(orderId);
        }
    }

    private void addListener(){
        ivBackLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefundResultActivity.this.finish();
            }
        });
        tvRevokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
//                hashMap.put("loginDoctorPosition",ParameUtil.loginDoctorPosition);
//                hashMap.put("mainDoctorCode",mainDoctorCode);
//                hashMap.put("mainDoctorName",mainDoctorName);
//                hashMap.put("signCode",orderId);
//                hashMap.put("signNo",signNo);
//                hashMap.put("mainPatientCode",
//                        mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
//                hashMap.put("mainUserName"
//                        ,mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
//                String s = RetrofitUtil.encodeParam(hashMap);
//                ApiHelper.getApiService().operTerminationRevoke(s).compose(
//                        Transformer.switchSchedulers(new ILoadingView() {
//                            @Override
//                            public void showLoadingView() {
//                                showLoading("",null);
//                            }
//
//                            @Override
//                            public void hideLoadingView() {
//                                dismissLoading();
//
//                            }
//                        })).subscribe(new CommonDataObserver() {
//                    @Override
//                    protected void onSuccessResult(BaseBean baseBean) {
//                        int resCode = baseBean.getResCode();
//                        if (resCode==1) {
//                            RefundResultActivity.this.finish();
//                        }else{
//                            ToastUtils.showToast(baseBean.getResMsg());
//                        }
//                    }
//
//                    @Override
//                    protected void onError(String s) {
//                        super.onError(s);
//                        ToastUtils.showToast(s);
//                    }
//                });
            }
        });
    }

    /**
     * 获取订单数据
     * @param code 订单Id
     */
    private void getOrderMsgByCode(String code){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition",ParameUtil.loginDoctorPosition);
        hashMap.put("mainPatientCode",mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        hashMap.put("mainUserName",mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        hashMap.put("signCode",code);
        String s = RetrofitUtil.encodeParam(hashMap);

        ApiHelper.getApiService().serchSignInfoByPatientCode(s)
                .compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    OrderAbstractDetialBean orderAbstractDetialBean = GsonUtils.fromJson(
                            baseBean.getResJsonData(), OrderAbstractDetialBean.class);
                    if (orderAbstractDetialBean!=null) {
                        long terminationTime = orderAbstractDetialBean.getTerminationTime();
                        tvTitleTipDate.setText(DateTimeUtils.formatLongDate(terminationTime,
                                "yyyy-MM-dd HH:mm:ss"));
                        tvRefundMoney.setText(String.format("¥%s", orderAbstractDetialBean.getSignPrice()));
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
