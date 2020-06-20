package www.patient.jykj_zxyl.adapter.myself;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.mySelf.MyOrderProcess;
import entity.mySelf.usercenter.ProvideViewDoctorReceiveOrderAndTreatmentAndInterrogation;
import org.w3c.dom.Text;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.StrUtils;

/**
 * 我的订单进行中
 */
public class MyOrderOnRecycleAdapter extends RecyclerView.Adapter<MyOrderOnRecycleAdapter.ViewHolder> {
    public List<MyOrderProcess> datas = new ArrayList<>();
    private MyOrderActivity mActivity;
    private         OnItemClickListener     mOnItemClickListener;
    private         Context                 mContext;


    public MyOrderOnRecycleAdapter(List<MyOrderProcess> list, Context context, MyOrderActivity mainActivity){
        mContext = context;
        datas = list;
        mActivity = mainActivity;
    }

    //重新设置数据
    public void setDate(List<MyOrderProcess> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_myorder_on,viewGroup,false);
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
        MyOrderProcess parbean = datas.get(position);
        viewHolder.treatment_type.setText(parbean.getTreatmentTypeName());
        viewHolder.process_state.setText(parbean.getDoctorReceiveShow());
        switch(parbean.getFlagColor()){
            case 0:
                break;
            case 1:
                viewHolder.process_state.setTextColor(mContext.getResources().getColor(R.color.color_red));
                break;
            case 2:
                viewHolder.process_state.setTextColor(mContext.getResources().getColor(R.color.color_orange));
                break;
            case 3:
                viewHolder.process_state.setTextColor(mContext.getResources().getColor(R.color.color_yellow));
                break;
            case 4:
                viewHolder.process_state.setTextColor(mContext.getResources().getColor(R.color.color_blue));
                break;
            case 5:
                viewHolder.process_state.setTextColor(mContext.getResources().getColor(R.color.color_green));
                break;
        }
        if(null!=parbean.getOrderDate()) {
            viewHolder.order_date.setText(DateUtils.fomrDateSeflFormat(parbean.getOrderDate(), "yyyy-MM-dd HH:mm"));
        }
        viewHolder.advise_doctor.setText(parbean.getDoctorName());
        switch (parbean.getTreatmentType()){
            case 1:
                viewHolder.treat_style.setText(mContext.getResources().getString(R.string.service_start_time));
                if(null!=parbean.getTreatmentDateStart()) {
                    viewHolder.treat_style_tool.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDateStart(), "yyyy-MM-dd HH:mm"));
                }
                viewHolder.service_time_title.setText(mContext.getResources().getString(R.string.service_deadline_time));
                if(null!=parbean.getTreatmentDateEnd()){
                    viewHolder.service_time.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDateEnd(), "yyyy-MM-dd HH:mm"));
                }
                break;
            case 2:
                viewHolder.treat_style.setText(mContext.getResources().getString(R.string.hold_on));
                viewHolder.treat_style_tool.setText(StrUtils.defaultStr(parbean.getTreatmentLinkPhone()));
                viewHolder.service_time_title.setText(mContext.getResources().getString(R.string.preserve_service_time));
                if(null!=parbean.getTreatmentDate()){
                    viewHolder.service_time.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDate(), "yyyy-MM-dd HH:mm"));
                }
                break;
            case 3:
                viewHolder.treat_style.setText(mContext.getResources().getString(R.string.hold_on));
                viewHolder.treat_style_tool.setText(StrUtils.defaultStr(parbean.getTreatmentLinkPhone()));
                viewHolder.service_time_title.setText(mContext.getResources().getString(R.string.preserve_service_time));
                if(null!=parbean.getTreatmentDate()){
                    viewHolder.service_time.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDate(), "yyyy-MM-dd HH:mm"));
                }
                break;
            case 4:
                viewHolder.treat_style.setText(mContext.getResources().getString(R.string.preserve_service_time_zone));
                viewHolder.treat_style_tool.setText(StrUtils.defaultStr(parbean.getTreatmentTimeSlotName()));
                viewHolder.service_time_title.setText(mContext.getResources().getString(R.string.preserve_service_time));
                if(null!=parbean.getTreatmentDate()){
                    viewHolder.service_time.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDate(), "yyyy-MM-dd HH:mm"));
                }
                break;
            case 5:
                viewHolder.treat_style.setText(mContext.getResources().getString(R.string.service_start_time));
                if(null!=parbean.getTreatmentDateStart()) {
                    viewHolder.treat_style_tool.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDateStart(), "yyyy-MM-dd HH:mm"));
                }
                viewHolder.service_time_title.setText(mContext.getResources().getString(R.string.service_deadline_time));
                if(null!=parbean.getTreatmentDateEnd()){
                    viewHolder.service_time.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDateEnd(), "yyyy-MM-dd HH:mm"));
                }
                break;
            case 0:
                viewHolder.treat_style.setText(mContext.getResources().getString(R.string.service_start_time));
                if(null!=parbean.getTreatmentDateStart()) {
                    viewHolder.treat_style_tool.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDateStart(), "yyyy-MM-dd HH:mm"));
                }
                viewHolder.service_time_title.setText(mContext.getResources().getString(R.string.service_deadline_time));
                if(null!=parbean.getTreatmentDateEnd()){
                    viewHolder.service_time.setText(DateUtils.fomrDateSeflFormat(parbean.getTreatmentDateEnd(), "yyyy-MM-dd HH:mm"));
                }
                break;
        }
        if (mOnItemClickListener != null)
        {
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
            viewHolder.back_btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
            viewHolder.mClickLinearLayout.setTag(parbean);
        }


    }
        //获取数据的数量
        @Override
        public int getItemCount(){

        return datas.size();
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            private TextView treatment_type;
            private TextView process_state;
            private TextView order_date;
            private TextView advise_doctor;
            public TextView treat_style;
            public TextView treat_style_tool;
            public TextView service_time_title;
            public TextView service_time;
            public TextView quest_btn;
            public TextView back_btn;

            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_fragmentYLZX_rmjxLayout);
                treatment_type = view.findViewById(R.id.treatment_type);
                process_state = view.findViewById(R.id.process_state);
                order_date = view.findViewById(R.id.order_date);
                advise_doctor = view.findViewById(R.id.advise_doctor);
                treat_style = view.findViewById(R.id.treat_style);
                treat_style_tool = view.findViewById(R.id.treat_style_tool);
                service_time_title = view.findViewById(R.id.service_time_title);
                service_time = view.findViewById(R.id.service_time);
                quest_btn = view.findViewById(R.id.quest_btn);
                back_btn = view.findViewById(R.id.back_btn);

            }
        }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}