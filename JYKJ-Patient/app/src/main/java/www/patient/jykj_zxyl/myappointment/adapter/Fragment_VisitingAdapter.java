package www.patient.jykj_zxyl.myappointment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.ProvideViewMyDoctorSigning;
import www.patient.jykj_zxyl.R;

/**
 * 首页==》我的医生（签约医生）
 */
public class Fragment_VisitingAdapter extends RecyclerView.Adapter<Fragment_VisitingAdapter.ViewHolder> {
    public List<ProvideViewMyDoctorSigning> datas = new ArrayList<>();
    private OnItemClickXYListener mOnItemClickXYListener;           //
    private OnItemClickZXListener mOnItemClickZXListener;           //
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public Fragment_VisitingAdapter(List<ProvideViewMyDoctorSigning> list, Context context) {
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
//        try {
//            int avatarResId = Integer.parseInt(datas.get(position).getUserLogoUrl());
//            Glide.with(mContext).load(avatarResId).into(viewHolder.mUserHeard);
//        } catch (Exception e) {
//            //use default avatar
//            Glide.with(mContext).load(datas.get(position).getUserLogoUrl())
//                    .apply(RequestOptions.placeholderOf(R.mipmap.tx_nh)
//                            .diskCacheStrategy(DiskCacheStrategy.ALL))
//                    .into(viewHolder.mUserHeard);
//        }

        //按钮的改变
//        if(datas==1){
//            viewHolder. visiting_cancelappointment.setText("查看病历");
//            viewHolder. visiting_cancelappointment.setBackgroundResource(R.drawable.bg_view);
//        }else{
//            viewHolder. visiting_cancelappointment.setText("取消预约");
//            viewHolder. visiting_cancelappointment.setBackgroundResource(R.drawable.bg_advisory);
//        }

        //取消预约
        if (mOnItemClickXYListener != null) {
            viewHolder.visiting_cancelappointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickXYListener.onClick(position);
                }
            });
        }

//        if (mOnItemClickZXListener != null) {
//            viewHolder.tv_zx.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mOnItemClickZXListener.onClick(position);
//                }
//            });
//        }
//
//
//        if (mOnItemClickListener != null) {
//            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mOnItemClickListener.onClick(position);
//                }
//            });
//
//            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//
//                @Override
//                public boolean onLongClick(View view) {
//                    mOnItemClickListener.onLongClick(position);
//                    return false;
//                }
//            });
//        }
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
        public TextView mUserTitle,visiting_appointment,visiting_project,visiting_front,visiting_cancelappointment;                          //

        public ViewHolder(View view) {
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mUserHeard = (ImageView) view.findViewById(R.id.userHead);
            mUserName = (TextView) view.findViewById(R.id.userName);
            mUserTitle = (TextView) view.findViewById(R.id.userTitle);
            visiting_appointment = (TextView) view.findViewById(R.id.visiting_appointment);
            visiting_project = (TextView) view.findViewById(R.id.visiting_project);
            visiting_front = (TextView) view.findViewById(R.id.visiting_front);
            visiting_cancelappointment = (TextView) view.findViewById(R.id.visiting_cancelappointment);
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