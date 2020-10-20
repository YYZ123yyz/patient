package com.hyphenate.easeui.widget.chatrow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.order.AncelAppActivity;
import com.hyphenate.easeui.order.CancelConfirmDeitalActivity;
import com.hyphenate.easeui.order.RefusedCancelContractActivity;
import com.hyphenate.easeui.order.RefusedOrderActivity;
import com.hyphenate.easeui.order.SignOrderDetialActivity;
import com.hyphenate.easeui.ui.WZXXActivity;
import com.hyphenate.easeui.widget.EaseImageView;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.ProvideViewSysUserPatientInfoAndRegion;
import www.patient.jykj_zxyl.base.base_bean.UpdateOrderResultBean;
import www.patient.jykj_zxyl.base.base_manager.OrderOperationManager;
import www.patient.jykj_zxyl.base.base_utils.DateUtils;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.http.ParameUtil;

/**
 * Description:自定义卡片
 *
 * @author: qiuxinhai
 * @date: 2020-07-29 14:27
 */
public class EaseChatRowOrderCard extends EaseChatRow {

    private EaseImageView mUserHead;//用户头像
    private TextView mTvCardTitle;//卡片标题
    private TextView mTvMonitValue;//监测类型
    private TextView mTvCoachRateValue;//辅导频次
    private TextView mTvSignTimeValue; //签约时长
    private TextView mTvPriceValue;//价格
    private TextView mTvAgreeBtn;//同意
    private TextView mTvUpdateBtn;//修改
    private TextView mTvRefuseBtn;//拒绝
    private TextView mTvCancelContractAgreeBtn;//同意解约按钮
    private TextView mTvCancelContractRefuseBtn;//拒绝解约按钮
    private TextView mTvFillInBtn;//问诊立即填写
    private ImageView ivStampIcon;//印章
    private TextView mTvOperReceivedMsg;//医生操作信息
    private TextView mTvOperMsg;//操作信息
    private Context mContext;
    private LinearLayout mLLRootView;
    private String orderId;
    private String singNO;
    private String monitoringType;
    private String coach;
    private String signUpTime;
    private String price;
    private String orderType;
    private String messageType;
    private String imageUrl;
    private RelativeLayout rlCancelContractOrderRoot;
    private RelativeLayout rlSignOrderRoot;

    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;
    private RelativeLayout rl_class;
    private TextView tv_class;
    private TextView tv_class_vlaue;
    private TextView tv_monitor_type;
    private TextView tv_coach_rate;
    private TextView tv_sign_time;
    private String startTime;
    private String cancelTime;
    private String appointMentProject;
    private String appointMentType;
    private TextView tv_patient_class;
    private TextView tv_patient_class_vlaue;
    private RelativeLayout patient_rl;
    private String endTime;
    private String patientType;
    private String opStatus;
    private String receiveTime;
    private String surplusTimes;
    private String userName;
    private String nickName;
    private RelativeLayout rl_one;
    private RelativeLayout rl_two;
    private RelativeLayout rl_three;
    private RelativeLayout rl_immediately;
    private TextView mTvEnsureBtn;
    private String singId;
    private String signNo_new;

