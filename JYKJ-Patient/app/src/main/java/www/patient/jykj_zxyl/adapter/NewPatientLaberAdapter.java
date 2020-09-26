package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import entity.mySelf.ProvidePatientLabel;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.RedeemRecordActivity;
import www.patient.jykj_zxyl.base.base_utils.DateUtils;
import www.patient.jykj_zxyl.util.Util;

import java.util.ArrayList;
import java.util.List;

public class NewPatientLaberAdapter extends RecyclerView.Adapter<NewPatientLaberAdapter.ViewHolder> {
    public List<ProvidePatientLabel> datas = new ArrayList<>();
    private RedeemRecordActivity mActivity;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;


    public NewPatientLaberAdapter(List<ProvidePatientLabel> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvidePatientLabel> list){
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_patientlaber_patientlabers,viewGroup,false);
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
        ProvidePatientLabel providePatientLabelBean = datas.get(position);
        if(providePatientLabelBean!=null){
            viewHolder.tv_name.setText(providePatientLabelBean.getCreateMan());
            String dates = DateUtils.fomrDateSeflFormat(providePatientLabelBean.getCreateDate(),"yyyy-MM-dd HH:mm:ss");
            viewHolder.tv_time.setText(dates);
            viewHolder.tv_status.setText(providePatientLabelBean.getUserLabelSecondName());
            Integer flagUseState = providePatientLabelBean.getFlagUseState();
            if(flagUseState==0){
                viewHolder.iv_stamp_icon.setImageResource(R.mipmap.ls);
            }else if(flagUseState==1){
                viewHolder.iv_stamp_icon.setImageResource(R.mipmap.dq);
            }

        }
    }
    //获取数据的数量
    @Override
    public int getItemCount(){
        if (datas == null )
            return 0;
        return datas.size();
    }




    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_status;
        private ImageView iv_stamp_icon;
        public ViewHolder(View view){
            super(view);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_status=itemView.findViewById(R.id.tv_status);
            iv_stamp_icon=itemView.findViewById(R.id.iv_stamp_icon);
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
