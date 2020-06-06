package www.patient.jykj_zxyl.adapter.patient.fragmentShouYe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.ProvideDoctorSetSchedulingInfoGroupDate;
import www.patient.jykj_zxyl.R;

/**
 * 医生排班 == >日期
 */
public class DoctorPBRQRecycleAdapter extends RecyclerView.Adapter<DoctorPBRQRecycleAdapter.ViewHolder> {
    public          List<ProvideDoctorSetSchedulingInfoGroupDate>            datas = new ArrayList<>();
    private         OnItemClickListener     mOnItemClickListener;           //早上点击事件

    private         Context                 mContext;


    public DoctorPBRQRecycleAdapter(List<ProvideDoctorSetSchedulingInfoGroupDate> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideDoctorSetSchedulingInfoGroupDate> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activitywxzz_pbinfoinfo,viewGroup,false);
                ViewHolder vh = new ViewHolder(view);
                return vh;
       }
        //将数据与界面进行绑定的操作

    /**
     * 如果是第一条数据，那么他肯定是该组的第一个用户，所以显示组别
     * 如果该用户是该组的第一个用户，那么就显示组别
     * 否则不再显示
     *
     *
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.mDate.setText(datas.get(position).getWorkDateName());
            if (datas.get(position).isChoice())
            {
                viewHolder.mDate.setBackgroundResource(R.mipmap.yspb_choice);
                viewHolder.mDate.setTextColor(mContext.getResources().getColor(R.color.writeColor));
            }
            else
            {
                viewHolder.mDate.setBackgroundResource(R.mipmap.yspb_nochoice);
                viewHolder.mDate.setTextColor(mContext.getResources().getColor(R.color.textColor_vo));
            }
        if (viewHolder.mDate != null)
            viewHolder.mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });
        if (viewHolder.mDate != null)
            viewHolder.mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });
    }
        //获取数据的数量
        @Override
        public int getItemCount(){

        return datas.size();
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            public TextView mDate;                                   //时间

            public ViewHolder(View view){
                super(view);
//                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_surplusDetail_suType);
                mDate = (TextView)view.findViewById(R.id.item_activityPBInfo_date);


            }
        }

    /**
     *
     */
    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onOnItemClickListener ){
        this.mOnItemClickListener=onOnItemClickListener;
    }
}