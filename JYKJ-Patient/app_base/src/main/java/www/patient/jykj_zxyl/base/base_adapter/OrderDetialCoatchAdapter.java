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
import www.patient.jykj_zxyl.base.base_utils.LogUtils;

/**
 * Description:已完成订单适配器
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 17:16
 */
public class OrderDetialCoatchAdapter extends RecyclerView.Adapter<OrderDetialCoatchAdapter.ViewHolder> {
    List<OrderDetialBean.OrderDetailListBean> datas;
    public OrderDetialCoatchAdapter(List<OrderDetialBean.OrderDetailListBean> list) {
    this.datas=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_order_coatch,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetialBean.OrderDetailListBean orderDetailListBean = datas.get(position);
        holder.mTvItemName.setText(orderDetailListBean.getMainConfigDetailName());
        double totlePrice = orderDetailListBean.getTotlePrice();
        String duration=orderDetailListBean.getDuration()+""+orderDetailListBean.getDurationUnitName();
        String rate=orderDetailListBean.getValue()+"次/"+orderDetailListBean.getRate()
                +orderDetailListBean.getRateUnitName();
        String mainConfigDetailCode = orderDetailListBean.getMainConfigDetailCode();
        if (mainConfigDetailCode.equals("1")) {
            holder.mTvItemValue.setText(String.format("%s元， %s", totlePrice, rate));
        }else{
            holder.mTvItemValue.setText(String.format("%s元， %s， %s", totlePrice, duration, rate));
        }

 //       holder.mTvItemValue.setText(String.format("%s元/次", orderDetailListBean.getTotlePrice()));
//        holder.mTvSignRateValue.setText(MessageFormat.format("{0}次/{1}", orderDetailListBean.getRate()
//                , orderDetailListBean.getRateUnitName()));

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvItemName;
        TextView mTvItemValue;
        TextView mTvSignRateValue;
        TextView mTvSignRateText;
        LinearLayout llItemRoot;

        public ViewHolder(View view) {
            super(view);
            mTvItemName = itemView.findViewById(R.id.tv_item_name);
            mTvItemValue = itemView.findViewById(R.id.tv_item_value);
            llItemRoot = itemView.findViewById(R.id.ll_item_root);
            mTvSignRateValue=itemView.findViewById(R.id.tv_sign_rate_value);
            mTvSignRateText=itemView.findViewById(R.id.tv_sign_rate_text);
        }
    }
}
