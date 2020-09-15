package www.patient.jykj_zxyl.activity.myself.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.allin.commonadapter.ViewHolder;
import com.allin.commonadapter.recyclerview.MultiItemRecycleViewAdapter;

import java.util.List;

import entity.mySelf.MyOrderProcess;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.MultiItemEntity;
import www.patient.jykj_zxyl.base.enum_type.OrderStatusEnum;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.StrUtils;

/**
 * Description:进行中订单适配器
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 17:13
 */
public class OrderOnGoingAdapter extends MultiItemRecycleViewAdapter<MultiItemEntity> {

    private OnClickItemListener onClickItemListener;



    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public OrderOnGoingAdapter(Context context, List<MultiItemEntity> datas) {
        super(context, datas, new CommonMutipleGoOrderListItemType());
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void convert(ViewHolder viewHolder, MultiItemEntity multiItemEntity, int i) {
        MyOrderProcess parbean = (MyOrderProcess) multiItemEntity;
        int layoutId = viewHolder.getLayoutId();
        switch (layoutId){
            case R.layout.item_ongoing_order_card_1:
                LinearLayout llItemRoot = viewHolder.getView(R.id.ll_item_root);
                RelativeLayout rlCancelContractRoot = viewHolder.getView(R.id.rl_cancel_contract_root);
                RelativeLayout rlCancelContractGoingRoot = viewHolder.getView(R.id.rl_cancel_contract_going_root);
                RelativeLayout rlDoctorCancelContractRoot = viewHolder.getView(R.id.rl_doctor_cancle_contract_root);
                rlCancelContractRoot.setVisibility(View.GONE);
                rlCancelContractGoingRoot.setVisibility(View.GONE);
                rlDoctorCancelContractRoot.setVisibility(View.GONE);

                TextView mTvOrderType = viewHolder.getView(R.id.tv_order_type);
                TextView mTvOrderTime = viewHolder.getView(R.id.tv_order_time);
                TextView mTvMonitorValue = viewHolder.getView(R.id.tv_monitor_value);
                TextView mTvCoachValue = viewHolder.getView(R.id.tv_coach_value);
                TextView mTvSignTimeValue = viewHolder.getView(R.id.tv_sign_time_value);
                TextView mTvPriceValue = viewHolder.getView(R.id.tv_price_vlaue);
                TextView mTvCancelContractBtn=viewHolder.getView(R.id.tv_cancel_contract_btn);
                TextView tvConsultBtn = viewHolder.getView(R.id.tv_consult_btn);
                TextView tvCancelContractGoingBtn = viewHolder.getView(R.id.tv_cancel_contract_going_btn);
                TextView tvConsultBtn2 = viewHolder.getView(R.id.tv_consult_btn_2);
                TextView tvRefuseBtn = viewHolder.getView(R.id.tv_refuse_btn);
                TextView tvAgreeBtn = viewHolder.getView(R.id.tv_agree_btn);
                Integer flagTreatmentState = parbean.getFlagTreatmentState();
                if (flagTreatmentState!=null) {
                    String orderState = flagTreatmentState.toString();
                    switch (orderState){
                        case OrderStatusEnum
                                .orderSignCompleteCode:
                            rlCancelContractRoot.setVisibility(View.VISIBLE);
                            rlCancelContractGoingRoot.setVisibility(View.GONE);
                            rlDoctorCancelContractRoot.setVisibility(View.GONE);
                            break;
                        case OrderStatusEnum.orderPatientCancelContractApplyCode:
                            rlCancelContractGoingRoot.setVisibility(View.VISIBLE);
                            rlCancelContractRoot.setVisibility(View.GONE);
                            rlDoctorCancelContractRoot.setVisibility(View.GONE);
                            break;
                        case OrderStatusEnum.orderDoctorCancelContractCode:
                            rlCancelContractGoingRoot.setVisibility(View.GONE);
                            rlCancelContractRoot.setVisibility(View.GONE);
                            rlDoctorCancelContractRoot.setVisibility(View.VISIBLE);
                            break;
                            default:
                    }

                }
                mTvPriceValue.setText(String.format("¥%s", parbean.getActualPayment()));
                mTvOrderType.setText(parbean.getTreatmentTypeName());
                mTvOrderTime.setText(DateUtils.getStringTimeMinute(parbean.getOrderDate().getTime()));
                Integer coachValue = parbean.getProCount();
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
                mTvCoachValue.setText(String.format("%d次/%s",
                        parbean.getCoachValue(), parbean.getCoachUnitName()));
                mTvSignTimeValue.setText(parbean.getTimesName());
               // mTvPriceValue.setText(String.format("¥%s", parbean.getp));
                mTvCancelContractBtn.setOnClickListener(v -> {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickCancelContract(i);
                    }
                });
                tvConsultBtn.setOnClickListener(v -> {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickConsult(i);
                    }
                });
                tvCancelContractGoingBtn.setOnClickListener(v -> {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickCancelOnGoing(i);
                    }
                });
                tvConsultBtn2.setOnClickListener(v -> {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickConsult(i);
                    }
                });
                llItemRoot.setOnClickListener(v -> {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickItem(i,viewHolder);
                    }
                });
                tvRefuseBtn.setOnClickListener(v -> {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickRefused(i);
                    }

                });
                tvAgreeBtn.setOnClickListener(v -> {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickAgree(i);
                    }
                });

                break;
            case R.layout.item_fragment_myorder_on_1:

               LinearLayout mClickLinearLayout =viewHolder.getView (R.id.item_fragmentYLZX_rmjxLayout);
                TextView treatment_type = viewHolder.getView(R.id.treatment_type);
                TextView process_state = viewHolder.getView(R.id.process_state);
                TextView order_date = viewHolder.getView(R.id.order_date);
                TextView advise_doctor = viewHolder.getView(R.id.advise_doctor);
                TextView treat_style = viewHolder.getView(R.id.treat_style);
                TextView treat_style_tool = viewHolder.getView(R.id.treat_style_tool);
                TextView service_time_title = viewHolder.getView(R.id.service_time_title);
                TextView service_time = viewHolder.getView(R.id.service_time);
                TextView quest_btn = viewHolder.getView(R.id.quest_btn);
                TextView back_btn = viewHolder.getView(R.id.back_btn);
                LinearLayout item_root = viewHolder.getView(R.id.item_root);
                treatment_type.setText("["+parbean.getTreatmentTypeName()+"]");
                process_state.setText(parbean.getDoctorReceiveShow());
                back_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener!=null) {
                            onClickItemListener.onClickRefund(i);
                        }
                    }
                });
                item_root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener!=null) {
                            onClickItemListener.onClickItem(i,viewHolder);
                        }
                    }
                });
                quest_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickItemListener!=null) {
                            onClickItemListener.onClickConsult(i);
                        }
                    }
                });
                if(parbean.getFlagColor()==null){

                }else{
                    switch (parbean.getFlagColor()) {
                        case 0:
                            break;
                        case 1:
                            process_state.setTextColor(mContext.getResources().getColor(R.color.color_red));
                            break;
                        case 2:
                            process_state.setTextColor(mContext.getResources().getColor(R.color.color_orange));
                            break;
                        case 3:
                            process_state.setTextColor(mContext.getResources().getColor(R.color.color_yellow));
                            break;
                        case 4:
                            process_state.setTextColor(mContext.getResources().getColor(R.color.color_blue));
                            break;
                        case 5:
                            process_state.setTextColor(mContext.getResources().getColor(R.color.color_green));
                            break;
                    }
                }

                if (null != parbean.getOrderDate()) {
                    order_date.setText(DateUtils.fomrDateSeflFormat(parbean.getOrderDate(), "yyyy-MM-dd HH:mm"));
                }
                advise_doctor.setText(parbean.getDoctorName());
                switch (parbean.getTreatmentType()) {
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
                }
                break;
                default:
        }
    }

    public interface OnClickItemListener{
        void onClickCancelContract(int pos);

        void onClickConsult(int pos);

        void onClickCancelOnGoing(int pos);

        void onClickItem(int position, ViewHolder holder);

        void onClickRefund(int pos);

        void onClickRefused(int pos);

        void onClickAgree(int pos);


    }
}
