package com.hyphenate.easeui.order;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.allin.commlibrary.CollectionUtils;
import com.google.gson.Gson;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.entity.ProvideViewSysUserPatientInfoAndRegion;
import com.hyphenate.easeui.ui.ChatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.base_utils.DateUtils;
import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;
import www.patient.jykj_zxyl.base.base_view.BaseToolBar;
import www.patient.jykj_zxyl.base.base_view.LoadingLayoutManager;
import www.patient.jykj_zxyl.base.enum_type.OrderStatusEnum;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;

/**
 * Description:解约详情activity
 *
 * @author: qiuxinhai
 * @date: 2020-08-01 11:34
 */
public class CancelConfirmDeitalActivity extends AbstractMvpBaseActivity<CancelConfirmDetialContract.View,
        CancelConfirmDetialContractPresenter> implements CancelConfirmDetialContract.View {
    private BaseToolBar mToolBar;//顶部toolBar
    private String orderId;//订单Id
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private RelativeLayout mRlContentRoot;
    private LinearLayout mLlContentRoot;
    private TextView mTvCancelContractDesc;
    private TextView mTvSignStartTime;
    private TextView mTvMonitorTypeValue;
    private TextView mTvCoatchRateValue;
    private TextView mTvSignTimeValue;
    private TextView mTvSignOrderPrice;
    private TextView mTvRefuseBtn;
    private TextView mTvAgreeBtn;
    private TextView mTvCancelContractReason;
    private RelativeLayout mLLOperButtomRoot;
    private List<OrderDetialBean.OrderDetailListBean> monitorTypeList;
    private List<OrderDetialBean.OrderDetailListBean> coachTypeList;
    private static final String DATA_MONITOR_CODE = "10";//监测类型
    private static final String DATA_COATCH_CODE = "20";//辅导类型
    private OrderDetialBean orderDetialBean;
    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;
    private String operDoctorCode;
    private String operDoctorName;
    private String mType = "";
    private LinearLayout refuseLin;
    private TextView refuseReason;
    private TextView refuseDes;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        monitorTypeList = new ArrayList<>();
        coachTypeList = new ArrayList<>();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            orderId = extras.getString("orderId");
            operDoctorCode = extras.getString("operDoctorCode");
            operDoctorName = extras.getString("operDoctorName");
            mType = extras.getString("type");
        }


    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_cancel_contract_deital;
    }

    @Override
    protected void initView() {
        super.initView();
        mToolBar = findViewById(R.id.toolbar);
        mRlContentRoot = findViewById(R.id.rl_content_root);
        mTvCancelContractDesc = findViewById(R.id.tv_cancel_contract_desc);
        mTvSignStartTime = findViewById(R.id.tv_sign_start_time);
        mTvMonitorTypeValue = findViewById(R.id.tv_monitor_type_value);
        mTvCoatchRateValue = findViewById(R.id.tv_coatch_rate_value);
        mTvSignTimeValue = findViewById(R.id.tv_sign_time_value);
        mTvSignOrderPrice = findViewById(R.id.tv_sign_order_price);
        mTvRefuseBtn = findViewById(R.id.tv_refuse_btn);
        mTvAgreeBtn = findViewById(R.id.tv_agree_btn);
        mLlContentRoot = findViewById(R.id.ll_content_root);
        mLLOperButtomRoot = findViewById(R.id.ll_oper_buttom_root);
        mTvCancelContractReason = findViewById(R.id.tv_cancel_contract_reason);
        refuseLin = findViewById(R.id.lin_refuse);
        refuseReason = findViewById(R.id.tv_cancel_refuse_reason);
        refuseDes = findViewById(R.id.tv_refuse_contract_desc);
        setToolBar();
//        initLoadingAndRetryManager();
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        if (mType.equals("1") || mType.equals("4") || mType.equals("5") ||mType.equals("6") ||mType.equals("7")  ||mType.equals("8")) {
            HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
            hashMap.put("loginPatientPosition", ParameUtil.loginDoctorPosition);
            hashMap.put("requestClientType", "1");
            hashMap.put("orderCode", orderId);
            hashMap.put("searchPatientCode", operDoctorCode);
            hashMap.put("searchPatientName", operDoctorName);
            String s = RetrofitUtil.encodeParam(hashMap);
            mPresenter.getOrderDet(s);
        } else {
            SharedPreferences_DataSave jykjdocter = new SharedPreferences_DataSave(this, "JYKJDOCTER");
            String userInfoSuLogin = jykjdocter.getString("viewSysUserDoctorInfoAndHospital", "");
            try {
                mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
            } catch (Exception e) {

            }
            mPresenter.sendSearchOrderDetialRequest(orderId, operDoctorCode, operDoctorName);
        }

    }

    /**
     * 添加监听
     */
    private void addListener() {
        mTvRefuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orderId);
                bundle.putString("operDoctorCode", operDoctorCode);
                bundle.putString("operDoctorName", operDoctorName);
                startActivity(RefusedCancelContractActivity.class, bundle);
            }
        });
        mTvAgreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderDetialBean != null) {
                    mPresenter.sendCancelContractConConfirmRequest(
                            ParameUtil.loginDoctorPosition,
                            orderDetialBean.getMainDoctorCode()
                            , orderDetialBean.getMainDoctorName(),
                            orderDetialBean.getSignCode()
                            , orderDetialBean.getSignNo()
                            , orderDetialBean.getMainPatientCode()
                            , orderDetialBean.getMainUserName(),
                            "1", "", "", "");
                }

            }
        });
        mLlContentRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType.equals("1") || mType.equals("4") || mType.equals("5") ||mType.equals("6") ||mType.equals("7")  ||mType.equals("8")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("signCode", orderId);
                    bundle.putString("operDoctorCode", operDoctorCode);
                    bundle.putString("operDoctorName", operDoctorName);
                    startActivity(SignOrderDetialActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("signCode", orderId);
                    bundle.putString("operDoctorCode", operDoctorCode);
                    bundle.putString("operDoctorName", operDoctorName);
                    startActivity(SignOrderDetialActivity.class, bundle);
                }

            }
        });
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        mToolBar.setMainTitle("解约详情");
        //返回键
        mToolBar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mRlContentRoot);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();
            mPresenter.sendSearchOrderDetialRequest(orderId, operDoctorCode, operDoctorName);
        });
        mLoadingLayoutManager.showLoading();

    }


    @Override
    public void showLoading(int code) {
        if (code == 101) {
            showLoading("", null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    /**
     * 设置订单数据
     *
     * @param signOrderInfoBean 订单数据
     */
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void setOrderData(OrderDetialBean signOrderInfoBean) {
        String refuseReasonName = "";
        String refuseRemark = "";
        if (mType.equals("4")) { //患者发送,医生拒绝
            refuseLin.setVisibility(View.VISIBLE);
            refuseReasonName = signOrderInfoBean.getRelieveReasonClassName();
            refuseRemark = signOrderInfoBean.getRelieveRemark();
        } else if (mType.equals("5")) {  //接收的医生发过来的解约
            refuseLin.setVisibility(View.GONE);
            refuseReasonName = signOrderInfoBean.getRelieveReasonClassNameD();
            refuseRemark = signOrderInfoBean.getRelieveRemarkD();

        } else if (mType.equals("6")) { //医生同意解约
            refuseLin.setVisibility(View.GONE);
            refuseReasonName = signOrderInfoBean.getRelieveReasonClassName();
            refuseRemark = signOrderInfoBean.getRelieveRemark();

        }else if (mType.equals("7")) { //医生发送,患者同意
            refuseLin.setVisibility(View.GONE);
            refuseReasonName = signOrderInfoBean.getRelieveReasonClassNameD();
            refuseRemark = signOrderInfoBean.getRelieveRemarkD();

        } else if (mType.equals("8")) { //医生发送,患者拒绝
            refuseLin.setVisibility(View.VISIBLE);
            refuseReasonName = signOrderInfoBean.getRelieveReasonClassNameD();
            refuseRemark = signOrderInfoBean.getRelieveRemarkD();

        }
        else { //mType.equals("1") //患者发送解约
            refuseLin.setVisibility(View.GONE);
            refuseReasonName = signOrderInfoBean.getRelieveReasonClassName();
            refuseRemark = signOrderInfoBean.getRelieveRemark();
        }
        //解约原因
        mTvCancelContractReason.setText(refuseReasonName);
        //解约描述
        mTvCancelContractDesc.setText(refuseRemark);
        //拒绝原因
        refuseReason.setText(signOrderInfoBean.getRejectReasonClassNameJ());
        //拒绝描述
        refuseDes.setText(signOrderInfoBean.getRejectRemarkJ());

        int coachValue = 0;

        mTvSignStartTime.setText(DateUtils.getLongYYYYMMDD(
                signOrderInfoBean.getSignStartTime()));
        mTvSignTimeValue.setText(String.format("%d个%s", signOrderInfoBean.getSignDuration()
                , signOrderInfoBean.getSignDurationUnit()));

        String signOtherServiceCode = signOrderInfoBean.getSignOtherServiceCode();
        if (signOtherServiceCode != null) {
            if (signOtherServiceCode.contains(",")) {
                String[] split = signOtherServiceCode.split(",");
                coachValue = split.length;
            } else {
                coachValue = 1;
            }
        } else {
            coachValue = 0;
        }
        mTvMonitorTypeValue.setText(coachValue + "项");
       /* if (coachValue == 1) {
            mTvMonitorTypeValue.setText("一项");
        } else if (coachValue == 2) {
            mTvMonitorTypeValue.setText("两项");
        } else if (coachValue == 3) {
            mTvMonitorTypeValue.setText("三项");
        } else if (coachValue == 4) {
            mTvMonitorTypeValue.setText("四项");
        } else if (coachValue == 5) {
            mTvMonitorTypeValue.setText("五项");
        } else if (coachValue == 6) {
            mTvMonitorTypeValue.setText("六项");
        }*/

        String rate = signOrderInfoBean.getDetectRate() + "次/" + signOrderInfoBean.getDetectRateUnitName();
        mTvCoatchRateValue.setText(rate);


//        mTvMonitorTypeValue.setText(monitorTypeList.size() + "项");

        String signPrice = signOrderInfoBean.getSignPrice();
        DecimalFormat df = new DecimalFormat("0.00");
        if (TextUtils.isEmpty(signPrice)) {
            signPrice = "0.00";
        } else {
            signPrice = df.format(Double.parseDouble(signPrice));
        }


        mTvSignOrderPrice.setText(String.format("¥%s", signPrice));
        /*if (!CollectionUtils.isEmpty(coachTypeList)) {
            OrderDetialBean.OrderDetailListBean orderDetailListBean = coachTypeList.get(0);
            String rate = orderDetailListBean.get + "次/" + orderDetailListBean.getRate()
                    + orderDetailListBean.getRateUnitName();
            mTvCoatchRateValue.setText(rate);
        }*/
        if (signOrderInfoBean.getSignStatus().equals(OrderStatusEnum.orderDoctorCancelContractCode)) {
            mLLOperButtomRoot.setVisibility(View.VISIBLE);
        } else {
            mLLOperButtomRoot.setVisibility(View.GONE);
        }

        if (mType.equals("7")  ||mType.equals("8")  ||mType.equals("4")){
            mLLOperButtomRoot.setVisibility(View.GONE);
        }

    }


    /**
     * 处理订单详情数据
     *
     * @param orderDetialData 订单详情
     */
    private void handleOrderListResult(OrderDetialBean orderDetialData) {
        List<OrderDetialBean.OrderDetailListBean> orderDetailList = orderDetialData.getOrderDetailList();
        for (OrderDetialBean.OrderDetailListBean orderDetailListBean : orderDetailList) {
            String configDetailTypeCode = orderDetailListBean.getConfigDetailTypeCode();
            if (configDetailTypeCode.equals(DATA_MONITOR_CODE)) {
                monitorTypeList.add(orderDetailListBean);
            } else if (configDetailTypeCode.equals(DATA_COATCH_CODE)) {
                coachTypeList.add(orderDetailListBean);
            }
        }
    }


    @Override
    public void showRetry() {
        mLoadingLayoutManager.showError();
    }

    @Override
    public void showEmpty() {
        mLoadingLayoutManager.showEmpty();
    }

    @Override
    public void getSearchOrderDetialResult(OrderDetialBean orderDetialBean) {
        mLoadingLayoutManager.showContent();
        this.orderDetialBean = orderDetialBean;
        handleOrderListResult(orderDetialBean);
        setOrderData(orderDetialBean);
    }

    @Override
    public void getCancelContractConfirmResult(boolean isSucess, String msg) {
        if (isSucess) {
            mPresenter.sendGetUserListRequest(orderDetialBean.getMainDoctorCode());
        } else {
            ToastUtils.showToast(msg);
        }

    }

    @Override
    public void getUserInfoResult(UserInfoBaseBean userInfoBaseBean) {
        OrderMessage terminationOrder = getOrderMessage("terminationOrder", "1");
        Activity top = ActivityStackManager.getInstance().getTop();
        if (top instanceof SignOrderDetialActivity) {
            if (ActivityStackManager.getInstance().exists(ChatActivity.class)) {
                ActivityStackManager.getInstance().finish(ChatActivity.class);
            }
            Intent intent = new Intent();
            intent.setClass(this, ChatActivity.class);
            intent.putExtra("userCode", orderDetialBean.getMainDoctorCode());
            intent.putExtra("userName", orderDetialBean.getMainDoctorName());
            intent.putExtra("doctorUrl", userInfoBaseBean.getUserLogoUrl());
            intent.putExtra("patientUrl", mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            intent.putExtra("operDoctorName", mProvideViewSysUserPatientInfoAndRegion.getUserName());
            terminationOrder.setImageUrl(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            terminationOrder.setIsPatient("1");
            Bundle bundle = new Bundle();
            bundle.putSerializable("orderMsg", terminationOrder);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void getDetSucess(OrderDetialBean orderDetialBean) {
        setOrderData(orderDetialBean);
    }


    /**
     * 获取订单信息
     *
     * @param messageType 消息类型
     * @param orderType   操作类型
     * @return orderMessage
     */
    private OrderMessage getOrderMessage(String messageType, String orderType) {
        @SuppressLint("DefaultLocale")
        String monitorRate = String.format("1次/%s%s", orderDetialBean.getDetectRate(),
                orderDetialBean.getDetectRateUnitName());
        return new OrderMessage(orderId, orderDetialBean.getSignNo(),
                monitorTypeList.size() + "项", monitorRate,
                orderDetialBean.getSignDuration() + orderDetialBean.getSignDurationUnit()
                , orderDetialBean.getSignPrice() + "", messageType, orderType, orderDetialBean.getSignCode());

    }
}
