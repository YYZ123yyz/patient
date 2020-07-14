package www.patient.jykj_zxyl.adapter.myself;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import entity.mySelf.VersionInfo;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.custom.ExpandableTextView;
import www.patient.jykj_zxyl.util.DateUtils;

import java.util.List;

public class VersionAdapter extends RecyclerView.Adapter<VersionAdapter.ViewHolder>{
    List<VersionInfo> datas;
    int screentwidth = 0;
    public VersionAdapter(List<VersionInfo> datas,int screentwidth){
        this.datas = datas;
        this.screentwidth = screentwidth;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.version_items,parent,false);
        ViewHolder retholder = new ViewHolder(view);
        return retholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VersionInfo parbean = datas.get(position);
//        holder.lasttime_up_desc.setOriginalText(parbean.getLatelyUpdDesc());
//        holder.thistime_up_desc.setText(parbean.getThisTimeUpdDesc());
//        holder.tv_pubversion_time.setText(parbean.getUpdateDateShow());
//        holder.version_num.setText(parbean.getVersionNum());
        holder.dateShow.setText(datas.get(position).getUpdateDateShow());
        holder.num.setText(datas.get(position).getVersionNum());
        if (datas.get(position).getThisTimeUpdDesc().indexOf("\\n") >= 0) {
            String str2 = datas.get(position).getThisTimeUpdDesc().replace("\\n", " \n ");
            holder.ver_this.setText(str2);
        }

        if (datas.get(position).getLatelyUpdDesc().indexOf("\\n") >= 0) {
            String str = datas.get(position).getLatelyUpdDesc().replace("\\n", " \n ");
            holder.ver_recent.setText(str);
        }
        holder.ver_recent.post(new Runnable() {
            @Override
            public void run() {
                int ellipsisCount = holder.ver_recent.getLayout().getEllipsisCount(holder.ver_recent.getLineCount() - 1);

                holder.ver_recent.getLayout().getEllipsisCount(holder.ver_recent.getLineCount() - 1);
                if (ellipsisCount > 0) {
                    holder.swi.setVisibility(View.VISIBLE);
                } else {
                    holder.swi.setVisibility(View.GONE);
                }

            }
        });
        holder.swi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ver_recent.setEllipsize(null);
                //这个方法是必须设置的，否则无法展开
                holder.ver_recent.setSingleLine(false);
                holder.swi.setVisibility(View.INVISIBLE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<VersionInfo> datas){
        this.datas = datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView ver_this,ver_recent,swi,dateShow,num;
        public ViewHolder(View view){
            super(view);
            ver_this=itemView.findViewById(R.id.ver_this);
            ver_recent=itemView.findViewById(R.id.ver_recent);
            swi=itemView.findViewById(R.id.swi);
            dateShow=itemView.findViewById(R.id.dateShow);
            num=itemView.findViewById(R.id.num);


        }
    }
}
