package www.patient.jykj_zxyl.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import entity.liveroom.SubjectLiveInfo;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.StrUtils;

import java.util.List;

public class SubtitleLiveAdapter extends RecyclerView.Adapter<SubtitleLiveAdapter.ViewHolder>{
    List<SubjectLiveInfo> datas;
    OnHotliveItemClickListener myListener;
    public SubtitleLiveAdapter(List<SubjectLiveInfo> datas){
        this.datas = datas;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_live_items,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SubjectLiveInfo parinfo = datas.get(i);
        viewHolder.subject_live_catalog.setText("类目:"+ StrUtils.defaultStr(parinfo.getBroadcastTypeName()));
        viewHolder.subject_live_desc.setText(StrUtils.defaultStr(parinfo.getKeywordsName()));
        viewHolder.subject_live_price.setText(StrUtils.defaultStr(parinfo.getExtendBroadcastPriceShow()));
        viewHolder.subject_live_title.setText(StrUtils.defaultStr(parinfo.getBroadcastTitle()));
        viewHolder.subject_watch_num.setText(StrUtils.defaultStr(parinfo.getExtendBroadcastViewsNum()));
        viewHolder.play_subject_live_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myListener.onClick(i,v);
            }
        });
        viewHolder.play_subject_live_btn.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                myListener.onLongClick(i,v);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView play_subject_live_btn;
        public TextView subject_live_title;
        public TextView subject_live_desc;
        public TextView subject_live_catalog;
        public TextView subject_watch_num;
        public TextView subject_live_price;
        public ViewHolder(View view){
            super(view);
            play_subject_live_btn = view.findViewById(R.id.play_subject_live_btn);
            subject_live_title = view.findViewById(R.id.subject_live_title);
            subject_live_desc = view.findViewById(R.id.subject_live_desc);
            subject_live_catalog = view.findViewById(R.id.subject_live_catalog);
            subject_watch_num = view.findViewById(R.id.subject_watch_num);
            subject_live_price = view.findViewById(R.id.subject_live_price);
        }

    }

    public OnHotliveItemClickListener getMyListener() {
        return myListener;
    }

    public void setMyListener(OnHotliveItemClickListener myListener) {
        this.myListener = myListener;
    }

    public interface OnHotliveItemClickListener{
        void onClick(int position, View view);
        void onLongClick(int position, View view);
    }

    public void setData(List<SubjectLiveInfo> datas){
        this.datas = datas;
    }
}
