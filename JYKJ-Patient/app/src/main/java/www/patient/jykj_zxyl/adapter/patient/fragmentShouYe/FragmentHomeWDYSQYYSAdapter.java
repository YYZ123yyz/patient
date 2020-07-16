package www.patient.jykj_zxyl.adapter.patient.fragmentShouYe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import entity.ProvideViewMyDoctorSigning;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.Util;

/**
 * 首页==》我的医生（签约医生）
 */
public class FragmentHomeWDYSQYYSAdapter extends RecyclerView.Adapter<FragmentHomeWDYSQYYSAdapter.ViewHolder> {
    public List<ProvideViewMyDoctorSigning> datas = new ArrayList<>();
    private OnItemClickXYListener mOnItemClickXYListener;           //
    private OnItemClickZXListener mOnItemClickZXListener;           //
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public FragmentHomeWDYSQYYSAdapter(List<ProvideViewMyDoctorSigning> list, Context context) {
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideViewMyDoctorSigning> list) {
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragmentshouye_qyys, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }
    //将数据与界面进行绑定的操作

    /**
     * 展示数据
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        try {
            int avatarResId = Integer.parseInt(datas.get(position).getUserLogoUrl());
            Glide.with(mContext).load(avatarResId).into(viewHolder.mUserHeard);
        } catch (Exception e) {
            //use default avatar
            Glide.with(mContext).load(datas.get(position).getUserLogoUrl())
                    .apply(RequestOptions.placeholderOf(R.mipmap.tx_nh)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(viewHolder.mUserHeard);
        }

        if (datas.get(position).getUserName() == null || "".equals(datas.get(position).getUserName()))
            viewHolder.mUserName.setText("未设置");
        else
            viewHolder.mUserName.setText(datas.get(position).getUserName());
        if (datas.get(position).getDoctorTitleName() == null || "".equals(datas.get(position).getDoctorTitleName()))
            viewHolder.mUserTitle.setText("未设置");
        else
            viewHolder.mUserTitle.setText(datas.get(position).getDoctorTitleName() + "|" + datas.get(position).getDepartmentSecondName());
        if (datas.get(position).getHospitalInfoName() == null || "".equals(datas.get(position).getHospitalInfoName()))
            viewHolder.mUserHospital.setText("未设置");
        else
            viewHolder.mUserHospital.setText(datas.get(position).getHospitalInfoName());
        if (datas.get(position).getGoodAtRealm() == null || "".equals(datas.get(position).getGoodAtRealm()))
            viewHolder.mUserGoodAtRealm.setText("未设置");
        else
            viewHolder.mUserGoodAtRealm.setText(datas.get(position).getGoodAtRealm());
        if (datas.get(position).getLimitSigningExpireDate() == null || "".equals(datas.get(position).getLimitSigningExpireDate()))
            viewHolder.tv_dqsj.setText("未设置");
        else
            viewHolder.tv_dqsj.setText("签约到期时间：" + Util.dateToStr(datas.get(position).getLimitSigningExpireDate()));

        if (mOnItemClickXYListener != null) {
            viewHolder.tv_xy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickXYListener.onClick(position);
                }
            });
        }

        if (mOnItemClickZXListener != null) {
            viewHolder.tv_zx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickZXListener.onClick(position);
                }
            });
        }


        if (mOnItemClickListener != null) {
            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }


    }

    //获取数据的数量
    @Override
    public int getItemCount() {

        return datas.size();
//            return 10;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public ImageView mUserHeard;                         //用户头像
        public TextView mUserName;                          //用户名
        public TextView mUserTitle;                          //
        public TextView mUserHospital;                          //
        public TextView mUserGoodAtRealm;                          //
        public TextView tv_dqsj;                          //
        public TextView tv_xy;                          //
        public TextView tv_zx;                          //


        public ViewHolder(View view) {
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mUserHeard = (ImageView) view.findViewById(R.id.userHead);
            mUserName = (TextView) view.findViewById(R.id.userName);
            mUserTitle = (TextView) view.findViewById(R.id.userTitle);
            mUserHospital = (TextView) view.findViewById(R.id.userHospital);
            mUserGoodAtRealm = (TextView) view.findViewById(R.id.userGoodAtRealm);
            tv_xy = (TextView) view.findViewById(R.id.tv_xy);
            tv_dqsj = (TextView) view.findViewById(R.id.tv_dqsj);
            tv_zx = (TextView) view.findViewById(R.id.tv_zx);

        }
    }


    public interface OnItemClickXYListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public interface OnItemClickZXListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickXYListener(OnItemClickXYListener onItemClickXYListener) {
        this.mOnItemClickXYListener = onItemClickXYListener;
    }

    public void setOnItemClickZXListener(OnItemClickZXListener onItemClickZXListener) {
        this.mOnItemClickZXListener = onItemClickZXListener;
    }



    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}