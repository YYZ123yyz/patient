package com.hyphenate.easeui.order;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;
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
    private static final String DATA_MONITOR_CODE="10";//监测类型
    private static final String DATA_COATCH_CODE="20";//辅导类型
    private OrderDetialBean orderDetialBean;
    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;
    private String operDoctorCode;
    private String operDoctorName;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        monitorTypeList = new ArrayList<>();
        coachTypeList = new ArrayList<>();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            orderId = extras.getString("orderId");
            operDoctorCode= extras.getString("operDoctorCode");
            operDoctorName=extras.getString("operDoctorName");
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
        mTvCancelContractDesc=findViewById(R.id.tv_cancel_contract_desc);
        mTvSignStartTime=findViewById(R.id.tv_sign_start_time);
        mTvMonitorTypeValue=findViewById(R.id.tv_monitor_type_value);
        mTvCoatchRateValue=findViewById(R.id.tv_coatch_rate_value);
        mTvSignTimeValue = findViewById(R.id.tv_sign_time_value);
        mTvSignOrderPrice=findViewById(R.id.tv_sign_order_price);
        mTvRefuseBtn=findViewById(R.id.tv_refuse_btn);
        mTvAgreeBtn=findViewById(R.id.tv_agree_btn);
        mLlContentRoot=findViewById(R.id.ll_content_root);
        mLLOperButtomRoot=findViewById(R.id.ll_oper_buttom_root);
        mTvCancelContractReason=findViewById(R.id.tv_cancel_contract_reason);
        setToolBar();
        initLoadingAndRetryManager();
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        SharedPreferences_DataSave jykjdocter = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        String userInfoSuLogin = jykjdocter.getString("viewSysUserDoctorInfoAndHospital", "");
        try {
            mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        } catch (Exception e) {

        }
        mPresenter.sendSearchOrderDetialRequest(orderId,operDoctorCode,operDoctorName);
    }

    /**
     * 添加监听
     */
    private void addListener(){
        mTvRefuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("orderId",orderId);
                bundle.putString("operDoctorCode",operDoctorCode);
                bundle.putString("operDoctorName",operDoctorName);
                startActivity(RefusedCancelContractActivity.class,bundle);
            }
        });
        mTvAgreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderDetialBean!=null) {
                    mPresenter.sendCancelContractConConfirmRequest(
                            ParameUtil.loginDoctorPosition,
                            orderDetialBean.getMainDoctorCode()
                            ,orderDetialBean.getMainDoctorName(),
                            orderDetialBean.getSignCode()
                            ,orderDetialBean.getSignNo()
                            ,orderDetialBean.getMainPatientCode()
                            ,orderDetialBean.getMainUserName(),
                            "1","","","");
                }

            }
        });
        mLlContentRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("signCode",orderId);
                bundle.putString("operDoctorCode",operDoctorCode);
                bundle.putString("operDoctorName",operDoctorName);
                startActivity(SignOrderDetialActivity.class,bundle);
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
            mPresenter.sendSearchOrderDetialRequest(orderId,operDoctorCode,operDoctorName);
        });
        mLoadingLayoutManager.showLoading();

    }


    @Override
    public void showLoading(int code) {
        if (code==101) {
            showLoading("",null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    /**
     * 设置订单数据
     * @param signOrderInfoBean 订单数据
     */
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void setOrderData(OrderDetialBean signOrderInfoBean){
        mTvCancelContractDesc.setText(signOrderInfoBean.getRefuseRemark());
        mTvSignStartTime.setText(DateUtils.getStringTimeMinute(
                signOrderInfoBean.getTerminationTime()));
        mTvSignTimeValue.setText(String.format("%d%s", signOrderInfoBean.getSignDuration()
                , signOrderInfoBean.getSignDurationUnit()));
        mTvMonitorTypeValue.setText(monitorTypeList.size()+"项");
        mTvSignOrderPrice.setText(String.format("¥%s", signOrderInfoBean.getSignPrice()));
        if (!CollectionUtils.isEmpty(coachTypeList)) {
            OrderDetialBean.OrderDetailListBean orderDetailListBean = coachTypeList.get(0);
            String rate=orderDetailListBean.getValue()+"次/"+orderDetailListBean.getRate()
                    +orderDetailListBean.getRateUnitName();
            mTvCoatchRateValue.setText(rate);
        }
        if (signOrderInfoBean.getSignStatus().equals(OrderStatusEnum.orderDoctorCancelContractCode)) {
            mLLOperButtomRoot.setVisibility(View.VISIBLE);
        }else{
            mLLOperButtomRoot.setVisibility(View.GONE);
        }
        mTvCancelContractReason.setText(signOrderInfoBean.getRefuseReasonClassName());


    }



    /**
     * 处理订单详情数据
     * @param orderDetialData 订单详情
     */
    private void handleOrderListResult(OrderDetialBean orderDetialData) {
        List<OrderDetialBean.OrderDetailListBean> orderDetailList = orderDetialData.getOrderDetailList();
        for (OrderDetialBean.OrderDetailListBean orderDetailListBean : orderDetailList) {
            String configDetailTypeCode = orderDetailListBean.getConfigDetailTypeCode();
            if (configDetailTypeCode.equals(DATA_MONITOR_CODE)) {
                monitorTypeList.add(orderDetailListBean);
            }else if(configDetailTypeCode.equals(DATA_COATCH_CODE)){
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
        this.orderDetialBean=orderDetialBean;
        handleOrderListResult(orderDetialBean);
        setOrderData(orderDetialBean);
    }

    @Override
    public void getCancelContractConfirmResult(boolean isSucess, String msg) {
        if(isSucess){
            mPresenter.sendGetUserListRequest(orderDetialBean.getMainDoctorCode());
        }else{
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
            Bundle bundle=new Bundle();
            bundle.putSerializable("orderMsg",terminationOrder);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    /**
     * 获取订单信息
     * @param messageType 消息类型
     * @param orderType 操作类型
     * @return orderMessage
     */
    private OrderMessage getOrderMessage(String messageType, String orderType) {
        @SuppressLint("DefaultLocale")
        String  monitorRate= String.format("一次/%d%s", orderDetialBean.getDetectRate(),
                orderDetialBean.getDetectRateUnitName());
        return new OrderMessage(orderDetialBean.getSignCode(),orderDetialBean.getSignNo(),
                monitorTypeList.size() + "项", monitorRate,
                orderDetialBean.getSignDuration()+ orderDetialBean.getSignDurationUnit()
                , orderDetialBean.getSignPrice() + "", messageType, orderType);

    }
}
