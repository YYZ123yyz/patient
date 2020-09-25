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

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_specification)
    TextView tvSpecification;
    @BindView(R.id.tv_frequency)
    TextView tvFrequency;
    @BindView(R.id.tv_useFrequencyName)
    TextView tvUseFrequencyName;
    @BindView(R.id.useNumName)
    TextView useNumName;
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
        PrescriptionDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean prescribeInfoBean = interactOrderPrescribeListBean.getPrescribeInfo().get(position);
        tvName.setText(prescribeInfoBean.getDrugName());
        tvSpecification.setText(prescribeInfoBean.getDrugAmountName());
        tvFrequency.setText(prescribeInfoBean.getUseNumName());

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

        private TextView mTvCancelContractText;
        private ImageView mIvItemChoosed;
        private RelativeLayout li_clickLayout;

        public ViewHolder(View view) {
            super(view);

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