package www.patient.jykj_zxyl.myappointment.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import www.patient.jykj_zxyl.FXActivity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientListBean;

/*
 *
 * */
public class Item_Reservation_List_Adapter extends RecyclerView.Adapter<Item_Reservation_List_Adapter.ViewHolder> {
    private List<ReservePatientListBean.ItemTimesBean> datas;
    private onClickListener onClickListener;
    int position;
    public Item_Reservation_List_Adapter(List<ReservePatientListBean.ItemTimesBean> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation_rv,
                parent, false);
        return new Item_Reservation_List_Adapter.ViewHolder(view);
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ViewHolder vh=null;
        ReservePatientListBean.ItemTimesBean itemTimesBean = datas.get(position);
        if(itemTimesBean!=null){
            holder.item_time.setText(itemTimesBean.getHoursMins());
            holder.item_number.setText("剩余 "+itemTimesBean.getOverCount());
            if(itemTimesBean.getOverCount()==0){
                holder.item_number.setTextColor(Color.parseColor("#ff666666"));
            }else{
                holder.item_number.setTextColor(Color.parseColor("#0DBB00"));
                if (itemTimesBean.getSelectState()) {
                    holder.item_lin.setBackgroundColor(Color.parseColor("#799DFE"));
                    holder.item_time.setTextColor(Color.parseColor("#ffffff"));
                    holder.item_number.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    holder.item_lin.setBackgroundColor(Color.parseColor("#F5F5F5"));
                    holder.item_time.setTextColor(R.color.transparent);
                    holder.item_number.setTextColor(Color.parseColor("#0DBB00"));
                }
                holder.item_lin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListener != null) {
                            onClickListener.onClick(v, position);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
    //重新设置数据
    public void setDate(List<ReservePatientListBean.ItemTimesBean> list) {
        datas = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item_time, item_number;
        private LinearLayout item_lin;

        public ViewHolder(View itemView) {
            super(itemView);
            item_time = itemView.findViewById(R.id.item_time);
            item_number = itemView.findViewById(R.id.item_number);
            item_lin = itemView.findViewById(R.id.item_lin);

        }
    }


    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void onClick(View view, int position);
    }
}
