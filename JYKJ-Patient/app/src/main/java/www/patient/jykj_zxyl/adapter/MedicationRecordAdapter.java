package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import entity.ProvidePatientConditionTakingRecordGroup;
import entity.shouye.ProvidePatientConditionTakingRecord;
import www.patient.jykj_zxyl.util.Util;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.MedicationRecordActivity;
import www.patient.jykj_zxyl.util.Util;

/**
 * 服药打卡适配器
 */
public class MedicationRecordAdapter extends RecyclerView.Adapter<MedicationRecordAdapter.ViewHolder>{
    private List<ProvidePatientConditionTakingRecordGroup> mData;
//    private List<ProvidePatientConditionTakingRecord> mProvidePatientConditionTakingRecord;
    private Context mContext;

    private MedicationRecordAdapter.OnItemClickListener mOnItemClickListener;           //用户资料点击事件
    private MedicationRecordActivity medicationRecordActivity;

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }


    public MedicationRecordAdapter(List<ProvidePatientConditionTakingRecordGroup> mData, Context mContext, MedicationRecordActivity activity){
        this.mData = mData;
        this.mContext = mContext;
        this.medicationRecordActivity = activity;
    }

    //重新设置数据
    public void setDate(List<ProvidePatientConditionTakingRecordGroup> list){
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_medication_record,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChildAdapter adapter = new ChildAdapter(mData.get(position).getPatientConditionTakingRecordList());
        holder.rv.setHasFixedSize(true);
        holder.rv.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv.setAdapter(adapter);
        adapter.setChildDate(mData.get(position).getPatientConditionTakingRecordList());
        if (mData.get(position).getGroupDate() == null || "".equals(mData.get(position).getGroupDate()))
            holder.tv_date.setText("未设置");
        else
            holder.tv_date.setText(Util.dateToStrDate(mData.get(position).getGroupDate()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
//        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date;
        private RecyclerView rv;
        private LinearLayout mOnItemClick;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            rv = (RecyclerView)itemView.findViewById(R.id.rv);
        }
    }


    private class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<ProvidePatientConditionTakingRecord> mProvidePatientConditionTakingRecords;

        public ChildAdapter(List<ProvidePatientConditionTakingRecord> rovidePatientConditionTakingRecord) {
            mProvidePatientConditionTakingRecords = rovidePatientConditionTakingRecord;
        }

        public void setChildDate(List<ProvidePatientConditionTakingRecord> providePatientConditionTakingRecord) {
            mProvidePatientConditionTakingRecords = providePatientConditionTakingRecord;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_medic_child, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            if (mProvidePatientConditionTakingRecords.get(position).getTakingMedicineTime() == null)
                viewHolder.tv_date.setText("未设置");
            else
                viewHolder.tv_date.setText(Util.dateToStrTime(mProvidePatientConditionTakingRecords.get(position).getTakingMedicineTime()));
            switch (mProvidePatientConditionTakingRecords.get(position).getRecordUserType())
            {
                case 1:
                    viewHolder.ybrtj.setText("由本人添加");
                    break;
                case 2:
                    viewHolder.ybrtj.setText("亲属添加");
                    break;
                case 3:
                    viewHolder.ybrtj.setText("医生添加");
                    break;
            }

            viewHolder.tv_durg_name.setText(mProvidePatientConditionTakingRecords.get(position).getDrugName());
            viewHolder.tv_unit.setText(mProvidePatientConditionTakingRecords.get(position).getUseUnit());
            switch (mProvidePatientConditionTakingRecords.get(position).getFlagTakingMedicine())
            {
                case 0:
                    viewHolder.tv_use_state.setVisibility(View.GONE);
                    viewHolder.tv_yfy.setVisibility(View.VISIBLE);
                    viewHolder.tv_use_wfy.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    viewHolder.tv_use_state.setVisibility(View.VISIBLE);
                    viewHolder.tv_yfy.setVisibility(View.GONE);
                    viewHolder.tv_use_wfy.setVisibility(View.GONE);
                    viewHolder.tv_use_state.setText("未按时服用");
                    break;
                case 2:
                    viewHolder.tv_use_state.setVisibility(View.VISIBLE);
                    viewHolder.tv_yfy.setVisibility(View.GONE);
                    viewHolder.tv_use_wfy.setVisibility(View.GONE);
                    viewHolder.tv_use_state.setText("已过期");
                    break;
                case 3:
                    viewHolder.tv_use_state.setVisibility(View.VISIBLE);
                    viewHolder.tv_yfy.setVisibility(View.GONE);
                    viewHolder.tv_use_wfy.setVisibility(View.GONE);
                    viewHolder.tv_use_state.setText("已按时服用");
                    break;
            }
//            final TopCategoryBean.MaleBean maleBean = mCategoryBeans.get(position);
//            viewHolder.mTvBookCount.setText(maleBean.getBookCount() + "本");
//            viewHolder.mTvCategoryName.setText(maleBean.getName());
            viewHolder.tv_yfy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击已服用
                    medicationRecordActivity.yfy(mProvidePatientConditionTakingRecords.get(position));
                }
            });
            viewHolder.tv_use_wfy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击已服用
                    medicationRecordActivity.wfy(mProvidePatientConditionTakingRecords.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProvidePatientConditionTakingRecords.size();
//            return 5;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView tv_date;
            TextView tv_durg_name;
            TextView tv_unit;
            TextView tv_use_state;
            TextView tv_yfy;
            TextView tv_use_wfy;
            TextView ybrtj;

            public ItemViewHolder(View itemView) {
                super(itemView);
                tv_date = itemView.findViewById(R.id.tv_date11);
                tv_durg_name = itemView.findViewById(R.id.tv_durg_name);
                tv_unit = itemView.findViewById(R.id.tv_unit);
                tv_use_state = itemView.findViewById(R.id.tv_use_state);
                tv_yfy = itemView.findViewById(R.id.tv_yfy);
                tv_use_wfy = itemView.findViewById(R.id.tv_use_wfy);
                ybrtj = itemView.findViewById(R.id.ybrtj);
//                mTvCategoryName = itemView.findViewById(R.id.tvCategoryName);
//                mTvBookCount = itemView.findViewById(R.id.tvBookCount);
//                llItem = itemView.findViewById(R.id.llItem);
            }
        }
    }

    private class NormalItemViewHolder extends RecyclerView.ViewHolder {


        public NormalItemViewHolder(View itemView) {
            super(itemView);

//            mRvCategory = itemView.findViewById(R.id.rvCategory);
        }
    }

}



