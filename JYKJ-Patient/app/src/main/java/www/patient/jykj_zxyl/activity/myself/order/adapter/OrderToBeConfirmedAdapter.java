package www.patient.jykj_zxyl.activity.myself.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allin.commonadapter.ViewHolder;
import com.allin.commonadapter.recyclerview.MultiItemRecycleViewAdapter;

import java.util.List;

import entity.mySelf.ProvideInteractOrderInfo;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.OrderMessage_OrderPayActivity;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.adapter.BloodLogListAdapter;
import www.patient.jykj_zxyl.base.base_bean.MultiItemEntity;
import www.patient.jykj_zxyl.base.enum_type.OrderStatusEnum;
import www.patient.jykj_zxyl.util.DateUtils;

/**
 * Description:待确认订单适配器
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 17:10
 */
public class OrderToBeConfirmedAdapter extends MultiItemRecycleViewAdapter<MultiItemEntity> {

    OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public OrderToBeConfirmedAdapter(Context context, List datas) {
        super(context, datas, new CommonMutipleNoOrderListItemType());
    }


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void convert(ViewHolder viewHolder, MultiItemEntity multiItemEntity, int i) {
        ProvideInteractOrderInfo parbean = (ProvideInteractOrderInfo)
                multiItemEntity;
        int layoutId = viewHolder.getLayoutId();
        switch (layoutId) {
            case R.layout.item_to_be_confirmed_order_card:
                LinearLayout mLlItemRoot = viewHolder.getView(R.id.ll_item_root);
                RelativeLayout mRlToBeConfirmedRoot =
                        viewHolder.getView(R.id.rl_to_be_confirmed_root);
                RelativeLayout mRlCancelContractRoot = viewHolder.getView(R.id.rl_cancel_contract_root);
                RelativeLayout rlOrderPaymentRoot = viewHolder.getView(R.id.rl_order_payment_root);

                TextView mTvOrderType = viewHolder.getView(R.id.tv_order_type);
                TextView mTvOrderTime = viewHolder.getView(R.id.tv_order_time);
                TextView mTvMonitorValue = viewHolder.getView(R.id.tv_monitor_value);
                TextView mTvCoachValue = viewHolder.getView(R.id.tv_coach_value);
                TextView mTvSignTimeValue = viewHolder.getView(R.id.tv_sign_time_value);
                TextView mTvPriceValue = viewHolder.getView(R.id.tv_price_vlaue);
                TextView mTvRefuseBtn = viewHolder.getView(R.id.tv_refuse_btn);
                TextView mTvUpdateBtn = viewHolder.getView(R.id.tv_update_btn);
                TextView tvAgreeBtn = viewHolder.getView(R.id.tv_agree_btn);
                TextView mTvCancelContractRefuseBtn = viewHolder.getView(R.id.tv_cancel_contract_refuse_btn);
                TextView mTvCancelContractAgreeBtn = viewHolder.getView(R.id.tv_cancel_contract_agree_btn);
                TextView tvOrderPaymentBtn = viewHolder.getView(R.id.tv_order_payment_btn);
                TextView mTvDeleteBtn = viewHolder.getView(R.id.tv_delete);

                String flagOrderState = parbean.getSignStatus();

                String orderState = flagOrderState.toString();
                switch (orderState) {
                    case OrderStatusEnum.orderSubmitCode:
                        mRlToBeConfirmedRoot.setVisibility(View.VISIBLE);
                        mRlCancelContractRoot.setVisibility(View.GONE);
                        rlOrderPaymentRoot.setVisibility(View.GONE);
                        break;
                    case OrderStatusEnum.orderExpireCancelContractCode:
                    case OrderStatusEnum.orderAdvenceCancelContractCode:
                        mRlToBeConfirmedRoot.setVisibility(View.GONE);
                        mRlCancelContractRoot.setVisibility(View.VISIBLE);
                        rlOrderPaymentRoot.setVisibility(View.GONE);

                        break;
                    case OrderStatusEnum.orderAgreeCode:
                        mRlToBeConfirmedRoot.setVisibility(View.GONE);
                        mRlCancelContractRoot.setVisibility(View.GONE);
                        rlOrderPaymentRoot.setVisibility(View.VISIBLE);
                        break;
                    default:
                        mRlToBeConfirmedRoot.setVisibility(View.GONE);
                        mRlCancelContractRoot.setVisibility(View.GONE);
                        rlOrderPaymentRoot.setVisibility(View.GONE);
                        break;
                }


//                mTvOrderType.setText(parbean.getTreatmentTypeName());
                mTvOrderTime.setText(DateUtils.getStringTimeSlash(parbean.getCreateDate()));
                Integer coachValue = parbean.getProCount();
                mTvMonitorValue.setText(coachValue + "项");

                /*if (coachValue != null) {
                    if (coachValue == 1) {
                        mTvMonitorValue.setText("一项");
                    } else if (coachValue == 2) {
                        mTvMonitorValue.setText("两项");
                    } else if (coachValue == 3) {
                        mTvMonitorValue.setText("三项");
                    } else if (coachValue == 4) {
                        mTvMonitorValue.setText("四项");
                    } else if (coachValue == 5) {
                        mTvMonitorValue.setText("五项");
                    } else if (coachValue == 6) {
                        mTvMonitorValue.setText("六项");
                    }
                }*/
               /* String signStatus = parbean.getSignStatus();
                if (signStatus.equals(OrderStatusEnum.orderSubmitCode)) {  //同意,修改,拒绝
                    mRlToBeConfirmedRoot.setVisibility(View.VISIBLE);
                    mRlCancelContractRoot.setVisibility(View.GONE);
                    rlOrderPaymentRoot.setVisibility(View.GONE);
                } else if (signStatus.equals(OrderStatusEnum.orderAgreeCode)) {  //支付


                } else if (signStatus.equals(OrderStatusEnum.orderNeedUpdateCode)) { //修改
                    mRlToBeConfirmedRoot.setVisibility(View.GONE);
                    mRlCancelContractRoot.setVisibility(View.GONE);
                    rlOrderPaymentRoot.setVisibility(View.VISIBLE);
                    tvOrderPaymentBtn.setText("需修改");

                } else {

                }*/


                mTvCoachValue.setText(String.format("%s次/%s",
                        parbean.getDetectRateUnitCode(), parbean.getDetectRateUnitName()));
                mTvSignTimeValue.setText(String.format("%s%s", parbean.getSignDuration(), parbean.getSignDurationUnit()));
                mTvPriceValue.setText(String.format("¥%s", parbean.getActualPayment()));
                mTvRefuseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener != null) {
                            onClickItemListener.onClickRefuse(i);
                        }
                    }
                });
                mTvUpdateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener != null) {
                            onClickItemListener.onClickUpdate(i);
                        }
                    }
                });
                tvAgreeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener != null) {
                            onClickItemListener.onClickAgree(i);
                        }
                    }
                });
                mTvCancelContractRefuseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener != null) {
                            onClickItemListener.onClickCancelContractRefuse(i);
                        }

                    }
                });
                mTvCancelContractAgreeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener != null) {
                            onClickItemListener.onClickCancelContractAgree(i);
                        }
                    }
                });
                tvOrderPaymentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener != null) {
                            onClickItemListener.onClickPayment(i);
                        }
                    }
                });
                mLlItemRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener != null) {
                            onClickItemListener.onClickItem(i, viewHolder);
                        }
                    }
                });
                mTvDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener != null) {
                            onClickItemListener.onClickDelete(i);
                        }
                    }
                });
                break;
            case R.layout.item_fragment_myorder_no_1:
                LinearLayout llItemRoot = viewHolder.getView(R.id.ll_item);
                TextView mDate = viewHolder.getView(R.id.order_date);
                TextView mSuType = viewHolder.getView(R.id.treat_type);
                TextView mSurPrice = viewHolder.getView(R.id.treat_state);
                TextView price = viewHolder.getView(R.id.order_desc);
                TextView pay_btn = viewHolder.getView(R.id.pay_btn);
                TextView find_doc_btn = viewHolder.getView(R.id.find_doc_btn);
                TextView mTvDelete = viewHolder.getView(R.id.tv_delete);
