package www.patient.jykj_zxyl.activity.pulsemonitoring.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import entity.patientInfo.ProvidePatientConditionBloodPressureRecord;
import www.patient.jykj_zxyl.R;

/**
 * 脉搏异常记录
 */
public class AbnormalPulseAdapter extends RecyclerView.Adapter<AbnormalPulseAdapter.ViewHolder> {

    public List<ProvidePatientConditionBloodPressureRecord> datas;


    private Context mContext;


    public AbnormalPulseAdapter(List<ProvidePatientConditionBloodPressureRecord> list, Context context) {
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvidePatientConditionBloodPressureRecord> list) {
        datas = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_abnormalpulse_detail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item_tv_date, item_tv_time, item_tv_data,item_tv_status;
        private ImageView item_img,img_position;

        public ViewHolder(View view) {
            super(view);
            item_img = view.findViewById(R.id.item_img);
            item_tv_date = view.findViewById(R.id.item_tv_date);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_data = view.findViewById(R.id.item_tv_data);
            item_tv_status = view.findViewById(R.id.item_tv_status);
            img_position = view.findViewById(R.id.img_position);
        }
    }
}
