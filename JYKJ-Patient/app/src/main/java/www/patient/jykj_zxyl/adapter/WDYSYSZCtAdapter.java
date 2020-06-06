package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import entity.basicDate.ProvideBasicsDomain;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;

/**
 * 我的医生==>医生职称Adapter
 */
public class WDYSYSZCtAdapter extends RecyclerView.Adapter<WDYSYSZCtAdapter.ViewHolder> {

    private List<ProvideBasicsDomain> mDate;
    private Context mContext;
    private WDYSActivity mActivity;
    public WDYSYSZCtAdapter(List<ProvideBasicsDomain> date, Context context, WDYSActivity activity){
        mDate = date;
        mContext = context;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jgbj_yszc, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mDate.get(position).isChoice())
        {
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.groabColor));
            holder.tv.setBackgroundResource(R.drawable.bg_jgbj__yszc_item);
        }
        else
        {
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.tjzj_jgdefault));
            holder.tv.setBackgroundResource(R.drawable.bg_jgbj__yszc_item_default);
        }
        holder.tv.setText(mDate.get(position).getAttrName());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.YSZCClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }


    public void setDate(List<ProvideBasicsDomain> list) {
        mDate = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public ViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.tv);
        }
    }
}
