package www.patient.jykj_zxyl.activity.myself.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allin.commonadapter.IMulItemViewType;
import com.allin.commonadapter.ViewHolder;
import com.allin.commonadapter.recyclerview.MultiItemRecycleViewAdapter;

import java.util.List;

import entity.mySelf.MyOrderProcess;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.myself.MyOrderAlRecycleAdapter;
import www.patient.jykj_zxyl.base.base_bean.MultiItemEntity;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.enum_type.OrderStatusEnum;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.StrUtils;

/**
 * Description:已完成订单适配器
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 17:16
 */
public class OrderComplatedAdapter extends MultiItemRecycleViewAdapter<MultiItemEntity> {

    private OnItemClickListener mOnItemClickListener;

    public OrderComplatedAdapter(Context context, List<MultiItemEntity> datas) {
        super(context, datas, new CommonMutipleComplateOrderListItemType());
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void convert(ViewHolder viewHolder, MultiItemEntity multiItemEntity, int i) {
        int layoutId = viewHolder.getLayoutId();
        switch (layoutId) {
            case R.layout.item_complated_order_card: //普通订单
                LinearLayout llItemRoot = viewHolder.getView(R.id.ll_item_root);
                RelativeLayout rlOrderComplateRoot = viewHolder.getView(R.id.rl_order_complate_root);
                RelativeLayout rlCancelContractSucessRoot = viewHolder.getView(R.id.rl_cancel_contract_sucess_root);
                MyOrderProcess parbean1 = (MyOrderProcess) multiItemEntity;
                TextView mTvOrderType = viewHolder.getView(R.id.tv_order_type);
                TextView mTvOrderTime = viewHolder.getView(R.id.tv_order_time);
                TextView mTvMonitorValue = viewHolder.getView(R.id.tv_monitor_value);
                TextView mTvCoachValue = viewHolder.getView(R.id.tv_coach_value);
                TextView mTvSignTimeValue = viewHolder.getView(R.id.tv_sign_time_value);
                TextView mTvPriceValue = viewHolder.getView(R.id.tv_price_vlaue);
                TextView tvLeaveMsgBtn = viewHolder.getView(R.id.tv_leave_msg_btn);
                TextView tvEvaluteBtn = viewHolder.getView(R.id.tv_evalute_btn);
                TextView tvCancelContractSucessBtn = viewHolder.getView(R.id.tv_cancel_contract_sucess_btn);
                int flagTreatmentState = parbean1.getSignStatus();

                String status = String.valueOf(flagTreatmentState);
                if (status.equals(OrderStatusEnum.orderAdvenceCancelContractCode)) {
                    rlCancelContractSucessRoot.setVisibility(View.VISIBLE);
                    rlOrderComplateRoot.setVisibility(View.GONE);
                } else {
                    rlCancelContractSucessRoot.setVisibility(View.GONE);
                    rlOrderComplateRoot.setVisibility(View.GONE);
                }

                String actualPayment = parbean1.getActualPayment();
                if (StringUtils.isNotEmpty(actualPayment)) {
                    mTvPriceValue.setText(String.format("¥%s", actualPayment));
                } else {
                    mTvPriceValue.setText(String.format("¥%s", "0"));
                }

//                mTvOrderType.setText(parbean1.getTreatmentTypeName());
                mTvOrderTime.setText(DateUtils.getStringTimeSlash(parbean1.getCreateDate()));
                Integer coachValue = parbean1.getProCount();
                if (coachValue != null) {
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
                }

                mTvCoachValue.setText(String.format("%s次/%s",
                        parbean1.getDetectRateUnitCode(), parbean1.getDetectRateUnitName()));
                mTvSignTimeValue.setText(String.format("%s%s", parbean1.getSignDuration(), parbean1.getSignDurationUnit()));
                llItemRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onClick(i, v);
                        }
                    }
                });
                tvLeaveMsgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onClickLeaveMsg(i);
                        }
                    }
                });
                tvEvaluteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onClickEvaluate(i);
                        }
                    }
                });
                tvCancelContractSucessBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
