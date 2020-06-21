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
        retholder.lasttime_up_desc.initWidth(screentwidth);
        retholder.lasttime_up_desc.setMaxLines(3);
        retholder.lasttime_up_desc.setHasAnimation(true);
        retholder.lasttime_up_desc.setCloseInNewLine(true);
        retholder.lasttime_up_desc.setOpenSuffixColor(parent.getContext().getResources().getColor(R.color.textColor_mainPageJF));
        retholder.lasttime_up_desc.setCloseSuffixColor(parent.getContext().getResources().getColor(R.color.textColor_mainPageJF));
        return retholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VersionInfo parbean = datas.get(position);
        holder.lasttime_up_desc.setOriginalText(parbean.getLatelyUpdDesc());
        holder.thistime_up_desc.setText(parbean.getThisTimeUpdDesc());
        holder.tv_pubversion_time.setText(parbean.getUpdateDateShow());
        holder.version_num.setText(parbean.getVersionNum());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<VersionInfo> datas){
        this.datas = datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView version_num;
        public TextView tv_pubversion_time;
        public TextView thistime_up_desc;
        public ExpandableTextView lasttime_up_desc;
        public ViewHolder(View view){
            super(view);
            version_num = view.findViewById(R.id.version_num);
            tv_pubversion_time = view.findViewById(R.id.tv_pubversion_time);
            thistime_up_desc = view.findViewById(R.id.thistime_up_desc);
            lasttime_up_desc = view.findViewById(R.id.lasttime_up_desc);

        }
    }
}
