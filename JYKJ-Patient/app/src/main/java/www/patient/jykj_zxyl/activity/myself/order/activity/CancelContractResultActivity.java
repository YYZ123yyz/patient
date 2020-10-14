package www.patient.jykj_zxyl.activity.myself.order.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allin.commlibrary.CollectionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.easeui.order.SignOrderDetialActivity;
import com.hyphenate.easeui.ui.ChatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_activity.BaseActivity;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.OrderAbstractDetialBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.enum_type.OrderStatusEnum;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.DateTimeUtils;


/**
 * Description:解约结果
 *
 * @author: qiuxinhai
 * @date: 2020-08-05 09:47
 */
public class CancelContractResultActivity extends BaseActivity {
    private ImageView ivBackLeft;
    private TextView tvTitle;
    private TextView tvTitleTip;
    private TextView tvTitleTipDate;
    private LinearLayout llStartCancelContractRoot;
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
    private  OrderDetialBean orderDetialBean;
    private List<OrderDetialBean.OrderDetailListBean> monitorTypeList;
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
        monitorTypeList=new ArrayList<>();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_cancel_result;
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
        llStartCancelContractRoot=findViewById(R.id.ll_start_cancel_contract_root);
        rlRefundRoot=findViewById(R.id.rl_refund_root);
        rlRefundMoneyRoot=findViewById(R.id.rl_refund_money_root);
        tvRefundMoney=findViewById(R.id.tv_refund_money);
        tvRefundPath=findViewById(R.id.tv_refund_path);
        tvRevokeBtn=findViewById(R.id.tv_revoke_btn);


        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        tvTitle.setText("解约详情");
        getOrderMsgByCode(orderId);
    }

    private void addListener(){
        ivBackLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelContractResultActivity.this.finish();
            }
        });
        tvRevokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderDetialBean==null){
                    return;
                }
                HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
                hashMap.put("loginDoctorPosition",ParameUtil.loginDoctorPosition);
                hashMap.put("mainDoctorCode",mainDoctorCode);
                hashMap.put("mainDoctorName",mainDoctorName);
                hashMap.put("signCode",orderId);
                hashMap.put("signNo",signNo);
                hashMap.put("mainPatientCode",
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                hashMap.put("mainUserName"
                        ,mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                String s = RetrofitUtil.encodeParam(hashMap);
                ApiHelper.getApiService().operTerminationRevoke(s).compose(
                        Transformer.switchSchedulers(new ILoadingView() {
                            @Override
                            public void showLoadingView() {
                                showLoading("",null);
                            }

                            @Override
                            public void hideLoadingView() {


                            }
                        })).subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        int resCode = baseBean.getResCode();
                        if (resCode==1) {
                           // CancelContractResultActivity.this.finish();
                            getUserInfo(mainDoctorCode);
                        }else{
                            ToastUtils.showShort(baseBean.getResMsg());
                        }
                    }

                    @Override
                    protected void onError(String s) {
                        super.onError(s);
                        ToastUtils.showShort(s);
                    }
                });
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
        hashMap.put("signOrderCode",code);
        hashMap.put("operDoctorCode",mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        hashMap.put("operDoctorName",mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        String s = RetrofitUtil.encodeParam(hashMap);

        ApiHelper.getPatientTestApi().searchSignPatientDoctorOrder(s)
                .compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                     orderDetialBean = GsonUtils.fromJson(
                            baseBean.getResJsonData(), OrderDetialBean.class);
                    if (orderDetialBean!=null) {

                        tvRefundMoney.setText(String.format("¥%s", orderDetialBean.getSignPrice()));
                        setLayoutVisible(orderDetialBean.getSignStatus());
                        handleOrderListResult(orderDetialBean);
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }
        });
    }

    /**
     * 设置页面显示状态
     * @param state 订单状态
     */
    private void setLayoutVisible(String state){
        if (state.equals(OrderStatusEnum.orderPatientCancelContractApplyCode)) {
            llStartCancelContractRoot.setVisibility(View.VISIBLE);
            rlRefundRoot.setVisibility(View.VISIBLE);
            rlRefundMoneyRoot.setVisibility(View.GONE);
            tvTitleTipDate.setText("请耐心等待1-3个工作日");
        }else if(state.equals(OrderStatusEnum.orderAdvenceCancelContractCode)){
            llStartCancelContractRoot.setVisibility(View.GONE);
            rlRefundRoot.setVisibility(View.VISIBLE);
            rlRefundMoneyRoot.setVisibility(View.VISIBLE);
            tvTitleTip.setText("解约成功");
            long terminationTime = orderDetialBean.getTerminationTime();
            tvTitleTipDate.setText(DateTimeUtils.formatLongDate(terminationTime,
                    "yyyy-MM-dd HH:mm"));
        }
    }


    /**
     * 获取用户信息
     * @param userCodeList 用户Id
     */
    private void getUserInfo(String userCodeList){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("userCodeList",userCodeList);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getUserInfoList(s).
                compose(Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {

                    }

                    @Override
                    public void hideLoadingView() {
                        dismissLoading();
                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    String resJsonData = baseBean.getResJsonData();
                    List<UserInfoBaseBean> userInfoBaseBeans = GsonUtils.jsonToList(resJsonData, UserInfoBaseBean.class);
                    if (!CollectionUtils.isEmpty(userInfoBaseBeans)) {
                        UserInfoBaseBean userInfoBaseBean = userInfoBaseBeans.get(0);
                        OrderMessage terminationOrder = getOrderMessage("terminationOrder", "3",orderDetialBean);

                        if (ActivityStackManager.getInstance().exists(ChatActivity.class)) {
                            ActivityStackManager.getInstance().finish(ChatActivity.class);
                        }
                        Intent intent = new Intent();
                        intent.setClass(CancelContractResultActivity.this, ChatActivity.class);
                        intent.putExtra("userCode", orderDetialBean.getMainDoctorCode());
                        intent.putExtra("userName", orderDetialBean.getMainDoctorName());
                        intent.putExtra("doctorUrl", userInfoBaseBean.getUserLogoUrl());
                        intent.putExtra("patientUrl", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
                        intent.putExtra("operDoctorName",mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                        terminationOrder.setImageUrl(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
                        terminationOrder.setIsPatient("1");
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("orderMsg",terminationOrder);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        setResult(1001);
                        CancelContractResultActivity.this.finish();
                    }

                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

        });
    }
    /**
     * 获取订单信息
     * @param messageType 消息类型
     * @param orderType 操作类型
     * @return orderMessage
     */
    private OrderMessage getOrderMessage(String messageType, String orderType, OrderDetialBean orderDetialBean) {
        @SuppressLint("DefaultLocale")
        String  monitorRate= String.format("一次/%d%s", orderDetialBean.getDetectRate(),
                orderDetialBean.getDetectRateUnitName());
        return new OrderMessage(orderId,orderDetialBean.getSignNo(),
                monitorTypeList.size() + "项", monitorRate,
                orderDetialBean.getSignDuration()+ orderDetialBean.getSignDurationUnit()
                , orderDetialBean.getSignPrice() + "", messageType, orderType,orderDetialBean.getSignCode());

    }

    /**
     * 处理订单详情数据
     * @param orderDetialData 订单详情
     */
    private void handleOrderListResult(OrderDetialBean orderDetialData) {
        monitorTypeList.clear();
        List<OrderDetialBean.OrderDetailListBean> orderDetailList = orderDetialData.getOrderDetailList();
        for (OrderDetialBean.OrderDetailListBean orderDetailListBean : orderDetailList) {
            String configDetailTypeCode = orderDetailListBean.getConfigDetailTypeCode();
            if (configDetailTypeCode.equals("10")) {
                monitorTypeList.add(orderDetailListBean);
            }

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
