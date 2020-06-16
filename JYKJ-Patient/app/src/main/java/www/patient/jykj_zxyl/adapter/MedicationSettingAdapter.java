package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import entity.ProvidePatientConditionTakingMedicine;
import www.patient.jykj_zxyl.R;

/**
 * 用药设置适配器
 */
public class MedicationSettingAdapter extends RecyclerView.Adapter<MedicationSettingAdapter.ViewHolder>{
    private List<ProvidePatientConditionTakingMedicine> mData;
    private Context mContext;

    private MedicationSettingAdapter.OnItemClickListener mOnItemClickListener;           //用户资料点击事件

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }


    public MedicationSettingAdapter(List<ProvidePatientConditionTakingMedicine> mData, Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    //重新设置数据
    public void setDate(List<ProvidePatientConditionTakingMedicine> list){
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_medication_setting,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dunName.setText(mData.get(position).getDrugName());
        holder.dunUnit.setText(mData.get(position).getUseNum()+mData.get(position).getUseUnit()+"/次");
        String[] time = mData.get(position).getTakingMedicineTimeShow().split("\\^");
        if (time == null)
        {
            holder.time1.setVisibility(View.GONE);
        }
        else
        {
            switch (time.length)
            {
                case 0:
                    holder.time1.setVisibility(View.GONE);

                    holder.time2.setVisibility(View.GONE);
                    holder.time3.setVisibility(View.GONE);
                    holder.time4.setVisibility(View.GONE);
                    holder.time5.setVisibility(View.GONE);
                    holder.time6.setVisibility(View.GONE);
                    holder.more_date.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.time1.setVisibility(View.VISIBLE);
                    holder.time1.setText(time[0]);
                    holder.time2.setVisibility(View.GONE);
                    holder.time3.setVisibility(View.GONE);
                    holder.time4.setVisibility(View.GONE);
                    holder.time5.setVisibility(View.GONE);
                    holder.time6.setVisibility(View.GONE);
                    holder.more_date.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.time1.setVisibility(View.VISIBLE);
                    holder.time2.setVisibility(View.VISIBLE);
                    holder.time3.setVisibility(View.GONE);
                    holder.time4.setVisibility(View.GONE);
                    holder.time5.setVisibility(View.GONE);
                    holder.time6.setVisibility(View.GONE);
                    holder.more_date.setVisibility(View.GONE);

                    holder.time1.setText(time[0]);
                    holder.time2.setText(time[1]);
                    break;
                case 3:
                    holder.time1.setVisibility(View.VISIBLE);
                    holder.time2.setVisibility(View.VISIBLE);
                    holder.time3.setVisibility(View.VISIBLE);

                    holder.time4.setVisibility(View.GONE);
                    holder.time5.setVisibility(View.GONE);
                    holder.time6.setVisibility(View.GONE);
                    holder.more_date.setVisibility(View.GONE);
                    holder.time1.setText(time[0]);
                    holder.time2.setText(time[1]);
                    holder.time3.setText(time[2]);

                    break;
                case 4:
                    holder.time1.setVisibility(View.VISIBLE);
                    holder.time2.setVisibility(View.VISIBLE);
                    holder.time3.setVisibility(View.VISIBLE);
                    holder.time4.setVisibility(View.VISIBLE);
                    holder.time5.setVisibility(View.GONE);
                    holder.time6.setVisibility(View.GONE);
                    holder.more_date.setVisibility(View.VISIBLE);
                    holder.time1.setText(time[0]);
                    holder.time2.setText(time[1]);
                    holder.time3.setText(time[2]);
                    holder.time4.setText(time[3]);
                    break;
                case 5:
                    holder.time1.setVisibility(View.VISIBLE);
                    holder.time2.setVisibility(View.VISIBLE);
                    holder.time3.setVisibility(View.VISIBLE);
                    holder.time4.setVisibility(View.VISIBLE);
                    holder.time5.setVisibility(View.VISIBLE);
                    holder.time6.setVisibility(View.GONE);
                    holder.more_date.setVisibility(View.VISIBLE);
                    holder.time1.setText(time[0]);
                    holder.time2.setText(time[1]);
                    holder.time3.setText(time[2]);
                    holder.time4.setText(time[3]);
                    holder.time5.setText(time[4]);
                    break;
                case 6:
                    holder.time1.setVisibility(View.VISIBLE);
                    holder.time2.setVisibility(View.VISIBLE);
                    holder.time3.setVisibility(View.VISIBLE);
                    holder.time4.setVisibility(View.VISIBLE);
                    holder.time5.setVisibility(View.VISIBLE);
                    holder.time6.setVisibility(View.VISIBLE);
                    holder.more_date.setVisibility(View.VISIBLE);
                    holder.time1.setText(time[0]);
                    holder.time2.setText(time[1]);
                    holder.time3.setText(time[2]);
                    holder.time4.setText(time[3]);
                    holder.time5.setText(time[4]);
                    holder.time6.setText(time[5]);
                    break;
                default:
                    holder.time1.setVisibility(View.VISIBLE);
                    holder.time2.setVisibility(View.VISIBLE);
                    holder.time3.setVisibility(View.VISIBLE);
                    holder.time4.setVisibility(View.VISIBLE);
                    holder.time5.setVisibility(View.VISIBLE);
                    holder.time6.setVisibility(View.VISIBLE);
                    holder.time1.setText(time[0]);
                    holder.time2.setText(time[1]);
                    holder.time3.setText(time[2]);
                    holder.time4.setText(time[3]);
                    holder.time5.setText(time[4]);
                    holder.time6.setText(time[5]);
                    break;
            }
        }


//        if (mData.get(position).get)
//        holder.dunName.setText(mData.get(position).getDrugName());
//        holder.dunName.setText(mData.get(position).getDrugName());
//        holder.dunName.setText(mData.get(position).getDrugName());


        if (mOnItemClickListener != null)
        {
            holder.mOnItemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.mOnItemClick.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
//        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dunName;
        private TextView dunUnit;
        private TextView time1;
        private TextView time2;
        private TextView time3;

        private TextView time4;
        private TextView time5;
        private TextView time6;
        private LinearLayout mOnItemClick;

        private LinearLayout more_date;

        public ViewHolder(View itemView) {
            super(itemView);
            dunName = itemView.findViewById(R.id.tv_drug_name);
            dunUnit = itemView.findViewById(R.id.tv_user_type);
            time1 = itemView.findViewById(R.id.tv_time1);
            time2 = itemView.findViewById(R.id.tv_time2);
            time3 = itemView.findViewById(R.id.tv_time3);
            time4 = itemView.findViewById(R.id.tv_time4);
            time5 = itemView.findViewById(R.id.tv_time5);
            time6 = itemView.findViewById(R.id.tv_time6);
            mOnItemClick = (LinearLayout)itemView.findViewById(R.id.layoutClick);

            more_date = (LinearLayout)itemView.findViewById(R.id.more_date);
        }
    }
}
