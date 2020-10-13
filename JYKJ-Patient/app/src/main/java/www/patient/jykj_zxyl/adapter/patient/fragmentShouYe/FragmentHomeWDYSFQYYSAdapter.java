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

import entity.ProvidePatientBindingMyDoctorInfo;
import www.patient.jykj_zxyl.R;

/**
 * 首页==》我的医生（非签约医生）
 */
public class FragmentHomeWDYSFQYYSAdapter extends RecyclerView.Adapter<FragmentHomeWDYSFQYYSAdapter.ViewHolder> {
    public List<ProvidePatientBindingMyDoctorInfo> datas = new ArrayList<>();
    private OnItemClickXYListener mOnItemClickXYListener;           //
    private OnItemClickSQBDListener mOnItemClickSQBDListener;           //
    private OnItemClickZXListener mOnItemClickZXListener;           //
    private OnItemClickJZJLListener mOnItemClickJZJLListener;           //


    private Context mContext;


    public FragmentHomeWDYSFQYYSAdapter(List<ProvidePatientBindingMyDoctorInfo> list, Context context) {
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvidePatientBindingMyDoctorInfo> list) {
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragmentshouye_fqyys, viewGroup, false);
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
            viewHolder.mUserGoodAtRealm.setText("擅长： "+datas.get(position).getGoodAtRealm());
        //医患绑定状态
//        if ( datas.get(position).getFlagBindingState() == 1) {
//            //   viewHolder.iv_bind.setVisibility(View.VISIBLE);
//            //  viewHolder.tv_jb.setVisibility(View.VISIBLE);
//            viewHolder.tv_zx.setVisibility(View.VISIBLE);
//            //  viewHolder.tv_sqbd.setVisibility(View.GONE);
//            viewHolder.tv_jzjl.setVisibility(View.GONE);
//        }
//        if ( datas.get(position).getFlagBindingState() == 0) {
//            //  viewHolder.iv_bind.setVisibility(View.GONE);
//            //      viewHolder.tv_jb.setVisibility(View.GONE);
//            viewHolder.tv_zx.setVisibility(View.GONE);
//            //    viewHolder.tv_sqbd.setVisibility(View.VISIBLE);
//            viewHolder.tv_jzjl.setVisibility(View.GONE);
//        }
        ProvidePatientBindingMyDoctorInfo providePatientBindingMyDoctorInfo = datas.get(position);
        //就诊记录
        if ( providePatientBindingMyDoctorInfo.getFlagTreatmentStatus() == 1) {
            viewHolder.tv_jzjl.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_jzjl.setVisibility(View.GONE);
        }
      //咨询按钮
     //   private	String flagConsultingStatus;
        if(providePatientBindingMyDoctorInfo.getFlagConsultingStatus()==0){
            viewHolder.tv_zx.setVisibility(View.GONE);
        }else{
            viewHolder.tv_zx.setVisibility(View.VISIBLE);
        }
        //预约按钮
    //    private	String flagAppointmentStatus;
        if(providePatientBindingMyDoctorInfo.getFlagAppointmentStatus()==0){
            viewHolder.tv_reservation.setVisibility(View.GONE);
        }else{
            viewHolder.tv_reservation.setVisibility(View.VISIBLE);
        }


        if (mOnItemClickSQBDListener != null) {
            viewHolder.tv_reservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickSQBDListener.onClick(position);
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

        if (mOnItemClickJZJLListener != null) {
            viewHolder.tv_jzjl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickJZJLListener.onClick(position);
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

        public TextView tv_jb;                          //
        public TextView tv_sqbd;                          //
        public TextView tv_zx;                          //
        public TextView tv_jzjl, tv_reservation;                          //

        public ImageView iv_bind;
        public ImageView iv_jz;

        public ViewHolder(View view) {
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mUserHeard = (ImageView) view.findViewById(R.id.userHead);
            mUserName = (TextView) view.findViewById(R.id.userName);
            mUserTitle = (TextView) view.findViewById(R.id.userTitle);
            mUserHospital = (TextView) view.findViewById(R.id.userHospital);
            mUserGoodAtRealm = (TextView) view.findViewById(R.id.userGoodAtRealm);
            //        tv_jb = (TextView)view.findViewById(R.id.tv_jb);
            //   tv_sqbd = (TextView)view.findViewById(R.id.tv_sqbd);
            tv_zx = (TextView) view.findViewById(R.id.tv_zx);
            tv_jzjl = (TextView) view.findViewById(R.id.tv_jzjl);
            //    iv_bind = (ImageView)view.findViewById(R.id.iv_bind);
            //    iv_jz = (ImageView)view.findViewById(R.id.iv_jz);
            tv_reservation = view.findViewById(R.id.tv_reservation);
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

    public void setOnItemClickXYListener(OnItemClickXYListener onItemClickXYListener) {
        this.mOnItemClickXYListener = onItemClickXYListener;
    }

    public void setOnItemClickZXListener(OnItemClickZXListener onItemClickZXListener) {
        this.mOnItemClickZXListener = onItemClickZXListener;
    }


    public interface OnItemClickSQBDListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickSQBDListener(OnItemClickSQBDListener onItemClickSQBDListener) {
        this.mOnItemClickSQBDListener = onItemClickSQBDListener;
    }

    public interface OnItemClickJZJLListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickJZJLListener(OnItemClickJZJLListener onItemClickJZJLListener) {
        this.mOnItemClickJZJLListener = onItemClickJZJLListener;
    }

}