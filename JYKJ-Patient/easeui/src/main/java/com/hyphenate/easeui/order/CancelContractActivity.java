package com.hyphenate.easeui.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.allin.commlibrary.CollectionUtils;
import com.allin.commlibrary.StringUtils;
import com.google.gson.Gson;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.entity.ProvideViewSysUserPatientInfoAndRegion;
import com.hyphenate.easeui.ui.ChatActivity;
import java.util.ArrayList;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.SignOrderInfoBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_dialog.BaseReasonDialog;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;
import www.patient.jykj_zxyl.base.base_view.BaseToolBar;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;

/**
 * Description:解约
 *
 * @author: qiuxinhai
 * @date: 2020-07-29 18:00
 */
public class CancelContractActivity extends AbstractMvpBaseActivity<CancelContract.View,
        CancelContractPresenter> implements CancelContract.View {
    private RelativeLayout mRlCancelContract;
    private BaseReasonDialog mCancelContractDialog;
    private List<BaseReasonBean> mCancelContractBeans;
    private TextView mTvCancelContract;
    private TextView mTvSubmitBtn;
    private BaseToolBar mToolBar;//顶部toolBar
    private BaseReasonBean mCancelContractBean;
    private SignOrderInfoBean mSignOrderInfoBean;
    private OrderDetialBean mOrderDetialBean;
    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;
    private EditText edCancelContractDesc;
    private String orderId;//订单Id
    private String doctorCode;//医生的code
    private List<OrderDetialBean.OrderDetailListBean> monitorTypeList;
    private List<OrderDetialBean.OrderDetailListBean> coachTypeList;
    private static final String DATA_MONITOR_CODE="10";//监测类型
    private static final String DATA_COATCH_CODE="20";//辅导类型
    private String operDoctorName;
    private String operDoctorCode;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        monitorTypeList = new ArrayList<>();
        coachTypeList = new ArrayList<>();
        mCancelContractDialog = new BaseReasonDialog(this,"解约原因");
        mCancelContractBeans = new ArrayList<>();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            orderId=extras.getString("orderId");
            doctorCode=extras.getString("doctorCode");
            operDoctorName = extras.getString("operDoctorName");
            operDoctorCode=extras.getString("operDoctorCode");
            mSignOrderInfoBean=(SignOrderInfoBean)extras.getSerializable("orderMsg");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_cancel_contract;
    }


    @Override
    protected void initView() {
        super.initView();
        mRlCancelContract = findViewById(R.id.rl_cancel_contract);
        mTvCancelContract = findViewById(R.id.tv_cancel_contract);
        mTvSubmitBtn = findViewById(R.id.tv_submit_btn);
        edCancelContractDesc=findViewById(R.id.ed_cancel_contract_desc);
        mToolBar=findViewById(R.id.toolbar);
        setToolBar();
        addListener();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        mToolBar.setMainTitle("解约");
        //返回键
        mToolBar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 添加监听
     */
    private void addListener() {
        mRlCancelContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CollectionUtils.isEmpty(mCancelContractBeans)) {
                    mCancelContractDialog.show();
                    mCancelContractDialog.setData(mCancelContractBeans);
                }
            }
        });
        mCancelContractDialog.setOnClickItemListener(new BaseReasonDialog.OnClickItemListener() {
            @Override
            public void onClickItem(BaseReasonBean cancelContractBean) {
                mTvCancelContract.setText(cancelContractBean.getAttrName());
                mCancelContractBean = cancelContractBean;
            }
        });
        mTvSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelContractBean != null) {
                    if(mOrderDetialBean!=null){
                        showLoading("",null);
                        mPresenter.setOperTerminationSumbitRequest(
                                ParameUtil.loginDoctorPosition,
                                mOrderDetialBean.getMainDoctorCode(),
                                mOrderDetialBean.getMainDoctorName(),
                                mOrderDetialBean.getSignCode(),
                                mOrderDetialBean.getSignNo(),
                                mOrderDetialBean.getMainPatientCode(),
                                mOrderDetialBean.getMainUserName(),
                                mCancelContractBean.getAttrCode() + "",
                                mCancelContractBean.getAttrName(),
                                edCancelContractDesc.getText().toString()
                        );
                    }else if(mSignOrderInfoBean!=null){
                        showLoading("",null);
                        mPresenter.setOperTerminationSumbitRequest(
                                ParameUtil.loginDoctorPosition,
                                mSignOrderInfoBean.getMainDoctorCode(),
                                mSignOrderInfoBean.getMainDoctorName(),
                                mSignOrderInfoBean.getSignCode(),
                                mSignOrderInfoBean.getSignNo(),
                                mSignOrderInfoBean.getMainPatientCode(),
                                mSignOrderInfoBean.getMainUserName(),
                                mCancelContractBean.getAttrCode() + "",
                                mCancelContractBean.getAttrName(),
                                edCancelContractDesc.getText().toString()
                        );
                    }

                }else{
                    ToastUtils.showToast("请选择解约原因");
                }

            }
        });
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
        mPresenter.sendGetBasicsDomainRequest("90003");
        if(StringUtils.isNotEmpty(orderId)){
            mPresenter.sendSearchSignPatientDoctorOrderRequest(orderId
                    , operDoctorCode,operDoctorName);
        }else if(mSignOrderInfoBean!=null){
            mPresenter.sendSearchSignPatientDoctorOrderRequest(mSignOrderInfoBean.getSignCode()
                    , operDoctorCode,operDoctorName);
        }

