package www.patient.jykj_zxyl.base.base_adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import www.patient.jykj_zxyl.base.R;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;

/**
 * Description:已完成订单适配器
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 17:16
 */
public class OrderDetialMonitorAdapter extends RecyclerView.Adapter<OrderDetialMonitorAdapter.ViewHolder> {
    List<OrderDetialBean.OrderDetailListBean> datas;
    public OrderDetialMonitorAdapter(List<OrderDetialBean.OrderDetailListBean> list) {
    this.datas=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_order_monitor,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetialBean.OrderDetailListBean orderDetailListBean = datas.get(position);
        holder.mTvItemName.setText(orderDetailListBean.getMainConfigDetailName());
        holder.mTvItemValue.setText(orderDetailListBean.getTotlePrice()+"元/次");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvItemName;
        TextView mTvItemValue;
        LinearLayout llItemRoot;

        public ViewHolder(View view) {
            super(view);
            mTvItemName = itemView.findViewById(R.id.tv_item_name);
            mTvItemValue = itemView.findViewById(R.id.tv_item_value);
            llItemRoot = itemView.findViewById(R.id.ll_item_root);
        }
    }
}
