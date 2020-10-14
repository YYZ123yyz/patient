package www.patient.jykj_zxyl.myappointment.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import entity.HomeDataBean;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.AllDepartmentBean;

/**
 * Created by G on 2020/10/9 11:43
 */
public class AllDepHotAdapter extends BaseQuickAdapter<AllDepartmentBean.TitleHospitalDepartmentBean, BaseViewHolder> {
    public AllDepHotAdapter(int layoutResId, @Nullable List<AllDepartmentBean.TitleHospitalDepartmentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllDepartmentBean.TitleHospitalDepartmentBean item) {


        helper.setText(R.id.tv_dep, item.getDepartmentName());

        ImageView ivDep = helper.getView(R.id.iv_dep);
        Glide.with(JYKJApplication.getConText()).load(item.getViewDepartmentImg()).into(ivDep);
    }
}