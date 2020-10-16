package com.hyphenate.easeui.order;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.allen.library.utils.ToastUtils;
import com.allin.commlibrary.CollectionUtils;
import com.google.gson.Gson;
import com.hyphenate.easeui.entity.ProvideViewSysUserPatientInfoAndRegion;
import com.hyphenate.easeui.ui.ChatActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.patient.jykj_zxyl.base.R;
import www.patient.jykj_zxyl.base.base_adapter.OrderDetialCoatchAdapter;
import www.patient.jykj_zxyl.base.base_adapter.OrderDetialMonitorAdapter;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.PaymentBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.base_utils.DateUtils;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;
import www.patient.jykj_zxyl.base.base_view.BaseToolBar;
import www.patient.jykj_zxyl.base.base_view.LoadingLayoutManager;
import www.patient.jykj_zxyl.base.enum_type.OrderStatusEnum;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.base.wxapi.AliPayEntryActivity;
import www.patient.jykj_zxyl.base.wxapi.PayResult;

/**
 * Description:订单详情
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:21
 */
public class SignOrderDetialActivity extends AbstractMvpBaseActivity<OrderDetialContract.View,
        OrderDetialPresenter> implements OrderDetialContract.View {
    private TextView mTvPatientName;//患者姓名
    private TextView mTvPatientAge;//患者年龄
    private TextView mTvPatientGender;//患者性别
    private TextView tvRefuseBtn;//拒绝
    private TextView tvUpdateBtn;//修改
    private TextView tvAgreeBtn;//同意
    private TextView tvCancelContractBtn;//解约
    private TextView tvCancelBtn;//取消
    private TextView tvConfirmPaymentBtn;//支付按钮
    private TextView tvAgreementMsg;//同意协议
    private TextView tvRateTimeValue;
    private RelativeLayout mRlWeixinRoot;//微信支付
    private RelativeLayout mRlAlipayRoot;//支付宝支付
    private ImageView mIvWeixinChoosed;//微信支付icon
    private ImageView mAliPayChoosed;//支付宝支付icon
    private ImageView ivChooseAgreementBtn;//同意协议按钮
    private ScrollView mScrollView;
    private BaseToolBar toolBar;//头布局toolBar
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private RecyclerView mRvMonitorList;
    private RecyclerView mRvCoatchList;
    private LinearLayout mLlMonitorType;
    private LinearLayout mLlCoatchType;
    private RelativeLayout rlOrderDetialSignUpRoot;//确定订单状态布局
    private RelativeLayout rlOrderDetialCancelRoot;//解约订单状态布局
    private LinearLayout llOrderDetialPaymentRoot;//支付订单状态布局
    private TextView tvSignStartTimeValue;
    private TextView tvSignTimeValue;
    private TextView tvTotalPriceValue;
    private TextView tvCancelDesc;
    private OrderDetialMonitorAdapter mMonitorOrderDetialAdapter;
    private OrderDetialCoatchAdapter mCoatchOrderDetialAdapter;
    private List<OrderDetialBean.OrderDetailListBean> monitorTypeList;
    private List<OrderDetialBean.OrderDetailListBean> coachTypeList;
    private OrderDetialBean orderDetialData;
    private static final String DATA_MONITOR_CODE = "10";//监测类型
    private static final String DATA_COATCH_CODE = "20";//辅导类型
    private int paymentType = 1;//1微信 2支付宝
    private String signCode;//订单Id
    private Handler mHandler;
    private OrderMessage orderCard = null;
    private String operDoctorCode;
    private String operDoctorName;
    private boolean isChoosedAgreeMent;
    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;
    private static final int SDK_PAY_FLAG = 3;
    private String signId;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        monitorTypeList = new ArrayList<>();
        coachTypeList = new ArrayList<>();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            signCode = extras.getString("signCode");
            signId = extras.getString("signId");
            operDoctorCode = extras.getString("operDoctorCode");
            operDoctorName = extras.getString("operDoctorName");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_sign_order_detial_1;

    }


    @Override
    protected void initView() {
        super.initView();
        toolBar = findViewById(R.id.toolbar);
        mRvMonitorList = findViewById(R.id.rv_monitor_list);
        mRvCoatchList = findViewById(R.id.rv_coatch_list);
        mTvPatientName = findViewById(R.id.tv_patient_name);
        mTvPatientAge = findViewById(R.id.tv_patient_age);
        mTvPatientGender = findViewById(R.id.tv_patient_gender);
        mRvMonitorList = findViewById(R.id.rv_monitor_list);
        mRvCoatchList = findViewById(R.id.rv_coatch_list);
        mScrollView = findViewById(R.id.scrollView);
        mLlMonitorType = findViewById(R.id.ll_monitor_type);
        mLlCoatchType = findViewById(R.id.ll_coatch_type);
        tvRefuseBtn = findViewById(R.id.tv_refuse_btn);
        tvUpdateBtn = findViewById(R.id.tv_update_btn);
        tvAgreeBtn = findViewById(R.id.tv_agree_btn);
        rlOrderDetialSignUpRoot = findViewById(R.id.rl_order_detialsign_up_root);
        rlOrderDetialCancelRoot = findViewById(R.id.rl_order_detial_cancel_root);
        llOrderDetialPaymentRoot = findViewById(R.id.ll_order_detial_payment_root);
        tvCancelContractBtn = findViewById(R.id.tv_cancel_contract_btn);
        tvCancelBtn = findViewById(R.id.tv_cancel_btn);
        mRlWeixinRoot = findViewById(R.id.rl_weixin_root);
        mRlAlipayRoot = findViewById(R.id.rl_alipay_root);
        mIvWeixinChoosed = findViewById(R.id.iv_weixin_choosed);
        mAliPayChoosed = findViewById(R.id.iv_alipay_choosed);
        tvConfirmPaymentBtn = findViewById(R.id.tv_confirm_payment_btn);
        tvSignStartTimeValue = findViewById(R.id.tv_sign_start_time_value);
        tvSignTimeValue = findViewById(R.id.tv_sign_time_value);
        tvTotalPriceValue = findViewById(R.id.tv_total_price_value);
        ivChooseAgreementBtn = findViewById(R.id.iv_choose_agreement_btn);
        tvAgreementMsg = findViewById(R.id.tv_agreement_msg);
        tvRateTimeValue = findViewById(R.id.tv_rate_time_value);
        tvCancelDesc = findViewById(R.id.tv_cancel_desc);
        SpannableString spannableString = new SpannableString("点击即代表您已同意《鹫一健康医生与患者签约协议》");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#799DFF"));
        spannableString.setSpan(colorSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvAgreementMsg.setText(spannableString);
        setToolBar();
        initLoadingAndRetryManager();
        initMonitorRecyclerView();
        initCoatchRecyclerView();
        addListener();
    }


    /**
     * 添加监听
     */
    @SuppressLint("HandlerLeak")
    private void addListener() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SDK_PAY_FLAG) {
                    initData();
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    Intent intent = new Intent(SignOrderDetialActivity.this, AliPayEntryActivity.class);
                    intent.putExtra(AliPayEntryActivity.PAY_MESSAGE, resultInfo);
//                    intent.putExtra("pay_appid", pay_appid);
//                    intent.putExtra("pay_productid", pay_productid);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(WXEntryActivity.this, getString(R.string.pay_success) + payResult);
                        intent.putExtra(AliPayEntryActivity.PAY_STATE, AliPayEntryActivity.SUCCESS);
                    } else if (TextUtils.equals(resultStatus, "6001")) {
//                         用户取消
                        intent.putExtra(AliPayEntryActivity.PAY_STATE, AliPayEntryActivity.CANCEL);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(WXEntryActivity.this, getString(R.string.pay_failed) + payResult);
                        intent.putExtra(AliPayEntryActivity.PAY_STATE, AliPayEntryActivity.FAILURE);
                    }
                    startActivity(intent);
                }
            }
        };

        tvRefuseBtn.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("orderId", signCode);
            bundle.putString("operDoctorCode", operDoctorCode);
            bundle.putString("operDoctorName", operDoctorName);
            startActivity(RefusedOrderActivity.class, bundle);
        });

        tvUpdateBtn.setOnClickListener(v -> mPresenter.sendOrderOperRequest(
                orderDetialData.getMainDoctorCode(),
                orderDetialData.getMainDoctorName(),
                orderDetialData.getSignCode(), signCode,
                orderDetialData.getMainPatientCode(), orderDetialData.getMainUserName(),
                "2", SignOrderDetialActivity.this));

        tvAgreeBtn.setOnClickListener(v -> mPresenter.sendOrderOperRequest(
                orderDetialData.getMainDoctorCode(),
                orderDetialData.getMainDoctorName(),
                orderDetialData.getSignCode(), signCode,
                orderDetialData.getMainPatientCode(), orderDetialData.getMainUserName(),
                "1", SignOrderDetialActivity.this));

        mRlWeixinRoot.setOnClickListener(v -> {
            paymentType = 1;
            setPaymentItemChoosed(paymentType);
        });
        mRlAlipayRoot.setOnClickListener(v -> {
            paymentType = 2;
            setPaymentItemChoosed(paymentType);
        });
        tvConfirmPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChoosedAgreeMent) {
                    mPresenter.sendOperPatientOrderPayRequest(
                            orderDetialData.getSignCode(),
                            paymentType + "", SignOrderDetialActivity.this);
                } else {
                    ToastUtils.showToast("支付前必须选中支付协议");
                }

            }
        });


        tvCancelContractBtn.setOnClickListener(v -> {
            if (orderDetialData != null) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId", signCode);
                bundle.putString("signId", signId);
                bundle.putString("operDoctorCode", orderDetialData.getMainDoctorCode());
                bundle.putString("operDoctorName", orderDetialData.getMainDoctorName());
                startActivity(CancelContractActivity.class, bundle);
            }


        });
        tvCancelBtn.setOnClickListener(v -> SignOrderDetialActivity.this.finish());
        ivChooseAgreementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChoosedAgreeMent) {
                    isChoosedAgreeMent = false;
                    ivChooseAgreementBtn.setImageResource(R.mipmap.bg_agreement_normal);
                } else {
                    isChoosedAgreeMent = true;
                    ivChooseAgreementBtn.setImageResource(R.mipmap.bg_agreement_press);
                }
            }
        });

    }


    /**
     * 设置支付状态
     *
     * @param type 1 微信支付 2支付宝支付
     */
    private void setPaymentItemChoosed(int type) {
        if (type == 1) {
            mIvWeixinChoosed.setImageResource(R.mipmap.bg_choosed_press);
            mAliPayChoosed.setImageResource(R.mipmap.bg_choosed_normal);
        } else if (type == 2) {
            mIvWeixinChoosed.setImageResource(R.mipmap.bg_choosed_normal);
            mAliPayChoosed.setImageResource(R.mipmap.bg_choosed_press);

        }

    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mScrollView);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();
            LogUtils.e("code   " + signCode);
            mPresenter.sendSearchOrderDetialRequest(signCode, mProvideViewSysUserPatientInfoAndRegion.getPatientCode(), mProvideViewSysUserPatientInfoAndRegion.getUserName());
        });
        mLoadingLayoutManager.showLoading();

    }

    /**
     * 初始化监测类型RecyclerView
     */
    private void initMonitorRecyclerView() {
        mMonitorOrderDetialAdapter = new OrderDetialMonitorAdapter(monitorTypeList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRvMonitorList.setLayoutManager(layoutManager);
        mRvMonitorList.setAdapter(mMonitorOrderDetialAdapter);
    }

    /**
     * 初始化辅导类型Recyclerview
     */
    private void initCoatchRecyclerView() {
        mCoatchOrderDetialAdapter = new OrderDetialCoatchAdapter(coachTypeList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRvCoatchList.setLayoutManager(layoutManager);
        mRvCoatchList.setAdapter(mCoatchOrderDetialAdapter);
    }


    @Override
    protected void initData() {
        SharedPreferences_DataSave jykjdocter = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        String userInfoSuLogin = jykjdocter.getString("viewSysUserDoctorInfoAndHospital", "");
        try {
            mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        } catch (Exception e) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("code  onResume " + signCode);
        mPresenter.sendSearchOrderDetialRequest(signCode, operDoctorCode, operDoctorName);
    }

    /**
     * 处理订单详情数据
     *
     * @param orderDetialData 订单详情
     */
    private void handleOrderListResult(OrderDetialBean orderDetialData) {
        coachTypeList.clear();
        monitorTypeList.clear();
        List<OrderDetialBean.OrderDetailListBean> orderDetailList = orderDetialData.getOrderDetailList();
        if (orderDetailList != null) {
            for (OrderDetialBean.OrderDetailListBean orderDetailListBean : orderDetailList) {
                String configDetailTypeCode = orderDetailListBean.getConfigDetailTypeCode();
                if (configDetailTypeCode.equals(DATA_MONITOR_CODE)) {
                    monitorTypeList.add(orderDetailListBean);
                } else if (configDetailTypeCode.equals(DATA_COATCH_CODE)) {
                    coachTypeList.add(orderDetailListBean);
                }
            }
        }

    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolBar.setMainTitle("签约详情");
        //返回键
        toolBar.setLeftTitleClickListener(view -> finish());
    }


    @Override
    public void showLoading(int code) {
        if (code == 101 || code == 102) {
            showLoading("", null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void getSearchOrderDetialResult(OrderDetialBean orderDetialBean) {
        mLoadingLayoutManager.showContent();
        this.orderDetialData = orderDetialBean;
        handleOrderListResult(orderDetialBean);
        setOrderDetialData(orderDetialBean);

        setOrderStatus(orderDetialData);
        if (!CollectionUtils.isEmpty(monitorTypeList)) {
            mLlMonitorType.setVisibility(View.VISIBLE);
        } else {
            mLlMonitorType.setVisibility(View.GONE);
        }
        if (!CollectionUtils.isEmpty(coachTypeList)) {
            mLlCoatchType.setVisibility(View.VISIBLE);
        } else {
            mLlCoatchType.setVisibility(View.GONE);
        }
        mMonitorOrderDetialAdapter.notifyDataSetChanged();
        mCoatchOrderDetialAdapter.notifyDataSetChanged();
    }

    /**
     * 获取订单信息
     *
     * @param messageType 消息类型
     * @param orderType   操作类型
     * @return orderMessage
     */
    @SuppressLint("DefaultLocale")
    private OrderMessage getOrderMessage(String messageType, String orderType) {

        String monitorRate = String.format("一次/%d%s", orderDetialData.getDetectRate(),
                orderDetialData.getDetectRateUnitName());
        OrderMessage orderMessage = new OrderMessage(signCode, orderDetialData.getSignNo(),
                String.format("%d项", monitorTypeList.size()), monitorRate,
                String.format("%d个%s", orderDetialData.getSignDuration(), orderDetialData.getSignDurationUnit())
                , orderDetialData.getSignPrice() + "", messageType, orderType, orderDetialData.getSignCode());
        return orderMessage;

    }

    @Override
    public void getOrderOperResult(boolean isSucess, String type) {
        if (isSucess) {

            switch (type) {
                case "0":
                    orderCard = getOrderMessage("card", "3");
                    break;
                case "1":
                    orderCard = getOrderMessage("card", "1");
                    break;
                case "2":
                    orderCard = getOrderMessage("card", "2");
                    break;
                default:
            }


            mPresenter.sendGetUserListRequest(orderDetialData.getMainDoctorCode());

        }
    }


    @Override
    public void getWeixPaymentResult(PaymentBean paymentBean) {
        weichatPay(paymentBean);
    }

    @Override
    public void getAliPaymentResult(String payInfo) {
        sendAliPay(payInfo);
    }


    @Override
    public void getPaymentResultError(String errorMsg) {
        ToastUtils.showToast(errorMsg);
    }

    @Override
    public void getUserInfoResult(UserInfoBaseBean userInfoBaseBean) {
        Activity top = ActivityStackManager.getInstance().getTop();
        if (top instanceof SignOrderDetialActivity) {
            if (ActivityStackManager.getInstance().exists(ChatActivity.class)) {
                ActivityStackManager.getInstance().finish(ChatActivity.class);
            }

            Intent intent = new Intent();
            intent.setClass(this, ChatActivity.class);
            intent.putExtra("userCode", orderDetialData.getMainDoctorCode());
            intent.putExtra("userName", orderDetialData.getMainDoctorName());
            intent.putExtra("doctorUrl", userInfoBaseBean.getUserLogoUrl());
            intent.putExtra("patientUrl", mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            intent.putExtra("operDoctorName", mProvideViewSysUserPatientInfoAndRegion.getUserName());
            orderCard.setImageUrl(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            orderCard.setOrderId(signCode);
            Bundle bundle = new Bundle();
            bundle.putSerializable("orderMsg", orderCard);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    /**
     * 开始调起微信支付
     *
     * @param paymentBean 支付信息
     */
    private void weichatPay(PaymentBean paymentBean) {
        //将appid注册到微信
        //调起微信支付
        IWXAPI msgApi = WXAPIFactory.createWXAPI(this, "wx4ccb2ac1c5491336");
        msgApi.registerApp("wx4ccb2ac1c5491336");
        PayReq request = new PayReq();
        request.appId = paymentBean.getAppId();
        request.partnerId = paymentBean.getPartnerid();
        request.prepayId = paymentBean.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = paymentBean.getNonceStr();
        request.timeStamp = paymentBean.getTimeStamp();
        request.sign = paymentBean.getSign();
        request.signType = "MD5";
        boolean result = msgApi.sendReq(request);
        if (result) {
            initData();
        }

    }

    /**
     * 发阿里支付请求
     *
     * @param orderInfo 订单信息
     */
    public void sendAliPay(final String orderInfo) {
        final Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(SignOrderDetialActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        new Thread(payRunnable).start();
    }


    /**
     * 设置订单状态
     *
     * @param orderDetialData 订单详情
     */
    private void setOrderStatus(OrderDetialBean orderDetialData) {
        rlOrderDetialSignUpRoot.setVisibility(View.GONE);
        rlOrderDetialCancelRoot.setVisibility(View.GONE);
        llOrderDetialPaymentRoot.setVisibility(View.GONE);
        String signStatus = orderDetialData.getSignStatus();
        if (signStatus != null) {
            switch (signStatus) {
                case OrderStatusEnum
                        .orderSubmitCode:
                    rlOrderDetialSignUpRoot.setVisibility(View.VISIBLE);
                    rlOrderDetialCancelRoot.setVisibility(View.GONE);
                    llOrderDetialPaymentRoot.setVisibility(View.GONE);
                    break;
                case OrderStatusEnum.orderSignCompleteCode:
                    rlOrderDetialSignUpRoot.setVisibility(View.GONE);
                    rlOrderDetialCancelRoot.setVisibility(View.VISIBLE);
                    llOrderDetialPaymentRoot.setVisibility(View.GONE);
                    break;
                case OrderStatusEnum.orderAgreeCode:
                    rlOrderDetialSignUpRoot.setVisibility(View.GONE);
                    rlOrderDetialCancelRoot.setVisibility(View.GONE);
                    llOrderDetialPaymentRoot.setVisibility(View.VISIBLE);
                    break;
                default:
            }
        }


    }

    /**
     * 设置订单详情数据data
     *
     * @param orderDetialData 订单详情
     */
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void setOrderDetialData(OrderDetialBean orderDetialData) {
        mTvPatientName.setText(orderDetialData.getMainUserName());
        mTvPatientAge.setText(orderDetialData.getPatientAge());
        mTvPatientGender.setText(orderDetialData.getGenderName());
        tvSignStartTimeValue.setText(DateUtils.getLongYYYYMMDD(
                orderDetialData.getSignStartTime()));
        tvSignTimeValue.setText(String.format("%d个月", orderDetialData.getSignDuration()));
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String format = decimalFormat.format(orderDetialData.getSignPrice());
        tvTotalPriceValue.setText(String.format("¥%s", format));
        mTvPatientAge.setText(orderDetialData.getAge() + "");
        tvRateTimeValue.setText(orderDetialData.getDetectRate() + "" + orderDetialData.getDetectRateUnitName());
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
    protected void onDestroy() {
        super.onDestroy();

    }
}
