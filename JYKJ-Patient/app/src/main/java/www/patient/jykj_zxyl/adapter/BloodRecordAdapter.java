package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import entity.patientInfo.BloodRecodeListData;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.DateUtils;

/**
 * 历史血压记录适配器
 */
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
//        BloodRecodeListData data = mData.get(position);
//        holder.tvRecordDate.setText(DateUtils.stampToDate(data.getRecordDate()));
//        holder.tvRecordNum.setText(data.getHighPressureNum()+"/"+data.getLowPressureNum()+"mmHg");
//        holder.tvRateNum.setText(data.getHeartRateNum()+"次/分钟");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView item_img,img_position;
        private TextView item_tv_date;
        private TextView item_tv_time;
        private TextView item_tv_data;

        public ViewHolder(View view){
            super(view);
            item_img = view.findViewById(R.id.item_img);
            item_tv_date = view.findViewById(R.id.item_tv_date);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_data = view.findViewById(R.id.item_tv_data);
            img_position = view.findViewById(R.id.img_position);

        }
    }

}
