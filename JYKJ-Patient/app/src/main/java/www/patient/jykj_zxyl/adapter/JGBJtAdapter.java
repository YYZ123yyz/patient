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

/**
 * 推荐专家机构背景Adapter
 */
public class JGBJtAdapter extends RecyclerView.Adapter<JGBJtAdapter.ViewHolder> {

    private List<ProvideBasicsDomain>   mDate;
    private Context                     mContext;
    private TJZJActivity                mActivity;
    public JGBJtAdapter(List<ProvideBasicsDomain> date, Context context,TJZJActivity activity){
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
                mActivity.setJGBJClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    /**
     * 设置数据
     * @param list
     */
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
