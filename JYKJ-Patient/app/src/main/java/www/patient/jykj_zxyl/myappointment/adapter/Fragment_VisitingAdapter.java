package www.patient.jykj_zxyl.myappointment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.ProvideViewMyDoctorSigning;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.MyReservationListBean;
import www.patient.jykj_zxyl.util.DateUtils;

import static www.patient.jykj_zxyl.R.drawable.bg_advisory;

/**
 * 首页==》我的医生（签约医生）
 */
public class Fragment_VisitingAdapter extends RecyclerView.Adapter<Fragment_VisitingAdapter.ViewHolder> {
    public List<MyReservationListBean> datas = new ArrayList<>();
    private OnItemClickXYListener mOnItemClickXYListener;           //
    private OnItemClickZXListener mOnItemClickZXListener;
    private OnItemClickDataListener mOnItemClickDataListener;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public Fragment_VisitingAdapter(List<MyReservationListBean> list, Context context) {
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<MyReservationListBean> list) {
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragmentvisiting, viewGroup, false);
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
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        try {
            int avatarResId = Integer.parseInt(datas.get(position).getDoctorLogoUrl());
            Glide.with(mContext).load(avatarResId).into(viewHolder.mUserHeard);
        } catch (Exception e) {
            //use default avatar
            Glide.with(mContext).load(datas.get(position).getDoctorLogoUrl())
                    .apply(RequestOptions.placeholderOf(R.mipmap.tx_nh)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(viewHolder.mUserHeard);
        }
        viewHolder.mUserName.setText(datas.get(position).getMainDoctorName());
        viewHolder.hospital.setText(datas.get(position).getDoctorHospitalName());
        String departmentName = datas.get(position).getDepartmentName();
        String departmentSecondName = datas.get(position).getDepartmentSecondName();
        if (TextUtils.isEmpty(departmentName)) {
            viewHolder.mUserTitle.setText("");
        } else if (!TextUtils.isEmpty(departmentName) && TextUtils.isEmpty(departmentSecondName)) {
            viewHolder.mUserTitle.setText(departmentName);
        } else if (!TextUtils.isEmpty(departmentName) && !TextUtils.isEmpty(departmentSecondName)) {
            viewHolder.mUserTitle.setText(departmentName + departmentSecondName);
        } else if (TextUtils.isEmpty(departmentName) && !TextUtils.isEmpty(departmentSecondName)) {
            viewHolder.mUserTitle.setText(departmentSecondName);
        }
        //时间
        long reserveConfigStart = datas.get(position).getReserveConfigStart();
        String s = DateUtils.stampToDate(reserveConfigStart);
        viewHolder.visiting_appointment.setText("预估时间:" + s);

        viewHolder.visiting_project.setText("预约项目:" + datas.get(position).getReserveProjectName());

        viewHolder.visiting_front.setText(datas.get(position).getViewReserveToDoctorCount()+"");

        String reserveStatus = datas.get(position).getReserveStatus();
        if (!TextUtils.isEmpty(reserveStatus)) {
            if (reserveStatus.equals("10")) {
                viewHolder.visiting_cancelappointment.setText("取消预约");
                viewHolder.visiting_cancelappointment.setTextColor(Color.parseColor("#7A9EFF"));
                viewHolder.visiting_cancelappointment.setBackgroundResource(bg_advisory);
                //取消预约
                if (mOnItemClickXYListener != null) {
                    viewHolder.visiting_cancelappointment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickXYListener.onClick(position);
                        }
                    });
                }
//                viewHolder.visiting_data.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mOnItemClickDataListener.onClick(position);
//                    }
//                });
            } else if (reserveStatus.equals("20") || reserveStatus.equals("30")) {
             //   viewHolder.visiting_data.setVisibility(View.GONE);
                viewHolder.visiting_cancelappointment.setText("查看病历");
                viewHolder.visiting_cancelappointment.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.visiting_cancelappointment.setBackgroundResource(bg_advisory);

                if (mOnItemClickZXListener != null) {
                    viewHolder.visiting_cancelappointment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickZXListener.onClick(position);
                        }
                    });
                }
            }
        }
        if (mOnItemClickListener != null) {
            viewHolder.li_clickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
//
//

//
//            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//
//                @Override
//                public boolean onLongClick(View view) {
//                    mOnItemClickListener.onLongClick(position);
//                    return false;
//                }
//            });
      //  }
//

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
        public TextView mUserTitle, hospital, visiting_appointment, visiting_project, visiting_front, visiting_cancelappointment,visiting_data;                          //
        private LinearLayout li_clickLayout;

        public ViewHolder(View view) {
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mUserHeard = (ImageView) view.findViewById(R.id.userHead);
            mUserName = (TextView) view.findViewById(R.id.userName);
            hospital = (TextView) view.findViewById(R.id.hospital);
            mUserTitle = (TextView) view.findViewById(R.id.userTitle);
            visiting_appointment = (TextView) view.findViewById(R.id.visiting_appointment);
            visiting_project = (TextView) view.findViewById(R.id.visiting_project);
            visiting_front = (TextView) view.findViewById(R.id.visiting_front);
            visiting_cancelappointment = (TextView) view.findViewById(R.id.visiting_cancelappointment);
            li_clickLayout = view.findViewById(R.id.li_clickLayout);
            visiting_data = view.findViewById(R.id.visiting_data);
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

    public interface OnItemClickDataListener {
        void onClick(int position);
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


    public void setmOnItemClickDataListener(OnItemClickDataListener mOnItemClickDataListener) {
        this.mOnItemClickDataListener = mOnItemClickDataListener;
    }


}