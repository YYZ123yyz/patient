package www.patient.jykj_zxyl.myappointment.adapter;

import android.annotation.SuppressLint;
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

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.myappointment.bean.CancelContractBean;

/**
 * Description:

 */
public class GraPhicAdapter extends RecyclerView.Adapter<GraPhicAdapter.ViewHolder> {

    private List<CancelContractBean> datas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public GraPhicAdapter(List<CancelContractBean> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }

    public List<CancelContractBean> getDatas() {
        return datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_graphic,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


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

        private TextView tv_name,tv_time;
        private ImageView graphic_image;
          private LinearLayout lin_graphic;
        public ViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_time = view.findViewById(R.id.tv_time);
            graphic_image = view.findViewById(R.id.graphic_image);
            lin_graphic = view.findViewById(R.id.lin_graphic);
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