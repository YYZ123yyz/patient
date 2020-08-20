package www.patient.jykj_zxyl.activity.myself.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-06-09 18:15
 */
public class SignOrderDetialAdapter extends SecondaryListAdapter<SignOrderDetialAdapter.GroupItemViewHolder,
        SignOrderDetialAdapter.SubItemViewHolder> {
    /**
     * 上下文
     */
    private Context context;
    private List<DataTree<String, OrderDetialBean.OrderDetailListBean>> dts;

    public SignOrderDetialAdapter(Context context) {
        this.context = context;
        dts = new ArrayList<>();
    }


    /**
     * 设置数据
     *
     * @param datas 数据
     */
    public void setData(List<DataTree<String,
            OrderDetialBean.OrderDetailListBean>> datas) {
        dts = datas;
        notifyNewData(dts);
    }

    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_order_monitor,
                parent, false);
        return new GroupItemViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_order_coatch,
                parent, false);
        return new SubItemViewHolder(v);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onGroupItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex) {
       String groupItem = dts.get(groupItemIndex).getGroupItem();
        ((GroupItemViewHolder) holder).mTvItemTitle.setText(groupItem);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onSubItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex, int subItemIndex) {
        OrderDetialBean.OrderDetailListBean orderDetailListBean = dts.get(groupItemIndex).getSubItems().get(subItemIndex);
        ((SubItemViewHolder) holder).mTvItemName.setText(orderDetailListBean.getMainConfigDetailName());
        ((SubItemViewHolder) holder).mTvItemValue.setText(orderDetailListBean.getTotlePrice()+"");
    }

    @Override
    public void onGroupItemClick(Boolean isExpand, GroupItemViewHolder holder, int groupItemIndex) {
    }

    @Override
    public void onSubItemClick(SubItemViewHolder holder, int groupItemIndex, int subItemIndex) {

    }


    /**
     * 二级评论列表
     */
    static class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvItemName;
        TextView mTvItemValue;
        LinearLayout llItemRoot;
        SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemName=itemView.findViewById(R.id.tv_item_name);
            mTvItemValue=itemView.findViewById(R.id.tv_item_value);
            llItemRoot=itemView.findViewById(R.id.ll_item_root);
        }
    }

    /**
     * 一级评论列表
     */
    static class GroupItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvItemTitle;
        GroupItemViewHolder(View itemView) {
            super(itemView);
            //mTvItemTitle=itemView.findViewById(R.id.tv_item_title);

        }
    }




    /**
     * 跳转Activity
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     */
    @SuppressWarnings("rawtypes")
    private void startActivity(Class paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(context, paramClass);
        context.startActivity(localIntent);
    }

}
