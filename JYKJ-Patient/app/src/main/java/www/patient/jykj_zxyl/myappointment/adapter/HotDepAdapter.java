package www.patient.jykj_zxyl.myappointment.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import entity.HomeDataBean;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.AllDepartmentBean;

/**
 * Created by G on 2020/10/9 10:58
 */
public class HotDepAdapter extends BaseQuickAdapter<HomeDataBean.HospitalDepartmentListBean, BaseViewHolder> {
    public HotDepAdapter(int layoutResId, @Nullable List<HomeDataBean.HospitalDepartmentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataBean.HospitalDepartmentListBean item) {


        helper.setText(R.id.tv_dep, item.getDepartmentName());

        ImageView ivDep = helper.getView(R.id.iv_dep);
        Glide.with(JYKJApplication.getConText()).load(item.getViewDepartmentImg()).into(ivDep);
    }
}