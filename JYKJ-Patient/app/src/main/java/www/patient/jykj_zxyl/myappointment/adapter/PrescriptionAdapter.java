package www.patient.jykj_zxyl.myappointment.adapter;

import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.base_bean.PrescriptionDetBean;

/**
 * Created by G on 2020/9/25 18:25
 */
public class PrescriptionAdapter extends BaseQuickAdapter<PrescriptionDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean, BaseViewHolder> {
    public PrescriptionAdapter(int layoutResId, @Nullable List<PrescriptionDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PrescriptionDetBean.InteractOrderPrescribeListBean.PrescribeInfoBean item) {

        helper.setText(R.id.tv_name, item.getDrugName())
                .setText(R.id.tv_specification,item.getDrugAmountName())
                .setText(R.id.tv_frequency,item.getUseNumName())
                .setText(R.id.tv_useFrequencyName,item.getUseFrequencyCode());

        View emptyView = helper.getView(R.id.empty_div);
        switch (item.getType()) {
            case 0:
                emptyView.setVisibility(View.VISIBLE);
                break;
            case 1:
                emptyView.setVisibility(View.GONE);
                break;
            case 2:
                emptyView.setVisibility(View.VISIBLE);
                break;
        }
    }
}