//        else{
//            mPresenter.sendSerchSignInfoByPatientCodeRequest(ParameUtil.loginDoctorPosition,
//                    mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),
//                    doctorCode);
//        }

    }


    @Override
    public void showLoading(int code) {

    }

    @Override
    public void getBasicDomainResult(List<BaseReasonBean> cancelContractBeans) {
        mCancelContractBeans = cancelContractBeans;
    }

    @Override
    public void getCancelContractResult(boolean isSucess, String msg) {
        if (isSucess) {
            if (mOrderDetialBean!=null) {
                mPresenter.sendGetUserListRequest(mOrderDetialBean.getMainDoctorCode());
            }
        } else {
            ToastUtils.showToast(msg);
        }
    }

    @Override
    public void getSerchSignInfoByPatientCodeResult(SignOrderInfoBean signOrderInfoBean) {
        mSignOrderInfoBean = signOrderInfoBean;
        mPresenter.sendSearchSignPatientDoctorOrderRequest(mSignOrderInfoBean.getSignCode()
                , operDoctorCode,operDoctorName);
    }

    @Override
    public void getSearchSignPatientDoctorOrderResult(OrderDetialBean orderDetialBean) {
        mOrderDetialBean=orderDetialBean;
        handleOrderListResult(mOrderDetialBean);
    }

    @Override
    public void getUserInfoResult(UserInfoBaseBean userInfoBaseBean) {
        dismissLoading();
        if (ActivityStackManager.getInstance().exists(ChatActivity.class)) {
            ActivityStackManager.getInstance().finish(ChatActivity.class);
        }
        OrderMessage terminationOrder = getOrderMessage("terminationOrder", "");
        terminationOrder.setImageUrl(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        Intent intent = new Intent();
        intent.setClass(this, ChatActivity.class);
        intent.putExtra("userCode", mOrderDetialBean.getMainDoctorCode());
        intent.putExtra("userName",mOrderDetialBean.getMainDoctorName());
        intent.putExtra("doctorUrl", userInfoBaseBean.getUserLogoUrl());
        intent.putExtra("patientUrl", mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        intent.putExtra("operDoctorName", mProvideViewSysUserPatientInfoAndRegion.getUserName());
        Bundle bundle=new Bundle();
        bundle.putSerializable("orderMsg",terminationOrder);
        intent.putExtras(bundle);
        startActivity(intent);
        setResult(1001);
        this.finish();
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
    /**
     * 获取订单信息
     * @param messageType 消息类型
     * @param orderType 操作类型
     * @return orderMessage
     */
    private OrderMessage getOrderMessage(String messageType, String orderType) {
        @SuppressLint("DefaultLocale")
        String  monitorRate= String.format("一次/%d%s", mOrderDetialBean.getDetectRate(),
                mOrderDetialBean.getDetectRateUnitName());
        OrderMessage orderMessage = new OrderMessage(orderId
                ,mOrderDetialBean.getSignNo(),
                monitorTypeList.size() + "项", monitorRate,
                mOrderDetialBean.getSignDuration()+ mOrderDetialBean.getSignDurationUnit()
                , mOrderDetialBean.getSignPrice() + "", messageType, orderType,mOrderDetialBean.getSignCode());
        return orderMessage;

    }



}
