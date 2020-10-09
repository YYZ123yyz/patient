package www.patient.jykj_zxyl.myappointment.adapter;

import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.AllDepartmentBean;
import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;

/**
 * Created by G on 2020/10/8 20:00
 */
public class AllDepartmentAdapter extends BaseQuickAdapter<AllDepartmentBean.HospitalDepartmentListBean, BaseViewHolder> {
    public AllDepartmentAdapter(int layoutResId, @Nullable List<AllDepartmentBean.HospitalDepartmentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllDepartmentBean.HospitalDepartmentListBean item) {

        int position = helper.getPosition();
        helper.setGone(R.id.tv_index, position == 0 || !getData().get(position).getIndex().equals(getData().get(position - 1).getIndex()));
        helper.setText(R.id.tv_index, item.getIndex())
                .setText(R.id.tv_name, item.getDepartmentName());
        helper.addOnClickListener(R.id.tv_name);
    }
}