    public EaseChatRowOrderCard(Context context, EMMessage message,
                                int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
        this.mContext = context;
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_order : R.layout.ease_row_sent_order, this);
    }

    @Override
    protected void onFindViewById() {
        mUserHead = findViewById(R.id.iv_userhead);
        mTvCardTitle = findViewById(R.id.tv_card_title);
        mTvMonitValue = findViewById(R.id.tv_monitor_value);
        mTvCoachRateValue = findViewById(R.id.tv_coach_rate_value);
        mTvSignTimeValue = findViewById(R.id.tv_sign_time_vlaue);
        mTvPriceValue = findViewById(R.id.tv_price_vlaue);
        mTvAgreeBtn = findViewById(R.id.tv_agree_btn);
        mTvUpdateBtn = findViewById(R.id.tv_update_btn);
        mTvRefuseBtn = findViewById(R.id.tv_refuse_btn);
        mLLRootView = findViewById(R.id.ll_root_view);
        ivStampIcon = findViewById(R.id.iv_stamp_icon);
        mTvOperMsg = findViewById(R.id.tv_oper_msg);
        mTvOperReceivedMsg = findViewById(R.id.tv_oper_received_msg);
        rlCancelContractOrderRoot = findViewById(R.id.rl_cancel_contract_order_root);
        rlSignOrderRoot = findViewById(R.id.rl_sign_order_root);
        mTvCancelContractAgreeBtn = findViewById(R.id.tv_cancel_contract_agree_btn);
        mTvCancelContractRefuseBtn = findViewById(R.id.tv_cancel_contract_refuse_btn);
        //预约类型
        rl_class = findViewById(R.id.rl_class);
        tv_class = findViewById(R.id.tv_class);
        tv_class_vlaue = findViewById(R.id.tv_class_vlaue);
        //预约修改的文字
        tv_monitor_type = findViewById(R.id.tv_monitor_type);
        tv_coach_rate = findViewById(R.id.tv_coach_rate);
        tv_sign_time = findViewById(R.id.tv_sign_time);
        //问诊资料  患者类型
        tv_patient_class = findViewById(R.id.tv_patient_class);
        tv_patient_class_vlaue = findViewById(R.id.tv_patient_class_vlaue);
        patient_rl = findViewById(R.id.patient_rl);
        //问诊资料
        rl_one = findViewById(R.id.rl_one);
        rl_two = findViewById(R.id.rl_two);
        rl_three = findViewById(R.id.rl_three);
        //立即填写
        rl_immediately = findViewById(R.id.rl_immediately);
        mTvEnsureBtn = findViewById(R.id.tv_ensure_btn);
        mTvFillInBtn = findViewById(R.id.tv_fill_in_btn);
        addListener();
    }

    private void setBtnOperStatus() {
        boolean isValid = message.getBooleanAttribute("isValid", false);

        if (isValid) {
            if (mTvAgreeBtn != null) {
                mTvAgreeBtn.setBackgroundResource(R.drawable.bg_oval_frame_7a9eff_13);
                mTvAgreeBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_7a9eff));
                mTvAgreeBtn.setTag(1);
            }
            if (mTvUpdateBtn != null) {
                mTvUpdateBtn.setBackgroundResource(R.drawable.bg_oval_frame_7a9eff_13);
                mTvUpdateBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_7a9eff));
                mTvUpdateBtn.setTag(1);
            }
            if (mTvRefuseBtn != null) {
                mTvRefuseBtn.setBackgroundResource(R.drawable.bg_oval_frame_7a9eff_13);
                mTvRefuseBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_7a9eff));
                mTvRefuseBtn.setTag(1);
            }
            if (mTvCancelContractAgreeBtn != null) {
                mTvCancelContractAgreeBtn.setTag(1);
                mTvCancelContractAgreeBtn.setBackgroundResource(R.drawable.bg_oval_frame_7a9eff_13);
                mTvCancelContractAgreeBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_7a9eff));
            }
            if (mTvCancelContractRefuseBtn != null) {
                mTvCancelContractRefuseBtn.setBackgroundResource(R.drawable.bg_oval_frame_7a9eff_13);
                mTvCancelContractRefuseBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_7a9eff));
                mTvCancelContractRefuseBtn.setTag(1);
            }

        } else {
            if (mTvAgreeBtn != null) {
                mTvAgreeBtn.setBackgroundResource(R.drawable.bg_oval_frame_999999_13);
                mTvAgreeBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                mTvAgreeBtn.setTag(0);
            }
            if (mTvUpdateBtn != null) {
                mTvUpdateBtn.setBackgroundResource(R.drawable.bg_oval_frame_999999_13);
                mTvUpdateBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                mTvUpdateBtn.setTag(0);
            }
            if (mTvRefuseBtn != null) {
                mTvRefuseBtn.setBackgroundResource(R.drawable.bg_oval_frame_999999_13);
                mTvRefuseBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                mTvRefuseBtn.setTag(0);
            }

            if (mTvCancelContractAgreeBtn != null) {
                mTvCancelContractAgreeBtn.setBackgroundResource(R.drawable.bg_oval_frame_999999_13);
                mTvCancelContractAgreeBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                mTvCancelContractAgreeBtn.setTag(0);
            }
            if (mTvCancelContractRefuseBtn != null) {
                mTvCancelContractRefuseBtn.setBackgroundResource(R.drawable.bg_oval_frame_999999_13);
                mTvCancelContractRefuseBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                mTvCancelContractRefuseBtn.setTag(0);
            }
        }

        String isConfirm = message.getStringAttribute("isConfirm", "");
        if (StringUtils.isNotEmpty(isConfirm)) {
            if (isConfirm.equals("1")) {
                mTvEnsureBtn.setText("已确定");
                mTvEnsureBtn.setBackgroundResource(0);
                mTvEnsureBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
            } else {
                mTvEnsureBtn.setBackgroundResource(R.drawable.bg_round_7a9eff_15);
                mTvEnsureBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_ffffff));
                mTvEnsureBtn.setText("确定");
            }
        } else {
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                mTvEnsureBtn.setBackgroundResource(R.drawable.bg_round_7a9eff_15);
                mTvEnsureBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_ffffff));
                mTvEnsureBtn.setText("确定");
            }

        }
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onSetUpView() {
        SharedPreferences_DataSave m_persist = new SharedPreferences_DataSave(activity,
                "JYKJDOCTER");
        String userInfoSuLogin = m_persist.getString("viewSysUserDoctorInfoAndHospital", "");
        mProvideViewSysUserPatientInfoAndRegion
                = GsonUtils.fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        // TODO: 2020-07-29 显示ui

        orderId = message.getStringAttribute("orderId", ""); //返回
        singId = message.getStringAttribute("signId", "");   //操作
        monitoringType = message.getStringAttribute("monitoringType", "");
        coach = message.getStringAttribute("coach", "");
        signUpTime = message.getStringAttribute("signUpTime", "");
        price = message.getStringAttribute("price", "");

        messageType = message.getStringAttribute("messageType", "");
        singNO = message.getStringAttribute("singNo", "");//详情orderno

        LogUtils.e("接受消息   orderId  " + orderId);
        LogUtils.e("接受消息   singId  " + singId);
        LogUtils.e("接受消息   singNO  " + singNO);
        imageUrl = message.getStringAttribute("imageUrl", "");
        startTime = message.getStringAttribute("startTime", "");
        cancelTime = message.getStringAttribute("cancelTime", "");
        appointMentProject = message.getStringAttribute("appointMentProject", "");
        appointMentType = message.getStringAttribute("appointMentType", "");
        //病历就诊结束时间
        endTime = message.getStringAttribute("endTime", "");
        //病历签约类型
        patientType = message.getStringAttribute("patientType", "");

        //病历操作状态
        opStatus = message.getStringAttribute("opStatus", "");
        //病历
        //病历 接诊时间
        receiveTime = message.getStringAttribute("receiveTime", "");
        //病历 图文 剩余次数
        surplusTimes = message.getStringAttribute("surplusTimes", "");
        mTvMonitValue.setText(monitoringType);
        mTvCoachRateValue.setText(coach);
        mTvSignTimeValue.setText(signUpTime);
        DecimalFormat df = new DecimalFormat("0.00");
        if (TextUtils.isEmpty(price)) {
            price = "0.00";
        } else {
            price = df.format(Double.parseDouble(price));
        }

        mTvPriceValue.setText(String.format("¥%s", price));
        orderType = message.getStringAttribute("orderType", "");//1已同意 2 修改 3 拒绝（由患者操作发起时会携带此参数）
        if (messageType.equals("terminationOrder")) {
            mTvCardTitle.setText("解约订单");
        } else if (messageType.equals("card")) {
            mTvCardTitle.setText("签约订单");
        } else if (messageType.equals("medicalRecord")) {
            mTvCardTitle.setText("病历");
        } else if (messageType.equals("appointment")) {
            mTvCardTitle.setText("取消预约");
        } else if (messageType.equals("receiveTreatment")) {
            if (appointMentProject.equals("10")) {
                mTvCardTitle.setText("图文");
            } else if (appointMentProject.equals("20")) {
                mTvCardTitle.setText("音频");
            } else if (appointMentProject.equals("30")) {
                mTvCardTitle.setText("视频");
            } else if (appointMentProject.equals("40")) {
                mTvCardTitle.setText("电话");
            }
        } else if (messageType.equals("consultation")) {
            mTvCardTitle.setText("问诊资料");
        }
        EMMessage.Direct direct = message.direct();
        if (direct == EMMessage.Direct.SEND) {
            if (messageType.equals("card")) {
                rl_class.setVisibility(GONE);
                switch (orderType) {
                    case "1":
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_agree_stamp);
                        mTvOperMsg.setText("您已同意");
                        break;
                    case "2":
                        ivStampIcon.setVisibility(View.GONE);
                        mTvOperMsg.setText("修改");
                        break;
                    case "3":
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_refuse_stamp);
                        mTvOperMsg.setText("您已拒绝");
                        break;
                    default:
                }
            } else if (messageType.equals("terminationOrder")) {
                rl_class.setVisibility(GONE);
                switch (orderType) {
                    case "1":
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_agree_stamp);
                        mTvOperMsg.setText("您已同意");
                        break;
                    case "2":
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_refuse_stamp);
                        mTvOperMsg.setText("您已拒绝");
                        break;
                    case "3":
                        ivStampIcon.setVisibility(View.GONE);
                        mTvOperMsg.setText("您已撤销解约");
                        break;
                    default:
                        ivStampIcon.setVisibility(View.GONE);
                        mTvOperMsg.setText("您已成功发起解约，等待对方确认");
                        break;
                }

            }
            //预约
            else if (messageType.equals("appointment")) {
                ivStampIcon.setVisibility(GONE);
                mTvPriceValue.setVisibility(GONE);
                tv_monitor_type.setText("预约时间");
                tv_coach_rate.setText("取消时间");
                tv_sign_time.setText("预约项目");
                if (startTime.contains("/")){
                   startTime = startTime.replace("/", "-");
                }
                mTvMonitValue.setText(startTime);
                mTvCoachRateValue.setText(cancelTime);
                mTvSignTimeValue.setText(appointMentProject);
                tv_class_vlaue.setText(appointMentType);
                mTvOperMsg.setText("您已取消预约");
            }

        } else if (direct == EMMessage.Direct.RECEIVE) {
            mTvEnsureBtn.setVisibility(View.GONE);
            ivStampIcon.setVisibility(View.GONE);
            if (messageType.equals("terminationOrder")) {//解约
                rl_immediately.setVisibility(View.GONE);
                rl_class.setVisibility(GONE);
                switch (orderType) {
                    case "1":
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_agree_stamp);
                        rlCancelContractOrderRoot.setVisibility(View.GONE);
                        rlSignOrderRoot.setVisibility(View.GONE);
                        mTvOperReceivedMsg.setVisibility(View.VISIBLE);
                        mTvOperReceivedMsg.setText("对方已同意");
                        break;
                    case "2":
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_refuse_stamp);
                        rlCancelContractOrderRoot.setVisibility(View.GONE);
                        rlSignOrderRoot.setVisibility(View.GONE);
                        mTvOperReceivedMsg.setVisibility(View.VISIBLE);
                        mTvOperReceivedMsg.setText("对方已拒绝");
                        break;
                    case "3":
                        ivStampIcon.setVisibility(View.GONE);
                        rlCancelContractOrderRoot.setVisibility(View.GONE);
                        rlSignOrderRoot.setVisibility(View.GONE);
                        mTvOperReceivedMsg.setVisibility(View.VISIBLE);
                        mTvOperReceivedMsg.setText("对方已撤销解约");
                        break;
                    default:
                        mTvUpdateBtn.setVisibility(View.GONE);
                        mTvOperReceivedMsg.setVisibility(View.GONE);
                        rlCancelContractOrderRoot.setVisibility(View.VISIBLE);
                        rlSignOrderRoot.setVisibility(View.GONE);
                        break;
                }


            } else if (messageType.equals("card")) { //签约
                mTvUpdateBtn.setVisibility(View.VISIBLE);
                rlCancelContractOrderRoot.setVisibility(View.GONE);
                rl_class.setVisibility(GONE);
                rlSignOrderRoot.setVisibility(View.VISIBLE);
                rl_immediately.setVisibility(View.GONE);
                rl_one.setVisibility(VISIBLE);
                rl_two.setVisibility(VISIBLE);
                rl_three.setVisibility(VISIBLE);
                mTvPriceValue.setVisibility(VISIBLE);
                tv_monitor_type.setText("检测类型");
                tv_coach_rate.setText("监测频次");
                tv_sign_time.setText("签约时长");
                mTvOperReceivedMsg.setVisibility(View.GONE);
                tv_patient_class.setVisibility(GONE);
            }
            //医生发的预约
            else if (messageType.equals("appointment")) {
                rlSignOrderRoot.setVisibility(View.GONE);
                ivStampIcon.setVisibility(GONE);
                mTvPriceValue.setVisibility(GONE);
                rl_immediately.setVisibility(View.GONE);
                tv_monitor_type.setText("预约时间");
                tv_coach_rate.setText("取消时间");
                tv_sign_time.setText("预约项目");
                mTvMonitValue.setText(startTime);
                mTvCoachRateValue.setText(cancelTime);
                mTvSignTimeValue.setText(appointMentProject);
                tv_class_vlaue.setText(appointMentType);
                mTvOperReceivedMsg.setVisibility(View.VISIBLE);
                mTvOperReceivedMsg.setText("医生已取消预约");
            }
            //病历
            else if (messageType.equals("medicalRecord")) {
                rl_immediately.setVisibility(View.GONE);
                rl_class.setVisibility(View.GONE);
                mTvPriceValue.setVisibility(View.GONE);
                mTvEnsureBtn.setVisibility(View.VISIBLE);
                rlSignOrderRoot.setVisibility(View.GONE);
                userName = mProvideViewSysUserPatientInfoAndRegion.getUserName();
                nickName = message.getStringAttribute("nickName", "");
                patient_rl.setVisibility(VISIBLE);
                tv_monitor_type.setText("患者");
                tv_coach_rate.setText("就诊结束时间");
                tv_sign_time.setText("就诊医生");
                mTvMonitValue.setText(userName);
                //预约结束时间
                mTvCoachRateValue.setText(endTime);
                nickName = message.getStringAttribute("nickName", "");
                mTvSignTimeValue.setText(nickName);
                tv_patient_class_vlaue.setText(patientType);
            }
            //医生接诊
            else if (messageType.equals("receiveTreatment")) {
                rl_one.setVisibility(VISIBLE);
                rl_two.setVisibility(VISIBLE);
                rl_three.setVisibility(VISIBLE);
                rlSignOrderRoot.setVisibility(View.GONE);
                rl_immediately.setVisibility(View.GONE);
                rl_class.setVisibility(GONE);
                mTvPriceValue.setVisibility(GONE);
                mTvOperReceivedMsg.setVisibility(View.VISIBLE);
                mTvOperReceivedMsg.setText("医生已接诊");
                //图文
                if (appointMentProject.equals("10")) {
                    patient_rl.setVisibility(VISIBLE);
                    tv_monitor_type.setText("接诊时间");
                    tv_coach_rate.setText("结束时间");
                    tv_sign_time.setText("接诊医生");
                    tv_patient_class.setText("剩余次数");
                    //赋值
                    mTvMonitValue.setText(receiveTime);
                    //预约结束时间
                    mTvCoachRateValue.setText(endTime);
                    nickName = message.getStringAttribute("nickName", "");
                    mTvSignTimeValue.setText(nickName);
                    tv_patient_class_vlaue.setText(surplusTimes);
                } else {
                    //音视频电话  显示
                    patient_rl.setVisibility(GONE);
                    tv_monitor_type.setText("接诊时间");
                    tv_coach_rate.setText("接诊医生");
                    tv_sign_time.setText("剩余时长");
                    //赋值
                    mTvMonitValue.setText(receiveTime);
                    //接诊医生
                    nickName = message.getStringAttribute("nickName", "");
                    mTvCoachRateValue.setText(nickName);
                    mTvSignTimeValue.setText(surplusTimes);
                }
            }
            //问诊资料
            else if (messageType.equals("consultation")) {
                mTvOperReceivedMsg.setVisibility(View.GONE);
                mTvEnsureBtn.setVisibility(View.GONE);
                ivStampIcon.setVisibility(GONE);
                rl_one.setVisibility(GONE);
                rl_two.setVisibility(GONE);
                rl_three.setVisibility(GONE);
                rl_class.setVisibility(View.GONE);
                rlSignOrderRoot.setVisibility(GONE);
                mTvPriceValue.setVisibility(View.GONE);
                rl_immediately.setVisibility(View.VISIBLE);
            }
        }
        setBtnOperStatus();

    }

    /**
     * 添加监听
     */
    private void addListener() {
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            mTvAgreeBtn.setOnClickListener(v -> {
                Object tag = mTvAgreeBtn.getTag();
                if (tag != null) {
                    String string = tag.toString();
                    int i = Integer.parseInt(string);
                    if (i == 1) {

                        LogUtils.e("接受消息  同意 orderId  " + orderId);
                        LogUtils.e("接受消息  同意 singId  " + singId);
                        LogUtils.e("接受消息  同意 singNO  " + singNO);


                        String patientCode = mProvideViewSysUserPatientInfoAndRegion.getPatientCode();
                        String userName = mProvideViewSysUserPatientInfoAndRegion.getUserName();
                        String nickName = message.getStringAttribute("nickName", "");
                        OrderOperationManager.getInstance().sendOrderOperRequest(message.getFrom()
                                , nickName, singId, singNO, patientCode, userName, "1",
                                ParameUtil.loginDoctorPosition, (isSucess, msg) -> {
                                    if (isSucess) {

                                        EventBus.getDefault().post(new OrderMessage(orderId,
                                                singNO, monitoringType, coach
                                                , signUpTime, price, messageType, "1", singId));
                                    } else {
                                        ToastUtils.showToast(msg);
                                    }
                                });
                    }
                }

            });
            mTvUpdateBtn.setOnClickListener(v -> {
                Object tag = mTvUpdateBtn.getTag();
                if (tag != null) {
                    String string = tag.toString();
                    int i = Integer.parseInt(string);
                    if (i == 1) {
                        if (messageType.equals("card")) {
                            String patientCode = mProvideViewSysUserPatientInfoAndRegion.getPatientCode();
                            String userName = mProvideViewSysUserPatientInfoAndRegion.getUserName();
                            String nickName = message.getStringAttribute("nickName", "");
                            OrderOperationManager.getInstance().sendOrderOperRequest(message.getFrom()
                                    , nickName, singId, singNO, patientCode, userName, "2",
                                    ParameUtil.loginDoctorPosition, (isSucess, msg) -> {
                                        if (isSucess) {
                                            if (StringUtils.isNotEmpty(msg)) {
                                                UpdateOrderResultBean updateOrderResultBean
                                                        = GsonUtils.fromJson(msg, UpdateOrderResultBean.class);
                                                if (updateOrderResultBean != null) {
                                                    orderId = updateOrderResultBean.getSignCode();
                                                    signNo_new = updateOrderResultBean.getSignNo();
                                                    singId = updateOrderResultBean.getSignNo();
                                                    message.setAttribute("orderId", orderId);
                                                }

                                            }

                                            LogUtils.e("接受消息  修改 orderId" + orderId);
                                            LogUtils.e("接受消息  修改  singNO" + singNO);
                                            LogUtils.e("接受消息  修改  signNo_new" + signNo_new);


                                            EventBus.getDefault().post(new OrderMessage(signNo_new,
                                                    singNO, monitoringType, coach
                                                    , signUpTime, price, messageType, "2", orderId));
                                        } else {
                                            ToastUtils.showToast(msg);

                                        }
                                    });


                        }
                    }
                }


            });
            mTvRefuseBtn.setOnClickListener(v -> {

                if (message.direct() == EMMessage.Direct.RECEIVE) {
                    Object tag = mTvRefuseBtn.getTag();
                    if (tag != null) {
                        String string = tag.toString();
                        int i = Integer.parseInt(string);
                        if (i == 1) {
                            if (messageType.equals("card")) {
                                String nickName = message.getStringAttribute("nickName", "");
                                Bundle bundle = new Bundle();
                                bundle.putString("orderId", singNO);
                                bundle.putString("orderNo", singId);
                                bundle.putString("operDoctorCode", message.getFrom());
                                bundle.putString("operDoctorName", nickName);
                                startActivity(RefusedOrderActivity.class, bundle);

                            } else if (messageType.equals("terminationOrder")) {
                                EventBus.getDefault().post(new OrderMessage(orderId,
                                        singNO, monitoringType, coach
                                        , signUpTime, price, messageType, "2", singId));
                            }

                        }
                    }


                }

            });
            mTvCancelContractAgreeBtn.setOnClickListener(v -> {
                Object tag = mTvCancelContractAgreeBtn.getTag();
                if (tag != null) {
                    String string = tag.toString();
                    int i = Integer.parseInt(string);
                    if (i == 1) {
                        String patientCode = mProvideViewSysUserPatientInfoAndRegion.getPatientCode();
                        String userName = mProvideViewSysUserPatientInfoAndRegion.getUserName();
                        String nickName = message.getStringAttribute("nickName", "");
                        OrderOperationManager.getInstance().sendOrderCancelContractOperRequest(message.getFrom()
                                , nickName, orderId, singNO, patientCode, userName, "1",
                                ParameUtil.loginDoctorPosition, (isSucess, msg) -> {
                                    if (isSucess) {
                                        OrderMessage event = new OrderMessage(orderId, singNO,
                                                monitoringType, coach
                                                , signUpTime, price, messageType, "1", singId);
                                        EventBus.getDefault().post(event);
                                    } else {
                                        ToastUtils.showToast(msg);
                                    }
                                });
                    }

                }


            });

            mTvCancelContractRefuseBtn.setOnClickListener(v -> {
                Object tag = mTvCancelContractAgreeBtn.getTag();
                if (tag != null) {
                    String string = tag.toString();
                    int i = Integer.parseInt(string);
                    if (i == 1) {
                        String nickName = message.getStringAttribute("nickName", "");
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId", singNO);
                        bundle.putString("orderNo", singId);
                        bundle.putString("operDoctorCode", message.getFrom());
                        bundle.putString("operDoctorName", nickName);

                        bundle.putString("monitor", monitoringType);
                        bundle.putString("num", coach);
                        bundle.putString("time", signUpTime);
                        bundle.putString("money", price);


                        startActivity(RefusedCancelContractActivity.class, bundle);
                    }
                }

            });
            //立即填写
            rl_immediately.setOnClickListener(v -> {
                Intent intent = new Intent();
                Object tag = mTvCancelContractAgreeBtn.getTag();
                if (tag != null) {
                    String string = tag.toString();
                    int i = Integer.parseInt(string);
                    if (i == 1) {
                        String nickName = message.getStringAttribute("nickName", "");
                        intent.setAction("android.intent.action.Inquiry");
                        intent.putExtra("order", orderId);
                        intent.putExtra("doctorCode", message.getFrom());
                        intent.putExtra("doctorName", nickName);
                        intent.putExtra("treatmentType", "1");
                        mContext.startActivity(intent);
                    }
                }


            });
            mTvEnsureBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTvEnsureBtn.getText().toString().equals("确定")) {
                        OrderOperationManager.getInstance()
                                .sendOperPatientMedicalRecordConfirmRequest(orderId, (Activity) mContext
                                        , new OrderOperationManager.OnCallBackListener() {
                                            @Override
                                            public void onResult(boolean isSucess, String msg) {
                                                if (isSucess) {
                                                    message.setAttribute("isConfirm", "1");
                                                    mTvEnsureBtn.setText("已确定");
                                                    mTvEnsureBtn.setBackgroundResource(0);
                                                    mTvEnsureBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                                                } else {
                                                    message.setAttribute("isConfirm", "0");
                                                    mTvEnsureBtn.setText("确定");
                                                    mTvEnsureBtn.setBackgroundResource(R.drawable.bg_round_7a9eff_15);
                                                    mTvEnsureBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_ffffff));
                                                    ToastUtils.showToast(msg);
                                                }
                                            }
                                        });
                    }

                }
            });
            mTvFillInBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Object tag = mTvCancelContractAgreeBtn.getTag();
                    if (tag != null) {
                        String string = tag.toString();
                        int i = Integer.parseInt(string);
                        if (i == 1) {
                            String nickName = message.getStringAttribute("nickName", "");
                            intent.setAction("android.intent.action.Inquiry");
                            intent.putExtra("order", orderId);
                            intent.putExtra("doctorCode", message.getFrom());
                            intent.putExtra("doctorName", nickName);
                            intent.putExtra("treatmentType", "1");
                            mContext.startActivity(intent);
                        }
                    }

                }
            });
        }

        mLLRootView.setOnClickListener(v -> {
            String nickName = message.getStringAttribute("nickName", "");
            switch (messageType) {
                case "card": {
                    if (orderType.equals("3")) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("operDoctorCode", message.getFrom());
                        bundle1.putString("operDoctorName", nickName);
                        bundle1.putString("orderId", singNO);
                        bundle1.putString("type", "1");
                        startActivity(CancelConfirmDeitalActivity.class, bundle1);


                    } else {
                        LogUtils.e("点击跳转  " + singNO);
                        Bundle bundle = new Bundle();
                        bundle.putString("signCode", singNO);
                        bundle.putString("operDoctorCode", message.getFrom());
                        bundle.putString("operDoctorName", nickName);
                        startActivity(SignOrderDetialActivity.class, bundle);
                    }

                    break;
                }
                case "terminationOrder":

                    if (!orderType.equals("3")) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("type", "1");
                        bundle1.putString("operDoctorCode", message.getFrom());
                        bundle1.putString("operDoctorName", nickName);
                        bundle1.putString("orderId", orderId);
                        startActivity(CancelConfirmDeitalActivity.class, bundle1);
                    }

                    break;
                case "appointment": {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId", orderId);
                    startActivity(AncelAppActivity.class, bundle);
                    break;
                }
                case "medicalRecord": {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.Med");
                    intent.putExtra("reserveCode", orderId);
                    mContext.startActivity(intent);
                    break;
                }
                //医生接诊详情
                case "receiveTreatment":{
//                    Bundle bundle = new Bundle();
//                    bundle.putString("orderId", orderId);
                 //   startActivity(OrderMessage_OrderPayActivity.class);

                }
                default:
            }

        });

    }


    /**
     * 跳转Activity
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     */
    @SuppressWarnings("rawtypes")
    protected void startActivity(Class paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(mContext, paramClass);
        mContext.startActivity(localIntent);
    }

}
