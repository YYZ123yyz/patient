package www.patient.jykj_zxyl.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import entity.HealthListBean;
import www.patient.jykj_zxyl.R;

/**
 * Created by G on 2020/9/17 14:26
 */
public class HealthListAdapter extends BaseQuickAdapter<HealthListBean, BaseViewHolder> {


    public HealthListAdapter(int layoutResId, @Nullable List<HealthListBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, HealthListBean item) {
        helper.setImageResource(R.id.item_health_image,item.getIvSrc())
                .setText(R.id.item_health_name,item.getType())
                .setText(R.id.tv_unit,item.getUnit())
                .setText(R.id.item_health_time,item.getTime())
                .setText(R.id.tv_data,item.getData());

    }
}

