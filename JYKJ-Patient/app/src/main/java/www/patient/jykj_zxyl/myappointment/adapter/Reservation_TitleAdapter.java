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

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientDoctorInfoBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientListBean;
import www.patient.jykj_zxyl.util.DateUtils;

/**
 * title
 */
public class Reservation_TitleAdapter extends RecyclerView.Adapter<Reservation_TitleAdapter.ViewHolder> {
    private List<ReservePatientDoctorInfoBean> datas;
    private onClickListener onClickListener;

    public Reservation_TitleAdapter(List<ReservePatientDoctorInfoBean> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation_title,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ReservePatientDoctorInfoBean reservePatientDoctorInfoBean = datas.get(position);
        String stringTimeMinute = DateUtils.getStringTimeMinute(reservePatientDoctorInfoBean.getTimes());
        holder.item_times.setText(stringTimeMinute);
        holder.item_viewReserveCount.setText(reservePatientDoctorInfoBean.getViewReserveCount() + "");
        int week = reservePatientDoctorInfoBean.getWeek();
        switch (week) {
            case 1:
                holder.item_week.setText("七");
                break;
            case 2:
                holder.item_week.setText("一");
                break;
            case 3:
                holder.item_week.setText("二");
                break;
            case 4:
                holder.item_week.setText("三");
                break;
            case 5:
                holder.item_week.setText("四");
                break;
            case 6:
                holder.item_week.setText("五");
                break;
            case 7:
                holder.item_week.setText("六");
                break;
        }

        if (reservePatientDoctorInfoBean.isChoosed()) {
            holder.calendar.setBackgroundColor(Color.parseColor("#799DFE"));
            holder.item_times.setTextColor(Color.parseColor("#ffffff"));
            holder.item_viewReserveCount.setTextColor(Color.parseColor("#ffffff"));
            holder.item_week.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.calendar.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.item_times.setTextColor(Color.parseColor("#999999"));
            holder.item_viewReserveCount.setTextColor(Color.parseColor("#999999"));
            holder.item_week.setTextColor(Color.parseColor("#999999"));
        }
        holder.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v, position);
                }
            }
        });
    }

    public void setDefSelect(int position) {
        for (int i = 0; i < datas.size(); i++) {
            if (i == position) {
                datas.get(i).setChoosed(true);
            } else {
                datas.get(i).setChoosed(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item_times, item_viewReserveCount, item_week;
        private LinearLayout calendar;

        public ViewHolder(View itemView) {
            super(itemView);
            item_times = itemView.findViewById(R.id.item_times);
            item_viewReserveCount = itemView.findViewById(R.id.item_viewReserveCount);
            item_week = itemView.findViewById(R.id.item_week);
            calendar = itemView.findViewById(R.id.calendar);
        }
    }


    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void onClick(View view, int position);
    }
}
