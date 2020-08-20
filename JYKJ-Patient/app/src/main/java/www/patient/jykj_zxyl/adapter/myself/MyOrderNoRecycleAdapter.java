package www.patient.jykj_zxyl.adapter.myself;

import android.app.Activity;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.mySelf.ProvideInteractOrderInfo;
import entity.wdzs.ProvideInteractPatientInterrogation;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.OrderMessage_OrderPayActivity;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WZXXOrderActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.StrUtils;

/**
 * 我的订单未完成
 */
public class MyOrderNoRecycleAdapter extends RecyclerView.Adapter<MyOrderNoRecycleAdapter.ViewHolder> {
    public List<ProvideInteractOrderInfo> datas = new ArrayList<>();
    private Activity mActivity;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;


    public MyOrderNoRecycleAdapter(List<ProvideInteractOrderInfo> list, Context context, Activity mainActivity) {
        mContext = context;
        datas = list;
        mActivity = mainActivity;
    }

    //重新设置数据
    public void setDate(List<ProvideInteractOrderInfo> list) {
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_myorder_no, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作

    /**
     * 如果是第一条数据，那么他肯定是该组的第一个用户，所以显示组别
     * 如果该用户是该组的第一个用户，那么就显示组别
     * 否则不再显示
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (mOnItemClickListener != null) {
            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

        ProvideInteractOrderInfo parbean = datas.get(position);
        viewHolder.mDate.setText(DateUtils.getStringTimeMinute(parbean.getOrderDate().getTime()));
        viewHolder.mSurPrice.setText("[" + parbean.getFlagOrderStateName() + "]");
        viewHolder.price.setText(parbean.getOrderShowContent());
        viewHolder.mSuType.setText(parbean.getTreatmentTypeName());
        if (1 == parbean.getFlagOrderState().intValue()) {
            viewHolder.pay_btn.setVisibility(View.VISIBLE);
            viewHolder.find_doc_btn.setVisibility(View.GONE);
        } else {
            viewHolder.pay_btn.setVisibility(View.GONE);
            viewHolder.find_doc_btn.setVisibility(View.VISIBLE);
        }

        viewHolder.pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entity.ProvideInteractOrderInfo provideInteractOrderInfo = new entity.ProvideInteractOrderInfo();
                //mProvideInteractPatientInterrogation.setDoctorCode(parbean.getDoctorCode());
                //mProvideInteractPatientInterrogation.setDoctorName(parbean.getDoctorName());
                provideInteractOrderInfo.setOrderCode(parbean.getOrderCode());
                //mProvideInteractPatientInterrogation.setIProvideInteractOrderInfo
                /*Intent parintent = new Intent(mContext, WZXXOrderActivity.class);
                String parorderid = parbean.getOrderCode();
                String parodertype = StrUtils.defaultStr(parbean.getTreatmentType());
                parintent.putExtra("provideInteractPatientInterrogation",mProvideInteractPatientInterrogation).putExtra("orderID",parorderid).putExtra("orderType",parodertype);
                mContext.startActivity(parintent);*/
                mContext.startActivity(new Intent(mActivity, OrderMessage_OrderPayActivity.class).putExtra("provideInteractOrderInfo", provideInteractOrderInfo));
            }
        });
        viewHolder.find_doc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, TJZJActivity.class));
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {

        return datas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_item;
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public TextView mSuType;                                //类型
        public TextView price;                                  //金额
        public TextView mDate;                                  //时间
        public TextView mSurPrice;                               //余额
        public TextView pay_btn;
        public TextView find_doc_btn;

        public ViewHolder(View view) {
            super(view);
            ll_item = view.findViewById(R.id.ll_item);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_fragmentYLZX_rmjxLayout);
            mDate = (TextView) view.findViewById(R.id.order_date);
            mSuType = (TextView) view.findViewById(R.id.treat_type);
            mSurPrice = (TextView) view.findViewById(R.id.treat_state);
            price = (TextView) view.findViewById(R.id.order_desc);
            pay_btn = (TextView) view.findViewById(R.id.pay_btn);
            find_doc_btn = (TextView) view.findViewById(R.id.find_doc_btn);
        }
    }


    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void updatas(List<ProvideInteractOrderInfo> paradatas) {
        this.datas.addAll(paradatas);
        this.notifyDataSetChanged();
    }
}
