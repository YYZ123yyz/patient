package www.patient.jykj_zxyl.myappointment.adapter;

import android.annotation.SuppressLint;
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
import www.patient.jykj_zxyl.myappointment.bean.CancelContractBean;

/**
 * Description:

 */
public class Home_VideoAdapter extends RecyclerView.Adapter<Home_VideoAdapter.ViewHolder> {

    private List<CancelContractBean> datas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public Home_VideoAdapter(List<CancelContractBean> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }

    public List<CancelContractBean> getDatas() {
        return datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_video,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
    //    PreLiveInfo parinfo = datas.get(i);
//        holder.pre_live_catalog.setText("类目:"+ StrUtils.defaultStr(parinfo.getClassName()));
//        holder.pre_live_desc.setText(StrUtils.defaultStr(parinfo.getAttrName()));
//        holder.pre_live_price.setText(StrUtils.defaultStr(parinfo.getExtendBroadcastPriceShow()));
//        holder.pre_live_title.setText(StrUtils.defaultStr(parinfo.getBroadcastTitle()));
//        holder.pre_watch_num.setText("想看人数："+StrUtils.defaultStr(parinfo.getExtendBroadcastFollowNum()));
//        holder.pre_live_btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                myListener.onClick(i,v);
//            }
//        });
//        holder.pre_live_btn.setOnLongClickListener(new View.OnLongClickListener(){
//            @Override
//            public boolean onLongClick(View v) {
//                myListener.onLongClick(i,v);
//                return false;
//            }
//        });
//        if(StrUtils.defaultStr(parinfo.getBroadcastCoverImgUrl()).length()>0){
//            Glide.with(holder.pre_live_btn.getContext()).load(parinfo.getBroadcastCoverImgUrl()).into(holder.pre_live_btn);
//        }
    }
    //重新设置数据
    public void setDate(List<CancelContractBean> list) {
        datas = list;
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView pre_live_btn;
        public TextView pre_live_title;
        public TextView pre_live_desc;
        public TextView pre_live_catalog;
        public TextView pre_watch_num;
        public TextView pre_live_price;
        public ViewHolder(View view) {
            super(view);
            pre_live_btn = view.findViewById(R.id.pre_live_btn);
            pre_live_title = view.findViewById(R.id.pre_live_title);
            pre_live_desc = view.findViewById(R.id.pre_live_desc);
            pre_live_catalog = view.findViewById(R.id.pre_live_catalog);
            pre_watch_num = view.findViewById(R.id.pre_watch_num);
            pre_live_price = view.findViewById(R.id.pre_live_price);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}