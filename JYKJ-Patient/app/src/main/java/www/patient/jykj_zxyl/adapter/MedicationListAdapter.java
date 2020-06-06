package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import entity.mySelf.ProvidePatientConditionTakingRecord;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.DateUtils;

public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.ViewHolder>{
    private List<ProvidePatientConditionTakingRecord> mData;
    private Context mContext;

    public MedicationListAdapter(List<ProvidePatientConditionTakingRecord> mData,Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    //重新设置数据
    public void setDate(List<ProvidePatientConditionTakingRecord> list){
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_medication_list,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProvidePatientConditionTakingRecord data = mData.get(position);
        holder.tvDrugName.setText(data.getDrugName());
        holder.tvTime.setText(DateUtils.stampToDate(data.getRemindDate()));
        holder.tvDetails.setText(DateUtils.getStringTime1(data.getRemindTime())+" "+data.getUseNum() +data.getUseUnit()+ "/次");
        if(data.getFlagTakingMedicineUserType()==1){
            holder.tvUserType.setText("由患者本人添加");
        }else{
            holder.tvUserType.setText("由患者亲属添加");
        }
        if(data.getFlagTakingMedicine()==0){
            holder.tvFlagType.setText("未操作");
        }else if(data.getFlagTakingMedicine()==1){
            holder.tvFlagType.setText("未服用");
        }else if(data.getFlagTakingMedicine()==2){
            holder.tvFlagType.setText("操作过期");
        }else{
            holder.tvFlagType.setText("已服用");
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime,tvDrugName,tvUserType,tvDetails,tvFlagType;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvDrugName = itemView.findViewById(R.id.tv_drug_name);
            tvDetails = itemView.findViewById(R.id.tv_detail);
            tvUserType = itemView.findViewById(R.id.tv_user_type);
            tvFlagType = itemView.findViewById(R.id.tv_flag_type);
        }
    }
}