//                            mOnItemClickListener.onClickCancelSucess(i);
                        }
                    }
                });

                break;
            case R.layout.item_fragment_myorder_al_1: //签约订单
                String treatmentType = "";
                String treat_styleStr = "";
                String service_time_titleStr = "";
                String treat_style_toolStr = "";
                String service_timeStr = "";
                LinearLayout mClickLinearLayout = viewHolder.getView(R.id.item_fragmentYLZX_rmjxLayout);
                TextView treatment_type = viewHolder.getView(R.id.treatment_type);
                TextView process_state = viewHolder.getView(R.id.process_state);
                TextView order_date = viewHolder.getView(R.id.order_date);
                TextView advise_doctor = viewHolder.getView(R.id.advise_doctor);
                TextView treat_style = viewHolder.getView(R.id.treat_style);
                TextView treat_style_tool = viewHolder.getView(R.id.treat_style_tool);
                TextView service_time_title = viewHolder.getView(R.id.service_time_title);
                TextView service_time = viewHolder.getView(R.id.service_time);
                TextView leave_btn = viewHolder.getView(R.id.leave_btn);
                TextView opinion_btn = viewHolder.getView(R.id.opinion_btn);
                LinearLayout item_root = viewHolder.getView(R.id.item_root);
                MyOrderProcess parbean = (MyOrderProcess) multiItemEntity;

                switch (parbean.getTreatmentType()) {
                    case 1:
                        treatmentType = "[图文就诊]";
                        treat_styleStr = "服务开始时间";
                        service_time_titleStr = "服务截止时间";
                        treat_style_toolStr = DateUtils.getStringTimeSlash(parbean.getReserveTime());
                        service_timeStr = DateUtils.getStringTimeSlash(parbean.getReserveEndTime());
                        break;
                    case 2:
                        treatmentType = "[音频就诊]";
                        treat_styleStr = "接听电话";
                        service_time_titleStr = "预约服务时间";
                        treat_style_toolStr = parbean.getDoctorPhone();
                        service_timeStr = DateUtils.getStringTimeSlash(parbean.getReserveTime());
                        break;
                    case 3:
                        treatmentType = "[视频就诊]";
                        treat_styleStr = "接听电话";
                        service_time_titleStr = "预约服务时间";
                        treat_style_toolStr = parbean.getDoctorPhone();
                        service_timeStr = DateUtils.getStringTimeSlash(parbean.getReserveTime());
                        break;
                    case 5:
                        treatmentType = "[电话就诊]";
                        treat_styleStr = "接听电话";
                        service_time_titleStr = "预约服务时间";
                        treat_style_toolStr = parbean.getDoctorPhone();
                        service_timeStr = DateUtils.getStringTimeSlash(parbean.getReserveTime());
                        break;
                }

                treatment_type.setText(treatmentType);
                treat_style.setText(treat_styleStr);
                service_time_title.setText(service_time_titleStr);
                treat_style_tool.setText(treat_style_toolStr);
                service_time.setText(service_timeStr);
                advise_doctor.setText(parbean.getMainDoctorName());


                process_state.setText(DateUtils.getStringTimeSlash(parbean.getCreateDate()));
//                switch (parbean.getFlagColor()) {
//                    case 0:
//                        break;
//                    case 1:
//                        process_state.setTextColor(mContext.getResources().getColor(R.color.color_red));
//                        break;
//                    case 2:
//                        process_state.setTextColor(mContext.getResources().getColor(R.color.color_orange));
//                        break;
//                    case 3:
//                        process_state.setTextColor(mContext.getResources().getColor(R.color.color_yellow));
//                        break;
//                    case 4:
//                        process_state.setTextColor(mContext.getResources().getColor(R.color.color_blue));
//                        break;
//                    case 5:
//                        process_state.setTextColor(mContext.getResources().getColor(R.color.color_green));
//                        break;
//                }
                if (null != parbean.getOrderDate()) {
                    order_date.setText(DateUtils.fomrDateSeflFormat(parbean.getOrderDate(), "yyyy-MM-dd HH:mm"));
                }

               /* switch (parbean.getTreatmentType()) {
                    case 1:
                    case 5:
                    case 0:
                        treat_style.setText(mContext.getResources().getString(R.string.service_start_time));
                        if (null != parbean.getTreatmentDateStart()) {
                            treat_style_tool.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDateStart(), "yyyy-MM-dd HH:mm"));
                        }
                        service_time_title.setText(mContext.getResources().getString(R.string.service_deadline_time));
                        if (null != parbean.getTreatmentDateEnd()) {
                            service_time.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDateEnd(), "yyyy-MM-dd HH:mm"));
                        }
                        break;
                    case 2:
                    case 3:
                        treat_style.setText(mContext.getResources().getString(R.string.hold_on));
                        treat_style_tool.setText(StrUtils.defaultStr(parbean.getTreatmentLinkPhone()));
                        service_time_title.setText(mContext.getResources().getString(R.string.preserve_service_time));
                        if (null != parbean.getTreatmentDate()) {
                            service_time.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDate(), "yyyy-MM-dd HH:mm"));
                        }
                        break;
                    case 4:
                        treat_style.setText(mContext.getResources().getString(R.string.preserve_service_time_zone));
                        treat_style_tool.setText(StrUtils.defaultStr(parbean.getTreatmentTimeSlotName()));
                        service_time_title.setText(mContext.getResources().getString(R.string.preserve_service_time));
                        if (null != parbean.getTreatmentDate()) {
                            service_time.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDate(), "yyyy-MM-dd HH:mm"));
                        }


                        break;
                }*/

                if (mOnItemClickListener != null) {
                    item_root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickListener.onClick(i, view);
                        }
                    });

                    item_root.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View view) {
                            mOnItemClickListener.onLongClick(i, view);
                            return false;
                        }
                    });
                    leave_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onClick(i, v);
                        }
                    });
                    opinion_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onClick(i, v);
                        }
                    });
                    item_root.setTag(parbean);
                    leave_btn.setTag(parbean);
                    opinion_btn.setTag(parbean);
                }
                break;
            default:


        }
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, View view);

        void onLongClick(int position, View view);

        void onClickLeaveMsg(int position);

        void onClickEvaluate(int position);

        void onClickCancelSucess(int position);
    }
}
