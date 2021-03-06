package www.patient.jykj_zxyl.myappointment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import entity.ProvideViewMyDoctorSigning;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.MyReservationListBean;
import www.patient.jykj_zxyl.util.DateUtils;

/**
 * 已完成  列表
 */
public class Fragment_CompletedAdapter extends RecyclerView.Adapter<Fragment_CompletedAdapter.ViewHolder> {
    public List<MyReservationListBean> datas = new ArrayList<>();
    private OnItemClickXYListener mOnItemClickXYListener;           //
    private OnItemClickZXListener mOnItemClickZXListener;           //
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private OnItemMessageClickListener mOnItemMessageClickListener;

    public Fragment_CompletedAdapter(List<MyReservationListBean> list, Context context) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragmentcompleted, viewGroup, false);
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
        String reserveStatus = datas.get(position).getReserveStatus();
        if (!TextUtils.isEmpty(reserveStatus)) {
            if (reserveStatus.equals("30")) {
                viewHolder.iv_stamp_icon.setVisibility(View.VISIBLE);
                viewHolder.iv_failure_icon.setVisibility(View.GONE);
                viewHolder.iv_cancel_icon.setVisibility(View.GONE);
            } else if (reserveStatus.equals("110")) {
                viewHolder.iv_stamp_icon.setVisibility(View.GONE);
                viewHolder.iv_failure_icon.setVisibility(View.VISIBLE);
                viewHolder.iv_cancel_icon.setVisibility(View.GONE);
            } else if (reserveStatus.equals("130") || reserveStatus.equals("140")) {
                viewHolder.iv_stamp_icon.setVisibility(View.GONE);
                viewHolder.iv_failure_icon.setVisibility(View.GONE);
                viewHolder.iv_cancel_icon.setVisibility(View.VISIBLE);
            }
        }
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
        viewHolder.completed_appointment.setText("预估时间:" + s);

        viewHolder.completed_project.setText("预约项目:" + datas.get(position).getReserveProjectName());

        //查看病历
        if (mOnItemClickXYListener != null) {
            viewHolder.completed_medicalrecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickXYListener.onClick(position);
                }
            });
        }
        //处方单
        if (mOnItemClickZXListener != null) {
            viewHolder.completed_prescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickZXListener.onClick(position);
                }
            });
        }
        //检查单
        if (mOnItemClickListener != null) {
            viewHolder.completed_checklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
        //诊后留言
        if (mOnItemMessageClickListener != null) {
            viewHolder.completed_Message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemMessageClickListener.onClick(position);
                }
            });
        }


    }

    //获取数据的数量
    @Override
    public int getItemCount() {

        return datas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public ImageView mUserHeard;                         //用户头像
        public TextView mUserName, completed_Message;                          //用户名
        public TextView mUserTitle, completed_appointment, completed_project, completed_front, completed_prescription, completed_checklist, completed_medicalrecord, hospital;
        public ImageView iv_stamp_icon, iv_failure_icon, iv_cancel_icon;

        public ViewHolder(View view) {
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mUserHeard = (ImageView) view.findViewById(R.id.userHead);
            mUserName = (TextView) view.findViewById(R.id.userName);
            mUserTitle = (TextView) view.findViewById(R.id.userTitle);
            completed_appointment = (TextView) view.findViewById(R.id.completed_appointment);
            completed_project = (TextView) view.findViewById(R.id.completed_project);
            completed_prescription = (TextView) view.findViewById(R.id.completed_prescription);
            completed_checklist = (TextView) view.findViewById(R.id.completed_checklist);
            completed_medicalrecord = (TextView) view.findViewById(R.id.completed_medicalrecord);
            hospital = (TextView) view.findViewById(R.id.hospital);
            iv_stamp_icon = view.findViewById(R.id.iv_stamp_icon);
            iv_failure_icon = view.findViewById(R.id.iv_failure_icon);
            iv_cancel_icon = view.findViewById(R.id.iv_cancel_icon);
            completed_Message = view.findViewById(R.id.completed_Message);
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

    //诊后留言
    public interface OnItemMessageClickListener {
        void onClick(int position);
    }

    public void setOnItemMessageClickListener(OnItemMessageClickListener onItemMessageClickListener) {
        this.mOnItemMessageClickListener = onItemMessageClickListener;
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