//                String stringTimeMinute = DateUtils.fomrDateSeflFormat(parbean.getOrderDate(),"yyyy-MM-dd");
//                if(!TextUtils.isEmpty(stringTimeMinute)){
//                    mDate.setText(stringTimeMinute);
//                }
                mSurPrice.setText(String.format("[%s]", parbean.getFlagOrderStateName()));
                price.setText(parbean.getOrderShowContent());
                mSuType.setText(parbean.getTreatmentTypeName());
                if (1 == parbean.getFlagOrderState()) {
                    pay_btn.setVisibility(View.VISIBLE);
                    find_doc_btn.setVisibility(View.GONE);
                } else {
                    pay_btn.setVisibility(View.GONE);
                    find_doc_btn.setVisibility(View.VISIBLE);
                }
                pay_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entity.ProvideInteractOrderInfo provideInteractOrderInfo =
                                new entity.ProvideInteractOrderInfo();
                        provideInteractOrderInfo.setOrderCode(parbean.getOrderCode());
                        mContext.startActivity(new Intent(mContext, OrderMessage_OrderPayActivity.class)
                                .putExtra("provideInteractOrderInfo", provideInteractOrderInfo));
                    }
                });
                find_doc_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, TJZJActivity.class));
                    }
                });
                llItemRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                mTvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener != null) {
                            onClickItemListener.onClickDelete(i);
                        }
                    }
                });
                break;
            default:
        }

    }


    public interface OnClickItemListener {
        void onClickAgree(int position);

        void onClickUpdate(int position);

        void onClickRefuse(int position);

        void onClickCancelContractRefuse(int position);

        void onClickCancelContractAgree(int position);

        void onClickPayment(int position);

        void onClickItem(int position, ViewHolder holder);

        void onClickDelete(int pos);
    }
}
