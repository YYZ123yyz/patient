package com.hyphenate.easeui.order;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.allin.commlibrary.CollectionUtils;
import com.google.gson.Gson;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.entity.ProvideViewSysUserPatientInfoAndRegion;
import com.hyphenate.easeui.ui.ChatActivity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_dialog.BaseReasonDialog;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;
import www.patient.jykj_zxyl.base.base_view.BaseToolBar;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;

/**
 * Description:订单拒绝
 *
 * @author: qiuxinhai
 * @date: 2020-08-07 16:27
 */
public class RefusedOrderActivity extends AbstractMvpBaseActivity<RefusedOrderContract.View,
        RefusedOrderPresenter> implements RefusedOrderContract.View {
    private LinearLayout mLlRefuseRoot;
    private TextView mTvCancelContract;
    private EditText edRefusedReason;
    private BaseToolBar mToolBar;//顶部toolBar
    private TextView mTvSubmitBtn;
    private BaseReasonDialog mCancelContractDialog;
    private List<BaseReasonBean> baseReasonBeans;
    private BaseReasonBean baseReasonBean;
    private OrderDetialBean orderDetialBean;
    private String orderId;
    private List<OrderDetialBean.OrderDetailListBean> monitorTypeList;
    private List<OrderDetialBean.OrderDetailListBean> coachTypeList;
    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;
    private String operDoctorCode;
    private String operDoctorName;
    private static final String DATA_MONITOR_CODE="10";//监测类型
    private static final String DATA_COATCH_CODE="20";//辅导类型
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        monitorTypeList = new ArrayList<>();
        coachTypeList = new ArrayList<>();
        mCancelContractDialog = new BaseReasonDialog(this,"拒绝原因");
        baseReasonBeans=new ArrayList<>();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
             orderId = extras.getString("orderId");
            operDoctorCode=extras.getString("operDoctorCode");
            operDoctorName=extras.getString("operDoctorName");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_refused_order;
    }


    @Override
    protected void initView() {
        super.initView();
        mLlRefuseRoot=findViewById(R.id.ll_refuse_root);
        mTvCancelContract=findViewById(R.id.tv_cancel_contract);
        edRefusedReason=findViewById(R.id.ed_refused_reason);
        mTvSubmitBtn=findViewById(R.id.tv_submit_btn);
        mToolBar=findViewById(R.id.toolbar);
        setToolBar();
        addListener();
    }

    @Override
    protected void initData() {
        SharedPreferences_DataSave jykjdocter = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        String userInfoSuLogin = jykjdocter.getString("viewSysUserDoctorInfoAndHospital", "");
        try {
            mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        } catch (Exception e) {

        }
        mPresenter.sendGetBasicsDomainRequest("90002");
        mPresenter.sendSearchSignPatientDoctorOrderRequest(orderId,operDoctorCode,operDoctorName);
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        mToolBar.setMainTitle("拒绝");
        //返回键
        mToolBar.setLeftTitleClickListener(view -> finish());
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
     * 添加监听
     */
    private void addListener(){
        mLlRefuseRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CollectionUtils.isEmpty(baseReasonBeans)) {
                    mCancelContractDialog.show();
                    mCancelContractDialog.setData(baseReasonBeans);
                }
            }
        });

        mCancelContractDialog.setOnClickItemListener(new BaseReasonDialog.OnClickItemListener() {
            @Override
            public void onClickItem(BaseReasonBean cancelContractBean) {
                RefusedOrderActivity.this.baseReasonBean=cancelContractBean;
                mTvCancelContract.setText(cancelContractBean.getAttrName());
            }
        });
        mTvSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(baseReasonBean!=null&&orderDetialBean!=null){
                    String refusedReason = edRefusedReason.getText().toString();

                    mPresenter.sendRefusedOrderRequest(ParameUtil.loginDoctorPosition
                            ,orderDetialBean.getMainDoctorCode(),
                            orderDetialBean.getMainDoctorName(),
                            orderDetialBean.getSignCode(),
                            orderDetialBean.getSignNo(),
                            orderDetialBean.getMainPatientCode()
                            ,orderDetialBean.getMainUserName(),"0",
                            baseReasonBean.getAttrCode()+"",baseReasonBean.getAttrName(),refusedReason);

                }else{
                    ToastUtils.showToast("请选择拒绝原因");
                }

            }
        });
    }



    @Override
    public void getBasicDomainResult(List<BaseReasonBean> cancelContractBeans) {
       this.baseReasonBeans=cancelContractBeans;
    }

    @Override
    public void getRefusedOrderResult(boolean isSucess, String msg) {
        if (isSucess) {
            if (orderDetialBean != null) {
                mPresenter.sendGetUserListRequest(orderDetialBean.getMainDoctorCode());
            }
        }else{
            ToastUtils.showToast(msg);
        }

    }

    @Override
    public void getSearchSignPatientDoctorOrderResult(OrderDetialBean orderDetialBean) {
        this.orderDetialBean=orderDetialBean;
        handleOrderListResult(orderDetialBean);
    }

    @Override
    public void getUserInfoResult(UserInfoBaseBean userInfoBaseBean) {
        if (ActivityStackManager.getInstance().exists(ChatActivity.class)) {
            ActivityStackManager.getInstance().finish(ChatActivity.class);
        }
        this.finish();
        Intent intent = new Intent();
        intent.setClass(this, ChatActivity.class);
        intent.putExtra("userCode", orderDetialBean.getMainDoctorCode());
        intent.putExtra("userName", orderDetialBean.getMainDoctorName());
        intent.putExtra("doctorUrl", userInfoBaseBean.getUserLogoUrl());
        intent.putExtra("patientUrl", mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        intent.putExtra("operDoctorName", mProvideViewSysUserPatientInfoAndRegion.getUserName());
        Bundle bundle=new Bundle();
        OrderMessage card = getOrderMessage("card", "3");
        if (card!=null) {
            card.setImageUrl(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        }
        bundle.putSerializable("orderMsg", card);
        intent.putExtras(bundle);
        startActivity(intent);
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
        OrderMessage orderMessage = new OrderMessage(orderDetialBean.getSignCode()
                ,orderDetialBean.getSignNo(),
                monitorTypeList.size() + "项", monitorRate,
                orderDetialBean.getSignDuration()+ orderDetialBean.getSignDurationUnit()
                , orderDetialBean.getSignPrice() + "", messageType, orderType);
        return orderMessage;

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


}
