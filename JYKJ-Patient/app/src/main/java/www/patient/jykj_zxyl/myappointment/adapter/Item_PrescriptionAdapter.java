package www.patient.jykj_zxyl.myappointment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.PrescriptionDetBean;

/**
 * Description:
 */
public class Item_PrescriptionAdapter extends RecyclerView.Adapter<Item_PrescriptionAdapter.ViewHolder> {


    private List<PrescriptionDetBean.InteractOrderPrescribeListBean> datas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public Item_PrescriptionAdapter(List<PrescriptionDetBean.InteractOrderPrescribeListBean> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    public List<PrescriptionDetBean.InteractOrderPrescribeListBean> getDatas() {
        return datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PrescriptionDetBean.InteractOrderPrescribeListBean interactOrderPrescribeListBean = datas.get(position);
        List<PrescriptionDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean> prescribeInfo = interactOrderPrescribeListBean.getPrescribeInfo();
        holder.tvName.setText(prescribeInfo.get(0).getDrugName());
        holder.tvSpecification.setText(prescribeInfo.get(0).getDrugAmountName());
        holder.tvFrequency.setText(prescribeInfo.get(0).getUseNumName());

    }

    //重新设置数据
    public void setDate(List<PrescriptionDetBean.InteractOrderPrescribeListBean> list) {
        datas = list;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
      private  TextView tvName;
        private    TextView tvSpecification;
        private   TextView tvFrequency;
        private TextView tvUseFrequencyName;
        private  TextView useNumName;


        public ViewHolder(View view) {
            super(view);
            tvName=view.findViewById(R.id.tv_name);
            tvSpecification=view.findViewById(R.id.tv_specification);
            tvFrequency=view.findViewById(R.id.tv_frequency);
            tvUseFrequencyName=view.findViewById(R.id.tv_useFrequencyName);
//            useNumName=view.findViewById(R.id.useNumName);
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