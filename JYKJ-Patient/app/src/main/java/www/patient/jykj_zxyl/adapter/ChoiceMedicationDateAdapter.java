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

import www.patient.jykj_zxyl.R;


/**
 * 用药时间
 */
public class ChoiceMedicationDateAdapter extends RecyclerView.Adapter<ChoiceMedicationDateAdapter.ViewHolder>{
    private List<String> mData;
    private Context mContext;

    private ChoiceMedicationDateAdapter.OnItemClickListener mOnItemClickListener;           //用户资料点击事件

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }


    public ChoiceMedicationDateAdapter(List<String> mData, Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    //重新设置数据
    public void setDate(List<String> list){
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_medication_choice_date,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_time.setText(mData.get(position));

        if (mOnItemClickListener != null)
        {
            holder.medicChoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.medicChoice.setOnLongClickListener(new View.OnLongClickListener() {

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
        private TextView tv_time;
        private ImageView    medicChoice;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            medicChoice = itemView.findViewById(R.id.iv_arrow4);
//            mOnItemClick = (LinearLayout)itemView.findViewById(R.id.layoutClick);
        }
    }
}
