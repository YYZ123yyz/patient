package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import entity.ProvideDrugInfo;
import www.patient.jykj_zxyl.R;

public class ChoiceMedicationAdapter extends RecyclerView.Adapter<ChoiceMedicationAdapter.ViewHolder>{
    private List<ProvideDrugInfo> mData;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;           //用户资料点击事件

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }


    public ChoiceMedicationAdapter(List<ProvideDrugInfo> mData, Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    //重新设置数据
    public void setDate(List<ProvideDrugInfo> list){
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_medication_choice,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.medicName.setText(mData.get(position).getDrugName());

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
        private TextView medicName;
        private ImageView    medicChoice;
        private LinearLayout mOnItemClick;

        public ViewHolder(View itemView) {
            super(itemView);
            medicName = itemView.findViewById(R.id.tv_medicName);
            medicChoice = itemView.findViewById(R.id.medicChoice);
            mOnItemClick = (LinearLayout)itemView.findViewById(R.id.layoutClick);
        }
    }
}
