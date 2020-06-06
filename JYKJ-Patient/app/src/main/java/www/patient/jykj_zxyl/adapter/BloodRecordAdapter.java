package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import entity.patientInfo.BloodRecodeListData;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.DateUtils;

public class BloodRecordAdapter extends RecyclerView.Adapter<BloodRecordAdapter.ViewHolder>{

    private List<BloodRecodeListData> mData;
    private Context mContext;

    public BloodRecordAdapter(List<BloodRecodeListData> mData,Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    //重新设置数据
    public void setDate(List<BloodRecodeListData> list){
        mData = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_blood_record,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodRecodeListData data = mData.get(position);
        holder.tvRecordDate.setText(DateUtils.stampToDate(data.getRecordDate()));
        holder.tvRecordNum.setText(data.getHighPressureNum()+"/"+data.getLowPressureNum()+"mmHg");
        holder.tvRateNum.setText(data.getHeartRateNum()+"次/分钟");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvRecordDate;
        private TextView tvRecordNum;
        private TextView tvRateNum;

        public ViewHolder(View view){
            super(view);
            tvRecordDate = view.findViewById(R.id.tv_recode_date);
            tvRecordNum = view.findViewById(R.id.tv_record_num);
            tvRateNum = view.findViewById(R.id.tv_rate_num);

        }
    }

}
