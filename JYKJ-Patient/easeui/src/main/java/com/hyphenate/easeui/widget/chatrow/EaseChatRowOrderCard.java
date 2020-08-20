package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.order.CancelConfirmDeitalActivity;
import com.hyphenate.easeui.order.RefusedCancelContractActivity;
import com.hyphenate.easeui.order.RefusedOrderActivity;
import com.hyphenate.easeui.order.SignOrderDetialActivity;
import com.hyphenate.easeui.widget.EaseImageView;

import org.greenrobot.eventbus.EventBus;

import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.ProvideViewSysUserPatientInfoAndRegion;
import www.patient.jykj_zxyl.base.base_bean.UpdateOrderResultBean;
import www.patient.jykj_zxyl.base.base_manager.OrderOperationManager;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
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
        orderId = message.getStringAttribute("orderId", "");
        monitoringType = message.getStringAttribute("monitoringType", "");
        coach = message.getStringAttribute("coach", "");
        signUpTime = message.getStringAttribute("signUpTime", "");
        price = message.getStringAttribute("price", "");
        messageType = message.getStringAttribute("messageType", "");
        singNO = message.getStringAttribute("singNo", "");
        imageUrl = message.getStringAttribute("imageUrl", "");
        mTvMonitValue.setText(monitoringType);
        mTvCoachRateValue.setText(coach);
        mTvSignTimeValue.setText(signUpTime);
        mTvPriceValue.setText(String.format("¥%s", price));
        orderType = message.getStringAttribute("orderType", "");//1已同意 2 修改 3 拒绝（由患者操作发起时会携带此参数）
        if (messageType.equals("terminationOrder")) {
            mTvCardTitle.setText("解约订单");
        } else if (messageType.equals("card")) {
            mTvCardTitle.setText("签约订单");
        }
        EMMessage.Direct direct = message.direct();
        if (direct == EMMessage.Direct.SEND) {
            if (messageType.equals("card")) {
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

        } else if (direct == EMMessage.Direct.RECEIVE) {

            ivStampIcon.setVisibility(View.GONE);
            if (messageType.equals("terminationOrder")) {

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


            } else if (messageType.equals("card")) {
                mTvUpdateBtn.setVisibility(View.VISIBLE);
                rlCancelContractOrderRoot.setVisibility(View.GONE);
                rlSignOrderRoot.setVisibility(View.VISIBLE);
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
                if (tag!=null) {
                    String string = tag.toString();
                    int i = Integer.parseInt(string);
                    if(i==1){
                        String patientCode = mProvideViewSysUserPatientInfoAndRegion.getPatientCode();
                        String userName = mProvideViewSysUserPatientInfoAndRegion.getUserName();
                        String nickName = message.getStringAttribute("nickName", "");
                        OrderOperationManager.getInstance().sendOrderOperRequest(message.getFrom()
                                , nickName, orderId, singNO, patientCode, userName, "1",
                                ParameUtil.loginDoctorPosition, (isSucess, msg) -> {
                                    if (isSucess) {
                                        EventBus.getDefault().post(new OrderMessage(orderId,
                                                singNO, monitoringType, coach
                                                , signUpTime, price, messageType, "1"));
                                    } else {
                                        ToastUtils.showToast(msg);
                                    }
                                });
                    }
                }

            });
            mTvUpdateBtn.setOnClickListener(v -> {
                Object tag = mTvUpdateBtn.getTag();
                if (tag!=null) {
                    String string = tag.toString();
                    int i = Integer.parseInt(string);
                    if (i==1) {
                        if (messageType.equals("card")) {

                            String patientCode = mProvideViewSysUserPatientInfoAndRegion.getPatientCode();
                            String userName = mProvideViewSysUserPatientInfoAndRegion.getUserName();
                            String nickName = message.getStringAttribute("nickName", "");
                            OrderOperationManager.getInstance().sendOrderOperRequest(message.getFrom()
                                    , nickName, orderId, singNO, patientCode, userName, "2",
                                    ParameUtil.loginDoctorPosition, (isSucess, msg) -> {
                                        if (isSucess) {
                                            if (StringUtils.isNotEmpty(msg)) {
                                                UpdateOrderResultBean updateOrderResultBean
                                                        = GsonUtils.fromJson(msg, UpdateOrderResultBean.class);
                                                if (updateOrderResultBean != null) {
                                                    orderId = updateOrderResultBean.getSignCode();
                                                    message.setAttribute("orderId", orderId);
                                                }

                                            }
                                            EventBus.getDefault().post(new OrderMessage(orderId, singNO, monitoringType, coach
                                                    , signUpTime, price, messageType, "2"));
                                        } else {
                                            ToastUtils.showToast(msg);

                                        }
                                    });


                        }
                    }
                }


            });
            mTvRefuseBtn.setOnClickListener(v -> {

                if (message.direct() == EMMessage.Direct.RECEIVE ) {
                    Object tag = mTvRefuseBtn.getTag();
                    if (tag!=null) {
                        String string = tag.toString();
                        int i = Integer.parseInt(string);
                        if (i==1) {
                            if (messageType.equals("card")) {
                                String nickName = message.getStringAttribute("nickName", "");
                                Bundle bundle = new Bundle();
                                bundle.putString("orderId", orderId);
                                bundle.putString("operDoctorCode",message.getFrom());
                                bundle.putString("operDoctorName",nickName);
                                startActivity(RefusedOrderActivity.class, bundle);

                            } else if (messageType.equals("terminationOrder")) {
                                EventBus.getDefault().post(new OrderMessage(orderId, singNO, monitoringType, coach
                                        , signUpTime, price, messageType, "2"));
                            }
                        }
                    }


                }

            });
            mTvCancelContractAgreeBtn.setOnClickListener(v -> {
                Object tag = mTvCancelContractAgreeBtn.getTag();
                if (tag!=null) {
                    String string = tag.toString();
                    int i = Integer.parseInt(string);
                    if (i==1) {
                        String patientCode = mProvideViewSysUserPatientInfoAndRegion.getPatientCode();
                        String userName = mProvideViewSysUserPatientInfoAndRegion.getUserName();
                        String nickName = message.getStringAttribute("nickName", "");
                        OrderOperationManager.getInstance().sendOrderCancelContractOperRequest(message.getFrom()
                                , nickName, orderId, singNO, patientCode, userName, "1",
                                ParameUtil.loginDoctorPosition, (isSucess, msg) -> {
                                    if (isSucess) {
                                        OrderMessage event = new OrderMessage(orderId, singNO,
                                                monitoringType, coach
                                                , signUpTime, price, messageType, "1");
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
                if (tag!=null) {
                    String string = tag.toString();
                    int i = Integer.parseInt(string);
                    if (i==1) {
                        String nickName = message.getStringAttribute("nickName", "");
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId", orderId);
                        bundle.putString("operDoctorCode",message.getFrom());
                        bundle.putString("operDoctorName",nickName);
                        startActivity(RefusedCancelContractActivity.class, bundle);
                    }
                }


            });
        }

        mLLRootView.setOnClickListener(v -> {
            String nickName = message.getStringAttribute("nickName", "");
            if (messageType.equals("card")) {
                Bundle bundle = new Bundle();
                bundle.putString("signCode", orderId);
                bundle.putString("operDoctorCode",message.getFrom());
                bundle.putString("operDoctorName",nickName);
                startActivity(SignOrderDetialActivity.class, bundle);
            } else if (messageType.equals("terminationOrder")) {

                if (!orderType.equals("3")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("operDoctorCode",message.getFrom());
                    bundle1.putString("operDoctorName",nickName);
                    bundle1.putString("orderId", orderId);
                    startActivity(CancelConfirmDeitalActivity.class, bundle1);
                }


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
