package com.hyphenate.easeui.order;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.entity.ProvideViewSysUserPatientInfoAndRegion;
import com.hyphenate.easeui.ui.ChatActivity;

import java.util.List;

import butterknife.ButterKnife;
import www.patient.jykj_zxyl.base.base_bean.AncelAppBean;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientCommitBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientDoctorInfoBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientListBean;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;

/*
* 取消预约
* */
public class AncelAppActivity extends AbstractMvpBaseActivity<AncelAppContract.View,
        AncelAppPresenter> implements AncelAppContract.View{
    private AncelAppActivity mActivity;
  private LinearLayout llBack;
    private RelativeLayout rl;
    private TextView tvName;
    private LinearLayout linDetect;
    private TextView edTermination;
    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;
    private String orderId;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            orderId = extras.getString("orderId");
        }
        ButterKnife.bind(this);
        mActivity=AncelAppActivity.this;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initView() {
        super.initView();
        llBack = findViewById(R.id.ll_back);
        rl = findViewById(R.id.rl);
        tvName = findViewById(R.id.tv_name);
        edTermination = findViewById(R.id.ed_termination);
        addListener();
    }

    private void addListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        mPresenter.sendAncelAppRequest(mProvideViewSysUserPatientInfoAndRegion.getLoginPatientPosition(),mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),mProvideViewSysUserPatientInfoAndRegion.getUserName(),orderId);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ancelapp_layout;
    }

    /**
     *  详情
     * @param ancelAppBean
     */
    @Override
    public void getAncelAppResult(AncelAppBean ancelAppBean) {
    if(ancelAppBean!=null){
        tvName.setText(ancelAppBean.getCancelReserveName());
        edTermination.setText(ancelAppBean.getCancelReserveRemark());
    }
    }

    @Override
    public void showLoading(int code) {

    }
}
